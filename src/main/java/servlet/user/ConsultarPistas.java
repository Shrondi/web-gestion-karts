package servlet.user;

import java.io.IOException;

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
			PistaDAO pistaDAO = new PistaDAO(prop);
			
			String filtro = request.getParameter("filtro");
			String tipo = request.getParameter("tipo");
			String minKartsInf = request.getParameter("min_karts_inf");
			String minKartsAdult = request.getParameter("min_karts_adult");
			
				//Caso 2a: Request viene vacio -> Ir al display
				if(filtro == null){
					
					dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarPistasDisplay.jsp");
					dispatcher.forward(request, response);
					
				//Caso 2b: Request viene lleno (se ha elegido la opcion de filtrar por numero minimo de karts
				}else if (filtro.contentEquals("tipo")){
					
					Dificultad dificultad = Dificultad.valueOf(tipo.toUpperCase());
					
					List<PistaDTO> pistas = pistaDAO.consultarByDif(dificultad);
					
					request.setAttribute("ListaPistas", pistas);
					
					request.setAttribute("nextPage", "/WebProyectoPW/ConsultarPistas");
					dispatcher = request.getRequestDispatcher("/mvc/view/user/PistasDisplay.jsp");
					dispatcher.forward(request, response);
					
				}else if(filtro.contentEquals("minKarts")) {
					
					int min_karts_inf = Integer.parseInt(minKartsInf);
					int min_karts_adult = Integer.parseInt(minKartsAdult);

					List<PistaDTO> pistas = pistaDAO.consultarByMinKarts(min_karts_inf, min_karts_adult);
					
					request.setAttribute("ListaPistas", pistas);
					
					request.setAttribute("nextPage", "/WebProyectoPW/ConsultarPistas");
					dispatcher = request.getRequestDispatcher("/mvc/view/user/PistasDisplay.jsp");
					dispatcher.forward(request, response);
					
				}else{
					int min_karts_inf = Integer.parseInt(minKartsInf);
					int min_karts_adult = Integer.parseInt(minKartsAdult);

					List<PistaDTO> pistas = pistaDAO.consultarPistas(tipo, min_karts_inf, min_karts_adult);
					
					request.setAttribute("ListaPistas", pistas);
					
					request.setAttribute("nextPage", "/WebProyectoPW/ConsultarPistas");
					dispatcher = request.getRequestDispatcher("/mvc/view/user/PistasDisplay.jsp");
					dispatcher.forward(request, response);
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
