package data.DAO.reserva;

import data.common.DBConnection;

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
	
	/**
	 * Insertar un nuevo bono
	 * @param id ID del bono
	 * @param correo Correo del usuario
	 * @param fecha Fecha 
	 */	
	
	public void insertarBono(int id, String correo, String fecha) {
		
		DBConnection connection = new DBConnection();
		con = connection.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("crearBonoSTM"));
			ps.setInt(1,id);
			ps.setString(2,correo);
			ps.setString(3,fecha);
			
			ps.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		connection.closeConnection();
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
	
	public List<Integer> consultarBono(String usuario){
		List<Integer> ids = new ArrayList<>();
		
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		int id = 0; 
		
		try {
			Statement stmt = con.createStatement();
			String query = prop.getProperty("obtenerBonoByUsuarioSTM")+String.format("'%s'", usuario);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				id = rs.getInt("id_Bono");
				ids.add(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		}
		
		connection.closeConnection();
		return ids;
	}
	
	/**
	 * Consultar el ID del ultimo bono
	 * @return id ID del ultimo bono 
	 */		
	
	public int consultarUltimoIdBono(){
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		int id = 0;
		
		try {
			Statement stmt = con.createStatement();
			String query = prop.getProperty("obtenerIdBonoSTM");
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		}
		
		connection.closeConnection();
		return id;
	}
}
