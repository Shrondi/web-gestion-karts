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

import business.reserva.ReservaAdultosDTO;
import business.reserva.ReservaFamiliarDTO;
import business.reserva.ReservaInfantilDTO;
import business.kart.*;
import data.DAO.reserva.ReservaAdultosDAO;
import data.DAO.reserva.ReservaDAO;
import data.DAO.reserva.ReservaFamiliarDAO;
import data.DAO.reserva.ReservaInfantilDAO;
import data.DAO.KartDAO;
import display.javabean.userBean;

/**
 * Servlet implementation class ModificarEstadoKart
 */

public class ModificarEstadoKart extends HttpServlet {
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
		
		//Caso 1: Usuario no esta logueado o no es administrador-> Volvemos al index
		if ( userBean == null || userBean.getCorreo().equals("") || userBean.getAdmin() == false ) {
			//dispatcher = request.getRequestDispatcher("/index.jsp");
			//dispatcher.forward(request, response);
			response.sendRedirect("/WebProyectoPW");
			
		//Caso 2: Usuario logueado
		}else{
			
			String kart = request.getParameter("kart");
			String estado = request.getParameter("estado");
			
			KartDAO kartdao = new KartDAO(prop);
			
			if (kart == null && estado == null) { //Nos vamos a la vista para seleccionar el kart y el estado
				
				List<KartDTO> ListaKarts = kartdao.listadoKarts();
				
				request.setAttribute("ListaKarts", ListaKarts);
				
				dispatcher = request.getRequestDispatcher("/mvc/view/admin/ModificarEstadoKartDisplay.jsp");
				dispatcher.forward(request, response);
				
			}
			else{ //Se modifica el estado del kart seleccionado
				
				int id_kart = Integer.parseInt(kart);
				
				kartdao.modificarEstadoKart(estado, id_kart);
				
				//request.setAttribute("mensaje", "El estado del kart " + kart + " ahora es " + estado);
				//dispatcher = request.getRequestDispatcher("/mvc/view/admin/ModificarEstadoKartDisplay.jsp");
				//dispatcher.forward(request, response);
				response.sendRedirect("/WebProyectoPW");
				
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
