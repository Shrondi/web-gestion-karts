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
			//dispatcher = request.getRequestDispatcher("/index.jsp");
			//dispatcher.forward(request, response);
			response.sendRedirect("/WebProyectoPW");
			
		//Caso 2: Usuario logueado
		}else{
			String estado = request.getParameter("estado");
			String tipo = request.getParameter("tipo");
			int min_karts_inf = Integer.parseInt(request.getParameter("min_karts_inf"));
			int min_karts_adult = Integer.parseInt(request.getParameter("min_karts_adult"));
			
			//Solo se permite consultar pistas disponibles
			if(estado.toUpperCase().contains("DISPONIBLE"))
			{
				//Consulta por tipo
				if(tipo == null)
				{
					dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarPistasDisplay.jsp");
					dispatcher.forward(request, response);
				}
				else
				{
					PistaDAO pistatipoDAO = new PistaDAO(prop);
					List<PistaDTO> pistastipo = pistatipoDAO.consultarByDif(tipo);
					
					request.setAttribute("pistastipo", pistastipo);
					request.setAttribute("tipo", tipo);
					
					dispatcher = request.getRequestDispatcher("/mvc/view/user/PistasTipoDisplay.jsp");
					dispatcher.forward(request, response);
				}
				
				//Consulta por min de karts
				if(min_karts_inf == 0 && min_karts_adult == 0)
				{
					dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarPistasDisplay.jsp");
					dispatcher.forward(request, response);
				}
				else
				{
					PistaDAO pista_min_kartsDAO = new PistaDAO(prop);
					List<PistaDTO> pista_min_karts = pista_min_kartsDAO.consultarByMinKarts(min_karts_inf,min_karts_adult);
					
					request.setAttribute("pista_min_karts", pista_min_karts);
					request.setAttribute("min_karts_inf", min_karts_inf);
					request.setAttribute("min_karts_adult", min_karts_adult);
					
					dispatcher = request.getRequestDispatcher("/mvc/view/user/PistasMinKartsDisplay.jsp");
					dispatcher.forward(request, response);
				}
				
				//Consulta por tipo y min de karts
				if(tipo == null && min_karts_inf == 0 && min_karts_adult == 0)
				{
					dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarPistasDisplay.jsp");
					dispatcher.forward(request, response);
				}
				else
				{
					PistaDAO pista_tipo_kartsDAO = new PistaDAO(prop);
					List<PistaDTO> pista_tipo_karts = pista_tipo_kartsDAO.consultarPistas(tipo,min_karts_inf,min_karts_adult);
					
					request.setAttribute("pista_tipo_karts", pista_tipo_karts);
					request.setAttribute("tipo", tipo);
					request.setAttribute("min_karts_inf", min_karts_inf);
					request.setAttribute("min_karts_adult", min_karts_adult);
					
					dispatcher = request.getRequestDispatcher("/mvc/view/user/PistasTipoKartsDisplay.jsp");
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
