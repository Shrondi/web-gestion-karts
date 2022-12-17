package display.javabean;

import java.util.List;

import business.kart.KartDTO;
import business.pista.PistaDTO;

public class asociarBean implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<PistaDTO> listadoPistas_ = null;
	private PistaDTO pista_ = null;
	private List<KartDTO> listadoKarts_ = null;
	
	/**
	 * Getter List pista
	 * @return lista de pistas
	 */
	public List<PistaDTO> getListadoPistas() {
		return this.listadoPistas_;
	}

	/**
	 * Getter pista
	 * @return pista
	 */
	public PistaDTO getPista() {
		return this.pista_;
	}
	
	/**
	 * Getter List karts
	 * @return lista de karts
	 */
	public List<KartDTO> getListadoKarts() {
		return this.listadoKarts_;
	}
	
	/**
	 * Setter List pista
	 * @param Listas de pistas
	 */
	public void setListadoPistas(List <PistaDTO> listadoPistas) {
		this.listadoPistas_ = listadoPistas;
	}
	
	/**
	 * Setter id pista
	 * @param id pista
	 */
	public void setPista(PistaDTO pista) {
		this.pista_ = pista;
	}
	
	/**
	 * Setter List kart
	 * @param Listas de karts
	 */
	public void setListadoKarts(List <KartDTO> listadoKarts) {
		this.listadoKarts_ = listadoKarts;
	}

	
}
