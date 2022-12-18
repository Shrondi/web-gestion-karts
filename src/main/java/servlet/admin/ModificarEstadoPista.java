package servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import business.pista.*;
import data.DAO.PistaDAO;
import display.javabean.asociarBean;
import display.javabean.userBean;

/**
 * Servlet implementation class ModificarEstadoKart
 */

public class ModificarEstadoPista extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*
	 * Las variables de clase, declaradas fuera de los métodos doGet y doPost (por ejemplo, en este caso bean), son
	 * persistentes, es decir, conservan sus valores en posteriores solicitudes al mismo servlet. Esto es así ya que
	 * el ServletContainer sólo crea una instancia del mismo servlet, creando posteriormente un nuevo hilo para
	 * servir cada solicitud.
	 * 
	 * Ref: http://gssi.det.uvigo.es/users/agil/public_html/LRO/jsp.pdf, Pagina: 2
	 */
	
	asociarBean bean = new asociarBean();
       
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
		
		//Caso 1: Usuario no esta logueado o no es administrador-> Volvemos al index
		if ( userBean == null || userBean.getCorreo().equals("") || userBean.getAdmin() == false ) {

			response.sendRedirect("/WebProyectoPW");
			
		//Caso 2: Usuario logueado
		}else{
			PistaDAO pistaDAO = new PistaDAO(prop);
		
			String[] pistasId = request.getParameterValues("pista");
			
			//Caso 2b: El request viene vacio (no se ha seleccionado ninguna pista) -> Vamos al disply
			if (pistasId == null) { 
				
				List<PistaDTO> ListaPistas = pistaDAO.listadoPistas();
				
				//Lo guardamos en el bean para evitar posteriores conexiones repetidas en la BD
				bean.setListadoPistas(ListaPistas);
				
				request.setAttribute("asociarBean", bean);
				dispatcher = request.getRequestDispatcher("/mvc/view/admin/ModificarEstadoPistaDisplay.jsp");
				dispatcher.forward(request, response);
				
			//Caso 2c: El request viene lleno (se ha seleccionado una pista) -> Modificar estado de la pista elegida
			}else{
				
				for(PistaDTO pistaDTO : bean.getListadoPistas()) {
					for (String pista : pistasId) {
						if( pistaDTO.getNombre().contentEquals(pista) == true) {
							
							if (pistaDTO.getEstado()) {
								pistaDAO.modificarEstadoPista(false, pista);
							}else {
								pistaDAO.modificarEstadoPista(true, pista);
							}
						}
					}
				}
				
				request.setAttribute("mensaje", "Las pistas seleccionadas han cambiado de estado");
				dispatcher = request.getRequestDispatcher("/");
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