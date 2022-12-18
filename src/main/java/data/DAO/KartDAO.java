package data.DAO;

import data.common.DBConnection;

import business.kart.KartDTO;
import business.kart.Estado;

import java.sql.*;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Carlos Lucena Robles.
 * @author Miguel Raigón Jiménez.
 * @author Pablo Roldán Puebla.
 * @author Paloma Romero Delgado.
 * @author Kamal Abdelkader Haddu.
 * @version 1.0.0
 */

/**
 * Implementacion de la clase KartDAO
 */

public class KartDAO {

	private Connection con;
	private Properties prop;
	
	public KartDAO(Properties properties) {
		prop = properties;
	}
	
	/**
	 * Crear kart 
	 * @param kart KartDTO
	 */
	
	public void crearKart(KartDTO kart) {
		
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("crearKartSTM"));
			ps.setBoolean(1, kart.geType());
			ps.setString(2,kart.getStatus().name());
			ps.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
		connection.closeConnection();
	}
	
	/**
	 * Actualizar kart con su pista asociada 
	 * @param pista Pista asociada
	 * @param id Id del kart
	 */
	
	public void actualizarPistaKart(String pista, int id) {
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("actualizarPistaKartSTM"));
			ps.setString(1,pista);
			ps.setInt(2,id);
			ps.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		connection.closeConnection();
	}
	
	/**
	 * Actualizar la pista asociada de un kart a NULL
	 * @param id Id del kart
	 */
	//NEW
	public void eliminarPistaKart(int id) {
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("actualizarPistaKartNullSTM"));
			ps.setInt(1,id);
			ps.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		connection.closeConnection();
	}
	
	
	//NEW
	public void actualizarEstadoKart(boolean tipo, Estado estado, String nombre, int numKart) {
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("actualizarEstadoKartReservaSTM"));
			ps.setString(1, estado.name());
			ps.setBoolean(2, tipo);
			ps.setString(3,nombre);
			ps.setInt(4,numKart);
			ps.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		connection.closeConnection();
	}
	
	/**
	 * Consultar karts sin asignar a pistas y que esten disponibles
	 * @return karts Listado de karts
	 */
	//NEW
	public List<KartDTO> consultarKartsDisponibles() {
		List<KartDTO> karts = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(prop.getProperty("obtenerKartDisponibleSTM"));
			while (rs.next()) {
				
				int id = rs.getInt("id");
				Boolean type = rs.getBoolean("tipo");
				String state = rs.getString("estado");
				KartDTO kartToPush = new KartDTO();
				kartToPush.setId(id);
				kartToPush.setStatus(Estado.valueOf(state.toUpperCase()));
				kartToPush.seType(type);
				karts.add(kartToPush);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		}
		
		connection.closeConnection();
		return karts;
	}
	
	/**
	 * Obtener listado de todos los karts
	 * @return karts Listado de todos los karts
	 */
	
	public List<KartDTO> listadoKarts() {
		List<KartDTO> karts = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(prop.getProperty("obtenerTodosKartsSTM"));
			while (rs.next()) {
				
				int id = rs.getInt("id");
				Boolean type = rs.getBoolean("tipo");
				String state = rs.getString("estado");
				String pista = rs.getString("pista_asociada");
				KartDTO kartToPush = new KartDTO();
				kartToPush.setId(id);
				kartToPush.setStatus(Estado.valueOf(state.toUpperCase()));
				kartToPush.seType(type);
				kartToPush.setPista(pista);
				karts.add(kartToPush);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		}
		connection.closeConnection();
		return karts;
	}
	
	//NEW
	public void modificarEstadoKart(Estado estado, int id_kart) {
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("modificacionEstadoKartSTM"));
			ps.setString(1,estado.name());
			ps.setInt(2,id_kart);
			ps.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		connection.closeConnection();
	}
	
	
}
