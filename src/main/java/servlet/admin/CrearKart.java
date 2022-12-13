package servlet.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DAO.KartDAO;
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
		if (userBean == null || userBean.getCorreo().equals(""))
		{
			//dispatcher = request.getRequestDispatcher("/index.jsp");
			//dispatcher.forward(request, response);
			response.sendRedirect("/WebProyectoPW");
		}
		
		//Caso 2: Usuario logueado y creamos kart
		else
		{
			String tipo = (String) request.getSession().getAttribute("tipo");
			String estado = (String) request.getSession().getAttribute("estado");
			
			KartDAO kartDAO = new KartDAO(prop);
			
			//REVISAR SI HACE FALTA ESTOS CONTROLES O NO
			boolean estado_control = false;
			boolean tipo_control = false;
			
			//Control de estado
			if(estado.toUpperCase().contains("DISPONIBLE") || estado.toUpperCase().contains("RESERVADO") || estado.toUpperCase().contains("MANTENIMIENTO")) {
				estado_control = true;
			}
			//Control de tipo
			if(tipo.toUpperCase().contains("INFANTIL") || tipo.toUpperCase().contains("ADULTOS")) {
				tipo_control = true;
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
