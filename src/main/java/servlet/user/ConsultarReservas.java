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

import display.javabean.rangoBean;
import display.javabean.userBean;
import business.reserva.*;
import data.DAO.reserva.*;

import java.util.ArrayList;
import java.util.Date;
/**
 * Servlet implementation class ConsultarReservas
 */

public class ConsultarReservas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*
	 * Las variables de clase, declaradas fuera de los métodos doGet y doPost (por ejemplo, en este caso bean), son
	 * persistentes, es decir, conservan sus valores en posteriores solicitudes al mismo servlet. Esto es así ya que
	 * el ServletContainer sólo crea una instancia del mismo servlet, creando posteriormente un nuevo hilo para
	 * servir cada solicitud.
	 * 
	 * Ref: http://gssi.det.uvigo.es/users/agil/public_html/LRO/jsp.pdf, Pagina: 2
	 */
	
	rangoBean bean = new rangoBean();
       
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
			
			String fechaInicio = request.getParameter("fechaInicio");
			String fechaFin = request.getParameter("fechaFin");
			
			if ( fechaInicio == null || fechaFin == null ) {
								
				dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarReservasRangoDisplay.jsp");
				dispatcher.forward(request, response);
				
				
			}else {
				
				ReservaInfantilDAO reservaInfantilDAO = new ReservaInfantilDAO(prop);
				ReservaFamiliarDAO reservaFamiliarDAO = new ReservaFamiliarDAO(prop);
				ReservaAdultosDAO reservaAdultosDAO = new ReservaAdultosDAO(prop);

				List<ReservaInfantilDTO> reservasInfantilPasadas = new ArrayList<>();
				List<ReservaInfantilDTO> reservasInfantilFuturas = new ArrayList<>();
				
				List<ReservaFamiliarDTO> reservasFamiliarPasadas = new ArrayList<>();
				List<ReservaFamiliarDTO> reservasFamiliarFuturas = new ArrayList<>();
				
				List<ReservaAdultosDTO> reservasAdultosPasadas = new ArrayList<>();
				List<ReservaAdultosDTO> reservasAdultosFuturas = new ArrayList<>();
				
				
				Date fecha = new Date();
				
				for (ReservaInfantilDTO reservaInfantil : reservaInfantilDAO.consultarReservasInfantilRango(fechaInicio, fechaFin, userBean.getCorreo()) ) {
					if (reservaInfantil.getFechaDate().compareTo(fecha) <= 0) {
						reservasInfantilPasadas.add(reservaInfantil);

					}else {
						reservasInfantilFuturas.add(reservaInfantil);
					}
				}
				
				for (ReservaFamiliarDTO reservaFamiliar : reservaFamiliarDAO.consultarReservasFamiliarRango(fechaInicio, fechaFin, userBean.getCorreo()) ) {
					if (reservaFamiliar.getFechaDate().compareTo(fecha) <= 0) {
						reservasFamiliarPasadas.add(reservaFamiliar);

					}else {
						reservasFamiliarFuturas.add(reservaFamiliar);
					}
				}
				
				for (ReservaAdultosDTO reservaAdultos : reservaAdultosDAO.consultarReservasAdultosRango(fechaInicio, fechaFin, userBean.getCorreo()) ) {
					if (reservaAdultos.getFechaDate().compareTo(fecha) <= 0) {
						reservasAdultosPasadas.add(reservaAdultos);

					}else {
						reservasAdultosFuturas.add(reservaAdultos);
					}
				}
				
				
				bean.setReservasInfantilPasadas(reservasInfantilPasadas);
				bean.setReservasInfantilFuturas(reservasInfantilFuturas);
				bean.setReservasFamiliarPasadas(reservasFamiliarPasadas);
				bean.setReservasFamiliarFuturas(reservasFamiliarFuturas);
				bean.setReservasAdultosPasadas(reservasAdultosPasadas);
				bean.setReservasAdultosFuturas(reservasAdultosFuturas);
				
				request.setAttribute("fechaInicio", fechaInicio);
				request.setAttribute("fechaFin", fechaFin);
				
				request.setAttribute("rangoBean", bean);
				dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarReservasRangoDisplay.jsp");
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
