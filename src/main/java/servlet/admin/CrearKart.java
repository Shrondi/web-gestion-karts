package servlet.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DAO.KartDAO;
import business.kart.*;
import display.javabean.userBean;

/**
 * Servlet implementation class CrearKart
 */
public class CrearKart extends HttpServlet {
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
		if (userBean == null || userBean.getCorreo().equals("") || userBean.getAdmin() == false)
		{
			//dispatcher = request.getRequestDispatcher("/index.jsp");
			//dispatcher.forward(request, response);
			response.sendRedirect("/WebProyectoPW");
		}
		
		//Caso 2: Usuario logueado, creamos kart
		else
		{
			Boolean tipo = Boolean.parseBoolean(request.getParameter("tipo"));
			String estado = request.getParameter("estado");
			
			//Caso 2a: Si no hay parametros en el request -> A la vista
			if (estado == null) {
				
				//Ruta de donde se encuentra la vista de pedir datos de kart
				dispatcher = request.getRequestDispatcher("/mvc/view/CrearKart.jsp");
				dispatcher.forward(request, response);
			
			//Caso 2b: Hay parametros en el request (viene de la vista)
			}else {
				KartDAO kartDAO = new KartDAO(prop);
				KartDTO kart = new KartDTO();
				
				kart.setStatus(Estado.valueOf(estado.toUpperCase()));
				kart.seType(tipo);
				
				kartDAO.crearKart(kart);
				response.sendRedirect("/WebProyectoPW");
			}
		}	
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
}
