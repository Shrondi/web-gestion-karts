package display.javabean;


public class reservaBean implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private int idReserva_= 0;
	private int duracion_ = 0;
	private int numeroNinios_ = 0;
	private int numeroAdultos_ = 0;
	private String tipoReserva_ = null;
	private String fecha_ = null;
	private String pista_ = null;
	private int idBono_ = 0;
	private int numeroSesiones_ = 0;
	private String fechaCaducidad_ = null;
	
	public int getIdReserva() {
		return idReserva_;
	}
	public int getDuracion() {
		return duracion_;
	}
	public int getNumeroNinios() {
		return numeroNinios_;
	}
	public int getNumeroAdultos() {
		return numeroAdultos_;
	}
	public String getTipoReserva() {
		return tipoReserva_;
	}
	public String getFecha() {
		return fecha_;
	}
	public int getIdBono() {
		return idBono_;
	}
	public int getNumeroSesiones() {
		return numeroSesiones_;
	}
	public String getFechaCaducidad() {
		return fechaCaducidad_;
	}
	public String getPista() {
		return pista_;
	}
	
	
	public void setIdReserva(int idReserva) {
		this.idReserva_ = idReserva;
	}
	public void setDuracion(int duracion) {
		this.duracion_ = duracion;
	}
	public void setNumeroNinios(int numeroNinios) {
		this.numeroNinios_ = numeroNinios;
	}
	public void setNumeroAdultos(int numeroAdultos) {
		this.numeroAdultos_ = numeroAdultos;
	}
	public void setTipoReserva(String tipoReserva) {
		this.tipoReserva_ = tipoReserva;
	}
	public void setFecha(String fecha) {
		this.fecha_ = fecha;
	}
	public void setIdBono(int idBono) {
		this.idBono_ = idBono;
	}
	public void setNumeroSesiones(int numeroSesiones) {
		this.numeroSesiones_ = numeroSesiones;
	}
	public void setFechaCaducidad(String fechaCaducidad) {
		this.fechaCaducidad_ = fechaCaducidad;
	}
	public void setPista(String pista) {
		this.pista_ = pista;
	}
	
}
