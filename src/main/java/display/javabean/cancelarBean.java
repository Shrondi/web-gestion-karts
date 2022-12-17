package display.javabean;

import java.util.List;

import business.reserva.ReservaAdultosDTO;
import business.reserva.ReservaFamiliarDTO;
import business.reserva.ReservaInfantilDTO;

public class cancelarBean implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private List<ReservaInfantilDTO> reservasInfantil_ = null;
	private List<ReservaFamiliarDTO> reservasFamiliar_ = null;
	private List<ReservaAdultosDTO> reservasAdultos_ = null;
	private String fechaInicio_ = null;
	private String fechaFin_ = null;
	
	
	public String getFechaInicio() {
		return this.fechaInicio_;
	}
	public String getFechaFin() {
		return this.fechaFin_;
	}
	public List<ReservaInfantilDTO> getReservasInfantil() {
		return this.reservasInfantil_;
	}
	public List<ReservaFamiliarDTO> getReservasFamiliar() {
		return this.reservasFamiliar_;
	}
	public List<ReservaAdultosDTO> getReservasAdultos() {
		return this.reservasAdultos_;
	}
	
	public void setReservasInfantil(List<ReservaInfantilDTO> reservasInfantil) {
		this.reservasInfantil_ = reservasInfantil;
	}
	public void setReservasFamiliar(List<ReservaFamiliarDTO> reservasFamiliar) {
		this.reservasFamiliar_ = reservasFamiliar;
	}
	public void setReservasAdultos(List<ReservaAdultosDTO> reservasAdultos) {
		this.reservasAdultos_ = reservasAdultos;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio_ = fechaInicio;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin_ = fechaFin;
	}
	
	
	
	
}
