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
	
	
	/**
	 * Constructor del DAO de Usuario
	 * @param properties Objeto properties que contiene las consultas relativas a la BD
	 */
	public UsuarioDAO(Properties properties){
		prop = properties;
	}
	
	//NEW
	
	/**
	 * Se usa a modo de consulta.
	 * Comprueba si un usuario existe a través de su correo
	 * Tablas usadas:
	 *	-USUARIO: Para seleccionar todos los usuarios existentes en la BD
	 *Atributos de la tabla usados:
	 *	-correo: Sirve de identificador del usuario, al ser la Primary Key
	 * @param correo Correo del usuario
	 * @return check Booleano que contiene true en caso de que el usuario existe y false en caso contrario
	 */
	
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
	 * Se usa para crear un nuevo usuario
	 * Tablas usadas:
	 *	-USUARIO: Para insertar el valor de los distintos atributos que componen la tabla
	 * Atributos de la tabla usados:
	 *	-correo: Correo que identificará al usuario
	 *	-nombre: Nombre del usuario
	 *	-apellidos: Apellidos del usuario
	 *	-fecha_Nacimiento: Fecha de nacimiento del usuario
	 *	-password: Contraseña del usuario
	 *	-administrador: Booleano que indica si el usuario será o no administrador
	 *	-fecha_Inscripcion: Fecha de inscripción del usuario
	 *
	 * @param usuario DTO de Usuario en el que se almacenará la información del nuevo usuario
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
	 * Se usa para modificar usuario registrado en la BD
	 * Tablas usadas:
	 *	-USUARIO: Para modificar el valor de los distintos atributos que conforman la tabla
	 * Atributos de la tabla usados:
	 *	-nombre: Nombre del usuario a modificar
	 *	-apellidos: Apellidos del usuario a modificar
	 *	-fecha_Nacimiento: Fecha de nacimiento del usuario a modificar
	 *	-password: Contraseña del usuario a modificar
	 *	-correo: Correo que sirve para identificar el usuario a modificar
	 *
	 * @param usuario DTO de Usuario en el que se almacenará la información modificada del usuario
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
	 * Se usa para eliminar un usuario registrado en la BD
	 * Tablas usadas:
	 *	-USUARIO: Para tomar el correo del usuario y, posteriormente, eliminar dicho usuario.
	 * Atributos de la tabla usados:
	 *	-correo: Correo que sirve para identificar el usuario a eliminar
	 *
	 * @param correo Correo del usuario que será eliminado
	 */
	
	public void eliminarUsuario(String correo) {
		DBConnection connection = new DBConnection();
		con = connection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(prop.getProperty("eliminarUsuarioSTM"));
			ps.setString(1, correo);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		connection.closeConnection();
	}

	/**
	 * Se usa para obtener un usuario a través de su correo
	 * Tablas usadas:
	 *	-USUARIO: Para obtener el correo del usuario
	 * Atributos de la tabla usados:
	 *	-correo: Correo que sirve para identificar el usuario a obtener
	 *
	 * @param correo Correo que se usará para obtener el usuario
	 * @return usuario Usuario conseguido a través de su correo
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
	 * Se usa para obtener una lista que contiene todos los usuarios
	 * Tablas usadas:
	 *	-USUARIO: Para obtener cada uno de los usuarios
	 *
	 * @return usuarios Lista formada por todos los usuarios registrados
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
