package servlet.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import display.javabean.userBean;
import data.DAO.reserva.*;

/**
 * Servlet implementation class CrearReservaIndividual
 */
public class CrearBono extends HttpServlet {
	
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

			String tipoBono = request.getParameter("tipoBono");
			
			//Caso 2a: No hay parametros en el request -> Ir a la vista para crear una reserva individual
			if (tipoBono == null){
				
				dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarBonoDisplay.jsp");
				dispatcher.forward(request, response);
			
			//Caso 2b: Hay parametros en el request
			}else{
		
				ReservaDAO reservaDAO = new ReservaDAO(prop);
				
				reservaDAO.insertarBono(userBean.getCorreo(), tipoBono);

				request.setAttribute("mensaje", "Puede elegir el bono reci&eacute;n creado si quiere asignarle reservas (puede hacerlo en otro momento): ");
				dispatcher = request.getRequestDispatcher("/ReservaBono");
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
