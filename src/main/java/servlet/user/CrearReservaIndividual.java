package servlet.user;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import display.javabean.userBean;
import business.kart.Estado;
import business.pista.PistaDTO;
import business.reserva.*;
import data.DAO.KartDAO;
import data.DAO.PistaDAO;
import data.DAO.UsuarioDAO;
import data.DAO.reserva.*;

/**
 * Servlet implementation class CrearReservaIndividual
 */
public class CrearReservaIndividual extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		HttpSession session = request.getSession();
		ServletContext application = session.getServletContext();
		userBean userBean = (userBean) session.getAttribute("userBean");
		
		//Obtenemos el valor del parametro sqlproperties, es decir, la ruta relativa al fichero sql.properties
		String sqlProperties= application.getInitParameter("sqlproperties"); 
		java.io.InputStream myIO = application.getResourceAsStream(sqlProperties);
		java.util.Properties prop = new java.util.Properties();
		prop.load(myIO);
		
		//Caso 1: Usuario no esta logueado -> Volvemos al index
		if (userBean == null || userBean.getCorreo().equals("") || userBean.getAdmin() == true) {
			//dispatcher = request.getRequestDispatcher("/index.jsp");
			//dispatcher.forward(request, response);
			response.sendRedirect("/WebProyectoPW");
			
		//Caso 2: Usuario logueado
		}else{

			String fecha = request.getParameter("fecha");
			String pista = request.getParameter("pista");
			
			//Caso 2a: No hay parametros en el request -> Ir a la vista para crear una reserva individual
			if (fecha == null && pista == null){
				
				dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarReservaIndividualDisplay.jsp");
				dispatcher.forward(request, response);
			
			//Caso 2b: Hay parametros en el request (se ha elegido la pista) -> Realizar la reserva
			}else if(pista != null){
				
				int duracion = (int) request.getSession().getAttribute("duracion");
				int numeroNinios = (int) request.getSession().getAttribute("numeroNinios");
				int numeroAdultos = (int) request.getSession().getAttribute("numeroAdultos");
				String tipoReserva = (String) request.getSession().getAttribute("tipoReserva");
				fecha = (String) request.getSession().getAttribute("fecha");

				UsuarioDAO usuarioDAO = new UsuarioDAO(prop);
				
				//Obtenemos la fecha de inscripcion (fecha de primera reserva) del usuario
				String fechaInscripcion = usuarioDAO.obtenerFechaInscripcion(userBean.getCorreo());
				/*
				 * Calculamos la antiguedad del usuario, el descuento por su antiguedad y el precio final de la reserva
				 */
				
				int antiguedad = 0;
				float precio = 1f;
				float descuento = 0;
				
				//Comprobamos si el valor de la fecha de inscripcion es el por defecto.
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				
				Date currDate = new Date();
				Date date = new Date();
				
				try {
					date = sdf.parse(fechaInscripcion);
				} catch (ParseException e){
					e.printStackTrace();
				}
				
				//Si no lo es, calculamos la antiguedad del usuario
				if (!fechaInscripcion.equals("1/1/1900")) {
					
					long diff = currDate.getTime() - date.getTime();
					long d = (1000l*60*60*24*365);
					long years = Math.round(diff/d);
					
					antiguedad = (int) years;
				}
				
				//Asignamos un descuento del 10% si el usuario tiene una antiguedad mayor a 2 años
				if(antiguedad > 2){
					descuento = 10f;
					precio = 0.9f;
				}
				
				//Calculamos el precio de la reserva en base a su duracion y aplicamos un descuento (si existe)
				if(duracion == 60) {
					precio = 20 * precio;
					
				}else if(duracion == 90) {
					precio = 30 * precio;
					
				}else if(duracion == 120){
					precio = 40 * precio;			
				}
				
				if (tipoReserva.contentEquals("INFANTIL")) {
					ReservaInfantilDAO reserva = new ReservaInfantilDAO(prop);
					ReservaInfantilDTO infantil = new ReservaInfantilDTO();
					
					infantil.setIdUsuario(userBean.getCorreo());
					infantil.setFecha(fecha);
					infantil.setParticipantesInfantiles(numeroNinios);
					infantil.setDuracion(duracion);
					infantil.setDescuento(descuento);
					infantil.setPrecio(precio);
					infantil.setIdPista(pista);
					
					reserva.crearReservaInfantil(infantil);
					
					request.setAttribute("reservaInfantil", infantil);
					
					
				}else if(tipoReserva.contentEquals("FAMILIAR")) {
					ReservaFamiliarDAO reserva = new ReservaFamiliarDAO(prop);
					ReservaFamiliarDTO familiar = new ReservaFamiliarDTO();
					
					familiar.setIdUsuario(userBean.getCorreo());
					familiar.setFecha(fecha);
					familiar.setParticipantesAdultos(numeroAdultos);
					familiar.setParticipantesInfantiles(numeroNinios);
					familiar.setDuracion(duracion);
					familiar.setDescuento(descuento);
					familiar.setPrecio(precio);
					familiar.setIdPista(pista);
					
					reserva.crearReservaFamiliar(familiar);
					
					request.setAttribute("reservaFamiliar", familiar);
					
				}else if(tipoReserva.contentEquals("ADULTOS")) {
					ReservaAdultosDAO reserva = new ReservaAdultosDAO(prop);
					ReservaAdultosDTO adultos = new ReservaAdultosDTO();
					
					adultos.setIdUsuario(userBean.getCorreo());
					adultos.setFecha(fecha);
					adultos.setParticipantesAdultos(numeroAdultos);
					adultos.setDuracion(duracion);
					adultos.setDescuento(descuento);
					adultos.setPrecio(precio);
					adultos.setIdPista(pista);
					
					reserva.crearReservaAdulto(adultos);
					
					request.setAttribute("reservaAdultos", adultos);
				}
				
				
				//Actualizamos el numero de karts disponibles de la pista
				PistaDAO pistaDAO = new PistaDAO(prop);
				pistaDAO.actualizarPista(pista, numeroNinios, numeroAdultos);
				
				//Actualizamos el estado de los karts a reservado por cada tipo
				KartDAO kartDAO = new KartDAO(prop);
				kartDAO.actualizarEstadoKart(true, Estado.RESERVADO, pista, numeroNinios);
				kartDAO.actualizarEstadoKart(false, Estado.RESERVADO, pista, numeroAdultos);
				
				//Borramos los atributos de la sesion correspondientes a esta reserva
				session.removeAttribute("ListaPistas");
				session.removeAttribute("duracion");
				session.removeAttribute("numeroNinios");
				session.removeAttribute("numeroAdultos");
				session.removeAttribute("tipoReserva");
				session.removeAttribute("fecha");
				
				//Mostramos el resumen de la reserva
				dispatcher = request.getRequestDispatcher("/mvc/view/user/ReservaIndividualDisplay.jsp");
				dispatcher.forward(request, response);
				
			//Caso 2c: Hay parametros en el request proveniente de la vista de crear reservas
			}else{
				
				int duracion = Integer.parseInt(request.getParameter("duracion"));
				int numeroNinios = Integer.parseInt(request.getParameter("numeroNinios"));
				int numeroAdultos = Integer.parseInt(request.getParameter("numeroAdultos"));
				String tipoReserva = request.getParameter("tipoReserva");
		
				ReservaDAO reservaDAO = new ReservaDAO(prop);
				
				//Si el usuario elige una fecha en en el rango de la fecha de la ultima reserva + 2 horas, tendra que elegir otra fecha
				// (Asi se evita tener dos reservas que transcurran a la vez para el mismo usuario)
				if (reservaDAO.comprobarReserva(fecha, userBean.getCorreo())) {
					request.setAttribute("mensaje", "Ya tiene una reserva para la fecha dada. Debe haber una diferencia de 2 horas minimo entre la fecha de las reservas");
					
					dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarReservaIndividualDisplay.jsp");
					dispatcher.forward(request, response);
					
				}else{
					PistaDAO pistaDAO = new PistaDAO(prop);
					List<PistaDTO> pistas = new ArrayList<>();
					
					pistas = pistaDAO.consultarPistas(tipoReserva, numeroNinios, numeroAdultos);
					
					//No se puede realizar una reserva ya que no hay pistas disponibles para los datos dados
					if (pistas.isEmpty()) {
						
						request.setAttribute("mensaje", "No hay pistas disponibles con los datos dados. Intentelo de nuevo mas tarde.");
						
						dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarReservaIndividualDisplay.jsp");
						dispatcher.forward(request, response);
						
					}else{
						session.setAttribute("ListaPistas", pistas);
						session.setAttribute("duracion", duracion);
						session.setAttribute("numeroNinios", numeroNinios);
						session.setAttribute("numeroAdultos", numeroAdultos);
						session.setAttribute("tipoReserva", tipoReserva);
						session.setAttribute("fecha", fecha);

						dispatcher = request.getRequestDispatcher("/mvc/view/user/ReservasPistasDisplay.jsp");
						dispatcher.forward(request, response);
					}
				}
						
			}
		}
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}
}
