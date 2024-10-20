package data.DAO.reserva;

import data.common.DBConnection;
import business.reserva.ReservaFamiliarDTO;

import java.util.Date;
import java.sql.*;
import java.util.ArrayList;
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
 * DAO correspondiente a las operaciones necesarias para la manipulación de Reservas Familiares
 *
 */

public class ReservaFamiliarDAO {

	private Connection con;
	private Properties prop;
	
	/**
	 * Constructor del DAO de Usuario
	 * @param properties Objeto properties que contiene las consultas relativas a la BD
	 */
	
	public ReservaFamiliarDAO(Properties properties) {
		
		prop = properties;
	}
	
	/**
	 * Se usa para incluir una nueva reserva familiar
	 * Tablas usadas:
	 *	-RESERVA: Para crear la reserva
	 *Atributos de la tabla usados:
	 *	-usuario: Usuario de la reserva
	 *	-modalidad_reserva: Modalidad de la reserva
	 *	-tipo_Reserva: Tipo de reserva
	 *	-participantes_adultos: Número de participantes adultos
	 *  -participantes_adultos: Número de participantes infantiles 
	 *	-fecha: Fecha de la reserva
	 *	-duracion: Duracion de la reserva
	 *	-descuento: Posible descuento a aplicar
	 *	-precio: Precio de la reserva
	 *	-pista: Pista de la reserva
	 * @param reservas Reserva familiar a crear
	 */
	
	public void crearReservaFamiliar(ReservaFamiliarDTO reservaFamiliar) {
		
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("crearReservaFamiliarSTM"));
			ps.setString(1, reservaFamiliar.getIdUsuario());
			ps.setInt(2,reservaFamiliar.getParticipantesInfantiles());
			ps.setInt(3,reservaFamiliar.getParticipantesAdultos());
			ps.setString(4, reservaFamiliar.getFecha());
			ps.setInt(5, reservaFamiliar.getDuracion());
			ps.setFloat(6, reservaFamiliar.getDescuento());
			ps.setFloat(7, reservaFamiliar.getPrecio());
			ps.setString(8, reservaFamiliar.getIdPista());
			
			ps.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		connection.closeConnection();
			
	}
	
	/**
	 * Metodo que crea un bono de reservas familiares
	 * Tablas usadas:
	 *	-RESERVA: Para crear el bono de reservas
	 *Atributos de la tabla usados:
	 *	-usuario: Usuario de la reserva
	 *	-modalidad_reserva: Modalidad de la reserva
	 *	-tipo_Reserva: Tipo de reserva
	 *	-participantes_adultos: Número de participantes adultos
	 *	-participantes_infantiles: Número de participantes infantiles
	 *	-fecha: Fecha de la reserva
	 *	-duracion: Duracion de la reserva
	 *	-descuento: Posible descuento a aplicar
	 *	-precio: Precio de la reserva
	 *	-pista: Pista de la reserva
	 *	-idBono: ID del bono
	 * @param reservaFamiliar Reserva familiar a crear
	 * @param idBono ID del bono
	 */
	
	public void crearReservaFamiliarBono(ReservaFamiliarDTO reservaFamiliar, int idBono) {
		
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("crearReservaFamiliarBonoSTM"));
			ps.setString(1, reservaFamiliar.getIdUsuario());
			ps.setInt(2, idBono);
			ps.setInt(3,reservaFamiliar.getParticipantesInfantiles());
			ps.setInt(4,reservaFamiliar.getParticipantesAdultos());
			ps.setString(5, reservaFamiliar.getFecha());
			ps.setInt(6, reservaFamiliar.getDuracion());
			ps.setFloat(7, reservaFamiliar.getDescuento());
			ps.setFloat(8, reservaFamiliar.getPrecio());
			ps.setString(9, reservaFamiliar.getIdPista());
			
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
	public List<ReservaFamiliarDTO> consultarReservasFamiliarFuturas(String modalidad, String usuario){
		List<ReservaFamiliarDTO> reservas = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerReservasFamiliarbyFechaSTM"));
			ps.setString(1, modalidad);
			ps.setString(2, usuario);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int idReserva = rs.getInt("id_Reserva");
				int participantes_infantiles = rs.getInt("participantes_infantiles");
				int participantes_adultos = rs.getInt("participantes_adultos");
				Date fecha = new Date(rs.getTimestamp("fecha").getTime());
				int duracion = rs.getInt("duracion");
				float descuento = rs.getFloat("descuento");
				float precio = rs.getFloat("precio");
				String pista = rs.getString("pista");
				
				ReservaFamiliarDTO reservafamiliar = new ReservaFamiliarDTO();
				
				reservafamiliar.setIdReserva(idReserva);
				reservafamiliar.setModalidad(modalidad);
				reservafamiliar.setIdUsuario(usuario);
				reservafamiliar.setParticipantesInfantiles(participantes_infantiles);
				reservafamiliar.setParticipantesAdultos(participantes_adultos);
				reservafamiliar.setFecha(fecha);
				reservafamiliar.setDuracion(duracion);
				reservafamiliar.setDescuento(descuento);
				reservafamiliar.setPrecio(precio);
				reservafamiliar.setIdPista(pista);
				reservas.add(reservafamiliar);
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
	 * Metodo que consulta reservas familiares en un rango de fechas
	 * Tablas usadas:
	 *	-RESERVA: Para consultar las reservas
	 *Atributos de la tabla usados:
	 *	-usuario: Usuario de la reserva
	 *	-fecha: Modalidad de la reserva
	 *	-tipo_Reserva: Tipo de reserva (familiar)
	 * @param fechaInicio Fecha de inicio
	 * @param fechaFIn Fecha de fin
	 * @param usuario Usuario que hace las reservas
	 * @return reservas Lista de reservas
	 */
	
	//NEW -- Consultar reservas de un usuario en un rango de fechas dado
	public List<ReservaFamiliarDTO> consultarReservasFamiliarRango(String fechaInicio, String fechaFin, String usuario){
		
		List<ReservaFamiliarDTO> reservas = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerReservasFamiliarRangoSTM"));
			
			ps.setString(1, usuario);
			ps.setString(2, fechaInicio);
			ps.setString(3, fechaFin);
			
		
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {

				int idReserva = rs.getInt("id_Reserva");
				String modalidad = rs.getString("modalidad_reserva");
				int participantes_adultos = rs.getInt("participantes_adultos");
				int participantes_infantiles = rs.getInt("participantes_infantiles");
				Date fecha = new Date(rs.getTimestamp("fecha").getTime());
				int duracion = rs.getInt("duracion");
				float descuento = rs.getFloat("descuento");
				float precio = rs.getFloat("precio");
				String pista = rs.getString("pista");
				
				ReservaFamiliarDTO reservafamiliar = new ReservaFamiliarDTO();
				
				reservafamiliar.setIdReserva(idReserva);
				reservafamiliar.setModalidad(modalidad);
				reservafamiliar.setIdUsuario(usuario);
				reservafamiliar.setParticipantesAdultos(participantes_adultos);
				reservafamiliar.setParticipantesInfantiles(participantes_infantiles);
				reservafamiliar.setFecha(fecha);
				reservafamiliar.setDuracion(duracion);
				reservafamiliar.setDescuento(descuento);
				reservafamiliar.setPrecio(precio);
				reservafamiliar.setIdPista(pista);
				reservas.add(reservafamiliar);
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
	 * Metodo que consulta reservas familiares
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
	
	//NEW -- Consultar todas las reservas de cualquier usuario en un rango de fechas dado
		public List<ReservaFamiliarDTO> consultarReservasFamiliar(String fechaInicio, String fechaFin){
			
			List<ReservaFamiliarDTO> reservas = new ArrayList<>();
			DBConnection connection = new DBConnection();
			con = connection.getConnection();
			
			try {
				PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerReservasFamiliarSTM"));
				
				ps.setString(1, fechaInicio);
				ps.setString(2, fechaFin);
				
			
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {

					int idReserva = rs.getInt("id_Reserva");
					String modalidad = rs.getString("modalidad_reserva");
					String usuario = rs.getString("usuario");
					int participantes_adultos = rs.getInt("participantes_adultos");
					int participantes_infantiles = rs.getInt("participantes_infantiles");
					Date fecha = new Date(rs.getTimestamp("fecha").getTime());
					int duracion = rs.getInt("duracion");
					float descuento = rs.getFloat("descuento");
					float precio = rs.getFloat("precio");
					String pista = rs.getString("pista");
					
					ReservaFamiliarDTO reservafamiliar = new ReservaFamiliarDTO();
					
					reservafamiliar.setIdReserva(idReserva);
					reservafamiliar.setModalidad(modalidad);
					reservafamiliar.setIdUsuario(usuario);
					reservafamiliar.setParticipantesAdultos(participantes_adultos);
					reservafamiliar.setParticipantesInfantiles(participantes_infantiles);
					reservafamiliar.setFecha(fecha);
					reservafamiliar.setDuracion(duracion);
					reservafamiliar.setDescuento(descuento);
					reservafamiliar.setPrecio(precio);
					reservafamiliar.setIdPista(pista);
					reservas.add(reservafamiliar);
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
		 * Metodo que consulta reservas familiares futuras en un rango
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
				public List<ReservaFamiliarDTO> consultarReservasFamiliarRangoFuturas(String modalidad, String fechaInicio, String fechaFin){
					
					List<ReservaFamiliarDTO> reservas = new ArrayList<>();
					DBConnection connection = new DBConnection();
					con = connection.getConnection();
					
					try {
						PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerReservasFamiliarRangobyFechaSTM"));
						
						ps.setString(1, modalidad);
						ps.setString(2, fechaInicio);
						ps.setString(3, fechaFin);
						
					
						ResultSet rs = ps.executeQuery();
						
						while (rs.next()) {

							int idReserva = rs.getInt("id_Reserva");
							String usuario = rs.getString("usuario");
							int participantes_adultos = rs.getInt("participantes_adultos");
							int participantes_infantiles = rs.getInt("participantes_infantiles");
							Date fecha = new Date(rs.getTimestamp("fecha").getTime());
							int duracion = rs.getInt("duracion");
							float descuento = rs.getFloat("descuento");
							float precio = rs.getFloat("precio");
							String pista = rs.getString("pista");
							
							ReservaFamiliarDTO reservafamiliar = new ReservaFamiliarDTO();
							
							reservafamiliar.setIdReserva(idReserva);
							reservafamiliar.setModalidad(modalidad);
							reservafamiliar.setIdUsuario(usuario);
							reservafamiliar.setParticipantesAdultos(participantes_adultos);
							reservafamiliar.setParticipantesInfantiles(participantes_infantiles);
							reservafamiliar.setFecha(fecha);
							reservafamiliar.setDuracion(duracion);
							reservafamiliar.setDescuento(descuento);
							reservafamiliar.setPrecio(precio);
							reservafamiliar.setIdPista(pista);
							reservas.add(reservafamiliar);
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
