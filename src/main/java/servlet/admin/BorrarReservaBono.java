package servlet.admin;

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

import display.javabean.cancelarBean;
import display.javabean.userBean;
import business.kart.Estado;
import business.reserva.*;
import data.DAO.KartDAO;
import data.DAO.PistaDAO;
import data.DAO.reserva.*;

/**
 * Servlet implementation class CrearReservaIndividual
 */
public class BorrarReservaBono extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	/*
	 * Las variables de clase, declaradas fuera de los métodos doGet y doPost (por ejemplo, en este caso bean), son
	 * persistentes, es decir, conservan sus valores en posteriores solicitudes al mismo servlet. Esto es así ya que
	 * el ServletContainer sólo crea una instancia del mismo servlet, creando posteriormente un nuevo hilo para
	 * servir cada solicitud.
	 * 
	 * Ref: http://gssi.det.uvigo.es/users/agil/public_html/LRO/jsp.pdf, Pagina: 2
	 */
	
	cancelarBean bean = new cancelarBean();
       
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
		if (userBean == null || userBean.getCorreo().equals("") || userBean.getAdmin() == false) {
			
			response.sendRedirect("/WebProyectoPW");
			
		//Caso 2: Usuario logueado
		}else{
			String reserva = request.getParameter("reserva");
			
			String fechaInicio = request.getParameter("fechaInicio");
			String fechaFin = request.getParameter("fechaFin");
			Boolean restriccion = Boolean.valueOf(request.getParameter("restriccion"));
			
			List<ReservaInfantilDTO> reservasInfantil = new ArrayList<>();
			List<ReservaFamiliarDTO> reservasFamiliar = new ArrayList<>();
			List<ReservaAdultosDTO> reservasAdultos = new ArrayList<>();
			
			//Caso 2a: Si request esta vacio -> Ir a la vista
			if (fechaInicio == null && reserva == null) {
				
				dispatcher = request.getRequestDispatcher("/mvc/view/admin/ConsultarBorrarReservasDisplay.jsp");
				dispatcher.forward(request, response);
				
			//Caso 2b: Request no esta vacio (Se ha elegido el rango de fechas)	
			}else {
		
				//Los guardamos en el bean para su uso
				bean.setFechaInicio(fechaInicio);
				bean.setFechaFin(fechaFin);
			
				//Caso 3a: No se ha elegido la reserva a borrar -> Ir al display
				if (reserva == null) {
					ReservaInfantilDAO reservaInfantilDAO = new ReservaInfantilDAO(prop);
					ReservaFamiliarDAO reservaFamiliarDAO = new ReservaFamiliarDAO(prop);
					ReservaAdultosDAO reservaAdultosDAO = new ReservaAdultosDAO(prop);

					//Si se ha activado la restriccion de borrar reservas a menos de 24 horas, llamamos a un DAO u otro
					if (restriccion) {
						reservasInfantil = reservaInfantilDAO.consultarReservasInfantil(fechaInicio, fechaFin);
						reservasFamiliar = reservaFamiliarDAO.consultarReservasFamiliar(fechaInicio, fechaFin);
						reservasAdultos = reservaAdultosDAO.consultarReservasAdultos(fechaInicio, fechaFin);
						
					}else{
						reservasInfantil = reservaInfantilDAO.consultarReservasInfantilRangoFuturas("BONO", fechaInicio, fechaFin);
						reservasFamiliar = reservaFamiliarDAO.consultarReservasFamiliarRangoFuturas("BONO", fechaInicio, fechaFin);
						reservasAdultos = reservaAdultosDAO.consultarReservasAdultosRangoFuturas("BONO", fechaInicio, fechaFin);
					}
					
					if (reservasInfantil.isEmpty() && reservasFamiliar.isEmpty() && reservasAdultos.isEmpty()) {
						request.setAttribute("mensaje", "No hay ninguna reserva para cancelar");
						dispatcher = request.getRequestDispatcher("/");
						dispatcher.forward(request, response);
						
					}else{
						//Guardamos en el bean para usarlo posteriormente y asi se evita hacer conexiones extra al servidor
						bean.setReservasInfantil(reservasInfantil);
						bean.setReservasFamiliar(reservasFamiliar);
						bean.setReservasAdultos(reservasAdultos);
						
						for (ReservaInfantilDTO reservaInfantilDTO : reservasInfantil) {
							System.out.println(reservaInfantilDTO.getIdUsuario());
						}
						
						request.setAttribute("cancelarBean", bean);
						dispatcher = request.getRequestDispatcher("/mvc/view/admin/ConsultarBorrarReservasDisplay.jsp");
						dispatcher.forward(request, response);
					}
					
				//Se ha elegido la reserva a borrar
				}else{
					
					reservasInfantil = bean.getReservasInfantil();
					reservasFamiliar = bean.getReservasFamiliar();
					reservasAdultos = bean.getReservasAdultos();
					
					ReservaDAO reservaDAO = new ReservaDAO(prop);
					PistaDAO pistaDAO = new PistaDAO(prop);
					KartDAO kartDAO = new KartDAO(prop);
					
					//Buscamos la ID de la reserva para borrarla
					int IdReserva = Integer.parseInt(reserva);
					
						for (ReservaInfantilDTO reservaInfantil : reservasInfantil) {
							if (reservaInfantil.getIdReserva() == IdReserva) {
								reservaDAO.borrarReserva(IdReserva);
								
								pistaDAO.actualizarPista(reservaInfantil.getIdPista(), -reservaInfantil.getParticipantesInfantiles(), 0);
								kartDAO.actualizarEstadoKart(true, Estado.DISPONIBLE, reservaInfantil.getIdPista(), reservaInfantil.getParticipantesInfantiles());
								break;
							}
						}

						for (ReservaFamiliarDTO reservaFamiliar : reservasFamiliar) {
							if (reservaFamiliar.getIdReserva() == IdReserva) {
								reservaDAO.borrarReserva(IdReserva);
								pistaDAO.actualizarPista(reservaFamiliar.getIdPista(), -reservaFamiliar.getParticipantesInfantiles(), -reservaFamiliar.getParticipantesAdultos());
								
								kartDAO.actualizarEstadoKart(true, Estado.DISPONIBLE, reservaFamiliar.getIdPista(), reservaFamiliar.getParticipantesInfantiles());
								kartDAO.actualizarEstadoKart(false, Estado.DISPONIBLE, reservaFamiliar.getIdPista(), reservaFamiliar.getParticipantesAdultos());
								break;
							}
						}
					
						for (ReservaAdultosDTO reservaAdultos : reservasAdultos) {
							if (reservaAdultos.getIdReserva() == IdReserva) {
								reservaDAO.borrarReserva(IdReserva);
						
								pistaDAO.actualizarPista(reservaAdultos.getIdPista(), 0, -reservaAdultos.getParticipantesAdultos());
								kartDAO.actualizarEstadoKart(false, Estado.DISPONIBLE, reservaAdultos.getIdPista(), reservaAdultos.getParticipantesAdultos());
								break;
							}
						}
					
					response.sendRedirect("/WebProyectoPW");
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
