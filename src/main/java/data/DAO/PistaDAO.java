package data.DAO;

import data.common.DBConnection;
import business.pista.Dificultad;
import business.pista.PistaDTO;

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
 * Implementación de la clase pistaDAO
 */

public class PistaDAO{
	
	private Connection con;
	private Properties prop;
	
	public PistaDAO(Properties properties){
		prop = properties;
	}
	
	/**
	 * Crear pista
	 * @param pista PistaDTO
	 */
	
	public void CrearPista(PistaDTO pista) {
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("crearPistaSTM"));
			ps.setString(1, pista.getNombre());
			ps.setBoolean(2, pista.getEstado());
			ps.setString(3, pista.getDificulty().name());
			ps.setInt(4, pista.getMaxAmmount());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connection.closeConnection();
	}
	

	//NEW
	public void actualizarPista(String nombre, int asocKartInfantiles, int asocKartAdultos) {
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("actualizarPistaSTM"));
			ps.setInt(1, asocKartInfantiles);
			ps.setInt(2, asocKartAdultos);
			ps.setString(3, nombre);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connection.closeConnection();
	}
	
	/**
	 * Obtener pistas por su estado
	 * @param estado Estado de la pista
	 * @return pistas Listado de pistas
	 */
	
	public List<PistaDTO> consultarByEstado(Boolean estado){
		List<PistaDTO> pistas = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		try {
			Statement stmt = con.createStatement();
			String query = prop.getProperty("obtenerPistasbyStateSTM")+estado;
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				String dificultad = rs.getString("dificultad");
				int max_karts = rs.getInt("max_karts");
				int asoc_karts = rs.getInt("asoc_karts");
				PistaDTO pistaToPush = new PistaDTO();
				pistaToPush.setNombre(nombre);
				pistaToPush.setDificulty(Dificultad.valueOf(dificultad.toUpperCase()));
				pistaToPush.setMaxAmmount(max_karts);
				//pistaToPush.setAsocAmmount(asoc_karts);
				pistaToPush.setEstado(estado);
				pistas.add(pistaToPush);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		}
		
		connection.closeConnection();
		return pistas;
	}
	
	/**
	 * Comprobar si una pista existe
	 * @param nombre Nombre de la pista
	 * @return boolean
	 */
	
	public boolean consultarPistaExiste(String nombre){
		boolean flag = false;
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		try {
			Statement stmt = con.createStatement();
			String query = prop.getProperty("checkPistabyNombreSTM")+String.format("'%s')", nombre);
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				flag = rs.getBoolean(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		}
		
		connection.closeConnection();
		return flag;
	}
	
	//NEW
	public List<PistaDTO> consultarPistas(String tipoPista, int kartsInfantiles, int kartsAdultos){
		List<PistaDTO> pistas = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerPistasSTM"));
			
			ps.setString(1, tipoPista);
			ps.setInt(2, kartsInfantiles);
			ps.setInt(3, kartsAdultos);
		
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {

				String nombre = rs.getString("nombre");
				Boolean estado = rs.getBoolean("estado");
				String dificultad = rs.getString("dificultad");
				int max_karts = rs.getInt("max_karts");
				int asoc_karts_infantiles = rs.getInt("asoc_karts_disp_infantiles");
				int asoc_karts_adultos = rs.getInt("asoc_karts_disp_adultos");
				
				PistaDTO pistaToPush = new PistaDTO();
				pistaToPush.setNombre(nombre);
				pistaToPush.setEstado(estado);
				pistaToPush.setDificulty(Dificultad.valueOf(dificultad.toUpperCase()));
				pistaToPush.setMaxAmmount(max_karts);
				pistaToPush.setAsocAmmountInf(asoc_karts_infantiles);
				pistaToPush.setAsocAmmountAdult(asoc_karts_adultos);
				
				pistas.add(pistaToPush);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		}
		
		connection.closeConnection();
		return pistas;
	}
	
	
}