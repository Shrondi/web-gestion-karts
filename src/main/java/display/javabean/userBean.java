package display.javabean;

public class userBean implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String nombre_="";
	private String apellidos_="";
	private String correo_="";
	private String fechaNacimiento_="";
	private String fechaInscripcion_="";
	private String passWord_="";
	private Boolean admin_= false;
	
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
		return fechaNacimiento_;
	}
	
	/**
	 * Getter fecha de inscripción
	 * @return Fecha de inscripción
	 */
	public String getFechaInscripcion() {
		return fechaInscripcion_;
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
		fechaNacimiento_ = fechaNacimiento;
	}
	
	/**
	 * Setter de la fecha de inscripcion
	 * @param fecha_inscripcion Fecha de inscripcion
	 */
	public void setFechaInscripcion(String fecha_inscripcion) {
		fechaInscripcion_ = fecha_inscripcion;
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
	
}
