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
	
	
	/**
	 * Constructor del DAO de Kart
	 * @param properties Objeto properties que contiene las consultas relativas a la BD
	 */
	
	public KartDAO(Properties properties) {
		prop = properties;
	}
	
	/**
	 * Se usa para crear un nuevo kart
	 * Tablas usadas:
	 *	-KART: Para crear el nuevo kart
	 * Atributos de la tabla usados:
	 *	-tipo: Nombre que identificará a la pista
	 *	-estado: Estado del kart (Disponible, Mantenimiento o Reservado)
	 *
	 * @param kart DTO de Kart en el que se almacenará la información del nuevo kart
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
	 * Se usa para actualizar la pista asociada a un kart
	 * Tablas usadas:
	 *	-KART: Para crear el nuevo kart
	 * Atributos de la tabla usados:
	 *	-pista_asociada: Pista que será asociada al kart
	 *	-id: ID del kart, que lo identifica
	 *
	 * @param pista Nueva pista asociada al kart
	 * @param id ID del kart cuya pista asociada se actualizará
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
	 * Se usa para eliminar la pista asociada a un kart
	 * Tablas usadas:
	 *	-KART: Para identificar el kart
	 * Atributos de la tabla usados:
	 *	-pista_asociada: Pista cuya asociación al kart será eliminada
	 *	-id: ID del kart, que lo identifica
	 *
	 * @param id ID del kart cuya pista asociada se eliminará
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
	
	
	/**
	 * Se usa para actualizar el estado de un kart
	 * Tablas usadas:
	 *	-KART: Para actualizar el estado del kart que corresponda
	 * Atributos de la tabla usados:
	 *	-estado: NUevo estado del kart
	 *	-tipo: ID del kart, que lo identifica
	 *	-pista_asociada: ID del kart, que lo identifica
	 *
	 * @param tipo Tipo de kart
	 * @param estado Estado que tomará el kart
	 * @param nombre Nombre de la pista asociada
	 * @param numKart Número máximo de karts
	 */
	
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
	 * Se usa para consultar los karts disponibles de una pista según su tipo
	 * Tablas usadas:
	 *	-KART: Para consultar los karts disponibles dependiendo de su tipo
	 * Atributos de la tabla usados:
	 *	-id: ID del kart
	 *	-tipo: Tipo de kart
	 *	-estado: Estado del kart, en este caso disponible
	 *	-pista_asociada: Pista asociada
	 * @param nombre Nombre de la pista asociada
	 * @return karts Lista de karts disponibles según el tipo correspondiente
	 */
	
	public List<KartDTO> consultarKartsPista(String nombre) {
		List<KartDTO> karts = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(prop.getProperty("obtenerKartsPistaSTM")+ String.format("'%s'", nombre));
			
			while (rs.next()) {
				int id = rs.getInt("id");
				Boolean type = rs.getBoolean("tipo");
				KartDTO kartToPush = new KartDTO();
				kartToPush.setId(id);
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
	 * Se usa para consultar los karts disponibles que estén sin asignar a una pista
	 * Tablas usadas:
	 *	-KART: Para consultar los karts sin asignar
	 * Atributos de la tabla usados:
	 *	-id: ID del kart
	 *	-tipo: Tipo de kart
	 *	-estado: Estado del kart, en este caso disponible
	 *	-pista_asociada: Pista asociada, en este caso NULL
	 * @return karts Lista de karts disponibles sin asignar
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
	 * Se usa para obtener todos los karts
	 * Tablas usadas:
	 *	-KART: Para obtener la lista de karts
	 * @return karts Lista de todos los karts 
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
	
	/**
	 * Se usa para modificar el estado de un kart
	 * Tablas usadas:
	 *	-KART: Para modificar el estado del kart que corresponda
	 * Atributos de la tabla usados:
	 *	-id: ID del kart
	 *	-estado: Nuevo estado del kart, en este caso disponible
	 * @param estado Estado al que pasa el kart
	 * @param id_kart ID del kart cuyo estado se modificará
	 */
	
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
