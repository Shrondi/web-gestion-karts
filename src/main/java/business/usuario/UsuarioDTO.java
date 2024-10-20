package business.usuario;

import java.util.Date;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * @author Carlos Lucena Robles.
 * @author Miguel Raigón Jiménez.
 * @author Pablo Roldán Puebla.
 * @author Paloma Romero Delgado.
 * @author Kamal Abdelkader Haddu.
 * @version 1.0.0
 */

/**
 * Implementación de la clase usuarioDTO
 *
 */

public class UsuarioDTO implements Serializable{
	
	private static final long serialVersionUID = -366946940529534569L;
	private String nombre_;
	private String apellidos_;
	private String correo_;
	private Date fechaNacimiento_;
	private Date fechaInscripcion_;
	private int antiguedad_;
	private String passWord_;
	private Boolean admin_; //true administrador, false usuario
	private int reservasCompletadas_;
	
	/**
	 * Constructor vacio (default)
	 */
	
	public UsuarioDTO() {
		this.nombre_ = null;
		this.apellidos_ = null;
		this.correo_ = null;
		this.fechaNacimiento_ = null;
		this.fechaInscripcion_ = null;
		this.antiguedad_ = 0;
		this.passWord_ = null;
		this.admin_ = false;
		this.reservasCompletadas_ = 0;
	}
	
	/**
	 * Constructor parametrizado
	 * @param correo Correo del usuario
	 * @param nombre Nombre del usuario
	 * @param apellidos Apellidos del usuario
	 * @param fechaNacimiento Fecha de nacimiento del usuario
	 * @param passWord Contraseña del usuario
	 * @param admin Permisos usuario
	 */
	
	public UsuarioDTO(String correo, String nombre, String apellidos, String fechaNacimiento, String passWord, Boolean admin) {
		this.correo_ = correo;
		this.nombre_ = nombre;
		this.apellidos_ = apellidos;
		this.admin_ = admin;  
		this.passWord_ = passWord;
		this.fechaInscripcion_ = new Date();
		
		String formato;
		if (fechaNacimiento.contains("/")) {
			formato = "dd/MM/yyyy";
		}else {
			formato = "yyyy-MM-dd";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		sdf.setLenient(false);
		
		try {
			this.fechaNacimiento_ = sdf.parse(fechaNacimiento);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Getter nombre del usuario
	 * @return Nombre del usuario
	 */
	public String getNombre() {
		return nombre_;
	}
	
	/**
	 * Getter apellidos del usuario
	 * @return Apellidos del usuario
	 */
	public String getApellidos() {
		return apellidos_;
	}
	
	/**
	 * Getter correo del usuario
	 * @return Correo del usuario
	 */
	public String getCorreo() {
		return correo_;
	}
	
	/**
	 * Getter fecha de nacimiento del usuario
	 * @return Fecha de nacimiento del usuario
	 */
	public String getFechaNacimiento() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
		return sdf.format(fechaNacimiento_);
	}
	
	/**
	 * Getter fecha de inscripción
	 * @return Fecha de inscripción
	 */
	public String getFechaInscripcion() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(fechaInscripcion_);
	}
	
	/**
	 * Getter fecha de inscripción como Date
	 * @return Fecha de inscripción
	 */
	public Date getFechaInscripcionDate() {
		return this.fechaInscripcion_;
	}
	
	/**
	 * Getter antiguedad usuario
	 * @return Antiguedad usuario
	 */
	
	public int getAntiguedad(){
		return this.antiguedad_;
	}
	
	/**
	 * Getter contraseña del usuario
	 * @return contraseña del usuario
	 */
	
	public String getPassWord(){
		return this.passWord_;
	}
	
	/**
	 * Getter Tipo de usuario
	 * @return Tipo de usuario
	 */
	
	public Boolean getAdmin() {
		return this.admin_;
	}
	
	/**
	 * Getter Numero de reservas completadas
	 * @return Numero de reservas completadas
	 */
	
	public int getReservas() {
		return this.reservasCompletadas_;
	}
	
	/**
	 * Setter del nombre del usuario
	 * @param nombre Nombre del usuario
	 */
	
	public void setNombre(String nombre) {
		this.nombre_ = nombre;
	}
	
	/**
	 * Setter de los apellidos del usuario
	 * @param apellidos Apellidos del usuario
	 */
	public void setApellidos(String apellidos) {
		this.apellidos_ = apellidos;
	}

	/**
	 * Setter del correo del usuario
	 * @param correo Correo del usaurio
	 */
	public void setCorreo(String correo) {
		this.correo_ = correo;
	}
	
	/**
	 * Setter de la fecha de nacimiento del usuario
	 * @param fechaNacimiento Fecha de nacimiento del usuario
	 */
	public void setFechaNacimiento(String fechaNacimiento) {	
		String formato;
		
		if (fechaNacimiento.contains("/")) {
			formato = "dd/MM/yyyy";
		}else {
			formato = "yyyy-MM-dd";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		sdf.setLenient(false);
		try {
			this.fechaNacimiento_ = sdf.parse(fechaNacimiento);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Setter de la fecha de inscripcion
	 * @param fecha_inscripcion Fecha de inscripcion
	 */
	public void setFechaInscripcion(String fecha_inscripcion) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		try {
			this.fechaInscripcion_ = sdf.parse(fecha_inscripcion);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Setter antiguedad usuario
	 * @return Antiguedad usuario
	 */
	
	public void setAntiguedad(int antiguedad){
		antiguedad_ = antiguedad;
	}
	
	/**
	 * Permite asignar una contraseña nueva al usuario
	 * @param passWord Nueva contraseña del usuario
	 */
	
	public void setPassWord(String passWord) {
		this.passWord_ = passWord;
	}
	
	/**
	 * Permite asignar el tipo de usuario
	 * @param admin tipo de usuario a asignar al usuario
	 */
	
	public void setAdmin(Boolean admin) {
		this.admin_ = admin;
	}
	
	/**
	 * Setter Numero de reservas completadas
	 * @param Numero de reservas completadas
	 */
	
	public void setReservas(int reservasCompletadas) {
		this.reservasCompletadas_ = reservasCompletadas;
	}
	
	/**
	 * Convierte todos los atributos de la clase en cadena
	 * Sobrescribe el método toString de la clase Object
	 * @return Cadena con los atributos de la clase
	 */
	@Override
	public String toString() {
		String usuarioInfo = "\n Nombre: " + this.nombre_ + "\n" + "Apellidos: " + this.apellidos_ + "\n" + "Correo: " + this.correo_ + "\n" + "Fecha nacimiento: " + this.getFechaNacimiento() + "\n" + "Fecha inscripcion: " + this.getFechaInscripcion() + "\n"+"Contraseña: "+this.getPassWord()+"\n"+"Admin: "+this.getAdmin();
		return usuarioInfo;
	}
	
}
