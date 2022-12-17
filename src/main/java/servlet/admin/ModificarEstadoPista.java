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
import business.pista.*;
import data.DAO.reserva.ReservaAdultosDAO;
import data.DAO.reserva.ReservaDAO;
import data.DAO.reserva.ReservaFamiliarDAO;
import data.DAO.reserva.ReservaInfantilDAO;
import data.DAO.KartDAO;
import data.DAO.PistaDAO;
import display.javabean.userBean;

/**
 * Servlet implementation class ModificarEstadoKart
 */

public class ModificarEstadoPista extends HttpServlet {
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
			
			String pista = (String) request.getParameter("pista");
			
			PistaDAO pistadao = new PistaDAO(prop);
			List<PistaDTO> ListaPistas = pistadao.listadoPistas();
			
			if (pista == null) { //Nos vamos a la vista para seleccionar la pista
				
				request.setAttribute("ListaPistas", ListaPistas);
				
				dispatcher = request.getRequestDispatcher("/mvc/view/admin/ModificarEstadoPistaDisplay.jsp");
				dispatcher.forward(request, response);
				
			}
			else{ //Se obtiene y modifica el estado de la pista elegida
				
				boolean estado=false;
								
				for(PistaDTO pistadto : ListaPistas) {
					
					if( pistadto.getNombre().contentEquals(pista) == true) {
						
						estado = pistadto.getEstado();
						break;
					}
				}
				
				//Cambiamos el estado
				if(estado == true) {
					estado = false;
				}
				
				else {
					estado = true;
				}
				
				pistadao.modificarEstadoPista(estado, pista);

				//request.setAttribute("mensaje", "La pista " + pista + " ha pasado a tener el estado " + estado);
				//dispatcher = request.getRequestDispatcher("/mvc/view/admin/ModificarEstadoPistaDisplay.jsp");
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