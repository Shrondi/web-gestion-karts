package display.javabean;


public class reservaBean implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private int duracion_ = 0;
	private int numeroNinios_ = 0;
	private int numeroAdultos_ = 0;
	private String tipoReserva_ = null;
	private String fecha_ = null;
	
	
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
	
	
	

	
}
