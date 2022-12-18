package data.DAO.reserva;

import data.common.DBConnection;
import business.reserva.ReservaAdultosDTO;
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
 * DAO correspondiente a las operaciones necesarias para la manipulación de Reservas Adultas
 *
 */

public class ReservaAdultosDAO {

	private Connection con;
	private Properties prop;
	
	
	/**
	 * Constructor del DAO de Usuario
	 * @param properties Objeto properties que contiene las consultas relativas a la BD
	 */
	
	public ReservaAdultosDAO(Properties properties) {
		prop = properties;
	}
	
	/**
	 * Se usa para incluir una nueva reserva de adultos
	 * Tablas usadas:
	 *	-RESERVA: Para seleccionar todos los usuarios existentes en la BD
	 *Atributos de la tabla usados:
	 *	-usuario: Usuario de la reserva
	 *	-modalidad_reserva: Modalidad de la reserva
	 *	-tipo_Reserva: Tipo de reserva
	 *	-participantes_adultos: Número de participantes adultos
	 *	-fecha: Fecha de la reserva
	 *	-duracion: Duracion de la reserva
	 *	-descuento: Posible descuento a aplicar
	 *	-precio: Precio de la reserva
	 *	-pista: Pista de la reserva
	 * @param reservaAdultos Reserva de adultos a crear
	 */
	
	public void crearReservaAdulto(ReservaAdultosDTO reservaAdultos) {
		
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("crearReservaAdultosSTM"));
			ps.setString(1, reservaAdultos.getIdUsuario());
			ps.setInt(2,reservaAdultos.getParticipantesAdultos());
			ps.setString(3, reservaAdultos.getFecha());
			ps.setInt(4, reservaAdultos.getDuracion());
			ps.setFloat(5, reservaAdultos.getDescuento());
			ps.setFloat(6, reservaAdultos.getPrecio());
			ps.setString(7, reservaAdultos.getIdPista());
			
			ps.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		connection.closeConnection();
			
	}
	
	/**
	 * Metodo que crea un bono de reservas de adultos
	 * Tablas usadas:
	 *	-RESERVA: Para crear el bono de reservas
	 *Atributos de la tabla usados:
	 *	-usuario: Usuario de la reserva
	 *	-modalidad_reserva: Modalidad de la reserva
	 *	-tipo_Reserva: Tipo de reserva
	 *	-participantes_adultos: Número de participantes adultos
	 *	-fecha: Fecha de la reserva
	 *	-duracion: Duracion de la reserva
	 *	-descuento: Posible descuento a aplicar
	 *	-precio: Precio de la reserva
	 *	-pista: Pista de la reserva
	 *	-idBono: ID del bono
	 * @param reservaAdultos Reserva de adultos a crear
	 * @param idBono ID del bono
	 */
	public void crearReservaAdultoBono(ReservaAdultosDTO reservaAdultos, int idBono) {
		
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("crearReservaAdultosBonoSTM"));
			ps.setString(1, reservaAdultos.getIdUsuario());
			ps.setInt(2, idBono);
			ps.setInt(3,reservaAdultos.getParticipantesAdultos());
			ps.setString(4, reservaAdultos.getFecha());
			ps.setInt(5, reservaAdultos.getDuracion());
			ps.setFloat(6, reservaAdultos.getDescuento());
			ps.setFloat(7, reservaAdultos.getPrecio());
			ps.setString(8, reservaAdultos.getIdPista());
			
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
	 *	-participantes_adultos: Número de participantes adultos
	 *	-fecha: Fecha de la reserva
	 *	-duracion: Duracion de la reserva
	 *	-descuento: Posible descuento a aplicar
	 *	-precio: Precio de la reserva
	 *	-pista: Pista de la reserva
	 *	-idBono: ID del bono
	 * @param modalidad Modalidad de la reserva
	 * @param usuario Usuario de la reserva
	 * @return reservas Lista de reservas
	 */
	
	//NEW -- Consultar reservas a partir de mañana de un usuario
	public List<ReservaAdultosDTO> consultarReservasAdultosFuturas(String modalidad, String usuario){
		List<ReservaAdultosDTO> reservas = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerReservasAdultosbyFechaSTM"));
			ps.setString(1, modalidad);
			ps.setString(2, usuario);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int idReserva = rs.getInt("id_Reserva");
				int participantes_adultos = rs.getInt("participantes_adultos");
				Date fecha = new Date(rs.getTimestamp("fecha").getTime());
				int duracion = rs.getInt("duracion");
				float descuento = rs.getFloat("descuento");
				float precio = rs.getFloat("precio");
				String pista = rs.getString("pista");
				
				ReservaAdultosDTO reservaadulto = new ReservaAdultosDTO();
				
				reservaadulto.setIdReserva(idReserva);
				reservaadulto.setModalidad(modalidad);
				reservaadulto.setIdUsuario(usuario);
				reservaadulto.setParticipantesAdultos(participantes_adultos);
				reservaadulto.setFecha(fecha);
				reservaadulto.setDuracion(duracion);
				reservaadulto.setDescuento(descuento);
				reservaadulto.setPrecio(precio);
				reservaadulto.setIdPista(pista);
				reservas.add(reservaadulto);
				
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
	 * Metodo que consulta reservas de adultos en un rango de fechas
	 * Tablas usadas:
	 *	-RESERVA: Para consultar las reservas
	 *Atributos de la tabla usados:
	 *	-usuario: Usuario de la reserva
	 *	-fecha: Modalidad de la reserva
	 *	-tipo_Reserva: Tipo de reserva (adultos)
	 * @param fechaInicio Fecha de inicio
	 * @param fechaFIn Fecha de fin
	 * @param usuario Usuario que hace las reservas
	 * @return reservas Lista de reservas
	 */
	
	///NEW -- Consultar reservas de un usuario en un rango de fechas dado
	public List<ReservaAdultosDTO> consultarReservasAdultosRango(String fechaInicio, String fechaFin, String usuario){
		List<ReservaAdultosDTO> reservas = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerReservasAdultosRangoSTM"));
			
			ps.setString(1, usuario);
			ps.setString(2, fechaInicio);
			ps.setString(3, fechaFin);
			
		
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {

				int idReserva = rs.getInt("id_Reserva");
				String modalidad = rs.getString("modalidad_reserva");
				int participantes_adultos = rs.getInt("participantes_adultos");
				Date fecha = new Date(rs.getTimestamp("fecha").getTime());
				int duracion = rs.getInt("duracion");
				float descuento = rs.getFloat("descuento");
				float precio = rs.getFloat("precio");
				String pista = rs.getString("pista");
				
				ReservaAdultosDTO reservaadulto = new ReservaAdultosDTO();
				
				reservaadulto.setIdReserva(idReserva);
				reservaadulto.setModalidad(modalidad);
				reservaadulto.setIdUsuario(usuario);
				reservaadulto.setParticipantesAdultos(participantes_adultos);
				reservaadulto.setFecha(fecha);
				reservaadulto.setDuracion(duracion);
				reservaadulto.setDescuento(descuento);
				reservaadulto.setPrecio(precio);
				reservaadulto.setIdPista(pista);
				reservas.add(reservaadulto);
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
	 * Metodo que consulta reservas de adultos
	 * Tablas usadas:
	 *	-RESERVA: Para consultar las reservas
	 *Atributos de la tabla usados:
	 *	-usuario: Usuario de la reserva
	 *	-fecha: Modalidad de la reserva
	 *	-tipo_Reserva: Tipo de reserva (adultos)
	 * @param fechaInicio Fecha de inicio
	 * @param fechaFIn Fecha de fin
	 * @return reservas Lista de reservas
	 */
		public List<ReservaAdultosDTO> consultarReservasAdultos(String fechaInicio, String fechaFin){
			List<ReservaAdultosDTO> reservas = new ArrayList<>();
			DBConnection connection = new DBConnection();
			con = connection.getConnection();
			
			try {
				PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerReservasAdultosSTM"));
				
				ps.setString(1, fechaInicio);
				ps.setString(2, fechaFin);
				
			
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {

					int idReserva = rs.getInt("id_Reserva");
					String modalidad = rs.getString("modalidad_reserva");
					String usuario = rs.getString("usuario");
					int participantes_adultos = rs.getInt("participantes_adultos");
					Date fecha = new Date(rs.getTimestamp("fecha").getTime());
					int duracion = rs.getInt("duracion");
					float descuento = rs.getFloat("descuento");
					float precio = rs.getFloat("precio");
					String pista = rs.getString("pista");
					
					ReservaAdultosDTO reservaadulto = new ReservaAdultosDTO();
					
					reservaadulto.setIdReserva(idReserva);
					reservaadulto.setModalidad(modalidad);
					reservaadulto.setIdUsuario(usuario);
					reservaadulto.setParticipantesAdultos(participantes_adultos);
					reservaadulto.setFecha(fecha);
					reservaadulto.setDuracion(duracion);
					reservaadulto.setDescuento(descuento);
					reservaadulto.setPrecio(precio);
					reservaadulto.setIdPista(pista);
					reservas.add(reservaadulto);
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
		 * Metodo que consulta reservas de adultos futuras en un rango
		 * Tablas usadas:
		 *	-RESERVA: Para consultar las reservas
		 *Atributos de la tabla usados:
		 *	-usuario: Usuario de la reserva
		 *	-fecha: Modalidad de la reserva
		 *	-tipo_Reserva: Tipo de reserva (adultos)
		 * @param modalidad Modalidad de la reserva
		 * @param fechaInicio Fecha de inicio
		 * @param fechaFIn Fecha de fin
		 * @return reservas Lista de reservas
		 */
		
		//NEW -- Consultar todas las reservas de cualquier usuario en un rango dado y a partir de mañana
		public List<ReservaAdultosDTO> consultarReservasAdultosRangoFuturas(String modalidad, String fechaInicio, String fechaFin){
			List<ReservaAdultosDTO> reservas = new ArrayList<>();
			DBConnection connection = new DBConnection();
			con = connection.getConnection();
					
			try {
				PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerReservasAdultosRangobyFechaSTM"));
						
				ps.setString(1, modalidad);
				ps.setString(2, fechaInicio);
				ps.setString(3, fechaFin);
						
					
				ResultSet rs = ps.executeQuery();
						
				while (rs.next()) {

					int idReserva = rs.getInt("id_Reserva");
					String usuario = rs.getString("usuario");
					int participantes_adultos = rs.getInt("participantes_adultos");
					Date fecha = new Date(rs.getTimestamp("fecha").getTime());
					int duracion = rs.getInt("duracion");
					float descuento = rs.getFloat("descuento");
					float precio = rs.getFloat("precio");
					String pista = rs.getString("pista");
							
					ReservaAdultosDTO reservaadulto = new ReservaAdultosDTO();
							
					reservaadulto.setIdReserva(idReserva);
					reservaadulto.setModalidad(modalidad);
					reservaadulto.setIdUsuario(usuario);
					reservaadulto.setParticipantesAdultos(participantes_adultos);
					reservaadulto.setFecha(fecha);
					reservaadulto.setDuracion(duracion);
					reservaadulto.setDescuento(descuento);
					reservaadulto.setPrecio(precio);
					reservaadulto.setIdPista(pista);
					reservas.add(reservaadulto);
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
