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
	 * Constructor sin parametros del DAO 
	 */
	
	public ReservaFamiliarDAO(Properties properties) {
		
		prop = properties;
	}
	
	/**
	 * Metodo para incluir una nueva reserva de tipo Familiar en la base de datos
	 * 
	 * @param reservaFamiliar reserva de tipo Familiar a incluir en la base de datos
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
	 * Metodo que crea una reserva de tipo Familiar asociada a un bono
	 * 
	 * @param reservaAFamiliar Reserva de tipo Familiar a incluir en la base de datos, asociada a un bono
	 * @param idBono Identificador del bono al que asociar a la reserva
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
	 * Modifica una entrada de reserva de tipo Familiar de la base de datos
	 * 
	 * @param reservaFamiliar reserva de tipo Familiar existente en la base de datos a modificar
	 */
	
	public void modificarReservaFamiliar(ReservaFamiliarDTO reservaFamiliar) {
		
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("crearReservaFamiliarSTM"));
			ps.setInt(2,reservaFamiliar.getParticipantesInfantiles());
			ps.setInt(3,reservaFamiliar.getParticipantesAdultos());
			ps.setString(4, reservaFamiliar.getFecha());
			ps.setInt(5, reservaFamiliar.getDuracion());
			ps.setFloat(7, reservaFamiliar.getPrecio());
			ps.setString(8, reservaFamiliar.getIdPista());
			
			ps.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		connection.closeConnection();
			
	}
	
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
