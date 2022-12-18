package data.DAO;

import data.common.DBConnection;
import business.usuario.UsuarioDTO;

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
 * Implementación de la clase usuarioDAO
 *
 */

public class UsuarioDAO{
	
	private Connection con;
	private Properties prop;
	
	public UsuarioDAO(Properties properties){
		prop = properties;
	}
	
	//NEW
	public boolean usuarioExiste(String correo){
		boolean check = false;
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("usuarioSTM"));
			
			ps.setString(1, correo);
			
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
	
	/**
	 * Dar de alta a un usuario
	 * @param usuarioDTO
	 */
	
	public void altaUsuario(UsuarioDTO usuario) {
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("altaUsuarioSTM"));
			ps.setString(1, usuario.getCorreo());
			ps.setString(2, usuario.getNombre());
			ps.setString(3, usuario.getApellidos());
			ps.setString(4, usuario.getFechaNacimiento());
			ps.setString(5, usuario.getPassWord());
			ps.setBoolean(6,usuario.getAdmin());
			ps.setString(7, usuario.getFechaInscripcion());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		connection.closeConnection();
	}
	
	/**
	 * Modificar todos los campos de usuario salvo correo
	 * puesto que correo es PK y si tiene reservas hechas salta error
	 * @param usuarioDTO
	 */
	
	public void modificarUsuario(UsuarioDTO usuario) {
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("modificarUsuarioSTM"));
			ps.setString(1, usuario.getNombre());
			ps.setString(2, usuario.getApellidos());
			ps.setString(3, usuario.getFechaNacimiento());
			ps.setString(4, usuario.getPassWord());
			ps.setString(5, usuario.getCorreo());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		connection.closeConnection();
	}

	/**
	 * Obtener usuario por su correo
	 * @param correo Correo del usuario
	 * @return usuario Usuario buscado
	 */
	
	public UsuarioDTO obtenerUsuario(String correo){
		UsuarioDTO usuario = null;
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(prop.getProperty("obtenerUsuariobyEmailSTM")+String.format("'%s'", correo));
			while (rs.next()) {
				String passWord = rs.getString("password");
				String nombre = rs.getString("nombre");
				String apellidos = rs.getString("apellidos");
				String fechaNacimiento = rs.getString("fecha_Nacimiento");
				String fechaInscripcion = rs.getString("fecha_Inscripcion");
				Boolean admin = rs.getBoolean("administrador");
				
				usuario = new UsuarioDTO();
				usuario.setPassWord(passWord);
				usuario.setCorreo(correo);
				usuario.setNombre(nombre);
				usuario.setApellidos(apellidos);
				usuario.setFechaNacimiento(fechaNacimiento);
				usuario.setFechaInscripcion(fechaInscripcion);
				usuario.setAdmin(admin);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		}
		
		connection.closeConnection();
		return usuario;
	}
	
	/**
	 * Obtener listado de todos los usuarios
	 * @return List<usuarioDTO> Lista de usuarios
	 */
	
	public List<UsuarioDTO> obtenerUsuarios(){
		List<UsuarioDTO> usuarios = new ArrayList<>();
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(prop.getProperty("obtenerUsuariosSTM"));
			while (rs.next()) {
				String correo = rs.getString("correo");
				String nombre = rs.getString("nombre");
				String apellidos = rs.getString("apellidos");
				String fechaNacimiento = rs.getString("fecha_Nacimiento");
				String fechaInscripcion = rs.getString("fecha_Inscripcion");
				Boolean admin = rs.getBoolean("administrador");
				
				UsuarioDTO usuarioToPush = new UsuarioDTO();
				usuarioToPush.setCorreo(correo);
				usuarioToPush.setNombre(nombre);
				usuarioToPush.setApellidos(apellidos);
				usuarioToPush.setFechaNacimiento(fechaNacimiento);
				usuarioToPush.setFechaInscripcion(fechaInscripcion);
				usuarioToPush.setAdmin(admin);
				usuarios.add(usuarioToPush);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		}
		
		connection.closeConnection();
		return usuarios;
	}
	
}