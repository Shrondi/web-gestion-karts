package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import display.javabean.userBean;
import business.reserva.*;
import data.DAO.reserva.*;

/**
 * Servlet implementation class CrearReservaIndividual
 */
public class CancelarReservaIndividual extends HttpServlet {
	
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
		if (userBean == null || userBean.getCorreo().equals("")) {
			//dispatcher = request.getRequestDispatcher("/index.jsp");
			//dispatcher.forward(request, response);
			response.sendRedirect("/WebProyectoPW");
			
		//Caso 2: Usuario logueado
		}else{
			
			String reserva = request.getParameter("reserva");
			
			if (reserva == null) {
				
				ReservaInfantilDAO reservaInfantilDAO = new ReservaInfantilDAO(prop);
				ReservaFamiliarDAO reservaFamiliarDAO = new ReservaFamiliarDAO(prop);
				ReservaAdultosDAO reservaAdultosDAO = new ReservaAdultosDAO(prop);

				List<ReservaInfantilDTO> reservasInfantil = reservaInfantilDAO.consultarReservasInfantilFuturas();
				List<ReservaFamiliarDTO> reservasFamiliar = reservaFamiliarDAO.consultarReservasFamiliarFuturas();
				List<ReservaAdultosDTO> reservasAdultos = reservaAdultosDAO.consultarReservasAdultosFuturas();
				
				request.setAttribute("reservasInfantil", reservasInfantil);
				request.setAttribute("reservasFamiliar", reservasFamiliar);
				request.setAttribute("reservasAdultos", reservasAdultos);
				
				dispatcher = request.getRequestDispatcher("/mvc/view/ReservasDisplay.jsp");
				dispatcher.forward(request, response);
				
			}else {
				ReservaDAO reservaDAO = new ReservaDAO(prop);
			
				reservaDAO.borrarReserva(userBean.getCorreo(), Integer.parseInt(reserva));
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
