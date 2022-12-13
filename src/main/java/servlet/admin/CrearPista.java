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

import data.DAO.PistaDAO;
import business.pista.*;
import display.javabean.userBean;

/**
 * Servlet implementation class CrearPista
 */
public class CrearPista extends HttpServlet {
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
		
		//Caso 2: Usuario logueado, creamos la pista
		else
		{
			String nombre_pista = request.getParameter("nombre");
			String estado = request.getParameter("estado");
			String dificultad = request.getParameter("dificultad");
			int max_karts = Integer.parseInt(request.getParameter("max_karts"));
			
			//Caso 2a: Si no hay parametros en el request -> A la vista
			if (nombre_pista != null) {
				
				//AÑADIR LA RUTA DE DONDE SE ENCUENTRA LA VISTA DE PEDIR DATOS DE LA PISTA: /mvc/display/admin/fichero.jsp
				dispatcher = request.getRequestDispatcher("/mvc/display/admin/");
				dispatcher.forward(request, response);
			
			//Caso 2b: Hay parametros en el request
			}else {
				PistaDAO pistaDAO = new PistaDAO(prop);
				
				//Si el nombre de la pista ya existe -> Volver al display
				if (pistaDAO.consultarPistaExiste(nombre_pista)) {
					
					request.setAttribute("mensaje", "El nombre ya esta siendo usando, por favor escriba otro nombre");
					dispatcher = request.getRequestDispatcher("/mvc/display/admin/");
					dispatcher.forward(request, response);
					
				//Si el nombre no existe
				}else {
					
					PistaDTO pista = new PistaDTO();
					
					pista.setNombre(nombre_pista);
					pista.setMaxAmmount(max_karts);
					pista.setDificulty(Dificultad.valueOf(dificultad.toUpperCase()));
					
					pistaDAO.CrearPista(pista);
					
					request.setAttribute("mensaje", "Se ha creado la pista correctamente");
					dispatcher = request.getRequestDispatcher("/WebProyectoPW");
					dispatcher.forward(request, response);
				}
					
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
