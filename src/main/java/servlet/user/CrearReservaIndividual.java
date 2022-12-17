package servlet.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import display.javabean.reservaBean;
import display.javabean.userBean;
import business.kart.Estado;
import business.pista.PistaDTO;
import business.reserva.*;
import data.DAO.KartDAO;
import data.DAO.PistaDAO;
import data.DAO.reserva.*;

/**
 * Servlet implementation class CrearReservaIndividual
 */
public class CrearReservaIndividual extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Las variables de clase, declaradas fuera de los métodos doGet y doPost (por ejemplo, en este caso bean), son
	 * persistentes, es decir, conservan sus valores en posteriores solicitudes al mismo servlet. Esto es así ya que
	 * el ServletContainer sólo crea una instancia del mismo servlet, creando posteriormente un nuevo hilo para
	 * servir cada solicitud.
	 * 
	 * Ref: http://gssi.det.uvigo.es/users/agil/public_html/LRO/jsp.pdf, Pagina: 2
	 */
	
	reservaBean bean = new reservaBean();
       
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
			
			response.sendRedirect("/WebProyectoPW");
			
		//Caso 2: Usuario logueado
		}else{

			String fechaReserva = request.getParameter("fecha");
			String pista = request.getParameter("pista");
			
			//Caso 3a: No hay parametros en el request -> Ir a la vista para crear una reserva individual
			if (fechaReserva == null && pista == null){
				
				dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarReservaIndividualDisplay.jsp");
				dispatcher.forward(request, response);
			
			//Caso 3b: Hay parametros en el request proveniente de la vista de crear reservas
			}else if (fechaReserva != null && pista == null){
				
					//Obtenemos los parametros restantes del request
					int duracion = Integer.parseInt(request.getParameter("duracion"));
					String tipoReserva = request.getParameter("tipoReserva");
					int numeroNinios = 0;
					int numeroAdultos = 0;
					
					//Dependiento del tipo de reserva, el valor del numero de niños o adultos sera 0 o se parseara
					
					if (tipoReserva.contentEquals("FAMILIAR") || tipoReserva.contentEquals("ADULTOS")) {
						numeroAdultos = Integer.parseInt(request.getParameter("numeroAdultos"));
					}
					
					if (tipoReserva.contentEquals("FAMILIAR") || tipoReserva.contentEquals("INFANTILES")) {
						numeroNinios = Integer.parseInt(request.getParameter("numeroNinios"));
					}
					
					ReservaDAO reservaDAO = new ReservaDAO(prop);
					
					//Si el usuario elige una fecha en en el rango de la fecha de la ultima reserva + 2 horas, tendra que elegir otra fecha
					// (Asi se evita tener dos reservas que transcurran a la vez para el mismo usuario)
					// Se ha elegido un franja de 2 horas por comodidad ya que la maxima duracion de una reserva son 2 horas
					if (reservaDAO.comprobarReserva(fechaReserva, userBean.getCorreo())) {
						request.setAttribute("mensaje", "Ya tiene una reserva para la fecha dada. Debe haber una diferencia de 2 horas minimo entre la fecha de las reservas");
						
						dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarReservaIndividualDisplay.jsp");
						dispatcher.forward(request, response);
					
					//Se puede realizar la reserva
					}else{
						PistaDAO pistaDAO = new PistaDAO(prop);
						
						List<PistaDTO> pistas = pistaDAO.consultarPistas(tipoReserva, numeroNinios, numeroAdultos);
						
						//No hay pistas disponibles para los datos dados -> Volver al display a pedir nuevos datos para la reserva
						if (pistas.isEmpty()) {
							
							request.setAttribute("mensaje", "No hay pistas disponibles con los datos dados. Intentelo de nuevo mas tarde.");
							dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarReservaIndividualDisplay.jsp");
							dispatcher.forward(request, response);
						
						//Hay pistas disponibles para los datos dados -> Ir al display a elegir una pista
						}else{
							bean.setFecha(fechaReserva);
							bean.setDuracion(duracion);
							bean.setTipoReserva(tipoReserva);
							bean.setNumeroNinios(numeroNinios);
							bean.setNumeroAdultos(numeroAdultos);
							
							request.setAttribute("ListaPistas", pistas);
							request.setAttribute("nextPage", "/WebProyectoPW/ReservaIndividual");
							dispatcher = request.getRequestDispatcher("/mvc/view/user/PistasDisplay.jsp");
							dispatcher.forward(request, response);
						}
					}
				
				//Caso 3c: Hay parametros en el request (se ha elegido la pista) -> Realizar la reserva
				}else if(pista != null){
				
				int duracion = bean.getDuracion();
				int numeroNinios = bean.getNumeroNinios();
				int numeroAdultos = bean.getNumeroAdultos();
				String tipoReserva = bean.getTipoReserva();
				String fecha = bean.getFecha();
				
				 //Calculamos el descuento por su antiguedad y el precio final de la reserva
				 
				float precio = 1f;
				float descuento = 0;
				
				//Asignamos un descuento del 10% si el usuario tiene una antiguedad mayor a 2 años (24 meses)
				if(userBean.getAntiguedad() > 24){
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
				request.setAttribute("modalidad", "Individual");
				dispatcher = request.getRequestDispatcher("/mvc/view/user/ReservaIndividualDisplay.jsp");
				dispatcher.forward(request, response);
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
