package servlet.admin;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import business.kart.*;
import data.DAO.reserva.ReservaDAO;
import data.DAO.KartDAO;
import data.DAO.PistaDAO;
import display.javabean.asociarBean;
import display.javabean.userBean;

/**
 * Servlet implementation class ModificarEstadoKart
 */

public class ModificarEstadoKart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*
	 * Las variables de clase, declaradas fuera de los métodos doGet y doPost (por ejemplo, en este caso bean), son
	 * persistentes, es decir, conservan sus valores en posteriores solicitudes al mismo servlet. Esto es así ya que
	 * el ServletContainer sólo crea una instancia del mismo servlet, creando posteriormente un nuevo hilo para
	 * servir cada solicitud.
	 * 
	 * Ref: http://gssi.det.uvigo.es/users/agil/public_html/LRO/jsp.pdf, Pagina: 2
	 */
	
	asociarBean bean = new asociarBean();
       
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
			
			response.sendRedirect("/web-gestion-karts");
			
		//Caso 2: Usuario logueado
		}else{
			
			String[] kart = request.getParameterValues("kart");
			String estado = request.getParameter("estado");
			
			KartDAO kartDAO = new KartDAO(prop);
			PistaDAO pistaDAO = new PistaDAO(prop);
			ReservaDAO reservaDAO = new ReservaDAO(prop);
			
			//Caso 2a: Request esta vacio (no se ha seleccionado un estado o un kart) -> Ir a la vista
			if (kart == null && estado == null) {
				
				//Guardamos en el bean el listado para evitar conexiones repetidas a la BD en un futuro
				bean.setListadoKarts(kartDAO.listadoKarts());
				
				request.setAttribute("asociarBean", bean);
				dispatcher = request.getRequestDispatcher("/mvc/view/admin/ModificarEstadoKartDisplay.jsp");
				dispatcher.forward(request, response);
				
			}
			else{ //Se modifica el estado del kart seleccionado
				
				//Parseamos la lista de string de ids de karts provenientde la vista a un array de enteros
				int[] kartsId = Arrays.stream(kart).mapToInt(Integer::parseInt).toArray();
				
				//Si el estado elegido para cambiar es RESERVADO:
				// No se hace nada ya que un administrador no puede hacer reservas y para no crear incosistencias en la BD no se puede poner a reservado
				
				//Si el estado elegido para cambiar es DISPONIBLE:
				// * Si antes era MANTENIMIENTO: No hace falta cambiar su pista asociada ya que era NULL
				// * Si antes era RESERVADO: Se resta un participante de la ultima reserva tipo que tenga como pista asociada del kart, se aumenta en 1 kart disponible en la pista asociada a la reserva
				// * Si antes era DISPONIBLE: No se hace nada
				if (estado.contentEquals("DISPONIBLE")) {
					for (KartDTO kartDTO : bean.getListadoKarts()) {
						for (int id : kartsId) {
							if (kartDTO.getId() == id && kartDTO.getStatus() == Estado.MANTENIMIENTO) {
								kartDAO.modificarEstadoKart(Estado.DISPONIBLE, id);
							
							}else if (kartDTO.getId() == id && kartDTO.getStatus() == Estado.RESERVADO) {
								//Si se encuentra una reserva de iguales caracteristicas se realiza el cambio
								int idReserva = reservaDAO.obtenerUltimaReservaID(kartDTO.getPista());
								
								if (idReserva != 0) {
									if (kartDTO.geType()) {
										reservaDAO.actualizarReserva(1, 0, idReserva);
										pistaDAO.actualizarPista(kartDTO.getPista(), -1, 0);
									}else {
										reservaDAO.actualizarReserva(0, 1, idReserva);
										pistaDAO.actualizarPista(kartDTO.getPista(), 0, -1);
									}
									
									kartDAO.modificarEstadoKart(Estado.DISPONIBLE, id);	
								}	
							}
						}
					}
					
				//Si el estado elegido para cambiar es MANTENIMIENTO:
				// * Si antes era DISPONIBLE: Su pista asociada se cambia a NULL y su pista asociada hay un kart menos asociado
				// * Si antes era RESERVADO: Se resta un participante de la ultima reserva tipo que tenga como pista asociada del kart, se aumenta en 1 kart disponible en la pista asociada a la reserva y su pista pasa a NULL
				// * Si antes era MANTENIMIENTO: No hace nada
				}else if (estado.contentEquals("MANTENIMIENTO")) {
					for (KartDTO kartDTO : bean.getListadoKarts()) {
						for (int id : kartsId) {
							if (kartDTO.getId() == id && kartDTO.getStatus() == Estado.DISPONIBLE) {
								
								kartDAO.modificarEstadoKart(Estado.MANTENIMIENTO, id);
								
								//Dependiendo del tipo de kart se resta 1 kart disponible en su pista asociada
								if (kartDTO.geType()) {
									pistaDAO.actualizarPista(kartDTO.getPista(), 1, 0);
								}else {
									pistaDAO.actualizarPista(kartDTO.getPista(), 0, 1);
								}
								
								kartDAO.eliminarPistaKart(id);
								
							}else if (kartDTO.getId() == id && kartDTO.getStatus() == Estado.RESERVADO) {
								
								//Si se encuentra una reserva de iguales caracteristicas se realiza el cambio
								int idReserva = reservaDAO.obtenerUltimaReservaID(kartDTO.getPista());
								
								if (idReserva != 0) {
									if (kartDTO.geType()) {
										reservaDAO.actualizarReserva(1, 0, idReserva);
										pistaDAO.actualizarPista(kartDTO.getPista(), 1, 0);
									}else {
										reservaDAO.actualizarReserva(0, 1, idReserva);
										pistaDAO.actualizarPista(kartDTO.getPista(), 0, 1);
									}
									
									kartDAO.eliminarPistaKart(id);
									kartDAO.modificarEstadoKart(Estado.MANTENIMIENTO, id);	
								}	
							}
						}
					}
				}
				
				request.setAttribute("mensaje", "Se ha cambiado el estado de los karts posibles");
				dispatcher = request.getRequestDispatcher("/");
				dispatcher.forward(request, response);
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
