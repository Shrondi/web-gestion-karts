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

import display.javabean.reservaBean;
import display.javabean.userBean;
import business.kart.Estado;
import business.pista.PistaDTO;
import business.reserva.*;
import data.DAO.KartDAO;
import data.DAO.PistaDAO;
import data.DAO.reserva.*;

/**
 * Servlet implementation class CrearReservaIndividual
 */
public class CrearBonoReserva extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Las variables de clase, declaradas fuera de los métodos doGet y doPost (por ejemplo, en este caso bean), son
	 * persistentes, es decir, conservan sus valores en posteriores solicitudes al mismo servlet. Esto es así ya que
	 * el ServletContainer sólo crea una instancia del mismo servlet, creando posteriormente un nuevo hilo para
	 * servir cada solicitud.
	 * 
	 * Ref: http://gssi.det.uvigo.es/users/agil/public_html/LRO/jsp.pdf, Pagina: 2
	 */
	
	reservaBean bean = new reservaBean();
       
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
			PistaDAO pistaDAO = new PistaDAO(prop);
			KartDAO kartDAO = new KartDAO(prop);
			
			String bono = request.getParameter("bono");
			
			String fechaReserva = request.getParameter("fecha");
			String pista = request.getParameter("pista");
			
			//Caso 2a: Request esta vacio -> Ir a la vista a seleccionar un bono
			if (bono == null && fechaReserva == null && pista == null) {
				
				ReservaDAO reserva = new ReservaDAO(prop);
				
				List<BonoDTO> bonos = reserva.consultarBonos(userBean.getCorreo());
				
				request.setAttribute("bonos", bonos);
				dispatcher = request.getRequestDispatcher("/mvc/view/user/BonosDisplay.jsp");
				dispatcher.forward(request, response);
				
			//Caso 2b: El request viene lleno (se ha seleccionado un bono)
			}else if (bono != null){
				System.out.println("ENTRO");
				
				//Obtenemos el bono del servidor segun la ID proporcionada
				int idBono = Integer.parseInt(bono);
				
				BonoDTO bonoDTO = reservaDAO.consultarBono(idBono);
				int numeroSesiones = bonoDTO.getSesiones();
				String tipo = bonoDTO.getTipo();
					
				//Guardamos en el bean los datos del bono para un posterior uso
				bean.setIdBono(idBono);
				bean.setTipoReserva(tipo);
				bean.setNumeroSesiones(numeroSesiones);
				
				//Si se supera la cantidad de sesiones por bono no se pueden añadir mas
				if (numeroSesiones == 5) {

					request.setAttribute("mensaje", "No se pueden añadir mas reservas al bono con ID " + idBono + ". El numero maximo de sesiones de un bono son 5.");
					dispatcher = request.getRequestDispatcher("/WebProyectoPW");
					dispatcher.forward(request, response);
				}
				
				request.setAttribute("reservaBean", bean);
				dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarReservaBonoDisplay.jsp");
				dispatcher.forward(request, response);
				
				//(A partir de aqui el codigo es el mismo que CrearReservaIndividual salvo que se cambia la fecha de caducidad del bono
				//si es la primera reserva y se calcula un descuento diferente y los datos de la reserva se piden en un display distinto dependiendo del tipo de bono
				
				//CASO 3a: No hay parametros en el request -> Ir a la vista para crear una reserva para el bono
				}else{
					
					//CASO 3b: Hay parametros en el request proveniente de la vista de crear reservas -> Elegimos la pista
					if (fechaReserva != null && pista == null){
						
							//Obtenemos los parametros del request
							int duracion = Integer.parseInt(request.getParameter("duracion"));
							int numeroNinios = 0;
							int numeroAdultos = 0;
							
							//Este parametro depende del tipo de bono utilizado
							String tipoReserva = bean.getTipoReserva();
							
							//Dependiento del tipo de reserva, el valor del numero de niños o adultos sera 0 o se parseara
							
							if (tipoReserva.contentEquals("FAMILIAR") || tipoReserva.contentEquals("ADULTOS")) {
								numeroAdultos = Integer.parseInt(request.getParameter("numeroAdultos"));
							}
							
							if (tipoReserva.contentEquals("FAMILIAR") || tipoReserva.contentEquals("INFANTILES")) {
								numeroNinios = Integer.parseInt(request.getParameter("numeroNinios"));
							}
							
							//Si el usuario elige una fecha en en el rango de la fecha de la ultima reserva + 2 horas, tendra que elegir otra fecha
							// (Asi se evita tener dos reservas que transcurran a la vez para el mismo usuario)
							// Se ha elegido un franja de 2 horas por comodidad ya que la maxima duracion de una reserva son 2 horas
							if (reservaDAO.comprobarReserva(fechaReserva, userBean.getCorreo())) {
								
								request.setAttribute("mensaje", "Ya tiene una reserva para la fecha dada. Debe haber una diferencia de 2 horas minimo entre la fecha de las reservas");
								request.setAttribute("reservaBean", bean);
								dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarReservaBonoDisplay.jsp");
								dispatcher.forward(request, response);
							
							
							}else if() {
							
							//Se puede realizar la reserva
							}else{
								
								List<PistaDTO> pistas = pistaDAO.consultarPistas(tipoReserva, numeroNinios, numeroAdultos);
								
								//No hay pistas disponibles para los datos dados -> Volver al display a pedir nuevos datos para la reserva
								if (pistas.isEmpty()) {
									
									request.setAttribute("mensaje", "No hay pistas disponibles con los datos dados. Intentelo de nuevo mas tarde.");
									dispatcher = request.getRequestDispatcher("/mvc/view/user/ConsultarReservaBonoDisplay.jsp");
									dispatcher.forward(request, response);
								
								//Hay pistas disponibles para los datos dados -> Ir al display a elegir una pista
								}else{
									
									//Guardamos los datos de la reserva para un posterior uso
									bean.setFecha(fechaReserva);
									bean.setDuracion(duracion);
									bean.setNumeroNinios(numeroNinios);
									bean.setNumeroAdultos(numeroAdultos);
									
									request.setAttribute("ListaPistas", pistas);
									request.setAttribute("nextPage", "/WebProyectoPW/ReservaBono");
									dispatcher = request.getRequestDispatcher("/mvc/view/user/PistasDisplay.jsp");
									dispatcher.forward(request, response);
								}
							}
						
						//CASO 3c: Hay parametros en el request (se ha elegido la pista) -> Realizar la reserva
						}else if(pista != null){
						
						int duracion = bean.getDuracion();
						int numeroNinios = bean.getNumeroNinios();
						int numeroAdultos = bean.getNumeroAdultos();
						String tipoReserva = bean.getTipoReserva();
						String fecha = bean.getFecha();
						
						 //Calculamos el descuento por ser una reserva de tipo bono y el precio final de la reserva
						 
						float precio = 0.95f;
						float descuento = 0.05f;
						
						//Calculamos el precio de la reserva en base a su duracion y aplicamos un descuento (si existe)
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
							infantil.setIdPista(pista);
							
							reserva.crearReservaInfantil(infantil);
							
							request.setAttribute("reservaInfantil", infantil);
							
							
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
							familiar.setIdPista(pista);
							
							reserva.crearReservaFamiliar(familiar);
							
							request.setAttribute("reservaFamiliar", familiar);
							
						}else if(tipoReserva.contentEquals("ADULTOS")) {
							ReservaAdultosDAO reserva = new ReservaAdultosDAO(prop);
							ReservaAdultosDTO adultos = new ReservaAdultosDTO();
							
							adultos.setIdUsuario(userBean.getCorreo());
							adultos.setFecha(fecha);
							adultos.setParticipantesAdultos(numeroAdultos);
							adultos.setDuracion(duracion);
							adultos.setDescuento(descuento);
							adultos.setPrecio(precio);
							adultos.setIdPista(pista);
							
							reserva.crearReservaAdulto(adultos);
							
							request.setAttribute("reservaAdultos", adultos);
						}
						
						if (bean.getNumeroSesiones() == 0) {
							reservaDAO.actualizarFechaBono(bean.getIdBono(), fecha);
						}
						
						//Actualizamos el numero de sesiones del bono
						reservaDAO.actualizarSesionesBono(bean.getIdBono());
						
						//Actualizamos el numero de karts disponibles de la pista
						pistaDAO.actualizarPista(pista, numeroNinios, numeroAdultos);
						
						//Actualizamos el estado de los karts a reservado por cada tipo
						kartDAO.actualizarEstadoKart(true, Estado.RESERVADO, pista, numeroNinios);
						kartDAO.actualizarEstadoKart(false, Estado.RESERVADO, pista, numeroAdultos);
						
						//Mostramos el resumen de la reserva
						request.setAttribute("modalidad", "Individual");
						dispatcher = request.getRequestDispatcher("/mvc/view/user/ReservaDisplay.jsp");
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
