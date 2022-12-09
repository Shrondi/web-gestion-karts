package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import display.javabean.userBean;
import business.reserva.*;
import data.DAO.UsuarioDAO;
import data.DAO.reserva.*;

/**
 * Servlet implementation class CrearReservaIndividual
 */
public class CrearReservaIndividual extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		HttpSession session = request.getSession();
		ServletContext application = session.getServletContext();
		userBean userBean = (userBean) session.getAttribute("userBean");
		
		//Caso 1: Usuario no esta logueado -> Volvemos al index
		if (userBean == null || userBean.getCorreo().equals("")) {
			//dispatcher = request.getRequestDispatcher("/index.jsp");
			//dispatcher.forward(request, response);
			response.sendRedirect("/WebProyectoPW");
			
		//Caso 2: Usuario logueado
		}else{
		
			//Obtenemos los parametros del request
			String fecha = request.getParameter("fecha");
			
			//Caso 2a: No hay parametros en el request
			if (fecha == null){
				dispatcher = request.getRequestDispatcher("/mvc/view/CrearReservaIndividualDisplay.jsp");
				dispatcher.forward(request, response);
				
			//Caso 2b: Hay parametros en el request
			}else{
			
				//Obtenemos los parametros restantes del request
				int duracion = Integer.parseInt(request.getParameter("duracion"));
				int numeroNinios = Integer.parseInt(request.getParameter("numeroNinios"));
				int numeroAdultos = Integer.parseInt(request.getParameter("numeroAdultos"));
				String tipoReserva = request.getParameter("tipoReserva");
				
				//Obtenemos el valor del parametro sqlproperties, es decir, la ruta relativa al fichero sql.properties
				String sqlProperties= application.getInitParameter("sqlproperties"); 
				java.io.InputStream myIO = application.getResourceAsStream(sqlProperties);
				java.util.Properties prop = new java.util.Properties();
				prop.load(myIO);
				
				UsuarioDAO usuarioDAO = new UsuarioDAO(prop);
				
				//Obtenemos la fecha de inscripcion (fecha de primera reserva) del usuario
				String fechaInscripcion = usuarioDAO.calcularAntiguedad(userBean.getCorreo());
				
				
				/*
				 * Calculamos la antiguedad del usuario, el descuento por su antiguedad y el precio final de la reserva
				 */
				
				int antiguedad = 0;
				float precio = 1f;
				float descuento = 0;
				
				//Comprobamos si el valor de la fecha de inscripcion es el por defecto.
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date currDate = new Date();
				
				//Si no lo es, calculamos la antiguedad del usuario
				if (!fechaInscripcion.equals("1/1/1900")) {
				
					Date date = new Date();
					try {
						date = sdf.parse(fechaInscripcion);
					} catch (ParseException e){
						e.printStackTrace();
					}
					
					long diff = currDate.getTime() - date.getTime();
					long d = (1000l*60*60*24*365);
					long years = Math.round(diff/d);
					
					antiguedad = (int) years;
				
				//Si lo es, establecemos la nueva fecha de inscripcion como la fecha de hoy, es decir, la fecha de cuando realiza la primera reserva
				}else{
					
					String nuevafechaInscripcion = null;
					nuevafechaInscripcion = sdf.format(currDate);
					
					usuarioDAO.modificarFechaInscripcionUsuario(userBean.getCorreo(), nuevafechaInscripcion);
				}
				
				
				//Asignamos un descuento del 10% si el usuario tiene una antiguedad mayor a 2 años
				if(antiguedad > 2){
					descuento = 10f;
					precio = 0.9f;
				}
				
				//Calculamos la duracion de la reserva en base al descuento (si existe)
				if(duracion == 60) {
					precio = 20 * precio;
					
				}else if(duracion == 90) {
					precio = 30 * precio;
					
				}else if(duracion == 120){
					precio = 40 * precio;			
				}
				
				if (tipoReserva.contentEquals("INFANTIL")) {
					ReservaInfantilDAO reserva = new ReservaInfantilDAO(prop);
					ReservaInfantilDTO infantil = new ReservaInfantilDTO();
					
					infantil.setIdUsuario(userBean.getCorreo());
					infantil.setFecha(fecha);
					infantil.setParticipantesInfantiles(numeroNinios);
					infantil.setDuracion(duracion);
					infantil.setDescuento(descuento);
					infantil.setPrecio(precio);
					
					reserva.crearReservaInfantil(infantil);
					
				}else if(tipoReserva.contentEquals("FAMILIAR")) {
					ReservaFamiliarDAO reserva = new ReservaFamiliarDAO(prop);
					ReservaFamiliarDTO familiar = new ReservaFamiliarDTO();
					
					familiar.setIdUsuario(userBean.getCorreo());
					familiar.setFecha(fecha);
					familiar.setParticipantesAdultos(numeroAdultos);
					familiar.setParticipantesInfantiles(numeroNinios);
					familiar.setDuracion(duracion);
					familiar.setDescuento(descuento);
					familiar.setPrecio(precio);
					
					reserva.crearReservaFamiliar(familiar);
					
				}else if(tipoReserva.contentEquals("ADULTOS")) {
					ReservaAdultosDAO reserva = new ReservaAdultosDAO(prop);
					ReservaAdultosDTO adultos = new ReservaAdultosDTO();
					
					adultos.setIdUsuario(userBean.getCorreo());
					adultos.setFecha(fecha);
					adultos.setParticipantesAdultos(numeroAdultos);
					adultos.setDuracion(duracion);
					adultos.setDescuento(descuento);
					adultos.setPrecio(precio);
					adultos.setIdPista("P1");
					
					reserva.crearReservaAdulto(adultos);
				}
		
				//dispatcher = request.getRequestDispatcher("/index.jsp");
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
