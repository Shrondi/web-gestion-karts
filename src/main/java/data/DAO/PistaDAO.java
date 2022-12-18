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
	
	
	/**
	 * Constructor del DAO de Pista
	 * @param properties Objeto properties que contiene las consultas relativas a la BD
	 */
	
	
	public PistaDAO(Properties properties){
		prop = properties;
	}
	
	/**
	 * Se usa para crear una nueva pista
	 * Tablas usadas:
	 *	-PISTA: Para insertar el valor de los distintos atributos que componen la tabla
	 * Atributos de la tabla usados:
	 *	-nombre: Nombre que identificará a la pista
	 *	-estado: Estado de la pista (True si es Dsiponible o False en caso contrario)
	 *	-dificultad: Dificultad de la pista (Infantil, Familiar o Adultos)
	 *	-max_karts: Número máximo de karts permitidos en la pista
	 *
	 * @param pista DTO de Pista en el que se almacenará la información de la nueva pista
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
	

	/**
	 * Se usa para actualizar el número de karts infantiles y de adultos disponibles asociados a esa pista
	 * Tablas usadas:
	 *	-PISTA: Para modificar el número de karts infantiles y de adultos disponibles
	 * Atributos de la tabla usados:
	 *	-asoc_karts_disp_infantiles: Nuevo número de karts infantiles asociados disponibles
	 *	-asoc_karts_disp_adultos: Nuevo número de karts infantiles asociados disponibles
	 *	-nombre: Nombre de la pista a modificar
	 *
	 * @param nombre Nombre de la pista elegida
	 * @param asocKartInfantiles Número de karts asociados infantiles
	 * @param asocKartAdultos Número de karts asociados de adultos
	 */
	
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
	 * Se usa para consultar las pistas que pueden asignarse a un kart
	 * Tablas usadas:
	 *	-PISTA: Para poder obtener las pistas con posibilidad de asignación a un kart
	 * Atributos de la tabla usados:
	 *	-estado: Estado de la pista. En este caso, debe estar disponible y, por tanto, tomar el valor 1.
	 *	-asoc_karts_disp_infantiles: Número de karts infantiles disponibles
	 *	-asoc_karts_disp_adultos: Número de karts de adultos disponibles
	 *	-max_karts: Número máximo de karts que pueda tener la pista. Debe ser mayor a la suma de karts infantiles y de adultos disponibles.
	 *	-nombre: Nombre de la pista a modificar
	 *
	 * @return pistas Lista de pistas disponibles para ser asignadas a un kart
	 */
	//NEW
	public List<PistaDTO> consultarPistasAsignacion(){
		List<PistaDTO> pistas = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		try {
			Statement stmt = con.createStatement();
			String query = prop.getProperty("obtenerPistasAsignacionSTM");
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
	 * Se usa para comprobar si una pista existe
	 * Tablas usadas:
	 *	-PISTA: Para poder obtener la pista en caso de que exista
	 * Atributos de la tabla usados:
	 *	-nombre: Nombre de la pista, que la identifica
	 *
	 * @param nombre Nombre de la pista
	 * @return flag Booleano que contiene True si la pista existe y False en caso contrario
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
	 * Se usa para obtener pistas disponibles según su dificultad
	 * Tablas usadas:
	 *	-PISTA: Para poder obtener las pistas con la dificultad elegida
	 * Atributos de la tabla usados:
	 *	-estado: Estado de la pista. En este caso, debe estar disponible y, por tanto, tomar el valor 1.
	 *	-dificultad: Dificultad de la pista (Infantil, Familiar o Adultos)
	 * @param dificultad Dificultad de las pistas
	 * @return pistas Lista de pistas con la dificultad correspondiente
	 */
	
	public List<PistaDTO> consultarByDif(Dificultad dificultad){
		List<PistaDTO> pistas = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		try {
			Statement stmt = con.createStatement();
			String query = prop.getProperty("obtenerPistasbyDifSTM")+String.format("'%s'", dificultad.name());
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
				pistaToPush.setDificulty(dificultad);
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
	 * Se usa para obtener una lista de pistas en función del número disponible de karts infantiles y adultos
	 * Tablas usadas:
	 *	-PISTA: Para poder obtener las pistas según el número disponible de karts infantiles y adultos disponibles
	 * Atributos de la tabla usados:
	 *	-estado: Estado de la pista. En este caso, debe estar disponible y, por tanto, tomar el valor 1.
	 *	-asoc_karts_disp_infantiles: Karts infantiles disponibles. Debe ser mayor o igual que el número de karts infantiles pasado como parámetro
	 *	-asoc_karts_disp_adultos: Karts de adultos disponibles. Debe ser mayor o igual que el número de karts de adultos pasado como parámetro.
	 * @param kartsInfantiles Número de karts infantiles
	 * @param kartsAdultos Número de karts de adultos
	 * @return pistas Lista de pistas con menor o igual número de karts infantiles y de adultos
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
	 * Se usa para consultar pistas por su tipo y por el número de karts infantiles y de adultos
	 * Tablas usadas:
	 *	-PISTA: Para poder obtener las pistas según el tipo de pista y el número de karts infantiles y adultos disponibles
	 * Atributos de la tabla usados:
	 *  -dificultad: Dificultad de la pista
	 *	-estado: Estado de la pista. En este caso, debe estar disponible y, por tanto, tomar el valor 1.
	 *	-asoc_karts_disp_adultos: Número de karts de tipo adulto disponibles
	 *	-asoc_karts_disp_infantiles: Número de karts de tipo infantil disponibles
	 * @param tipoPista Tipo de pista
	 * @param kartsInfantiles Número de karts infantiles
	 * @param kartsAdultos Número de karts de adultos
	 * @return pistas Lista de pistas con menor o igual número de karts infantiles y de adultos
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
	 * Se usa para obtener un listado con todas las pistas
	 * Tablas usadas:
	 *	-PISTA: Para poder obtener todas las pistas
	 *
	 * @return pistas Listado de todas las pistas
	 */
	
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
	
	/**
	 * Se usa para modificar el estado de una pista
	 * Tablas usadas:
	 *	-PISTA: Para poder modificar el estado de la pista deseada
	 * Atributos de la tabla usados:
	 *  -nombre: Nombre de la pista
	 *	-estado: Estado nuevo que tendrá la pista
	 *
	 * @param estado Estado al que pasará la pista
	 * @param nombre_pista Nombre de la pista a modificar
	 */
	
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