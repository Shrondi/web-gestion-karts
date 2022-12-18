package data.DAO.reserva;

import data.common.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Properties;

import business.reserva.AbstractReservaDTO;
import business.reserva.BonoDTO;

/**
 * @author Carlos Lucena Robles.
 * @author Miguel Raigón Jiménez.
 * @author Pablo Roldán Puebla.
 * @author Paloma Romero Delgado.
 * @author Kamal Abdelkader Haddu.
 * @version 1.0.0
 */

/**
 * DAO correspondiente a las operaciones necesarias para la manipulación de Reservas
 *
 */

public class ReservaDAO {

	private Connection con;
	private Properties prop;
	
	
	/**
	 * Constructor del DAO de Reserva
	 * @param properties Objeto properties que contiene las consultas relativas a la BD
	 */
	
	public ReservaDAO(Properties properties) {
		
		prop = properties;
	}
	
	//NEW
	
	/**
	 * Se usa para eliminar unq reserva
	 * Tablas usadas:
	 *	-RESERVA: Para eliminar la reserva correspondiente
	 * Atributos de la tabla usados:
	 *	-id_Reserva: ID que identifica a la reserva a eliminar
	 *
	 * @param idReserva ID de la reserva a borrar
	 * @return flag Booleano que devuelve True si se borra de forma correcta la reserva y False en caso contrario
	 */
	
	public boolean borrarReserva(int idReserva) {
		
		boolean flag = true;
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("cancelarReservaSTM"));
			ps.setInt(1,idReserva);
			
			ps.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
			flag = false;
		}
		
		connection.closeConnection();
		return flag;
	}
	
	//NEW
	
	/**
	 * Se usa para comprobar si una reserva existe en el rango [fechaNuevaReserva - 2 horas, fechaNuevaReserva]
	 * Tablas usadas:
	 *	-RESERVA: Para comprobar si eixste la reserva correspondiente
	 * Atributos de la tabla usados:
	 *	-usuario: Usuario que hizo la reserva
	 *	-fecha: Fecha de la reserva
	 *
	 * @param fecha Fecha de la reserva
	 * @param correo Correo del usuario
	 * @return check Booleano que devuelve True si la reserva existe y false en caso contrario
	 */
	
	public boolean comprobarReserva(String fecha, String correo){
		boolean check = false;
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerReservaSTM"));
			
			ps.setString(1, correo);
			ps.setString(2, fecha);
			ps.setString(3, fecha);
				
			ResultSet rs = ps.executeQuery();
				
			rs.first();
			check = rs.getBoolean(1);	
				
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		}
			
		connection.closeConnection();
		return check;
	}
	
	//NEW
	
	/**
	 * Se usa para obtener la próxima reserva de un usuario
	 * Tablas usadas:
	 *	-RESERVA: Para eliminar la reserva correspondiente
	 * Atributos de la tabla usados:
	 *	-usuario: Usuario que hizo la reserva
	 *	-fecha: Fecha de la reserva
	 *
	 * @param correo Correo del usuario que hizo la reserva
	 * @return reserva Próxima reservada realizada
	 */
	
	public AbstractReservaDTO obtenerProximaReserva(String correo){
		AbstractReservaDTO reserva = new AbstractReservaDTO();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerReservaProximaSTM"));
			
			ps.setString(1, correo);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				String pista = rs.getString("pista");
				Date fecha = new Date(rs.getTimestamp("fecha").getTime());
				
				reserva.setIdPista(pista);
				reserva.setFecha(fecha);
			}
					
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		}
				
		connection.closeConnection();
		return reserva;
	}
	
	
	//NEW
	
	/**
	 * Se usa para obtener la última reserva en función de la pista asociada
	 * Tablas usadas:
	 *	-RESERVA: Para obtener la reserva correspondiente
	 * Atributos de la tabla usados:
	 *	-pista: Usuario que hizo la reserva
	 *	-fecha: Fecha de la reserva
	 *
	 * @param pista Pista asociada a la reserva
	 * @return id ID de la última reserva
	 */
	
	public int obtenerUltimaReservaID(String pista) {
						
		int id = 0;
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerUltimaReservaIDSTM"));
			ps.setString(1, pista);
							
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				id = rs.getInt(1);
			}
						
		} catch(SQLException e) {
				e.printStackTrace();
		}
						
		connection.closeConnection();
		return id;
	}
	
	//NEW
	
	/**
	 * Se usa para actualizar una reserva,concretamente el número de participantes infantiles y/o adultos
	 * Tablas usadas:
	 *	-RESERVA: Para actualizar la reserva correspondiente
	 * Atributos de la tabla usados:
	 *	-participantes_infantiles: Nuevo número de participantes infantiles
	 *	-participantes_adultos: Nuevo número de participantes adultos
	 *	-id_Reserva: ID de la reserva a actualizar
	 *
	 * @param numInfantiles Número de participantes infantiles a modificar
	 * @param numAdultos Número de participantes adultos a modificar
	 * @param idReserva ID de la reserva
	 */
	
		public void actualizarReserva(int numInfantiles, int numAdultos, int idReserva) {
			
			DBConnection connection = new DBConnection();
			con = connection.getConnection();

			try {
				PreparedStatement ps = con.prepareStatement(prop.getProperty("actualizarReservabyIDSTM"));

				ps.setInt(1, numInfantiles);
				ps.setInt(2, numAdultos);
				ps.setInt(3, idReserva);
				
				ps.executeUpdate();
			
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			connection.closeConnection();
		}
	
		/**
		 * Se usa para insertar un bono
		 * Tablas usadas:
		 *	-BONO: Para insertar el bono
		 * Atributos de la tabla usados:
		 *	-usuario: Usuario al que pertenece el bono
		 *	-tipo_bono: Tipo de bono
		 *
		 * @param correo Correo del usuario cuyo bono será insertado
		 * @param tipoBono Tipo de bono
		 */
		
	//NEW
	public void insertarBono(String correo, String tipoBono) {
		
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("crearBonoSTM"));

			ps.setString(1,correo);
			ps.setString(2,tipoBono);
			
			ps.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		connection.closeConnection();
	}
	
	//NEW
	
	/**
	 * Se usa para consultar un bono
	 * Tablas usadas:
	 *	-BONO: Para acceder al bono correspondiente y poder consultarlo
	 * Atributos de la tabla usados:
	 *	-id_Bono: ID del bono a consultar
	 *	-tipo_bono: Tipo de bono
	 *
	 * @param idBono ID del bono a consultar
	 * @return bono Bono elegido para ser consultado
	 */
	
	public BonoDTO consultarBono(int idBono) {
		BonoDTO bono = new BonoDTO();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("consultarBonoSTM"));

			ps.setInt(1,idBono);
			
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int sesiones = rs.getInt("numero_sesiones");
				Date fechaCaducidad = rs.getDate("fecha_caducidad");
				String tipo = rs.getString("tipo_Bono");
				
				bono.setSesiones(sesiones);
				bono.setFechaCaducidad(fechaCaducidad);
				bono.setTipo(tipo);
			}
				
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
				
			connection.closeConnection();
			return bono;
	}
		
	//NEW
	
	/**
	 * Se usa para actualizar la fecha de caducidad de un bono
	 * Tablas usadas:
	 *	-BONO: Para poder acceder al bono que se quiera actualizar
	 * Atributos de la tabla usados:
	 *	-id_Bono: ID del bono a consultar
	 *	-fecha_caducidad: Fecha de caducidad del bono, que será actualizada
	 *
	 * @param idBono ID del bono a consultar
	 * @param fecha Nueva fecha de caducidad del bono
	 */
	
	public int actualizarFechaBono(int idBono, String fecha) {
					
		int sesiones = 0;
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
		PreparedStatement ps = con.prepareStatement(prop.getProperty("actualizarFechaBonoSTM"));

		ps.setString(1, fecha);
		ps.setInt(2, idBono);
						
		ps.executeUpdate();
					
		} catch(SQLException e) {
			e.printStackTrace();
		}
					
			connection.closeConnection();
			return sesiones;
	}
				
	//NEW
	
	/**
	 * Se usa para actualizar el número de sesiones de un bono
	 * Tablas usadas:
	 *	-BONO: Para poder acceder al bono cuyo número de sesiones se desee modificar
	 * Atributos de la tabla usados:
	 *	-id_Bono: ID del bono cuyas sesiones s everán modificados
	 *	-numero_sesiones: Número de sesiones, que será modificado
	 *
	 * @param numeroSesiones Nuevo número de sesiones del bono
	 * @param idBono ID del bono
	 */
	
	public void actualizarSesionesBono(int numeroSesiones, int idBono) {
					
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			PreparedStatement ps2 = con.prepareStatement(prop.getProperty("actualizarSesionesBonoSTM"));
			ps2.setInt(1, numeroSesiones);
			ps2.setInt(2, idBono);
						
			ps2.executeUpdate();
					
		} catch(SQLException e) {
				e.printStackTrace();
		}
					
		connection.closeConnection();
	}
	
	//NEW
	
	/**
	 * Se usa para consultar el ID del bono de una reserva
	 * Tablas usadas:
	 *	-RESERVA: Para poder acceder al bono
	 * Atributos de la tabla usados:
	 *	-id_Bono: ID del bono 
	 *	-id_Reserva: ID de la reserva
	 *
	 * @param idReserva ID de la reserva
	 * @param idBono ID del bono a consultar
	 */
	
		public int consultarIDBonoReserva(int idReserva) {
			int idBono = 0;
			DBConnection connection = new DBConnection();
			con = connection.getConnection();

			try {
				PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerIDBonoSTM"));

				ps.setInt(1,idReserva);
				
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					idBono = rs.getInt("id_Bono");
				}
					
					
				} catch(SQLException e) {
					e.printStackTrace();
				}
					
				connection.closeConnection();
				return idBono;
		}
	
	//NEW
		
		/**
		 * Se usa para consultar el número de reservas completadas
		 * Tablas usadas:
		 *	-RESERVA: Para acceder a las reservas
		 * Atributos de la tabla usados:
		 *	-fecha: Fecha de las reservas completadas
		 *
		 * @return reservas Número de reservas completadas
		 */
		
	public int consultarReservasCompletadas() {
				
		int reservas = 0;
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(prop.getProperty("obtenerReservasCompletadasSTM"));

			rs.first();
			reservas = rs.getInt(1);			
					
			} catch(SQLException e) {
				e.printStackTrace();
			}
					
			connection.closeConnection();
			return reservas;
	}
			
	
	//NEW
	
	/**
	 * Se usa para consultar el número de reservas de un usuario completadas
	 * Tablas usadas:
	 *	-RESERVA: Para acceder a las reservas del usuario
	 * Atributos de la tabla usados:
	 *	-fecha: Fecha de las reservas completadas por parte del usuario
	 *	-usuario: Usuario del que se quiere saber el número de reservas completadas
	 *
	 * @return reservas Número de reservas del usuario que han sido completadas
	 */
	
	public int obtenerReservasCompletadasUsuario(String correo){
						
		int reservas = 0;
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(prop.getProperty("obtenerReservasCompletadasUsuarioSTM")+String.format("'%s'", correo));

			rs.first();
			reservas = rs.getInt(1);			
					
			} catch(SQLException e) {
				e.printStackTrace();
			}
					
			connection.closeConnection();
			return reservas;
	}
	
	/**
	 * Se usa para borrar un bono
	 * Tablas usadas:
	 *	-BONO: Para acceder a los bonos y borrar el deseado
	 * Atributos de la tabla usados:
	 *	-id_Bono: ID del bono a borrar
	 *
	 * @param id ID del bono a eliminar
	 */		
	
	public void borrarBono(int id) {
		
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("borrarBonoSTM")+id);

			ps.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		connection.closeConnection();
	}
	
	/**
	 * Se usa para consultar los bonos de un usuario
	 * Tablas usadas:
	 *	-BONO: Para acceder a los bonos y elegir los relativos a un usuario
	 * Atributos de la tabla usados:
	 *	-usuario: Usuario cuyos bonos quieren obtenerse
	 *
	 * @param usuario Usuario al que pertenecen los bonos
	 * @return bonos Lista con los bonos del usuario
	 */
	
	//NEW
	public List<BonoDTO> consultarBonos(String usuario){
		
		List<BonoDTO> bonos = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
			
		try {
			Statement stmt = con.createStatement();
			String query = prop.getProperty("obtenerBonoByUsuarioSTM")+String.format("'%s'", usuario);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				
				int id = rs.getInt("id_Bono");
				String tipoBono = rs.getString("tipo_Bono");
				Date fechaCaducidad = rs.getDate("fecha_caducidad");
				int sesiones = rs.getInt("numero_sesiones");
				
				
				BonoDTO bono = new BonoDTO();
				bono.setBonoId(id);
				bono.setUsuario(usuario);
				bono.setFechaCaducidad(fechaCaducidad);
				bono.setTipo(tipoBono);
				bono.setSesiones(sesiones);
				
				bonos.add(bono);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		}
		
		connection.closeConnection();
		return bonos;
	}
	
	
}
