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

import display.javabean.userBean;
import business.reserva.*;
import data.DAO.reserva.*;

/**
 * Servlet implementation class CrearReservaIndividual
 */
public class CancelarBono extends HttpServlet {
	
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
			ReservaDAO reservaDAO = new ReservaDAO(prop);
			
			String bono = request.getParameter("bono");
			
			
			//Caso 2a: Request esta vacio -> Ir a la vista a seleccionar un bono
			if (bono == null) {
				
				ReservaDAO reserva = new ReservaDAO(prop);
				
				List<BonoDTO> bonos = reserva.consultarBonos(userBean.getCorreo());
				
				request.setAttribute("bonos", bonos);
				request.setAttribute("nextPage", "/WebProyectoPW/CancelarBono");
				dispatcher = request.getRequestDispatcher("/mvc/view/user/BonosDisplay.jsp");
				dispatcher.forward(request, response);
				
			//Caso 2b: El request viene lleno (se ha seleccionado un bono)
			}else if (bono != null){
						
				int idBono = Integer.parseInt(bono);
				
				//Obtenemos el bono del servidor segun la ID proporcionada
				BonoDTO bonoDTO = reservaDAO.consultarBono(idBono);
				
				int numeroSesiones = bonoDTO.getSesiones();
				
				//Si se supera la cantidad de sesiones por bono no se pueden añadir mas
				if (numeroSesiones > 0) {

					request.setAttribute("mensaje", "No se puede borrar el bono hasta que borre todas las sesiones pendientes " + numeroSesiones);
					dispatcher = request.getRequestDispatcher("/");
					dispatcher.forward(request, response);
					
				}else{
					reservaDAO.borrarBono(idBono);
					request.setAttribute("mensaje", "Se ha borrado el bono con ID " + idBono);
					dispatcher = request.getRequestDispatcher("/");
					dispatcher.forward(request, response);
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
