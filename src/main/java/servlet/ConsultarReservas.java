package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import display.javabean.userBean;
import business.pista.PistaDTO;
import business.reserva.*;
import data.DAO.KartDAO;
import data.DAO.PistaDAO;
import data.DAO.UsuarioDAO;
import data.DAO.reserva.*;

import display.javabean.userBean;

/**
 * Servlet implementation class ConsultarReservas
 */

public class ConsultarReservas extends HttpServlet {
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
			
			//String reserva = request.getParameter("reserva");
			String fechaInicio = request.getParameter("fechaInicio");
			String fechaFin = request.getParameter("fechaFin");
			
			if ( fechaInicio == null || fechaFin == null ) {
								
				dispatcher = request.getRequestDispatcher("/mvc/view/PedirFechasDisplay.jsp");
				dispatcher.forward(request, response);
				
				
			}else {
				
				ReservaInfantilDAO reservaInfantilDAO = new ReservaInfantilDAO(prop);
				ReservaFamiliarDAO reservaFamiliarDAO = new ReservaFamiliarDAO(prop);
				ReservaAdultosDAO reservaAdultosDAO = new ReservaAdultosDAO(prop);

				List<ReservaInfantilDTO> reservasInfantilPasadas = reservaInfantilDAO.consultarReservasInfantilPasadasRango(fechaInicio,fechaFin,userBean.getCorreo());
				List<ReservaInfantilDTO> reservasInfantilFuturas = reservaInfantilDAO.consultarReservasInfantilFuturasRango(fechaInicio,fechaFin,userBean.getCorreo());
				
				List<ReservaFamiliarDTO> reservasFamiliarPasadas = reservaFamiliarDAO.consultarReservasFamiliarPasadasRango(fechaInicio,fechaFin,userBean.getCorreo());
				List<ReservaFamiliarDTO> reservasFamiliarFuturas = reservaFamiliarDAO.consultarReservasFamiliarFuturasRango(fechaInicio,fechaFin,userBean.getCorreo());
				
				List<ReservaAdultosDTO> reservasAdultosPasadas = reservaAdultosDAO.consultarReservasAdultosPasadasRango(fechaInicio,fechaFin,userBean.getCorreo());
				List<ReservaAdultosDTO> reservasAdultosFuturas = reservaAdultosDAO.consultarReservasAdultosFuturasRango(fechaInicio,fechaFin,userBean.getCorreo());
				
				request.setAttribute("reservasInfantilPasadas", reservasInfantilPasadas);
				request.setAttribute("reservasInfantilFuturas", reservasInfantilFuturas);
				
				request.setAttribute("reservasFamiliarPasadas", reservasFamiliarPasadas);
				request.setAttribute("reservasFamiliarFuturas", reservasFamiliarFuturas);
				
				request.setAttribute("reservasAdultosPasadas", reservasAdultosPasadas);
				request.setAttribute("reservasAdultosFuturas", reservasAdultosFuturas);
				
				dispatcher = request.getRequestDispatcher("/mvc/view/ConsultarReservasDisplay.jsp");
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
