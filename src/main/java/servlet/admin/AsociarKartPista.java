package servlet.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import business.kart.KartDTO;
import business.pista.Dificultad;
import business.pista.PistaDTO;
import data.DAO.KartDAO;
import data.DAO.PistaDAO;
import display.javabean.userBean;
import display.javabean.asociarBean;

/**
 * Servlet implementation class AsociarKartPista
 */

public class AsociarKartPista extends HttpServlet {
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
		
		//Caso 1: Usuario no esta logueado -> Volvemos al index
		if (userBean == null || userBean.getCorreo().equals("") || userBean.getAdmin() == false) {
			
			response.sendRedirect("/WebProyectoPW");
			
		//Caso 2: Usuario logueado
		}else{
			PistaDAO pistaDAO = new PistaDAO(prop);
			
			String[] ids = request.getParameterValues("ids");
			String pista = request.getParameter("pista");
			
			//Caso 3a: El request viene vacio (no se ha elegido una pista) -> Ir a la vista (para elegir una pista)
			if (pista == null && ids == null) {
				
				List<PistaDTO> pistas = pistaDAO.consultarPistasAsignacion();
				
				//Guardamos en un bean el listado de pistas para evitar conexiones repetidas al servidor de la misma informacion
				bean.setListadoPistas(pistas);
				request.setAttribute("asociarBean", bean);
				
				dispatcher = request.getRequestDispatcher("/mvc/view/admin/PistasDisplay.jsp");
				dispatcher.forward(request, response);
				
			//Caso 3b: El request no viene vacio (se ha elegido una pista)
			}else{
				
				//Caso 4a: El request viene vacio (no se ha elegido ningun kart) -> Ir a la vista
				if (ids == null) {
					
					//Obtenemos la pista
					//(Se puede los datos de la pista en la vista a traves del objeto request ya que en el display "iteramos" sobre la lista, pero es inseguro al ser client-side)
					for (PistaDTO pistaDTO : bean.getListadoPistas()) {
						if (pistaDTO.getNombre().contentEquals(pista)) {
							bean.setPista(pistaDTO);
						}
					}
					
					KartDAO kartDAO = new KartDAO(prop);
					
					//Obtenemos todos los karts del servidor, y guardamos aquellos que cumplan la restriccion de la dificultad de la pista elegida
					List<KartDTO> karts = new ArrayList<>();
					for (KartDTO kartDTO : kartDAO.consultarKartsDisponibles()) {
						if (kartDTO.geType() == true && (bean.getPista().getDificulty()  == Dificultad.FAMILIAR || bean.getPista().getDificulty()  == Dificultad.INFANTIL)) {
							karts.add(kartDTO);
						}else if (kartDTO.geType() == false && (bean.getPista().getDificulty() == Dificultad.FAMILIAR || bean.getPista().getDificulty()  == Dificultad.ADULTOS)) {
							karts.add(kartDTO);
						}
					}
					
					bean.setListadoKarts(karts);
					request.setAttribute("asociarBean", bean);
					
					dispatcher = request.getRequestDispatcher("/mvc/view/admin/KartsDisplay.jsp");
					dispatcher.forward(request, response);
					
				//Caso 4b: El request no viene vacio (se han elegido karts)
				}else {
					
					//Se hacen las comprobaciones oportunas para ver si es posible hacer la asociacion:
					boolean done = true;
					
					//Si se han elegido mas karts de los maximos permitidos en la pista -> Volver al display
					if (ids.length > bean.getPista().getMaxAmmount()) {
						done = false;
						request.setAttribute("adminBean", bean);
						request.setAttribute("mensaje", "Ha seleccionado m&aacute;s karts que los permitidos en la pista. Max karts = " + bean.getPista().getMaxAmmount());
					}
					
					
					//Parseamos la lista de string de ids de karts provenientde la vista a un array de enteros
					int[] idKarts = Arrays.stream(ids).mapToInt(Integer::parseInt).toArray();
					 
					//Si la pista era familiar hay que comprobar que se hayan elegido los karts correctos de cada tipo
					int numKartAdultos = 0;
					int numKartInfantiles = 0;
					
					//Calculamos cuantos karts de cada tipo se han seleccionado de la vista
					for (KartDTO kartDTO : bean.getListadoKarts()) {
						for (int id : idKarts) {
							if (id == kartDTO.getId()) {
								if (kartDTO.geType()) {
									++numKartInfantiles;
								}else {
									++numKartAdultos;
								}
							}
						}
					}
					
						
				//Si se han elegido mas karts de adultos que los permitidos en la pista -> Volver al display
				if(numKartAdultos+bean.getPista().getAsocAmmountAdult() > bean.getPista().getMaxAmmount() ) {
					done = false;
					request.setAttribute("adminBean", bean);
					request.setAttribute("mensaje", "Ha seleccionado m&aacute;s karts adultos que los permitidos en la pista. N&uacute;mero asoc adultos= "+ bean.getPista().getAsocAmmountAdult() + " Max karts= " + bean.getPista().getMaxAmmount());
				}
							
				//Si se han elegido mas karts infantiles que los permitidos en la pista -> Volver al display
				if(numKartInfantiles+bean.getPista().getAsocAmmountInf() > bean.getPista().getMaxAmmount()) {
					done = false;
					request.setAttribute("adminBean", bean);
					request.setAttribute("mensaje", "Ha seleccionado m&aacute;s karts infantiles que los permitidos en la pista. N&uacute;mero asoc infantiles= "+ bean.getPista().getAsocAmmountInf() + " Max karts= " + bean.getPista().getMaxAmmount());
								
				}
						
					
				//Si todo esta correcto, se hace la asociacion
				if (done){
					KartDAO kartDAO = new KartDAO(prop);
								
					for (KartDTO kartDTO : bean.getListadoKarts()) {
						for (int id : idKarts) {
							if (id == kartDTO.getId()) {
								kartDAO.actualizarPistaKart(bean.getPista().getNombre(), kartDTO.getId());
							}
						}
					}
					
					//Añadimos los karts a la pista
					pistaDAO.actualizarPista(bean.getPista().getNombre(), -numKartInfantiles, -numKartAdultos);
								
					request.setAttribute("mensaje", "Se han asociado correctamente los karts");
					dispatcher = request.getRequestDispatcher("/");
					dispatcher.forward(request, response);	

				//Si hay un fallo, se vuelve al display
				}else{
						dispatcher = request.getRequestDispatcher("/mvc/view/admin/KartsDisplay.jsp");
						dispatcher.forward(request, response);
				}
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
