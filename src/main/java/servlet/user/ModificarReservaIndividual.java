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
public class ModificarReservaIndividual extends HttpServlet {
	
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
			response.sendRedirect("/WebProyectoPW");
			
		//Caso 2: Usuario logueado
		}else{
			
			String reservaId = request.getParameter("reserva");
			
			String fecha = request.getParameter("fecha");
			String pista = request.getParameter("pista");
			
				
			List<ReservaInfantilDTO> reservasInfantil = new ArrayList<>();
			List<ReservaFamiliarDTO> reservasFamiliar = new ArrayList<>();
			List<ReservaAdultosDTO> reservasAdultos = new ArrayList<>();
				
			//Caso 2a: Si request esta vacio -> Ir a la vista
			if (reservaId == null && fecha == null && pista == null) {
					
				ReservaInfantilDAO reservaInfantilDAO = new ReservaInfantilDAO(prop);
				ReservaFamiliarDAO reservaFamiliarDAO = new ReservaFamiliarDAO(prop);
				ReservaAdultosDAO reservaAdultosDAO = new ReservaAdultosDAO(prop);

				reservasInfantil = reservaInfantilDAO.consultarReservasInfantilFuturas(userBean.getCorreo());
				reservasFamiliar = reservaFamiliarDAO.consultarReservasFamiliarFuturas(userBean.getCorreo());
				reservasAdultos = reservaAdultosDAO.consultarReservasAdultosFuturas(userBean.getCorreo());
					
				session.setAttribute("reservasInfantil", reservasInfantil);
				session.setAttribute("reservasFamiliar", reservasFamiliar);
				session.setAttribute("reservasAdultos", reservasAdultos);
					
				request.setAttribute("nextPage", "/WebProyectoPW/ModificarReservaIndividual");
				request.setAttribute("mensaje", "Aviso: Solo se estan mostrando aquellas reservas que se pueden modificar");
				dispatcher = request.getRequestDispatcher("/mvc/view/user/ReservasCancelarDisplay.jsp");
				dispatcher.forward(request, response);
				
			}else if (fecha == null && pista == null){
				reservasInfantil = (List<ReservaInfantilDTO>) request.getSession().getAttribute("reservasInfantil");
				reservasFamiliar = (List<ReservaFamiliarDTO>) request.getSession().getAttribute("reservasFamiliar");
				reservasAdultos =  (List<ReservaAdultosDTO>) request.getSession().getAttribute("reservasAdultos");
				 
				ReservaDAO reservaDAO = new ReservaDAO(prop);
				PistaDAO pistaDAO = new PistaDAO(prop);
				KartDAO kartDAO = new KartDAO(prop);
				
				int IdReserva = Integer.parseInt(reservaId);
				
				boolean done = false;
				
				if (done == false) {
					for (ReservaInfantilDTO reservaInfantil : reservasInfantil) {
						if (reservaInfantil.getIdReserva() == IdReserva) {
							
							pistaDAO.actualizarPista(reservaInfantil.getIdPista(), -reservaInfantil.getParticipantesInfantiles(), 0);
							kartDAO.actualizarEstadoKart(true, Estado.DISPONIBLE, reservaInfantil.getIdPista(), reservaInfantil.getParticipantesInfantiles());
							break;
						}
					}
				}
				
				if (done == false) {
					for (ReservaFamiliarDTO reservaFamiliar : reservasFamiliar) {
						if (reservaFamiliar.getIdReserva() == IdReserva) {

							pistaDAO.actualizarPista(reservaFamiliar.getIdPista(), -reservaFamiliar.getParticipantesInfantiles(), -reservaFamiliar.getParticipantesAdultos());
							
							kartDAO.actualizarEstadoKart(true, Estado.DISPONIBLE, reservaFamiliar.getIdPista(), reservaFamiliar.getParticipantesInfantiles());
							kartDAO.actualizarEstadoKart(false, Estado.DISPONIBLE, reservaFamiliar.getIdPista(), reservaFamiliar.getParticipantesAdultos());
							break;
						}
					}
				}
				
				if (done == false) {
					for (ReservaAdultosDTO reservaAdultos : reservasAdultos) {
						if (reservaAdultos.getIdReserva() == IdReserva) {
					
							pistaDAO.actualizarPista(reservaAdultos.getIdPista(), 0, -reservaAdultos.getParticipantesAdultos());
							kartDAO.actualizarEstadoKart(false, Estado.DISPONIBLE, reservaAdultos.getIdPista(), reservaAdultos.getParticipantesAdultos());
							break;
						}
					}
				}
				
				
				//Caso 2a: No hay parametros en el request -> Ir a la vista para modificar los campos
				if (fecha == null && pista == null){
					System.out.println("LLEGUE");
					request.setAttribute("nextPage", "/WebProyectoPW/ModificarReservaIndividual");
					dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarReservaDisplay.jsp");
					dispatcher.forward(request, response);
				
				//Caso 2b: Hay parametros en el request (se ha elegido la pista) -> Realizar la reserva
				}else if(pista != null){
					
					int duracion = (int) request.getSession().getAttribute("duracion");
					int numeroNinios = (int) request.getSession().getAttribute("numeroNinios");
					int numeroAdultos = (int) request.getSession().getAttribute("numeroAdultos");
					String tipoReserva = (String) request.getSession().getAttribute("tipoReserva");
					fecha = (String) request.getSession().getAttribute("fecha");
					
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
						
						reserva.modificarReservaInfantil(infantil);
						
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
						
						reserva.modificarReservaFamiliar(familiar);
						
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
						
						reserva.modificarReservaAdulto(adultos);
						
						request.setAttribute("reservaAdultos", adultos);
					}
					
					//Actualizamos el numero de karts disponibles de la pista
					pistaDAO.actualizarPista(pista, numeroNinios, numeroAdultos);
					
					//Actualizamos el estado de los karts a reservado por cada tipo
					kartDAO.actualizarEstadoKart(true, Estado.RESERVADO, pista, numeroNinios);
					kartDAO.actualizarEstadoKart(false, Estado.RESERVADO, pista, numeroAdultos);
					
					//Borramos los atributos de la sesion correspondientes a esta reserva
					session.removeAttribute("ListaPistas");
					session.removeAttribute("duracion");
					session.removeAttribute("numeroNinios");
					session.removeAttribute("numeroAdultos");
					session.removeAttribute("tipoReserva");
					session.removeAttribute("fecha");
					session.removeAttribute("reservasInfantil");
					session.removeAttribute("reservasFamiliar");
					session.removeAttribute("reservasAdulto");
					
					//Mostramos el resumen de la reserva
					request.setAttribute("modalidad", "Individual");
					request.setAttribute("mensaje", "Se ha modificado correctamente la reserva.");
					dispatcher = request.getRequestDispatcher("/mvc/view/user/ReservaIndividualDisplay.jsp");
					dispatcher.forward(request, response);
					
				//Caso 2c: Hay parametros en el request proveniente de la vista de crear reservas
				}else{
					
					int duracion = Integer.parseInt(request.getParameter("duracion"));
					String tipoReserva = request.getParameter("tipoReserva");
					int numeroNinios = 0;
					int numeroAdultos = 0;
					
					if (tipoReserva.contentEquals("FAMILIAR") || tipoReserva.contentEquals("ADULTOS")) {
						numeroAdultos = Integer.parseInt(request.getParameter("numeroAdultos"));
					}
					
					if (tipoReserva.contentEquals("FAMILIAR") || tipoReserva.contentEquals("INFANTILES")) {
						numeroNinios = Integer.parseInt(request.getParameter("numeroNinios"));
					}
			
					
					//Si el usuario elige una fecha en en el rango de la fecha de la ultima reserva + 2 horas, tendra que elegir otra fecha
					// (Asi se evita tener dos reservas que transcurran a la vez para el mismo usuario)
					if (reservaDAO.comprobarReserva(fecha, userBean.getCorreo())) {
						request.setAttribute("mensaje", "Ya tiene una reserva para la fecha dada. Debe haber una diferencia de 2 horas minimo entre la fecha de las reservas");
						
						request.setAttribute("nextPage", "/WebProyectoPW/ModificarReservaIndividual");
						dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarReservaDisplay.jsp");
						dispatcher.forward(request, response);
						
					}else{
						List<PistaDTO> pistas = new ArrayList<>();
						
						pistas = pistaDAO.consultarPistas(tipoReserva, numeroNinios, numeroAdultos);
						
						//No se puede realizar una reserva ya que no hay pistas disponibles para los datos dados
						if (pistas.isEmpty()) {
							
							request.setAttribute("mensaje", "No hay pistas disponibles con los datos dados. Intentelo de nuevo mas tarde.");
							
							request.setAttribute("nextPage", "/WebProyectoPW/ModificarReservaIndividual");
							dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarReservaDisplay.jsp");
							dispatcher.forward(request, response);
							
						}else{
							session.setAttribute("ListaPistas", pistas);
							session.setAttribute("duracion", duracion);
							session.setAttribute("numeroNinios", numeroNinios);
							session.setAttribute("numeroAdultos", numeroAdultos);
							session.setAttribute("tipoReserva", tipoReserva);
							session.setAttribute("fecha", fecha);

							request.setAttribute("nextPage", "/WebProyectoPW/ModificarReservaIndividual");
							dispatcher = request.getRequestDispatcher("/mvc/view/user/ReservasPistasDisplay.jsp");
							dispatcher.forward(request, response);
						}
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
