package display.javabean;

import java.util.List;

import business.reserva.ReservaAdultosDTO;
import business.reserva.ReservaFamiliarDTO;
import business.reserva.ReservaInfantilDTO;

public class rangoBean implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private List<ReservaInfantilDTO> reservasInfantilPasadas_ = null;
	private List<ReservaFamiliarDTO> reservasFamiliarPasadas_ = null;
	private List<ReservaAdultosDTO> reservasAdultosPasadas_ = null;
	private List<ReservaInfantilDTO> reservasInfantilFuturas_ = null;
	private List<ReservaFamiliarDTO> reservasFamiliarFuturas_ = null;
	private List<ReservaAdultosDTO> reservasAdultosFuturas_ = null;
	
	
	public List<ReservaInfantilDTO> getReservasInfantilPasadas() {
		return this.reservasInfantilPasadas_;
	}
	public List<ReservaFamiliarDTO> getReservasFamiliarPasadas() {
		return this.reservasFamiliarPasadas_;
	}
	public List<ReservaAdultosDTO> getReservasAdultosPasadas() {
		return this.reservasAdultosPasadas_;
	}
	
	public void setReservasInfantilPasadas(List<ReservaInfantilDTO> reservasInfantil) {
		this.reservasInfantilPasadas_ = reservasInfantil;
	}
	public void setReservasFamiliarPasadas(List<ReservaFamiliarDTO> reservasFamiliar) {
		this.reservasFamiliarPasadas_ = reservasFamiliar;
	}
	public void setReservasAdultosPasadas(List<ReservaAdultosDTO> reservasAdultos) {
		this.reservasAdultosPasadas_ = reservasAdultos;
	}
	public List<ReservaInfantilDTO> getReservasInfantilFuturas() {
		return reservasInfantilFuturas_;
	}
	public List<ReservaFamiliarDTO> getReservasFamiliarFuturas() {
		return reservasFamiliarFuturas_;
	}
	public List<ReservaAdultosDTO> getReservasAdultosFuturas() {
		return reservasAdultosFuturas_;
	}
	
	public void setReservasInfantilFuturas(List<ReservaInfantilDTO> reservasInfantilFuturas) {
		this.reservasInfantilFuturas_ = reservasInfantilFuturas;
	}
	public void setReservasFamiliarFuturas(List<ReservaFamiliarDTO> reservasFamiliarFuturas) {
		this.reservasFamiliarFuturas_ = reservasFamiliarFuturas;
	}
	public void setReservasAdultosFuturas(List<ReservaAdultosDTO> reservasAdultosFuturas) {
		this.reservasAdultosFuturas_ = reservasAdultosFuturas;
	}
	
	
	
	
	
}
