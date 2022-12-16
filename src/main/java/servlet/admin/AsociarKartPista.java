package servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import business.kart.KartDTO;
import business.pista.PistaDTO;
import data.DAO.KartDAO;
import data.DAO.PistaDAO;
import display.javabean.userBean;

/**
 * Servlet implementation class AsociarKartPista
 */

public class AsociarKartPista extends HttpServlet {
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
		if (userBean == null || userBean.getCorreo().equals("") || userBean.getAdmin() == false) {
			
			response.sendRedirect("/WebProyectoPW");
			
		//Caso 2: Usuario logueado
		}else{
			
			String[] ids = request.getParameterValues("ids");
			String pista = request.getParameter("pista");
			
			if (pista == null && ids == null) {
				PistaDAO pistaDAO = new PistaDAO(prop);
				List<PistaDTO> pistas = pistaDAO.consultarByEstado(true);
				request.setAttribute("pistas", pistas);
				
				dispatcher = request.getRequestDispatcher("/mvc/view/admin/PistasDisplay.jsp");
				dispatcher.forward(request, response);
			}else{
				System.out.println("Vengo de la vista relleno" + pista);
				//Caso 3a: El request viene vacio (no se ha elegido ningun kart) -> Ir a la vista
				if (ids == null) {
					
					KartDAO kartDAO = new KartDAO(prop);
					List<KartDTO> karts = kartDAO.consultarKartsDisponibles();
					request.setAttribute("karts", karts);
					
					dispatcher = request.getRequestDispatcher("/mvc/view/admin/KartsDisplay.jsp");
					dispatcher.forward(request, response);
					
				//Caso 3b: El request viene lleno (se han elegido karts)
				}else {
					
					for (String id : ids) {
						System.out.println(id);
					}
					
					System.out.println("Vengo de la otra vista" + pista);
					
					
					
					
				}
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
