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
	//NEW
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
				int asoc_karts_infantiles = rs.getInt("asoc_karts_disp_infantiles");
				int asoc_karts_adultos = rs.getInt("asoc_karts_disp_adultos");
				PistaDTO pistaToPush = new PistaDTO();
				pistaToPush.setNombre(nombre);
				pistaToPush.setDificulty(Dificultad.valueOf(dificultad.toUpperCase()));
				pistaToPush.setMaxAmmount(max_karts);
				pistaToPush.setAsocAmmountInf(asoc_karts_infantiles);
				pistaToPush.setAsocAmmountAdult(asoc_karts_adultos);
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
	/**
	 * Obtener pistas por su dificultad (infantil,familiar,adultos)
	 * @param dificultad Tipo de pista
	 * @return pistas Listado de pistas
	 */
	
	public List<PistaDTO> consultarByDif(String dificultad){
		List<PistaDTO> pistas = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		try {
			Statement stmt = con.createStatement();
			String query = prop.getProperty("obtenerPistasbyDifSTM")+dificultad;
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				Boolean estado = rs.getBoolean("estado");
				int max_karts = rs.getInt("max_karts");
				int asoc_karts_infantiles = rs.getInt("asoc_karts_disp_infantiles");
				int asoc_karts_adultos = rs.getInt("asoc_karts_disp_adultos");
				
				PistaDTO pistaToPush = new PistaDTO();
				pistaToPush.setNombre(nombre);
				pistaToPush.setEstado(estado);
				pistaToPush.setMaxAmmount(max_karts);
				pistaToPush.setAsocAmmountInf(asoc_karts_infantiles);
				pistaToPush.setAsocAmmountAdult(asoc_karts_adultos);
				pistaToPush.setDificulty(Dificultad.valueOf(dificultad.toUpperCase()));
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
	
	//NEW
	/**
	 * Obtener pistas por su numero de karts infantiles y karts adultos
	 * @param kartsInfantiles Numero de karts infantiles
	 * @param kartsAdultos Numero de karts adultos
	 * @return pistas Listado de pistas
	 */
	public List<PistaDTO> consultarByMinKarts(int kartsInfantiles, int kartsAdultos){
		List<PistaDTO> pistas = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerPistasByMinKartsSTM"));
			
			ps.setInt(1, kartsInfantiles);
			ps.setInt(2, kartsAdultos);
		
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
	
	//NEW
	/**
	 * Obtener pistas por su tipo y numero de karts infantiles y karts adultos
	 * @param tipoPista Tipo de pista (infantil,familiar,adultos)
	 * @param kartsInfantiles Numero de karts infantiles
	 * @param kartsAdultos Numero de karts adultos
	 * @return pistas Listado de pistas
	 */
	public List<PistaDTO> consultarPistas(String tipoPista, int kartsInfantiles, int kartsAdultos){
		List<PistaDTO> pistas = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("obtenerPistasSTM"));
			
			ps.setString(1, tipoPista);
			ps.setInt(2, kartsInfantiles);
			ps.setInt(3, kartsAdultos);
			ps.setInt(4, kartsInfantiles);
			ps.setInt(5, kartsAdultos);
		
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
	
	//NEW
	public List<PistaDTO> listadoPistas() {
		List<PistaDTO> pistas = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(prop.getProperty("obtenerTodasPistasSTM"));
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
	
	//NEW
	public void modificarEstadoPista(boolean estado, String nombre_pista) {
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("modificacionEstadoPistaSTM"));
			ps.setBoolean(1,estado);
			ps.setString(2,nombre_pista);
			ps.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		connection.closeConnection();
	}
	
}