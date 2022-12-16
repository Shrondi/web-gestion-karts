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
	
	public ReservaDAO(Properties properties) {
		
		prop = properties;
	}
	
	//NEW
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
	
	
	/**
	 * Insertar un nuevo bono
	 * @param correo Correo del usuario
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
	public int consultarSesionesBono(String correo, int idBono) {
			
		int sesiones = 0;
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("consultarSesionesBonoSTM"));

			ps.setInt(1,idBono);
			ps.setString(2, correo);
				
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				sesiones = rs.getInt("numero_sesiones");
			}
				
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
				
			connection.closeConnection();
			return sesiones;
	}
		
	//NEW
	public int actualizarFechaBono(int idBono, java.util.Date fecha) {
					
		int sesiones = 0;
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
		PreparedStatement ps = con.prepareStatement(prop.getProperty("actualizarFechaBonoSTM"));

		ps.setDate(1, new Date(fecha.getTime()));
		ps.setInt(2, idBono);
						
		ps.executeUpdate();
					
		} catch(SQLException e) {
			e.printStackTrace();
		}
					
			connection.closeConnection();
			return sesiones;
	}
				
	//NEW
	public int actualizarSesionesBono(int idBono) {
					
		int sesiones = 0;
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			PreparedStatement ps2 = con.prepareStatement(prop.getProperty("actualizarSesionesBonoSTM"));
			ps2.setInt(1, idBono);
						
			ps2.executeUpdate();
					
		} catch(SQLException e) {
				e.printStackTrace();
		}
					
		connection.closeConnection();
		return sesiones;
	}
	
	//NEW
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
	 * Borrar una reserva de un bono
	 * @param id ID del bono
	 */		
	
	public void borrarReservaBono(int id) {
		
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("cancelarReservaBonoSTM")+id);
			
			ps.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		connection.closeConnection();
	}
	
	/**
	 * Borrar un bono
	 * @param id ID del bono
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
	 * Consultar un bono
	 * @param usuario Usuario poseedor del bono
	 * @return ids Lista de IDs de los bonos
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
