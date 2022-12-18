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
	 * Constructor sin parametros del DAO 
	 */
	
	public ReservaAdultosDAO(Properties properties) {
		prop = properties;
	}
	
	/**
	 * Metodo para incluir una nueva reserva de tipo Adulto en la base de datos
	 * 
	 * @param reservaAdultos reserva de tipo Adulto a incluir en la base de datos
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
	 * Metodo que crea una reserva de tipo Adulto asociada a un bono
	 * 
	 * @param reservaAdultos Reserva de tipo Adulto a incluir en la base de datos, asociada a un bono
	 * @param idBono Identificador del bono al que asociar a la reserva
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
	 * Modifica una entrada de reserva de tipo Adulto de la base de datos
	 * 
	 * @param reservaAdultos reserva de tipo Adulto existente en la base de datos a modificar
	 */
	
	public void modificarReservaAdulto(ReservaAdultosDTO reservaAdultos) {
		
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("crearReservaAdultosSTM"));
			ps.setInt(2,reservaAdultos.getParticipantesAdultos());
			ps.setString(3, reservaAdultos.getFecha());
			ps.setInt(4, reservaAdultos.getDuracion());
			ps.setFloat(6, reservaAdultos.getPrecio());
			ps.setString(7, reservaAdultos.getIdPista());
			
			ps.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		connection.closeConnection();
			
	}
	
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
	
	////NEW -- Consultar todas las reservas de cualquier usuario en un rango de fechas dado
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
		
		//NEW -- Consultar todas las reservas de cualquier usuario en un rango dado y a partir de mañana
				public List<ReservaAdultosDTO> consultarReservasAdultosRangoFuturas(String fechaInicio, String fechaFin){
					List<ReservaAdultosDTO> reservas = new ArrayList<>();
					DBConnection connection = new DBConnection();
					con = connection.getConnection();
					
					try {
						PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerReservasAdultosRangobyFechaSTM"));
						
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
	 * 
	 * @param Fecha Fecha que deben tener las reservas de tipo Adulto
	 * @param Pista Pista a la que deben estar asociadas las reservas de tipo Adulto
	 * @return Lista de reservas de tipo Adulto con una fecha y pista concretas
	 */
	public List<ReservaAdultosDTO> consultarReservasAdultosbyFechaPista(String fecha, String pista){
		List<ReservaAdultosDTO> reservas = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		try {
			Statement stmt = con.createStatement();
			String query = prop.getProperty("obtenerReservasAdultosbyFechaPistaSTM")+String.format("'%s' AND pista='%s'", fecha, pista);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String usuario = rs.getString("usuario");
				int participantes_adultos = rs.getInt("participantes_adultos");
				int duracion = rs.getInt("duracion");
				float descuento = rs.getFloat("descuento");
				float precio = rs.getFloat("precio");
				
				ReservaAdultosDTO reservaadulto = new ReservaAdultosDTO();
				
				reservaadulto.setIdUsuario(usuario);
				reservaadulto.setParticipantesAdultos(participantes_adultos);
				reservaadulto.setDuracion(duracion);
				reservaadulto.setDescuento(descuento);
				reservaadulto.setPrecio(precio);
				reservaadulto.setIdPista(pista);
				reservas.add(reservaadulto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		}
		
		connection.closeConnection();
		return reservas;
	}
	

	
	
	
}
