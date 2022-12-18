package data.DAO.reserva;

import data.common.DBConnection;
import business.reserva.ReservaInfantilDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
/**
 * @author Carlos Lucena Robles.
 * @author Miguel Raigón Jiménez.
 * @author Pablo Roldán Puebla.
 * @author Paloma Romero Delgado.
 * @author Kamal Abdelkader Haddu.
 * @version 1.0.0
 */

/**
 * DAO correspondiente a las operaciones necesarias para la manipulación de Reservas Infantiles
 *
 */

public class ReservaInfantilDAO {

	private Connection con;
	private Properties prop;
	
	/**
	 * Constructor del DAO de Usuario
	 * @param properties Objeto properties que contiene las consultas relativas a la BD
	 */
	
	public ReservaInfantilDAO(Properties properties) {
		
		prop = properties;
	}
	
	/**
	 * Se usa para incluir una nueva reserva infantil
	 * Tablas usadas:
	 *	-RESERVA: Para seleccionar todos los usuarios existentes en la BD
	 *Atributos de la tabla usados:
	 *	-usuario: Usuario de la reserva
	 *	-modalidad_reserva: Modalidad de la reserva
	 *	-tipo_Reserva: Tipo de reserva
	 *	-participantes_infantiles: Número de participantes infantiles
	 *	-fecha: Fecha de la reserva
	 *	-duracion: Duracion de la reserva
	 *	-descuento: Posible descuento a aplicar
	 *	-precio: Precio de la reserva
	 *	-pista: Pista de la reserva
	 * @param reservaInfantil Reserva infantil a crear
	 */
	
	public void crearReservaInfantil(ReservaInfantilDTO reservaInfantil) {
		
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("crearReservaInfantilSTM"));
			ps.setString(1, reservaInfantil.getIdUsuario());
			ps.setInt(2,reservaInfantil.getParticipantesInfantiles());
			ps.setString(3, reservaInfantil.getFecha());
			ps.setInt(4, reservaInfantil.getDuracion());
			ps.setFloat(5, reservaInfantil.getDescuento());
			ps.setFloat(6, reservaInfantil.getPrecio());
			ps.setString(7, reservaInfantil.getIdPista());
			
			ps.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		connection.closeConnection();
			
	}
	
	/**
	 * Metodo que crea un bono de reservas infantiles
	 * Tablas usadas:
	 *	-RESERVA: Para crear el bono de reservas
	 *Atributos de la tabla usados:
	 *	-usuario: Usuario de la reserva
	 *	-modalidad_reserva: Modalidad de la reserva
	 *	-tipo_Reserva: Tipo de reserva
	 *	-participantes_infantiles: Número de participantes infantiles
	 *	-fecha: Fecha de la reserva
	 *	-duracion: Duracion de la reserva
	 *	-descuento: Posible descuento a aplicar
	 *	-precio: Precio de la reserva
	 *	-pista: Pista de la reserva
	 *	-idBono: ID del bono
	 * @param reservaInfantil Reserva de adultos a crear
	 * @param idBono ID del bono
	 */
	
	public void crearReservaInfantilBono(ReservaInfantilDTO reservaInfantil, int idBono) {
		
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("crearReservaInfantilBonoSTM"));
			ps.setString(1, reservaInfantil.getIdUsuario());
			ps.setInt(2, idBono);
			ps.setInt(3,reservaInfantil.getParticipantesInfantiles());
			ps.setString(4, reservaInfantil.getFecha());
			ps.setInt(5, reservaInfantil.getDuracion());
			ps.setFloat(6, reservaInfantil.getDescuento());
			ps.setFloat(7, reservaInfantil.getPrecio());
			ps.setString(8, reservaInfantil.getIdPista());
			
			ps.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		connection.closeConnection();
			
	}
	
	/**
	 * Metodo que consulta reservas a partir del día siguiente
	 * Tablas usadas:
	 *	-RESERVA: Para consultar las reservas
	 *Atributos de la tabla usados:
	 *	-usuario: Usuario de la reserva
	 *	-modalidad_reserva: Modalidad de la reserva
	 *	-tipo_Reserva: Tipo de reserva
	 *	-participantes_infantiles: Número de participantes infantiles
	 *	-fecha: Fecha de la reserva
	 *	-duracion: Duracion de la reserva
	 *	-descuento: Posible descuento a aplicar
	 *	-precio: Precio de la reserva
	 *	-pista: Pista de la reserva
	 * @param modalidad Modalidad de la reserva
	 * @param usuario Usuario de la reserva
	 * @return reservas Lista de reservas
	 */
	
	//NEW -- Consultar reservas a partir de mañana de un usuario
	public List<ReservaInfantilDTO> consultarReservasInfantilFuturas(String modalidad, String usuario){
		List<ReservaInfantilDTO> reservas = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerReservasInfantilbyFechaSTM"));
			ps.setString(1, modalidad);
			ps.setString(2, usuario);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int idReserva = rs.getInt("id_Reserva");
				int participantes_infantiles = rs.getInt("participantes_infantiles");
				Date fecha = new Date(rs.getTimestamp("fecha").getTime());
				int duracion = rs.getInt("duracion");
				float descuento = rs.getFloat("descuento");
				float precio = rs.getFloat("precio");
				String pista = rs.getString("pista");
				
				ReservaInfantilDTO reservainfantil = new ReservaInfantilDTO();
				
				reservainfantil.setIdReserva(idReserva);
				reservainfantil.setModalidad(modalidad);
				reservainfantil.setIdUsuario(usuario);
				reservainfantil.setParticipantesInfantiles(participantes_infantiles);
				reservainfantil.setFecha(fecha);
				reservainfantil.setDuracion(duracion);
				reservainfantil.setDescuento(descuento);
				reservainfantil.setPrecio(precio);
				reservainfantil.setIdPista(pista);
				reservas.add(reservainfantil);
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		}
		
		
		
		connection.closeConnection();
		return reservas;
	}
	
	
	/**
	 * Metodo que consulta reservas infantiles en un rango de fechas
	 * Tablas usadas:
	 *	-RESERVA: Para consultar las reservas
	 *Atributos de la tabla usados:
	 *	-usuario: Usuario de la reserva
	 *	-fecha: Modalidad de la reserva
	 *	-tipo_Reserva: Tipo de reserva (infantil)
	 * @param fechaInicio Fecha de inicio
	 * @param fechaFIn Fecha de fin
	 * @param usuario Usuario que hace las reservas
	 * @return reservas Lista de reservas
	 */
	//NEW -- Consultar reservas de un usuario en un rango de fechas dado
	public List<ReservaInfantilDTO> consultarReservasInfantilRango(String fechaInicio, String fechaFin, String usuario){
		
		List<ReservaInfantilDTO> reservas = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerReservasInfantilRangoSTM"));
			
			ps.setString(1, usuario);
			ps.setString(2, fechaInicio);
			ps.setString(3, fechaFin);
		
		
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {

				int idReserva = rs.getInt("id_Reserva");
				String modalidad = rs.getString("modalidad_reserva");
				int participantes_infantiles = rs.getInt("participantes_infantiles");
				Date fecha = new Date(rs.getTimestamp("fecha").getTime());
				int duracion = rs.getInt("duracion");
				float descuento = rs.getFloat("descuento");
				float precio = rs.getFloat("precio");
				String pista = rs.getString("pista");
				
				ReservaInfantilDTO reservainfantil = new ReservaInfantilDTO();
				
				reservainfantil.setIdReserva(idReserva);
				reservainfantil.setModalidad(modalidad);
				reservainfantil.setIdUsuario(usuario);
				reservainfantil.setParticipantesInfantiles(participantes_infantiles);
				reservainfantil.setFecha(fecha);
				reservainfantil.setDuracion(duracion);
				reservainfantil.setDescuento(descuento);
				reservainfantil.setPrecio(precio);
				reservainfantil.setIdPista(pista);
				reservas.add(reservainfantil);
			}
			
			rs.close();
			ps.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		}
		
		connection.closeConnection();
		return reservas;
		
	}
	
	
	/**
	 * Metodo que consulta reservas infantiles
	 * Tablas usadas:
	 *	-RESERVA: Para consultar las reservas
	 *Atributos de la tabla usados:
	 *	-usuario: Usuario de la reserva
	 *	-fecha: Modalidad de la reserva
	 *	-tipo_Reserva: Tipo de reserva (infantil)
	 * @param fechaInicio Fecha de inicio
	 * @param fechaFIn Fecha de fin
	 * @return reservas Lista de reservas
	 */
	
	//NEW -- Consultar todas las reservas de cualquier usuario en un rango de fechas dado
		public List<ReservaInfantilDTO> consultarReservasInfantil(String fechaInicio, String fechaFin){
			
			List<ReservaInfantilDTO> reservas = new ArrayList<>();
			DBConnection connection = new DBConnection();
			con = connection.getConnection();
			
			try {
				PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerReservasInfantilSTM"));
				
				ps.setString(1, fechaInicio);
				ps.setString(2, fechaFin);
			
			
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {

					int idReserva = rs.getInt("id_Reserva");
					String modalidad = rs.getString("modalidad_reserva");
					String usuario = rs.getString("usuario");
					int participantes_infantiles = rs.getInt("participantes_infantiles");
					Date fecha = new Date(rs.getTimestamp("fecha").getTime());
					int duracion = rs.getInt("duracion");
					float descuento = rs.getFloat("descuento");
					float precio = rs.getFloat("precio");
					String pista = rs.getString("pista");
					
					ReservaInfantilDTO reservainfantil = new ReservaInfantilDTO();
					
					reservainfantil.setIdReserva(idReserva);
					reservainfantil.setModalidad(modalidad);
					reservainfantil.setIdUsuario(usuario);
					reservainfantil.setParticipantesInfantiles(participantes_infantiles);
					reservainfantil.setFecha(fecha);
					reservainfantil.setDuracion(duracion);
					reservainfantil.setDescuento(descuento);
					reservainfantil.setPrecio(precio);
					reservainfantil.setIdPista(pista);
					reservas.add(reservainfantil);
				}
				
				rs.close();
				ps.close();
			
			} catch (SQLException e) {
				e.printStackTrace();
				
			} catch(IllegalArgumentException e){
				e.printStackTrace();
			}
			
			connection.closeConnection();
			return reservas;
			
		}
		
		
		/**
		 * Metodo que consulta reservas infantiles futuras en un rango
		 * Tablas usadas:
		 *	-RESERVA: Para consultar las reservas
		 *Atributos de la tabla usados:
		 *	-usuario: Usuario de la reserva
		 *	-fecha: Modalidad de la reserva
		 *	-tipo_Reserva: Tipo de reserva (infantil)
		 * @param modalidad Modalidad de la reserva
		 * @param fechaInicio Fecha de inicio
		 * @param fechaFIn Fecha de fin
		 * @return reservas Lista de reservas
		 */
		///NEW -- Consultar todas las reservas de cualquier usuario en un rango dado y a partir de mañana
		public List<ReservaInfantilDTO> consultarReservasInfantilRangoFuturas(String modalidad, String fechaInicio, String fechaFin){
					
			List<ReservaInfantilDTO> reservas = new ArrayList<>();
			DBConnection connection = new DBConnection();
			con = connection.getConnection();
					
			try {
				PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerReservasInfantilRangobyFechaSTM"));
						
				ps.setString(1, modalidad);
				ps.setString(2, fechaInicio);
				ps.setString(3, fechaFin);
					
					
				ResultSet rs = ps.executeQuery();
						
				while (rs.next()) {

					int idReserva = rs.getInt("id_Reserva");
					String usuario = rs.getString("usuario");
					int participantes_infantiles = rs.getInt("participantes_infantiles");
					Date fecha = new Date(rs.getTimestamp("fecha").getTime());
					int duracion = rs.getInt("duracion");
					float descuento = rs.getFloat("descuento");
					float precio = rs.getFloat("precio");
					String pista = rs.getString("pista");
							
					ReservaInfantilDTO reservainfantil = new ReservaInfantilDTO();
							
					reservainfantil.setIdReserva(idReserva);
					reservainfantil.setModalidad(modalidad);
					reservainfantil.setIdUsuario(usuario);
					reservainfantil.setParticipantesInfantiles(participantes_infantiles);
					reservainfantil.setFecha(fecha);
					reservainfantil.setDuracion(duracion);
					reservainfantil.setDescuento(descuento);
					reservainfantil.setPrecio(precio);
					reservainfantil.setIdPista(pista);
					reservas.add(reservainfantil);
				}
						
				rs.close();
				ps.close();
					
			} catch (SQLException e) {
				e.printStackTrace();
						
			} catch(IllegalArgumentException e){
				e.printStackTrace();
			}
					
			connection.closeConnection();
			return reservas;
					
	}
	

}

