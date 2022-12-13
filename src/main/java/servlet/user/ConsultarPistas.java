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
		if (userBean == null || userBean.getCorreo().equals("")) {
			//dispatcher = request.getRequestDispatcher("/index.jsp");
			//dispatcher.forward(request, response);
			response.sendRedirect("/WebProyectoPW");
			
		//Caso 2: Usuario logueado
		}else{
			String estado = request.getParameter("estado");
			String tipo = request.getParameter("tipo");
			String fechaInicio = request.getParameter("fechaInicio");
			String min_karts = request.getParameter("min_karts");
			
			//Solo se permite consultar pistas disponibles
			if(estado.toUpperCase().contains("DISPONIBLE"))
			{
				//Consulta por tipo
				if(tipo == null)
				{
					dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarPistasTipoDisplay.jsp");
					dispatcher.forward(request, response);
				}
				else
				{
					PistaDAO pistatipoDAO = new PistaDAO(prop);
					List<PistaDTO> pistastipo = pistatipoDAO.consultarByDif(tipo,userBean.getCorreo());
					
					request.setAttribute("pistastipo", pistastipo);
					request.setAttribute("tipo", tipo);
					
					dispatcher = request.getRequestDispatcher("/mvc/view/user/PistasTipoDisplay.jsp");
					dispatcher.forward(request, response);
				}
				
				//Consulta por fecha
				if(fechaInicio == null)
				{
					dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarPistasFechaDisplay.jsp");
					dispatcher.forward(request, response);
				}
				else
				{
					PistaDAO pistafechaDAO = new PistaDAO(prop);
					List<PistaDTO> pistasfecha = pistafechaDAO.consultarByFecha(fechaInicio,userBean.getCorreo());
					
					request.setAttribute("pistasfecha", pistasfecha);
					request.setAttribute("fechaInicio", fechaInicio);
					
					dispatcher = request.getRequestDispatcher("/mvc/view/user/PistasFechaDisplay.jsp");
					dispatcher.forward(request, response);
				}
				
				//Consulta por min de karts
				if(min_karts == null)
				{
					dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarPistasMinKartsDisplay.jsp");
					dispatcher.forward(request, response);
				}
				else
				{
					PistaDAO pista_min_kartsDAO = new PistaDAO(prop);
					List<PistaDTO> pista_min_karts = pista_min_kartsDAO.consultarByMinKarts(min_karts,userBean.getCorreo());
					
					request.setAttribute("pista_min_karts", pista_min_karts);
					request.setAttribute("min_karts", min_karts);
					
					dispatcher = request.getRequestDispatcher("/mvc/view/user/PistasMinKartsDisplay.jsp");
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
