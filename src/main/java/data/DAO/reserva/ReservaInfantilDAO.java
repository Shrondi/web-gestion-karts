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
	 * Constructor sin parametros del DAO 
	 */
	
	public ReservaInfantilDAO(Properties properties) {
		
		prop = properties;
	}
	
	/**
	 * Metodo para incluir una nueva reserva de tipo Infantil en la base de datos
	 * 
	 * @param reservaInfantil reserva de tipo Infantil a incluir en la base de datos
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
	 * Metodo que crea una reserva de tipo Infantil asociada a un bono
	 * 
	 * @param reservaInfantil Reserva de tipo Infantil a incluir en la base de datos, asociada a un bono
	 * @param idBono Identificador del bono al que asociar a la reserva
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
	 * Modifica una entrada de reserva de tipo Infantil de la base de datos
	 * 
	 * @param reservaInfantil reserva de tipo Infantil existente en la base de datos a modificar
	 */
	
	public void modificarReservaInfantil(ReservaInfantilDTO reservaInfantil) {
		
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("modificarReservaInfantilSTM"));
			ps.setInt(2,reservaInfantil.getParticipantesInfantiles());
			ps.setString(3, reservaInfantil.getFecha());
			ps.setInt(4, reservaInfantil.getDuracion());
			ps.setFloat(6, reservaInfantil.getPrecio());
			ps.setString(7, reservaInfantil.getIdPista());
			
			ps.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		connection.closeConnection();
		
	}
	
	
	//NEW
	public List<ReservaInfantilDTO> consultarReservasInfantilFuturas(String usuario){
		List<ReservaInfantilDTO> reservas = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		try {
			Statement stmt = con.createStatement();
			
			String query = prop.getProperty("obtenerReservasInfantilbyFechaSTM") + String.format("'%s'", usuario);
			ResultSet rs = stmt.executeQuery(query);
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
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		}
		
		
		
		connection.closeConnection();
		return reservas;
	}
	
	//NEW
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
				int participantes_infantiles = rs.getInt("participantes_infantiles");
				Date fecha = new Date(rs.getTimestamp("fecha").getTime());
				int duracion = rs.getInt("duracion");
				float descuento = rs.getFloat("descuento");
				float precio = rs.getFloat("precio");
				String pista = rs.getString("pista");
				
				ReservaInfantilDTO reservainfantil = new ReservaInfantilDTO();
				
				reservainfantil.setIdReserva(idReserva);
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
	 * 
	 * @param fecha Fecha que deben tener las reservas de tipo Infantil
	 * @param pista Pista a la que deben estar asociadas las reservas de tipo Infantil
	 * @return Lista de reservas de tipo Infantil con una fecha y pista concretas
	 */
	
	public List<ReservaInfantilDTO> consultarReservasInfantilbyFechaPista(String fecha, String pista){
		List<ReservaInfantilDTO> reservas = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		try {
			Statement stmt = con.createStatement();
			String query = prop.getProperty("obtenerReservasInfantilbyFechaPistaSTM")+String.format("'%s' AND pista='%s'", fecha, pista);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String usuario = rs.getString("usuario");
				int participantes_infantiles = rs.getInt("participantes_infantiles");
				int duracion = rs.getInt("duracion");
				float descuento = rs.getFloat("descuento");
				float precio = rs.getFloat("precio");
				
				ReservaInfantilDTO reservainfantil = new ReservaInfantilDTO();
				
				reservainfantil.setIdUsuario(usuario);
				reservainfantil.setParticipantesInfantiles(participantes_infantiles);
				reservainfantil.setDuracion(duracion);
				reservainfantil.setDescuento(descuento);
				reservainfantil.setPrecio(precio);
				reservainfantil.setIdPista(pista);
				reservas.add(reservainfantil);
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

