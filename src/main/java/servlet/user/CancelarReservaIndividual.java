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
public class CancelarReservaIndividual extends HttpServlet {
	
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
		if (userBean == null || userBean.getCorreo().equals("") || userBean.getAdmin() == true) {
		
			response.sendRedirect("/WebProyectoPW");
			
		//Caso 2: Usuario logueado
		}else{
			
			String reserva = request.getParameter("reserva"); 
			
			List<ReservaInfantilDTO> reservasInfantil = new ArrayList<>();
			List<ReservaFamiliarDTO> reservasFamiliar = new ArrayList<>();
			List<ReservaAdultosDTO> reservasAdultos = new ArrayList<>();
			
			//Caso 2a: Si request esta vacio -> Ir a la vista
			if (reserva == null) {
				
				ReservaInfantilDAO reservaInfantilDAO = new ReservaInfantilDAO(prop);
				ReservaFamiliarDAO reservaFamiliarDAO = new ReservaFamiliarDAO(prop);
				ReservaAdultosDAO reservaAdultosDAO = new ReservaAdultosDAO(prop);

				reservasInfantil = reservaInfantilDAO.consultarReservasInfantilFuturas(userBean.getCorreo());
				reservasFamiliar = reservaFamiliarDAO.consultarReservasFamiliarFuturas(userBean.getCorreo());
				reservasAdultos = reservaAdultosDAO.consultarReservasAdultosFuturas(userBean.getCorreo());
				
				bean.setReservasInfantil(reservasInfantil);
				bean.setReservasFamiliar(reservasFamiliar);
				bean.setReservasAdultos(reservasAdultos);
				
				request.setAttribute("cancelarBean", bean);
				request.setAttribute("nextPage", "/WebProyectoPW/CancelarReservaIndividual");
				request.setAttribute("mensaje", "Aviso: Solo se estan mostrando aquellas reservas que se pueden cancelar");
				dispatcher = request.getRequestDispatcher("/mvc/view/common/ReservasCancelarDisplay.jsp");
				dispatcher.forward(request, response);
				
			//Caso 2b: Request no esta vacio	
			}else {
				reservasInfantil = bean.getReservasInfantil();
				reservasFamiliar = bean.getReservasFamiliar();
				reservasAdultos =  bean.getReservasAdultos();
				 
				ReservaDAO reservaDAO = new ReservaDAO(prop);
				PistaDAO pistaDAO = new PistaDAO(prop);
				KartDAO kartDAO = new KartDAO(prop);
				
				int IdReserva = Integer.parseInt(reserva);
				
				//Para evitar recorrer los otros bucles, cuando una reserva se cancele la variable cambia a true
				boolean done = false;
				
				if (done == false) {
					for (ReservaInfantilDTO reservaInfantil : reservasInfantil) {
						if (reservaInfantil.getIdReserva() == IdReserva) {
							done = reservaDAO.borrarReserva(IdReserva);
							
							pistaDAO.actualizarPista(reservaInfantil.getIdPista(), -reservaInfantil.getParticipantesInfantiles(), 0);
							kartDAO.actualizarEstadoKart(true, Estado.DISPONIBLE, reservaInfantil.getIdPista(), reservaInfantil.getParticipantesInfantiles());
							break;
						}
					}
				}
				
				if (done == false) {
					for (ReservaFamiliarDTO reservaFamiliar : reservasFamiliar) {
						if (reservaFamiliar.getIdReserva() == IdReserva) {
							done = reservaDAO.borrarReserva(IdReserva);
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
							done = reservaDAO.borrarReserva(IdReserva);
					
							pistaDAO.actualizarPista(reservaAdultos.getIdPista(), 0, -reservaAdultos.getParticipantesAdultos());
							kartDAO.actualizarEstadoKart(false, Estado.DISPONIBLE, reservaAdultos.getIdPista(), reservaAdultos.getParticipantesAdultos());
							break;
						}
					}
				}
				
				response.sendRedirect("/WebProyectoPW");
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
