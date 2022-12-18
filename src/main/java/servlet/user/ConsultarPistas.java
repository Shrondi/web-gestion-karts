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

import business.pista.Dificultad;
import business.pista.PistaDTO;
import display.javabean.userBean;
import data.DAO.PistaDAO;


/**
 * Servlet implementation class ConsultarPistas
 */

public class ConsultarPistas extends HttpServlet {
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
			List<PistaDTO> pistas = new ArrayList<>();
			
			PistaDAO pistaDAO = new PistaDAO(prop);
			
			String tipo_check = request.getParameter("tipo_check");
			String minKarts_check = request.getParameter("minKarts_check");
			
			String tipo = request.getParameter("tipo");
			String minKartsInf = request.getParameter("min_karts_inf");
			String minKartsAdult = request.getParameter("min_karts_adult");
			
				//Caso 2a: Request viene vacio -> Ir al display
				if(tipo_check == null && minKarts_check == null){
					
					dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarPistasDisplay.jsp");
					dispatcher.forward(request, response);
					
				//Caso 2b: Request viene lleno (se ha elegido la opcion de filtrar por numero minimo de karts
				}else{
					
					if (tipo_check != null && minKarts_check == null){
						
						
						Dificultad dificultad = Dificultad.valueOf(tipo.toUpperCase());
						
						 pistas = pistaDAO.consultarByDif(dificultad);
					
					}else if(tipo_check == null && minKarts_check != null) {
						
						int min_karts_inf = Integer.parseInt(minKartsInf);
						int min_karts_adult = Integer.parseInt(minKartsAdult);

						pistas = pistaDAO.consultarByMinKarts(min_karts_inf, min_karts_adult);
						
						
					}else if (tipo_check != null && minKarts_check != null){
						int min_karts_inf = Integer.parseInt(minKartsInf);
						int min_karts_adult = Integer.parseInt(minKartsAdult);

						pistas = pistaDAO.consultarPistas(tipo, min_karts_inf, min_karts_adult);
					}
					
					if (pistas == null) {
						request.setAttribute("mensaje", "No hay pistas disponibles");
						dispatcher = request.getRequestDispatcher("/mvc/view/user/PistasConsultaDisplay.jsp");
						dispatcher.forward(request, response);
						
					}else {
						request.setAttribute("ListaPistas", pistas);
						dispatcher = request.getRequestDispatcher("/mvc/view/user/PistasConsultaDisplay.jsp");
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
