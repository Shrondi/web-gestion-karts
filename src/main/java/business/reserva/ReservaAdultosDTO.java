package business.reserva;

import java.io.Serializable;
/**
 * @author Carlos Lucena Robles.
 * @author Miguel Raigón Jiménez.
 * @author Pablo Roldán Puebla.
 * @author Paloma Romero Delgado.
 * @author Kamal Abdelkader Haddu.
 * @version 1.0.0
 */

/**
 * Implementación de la Reserva Adultos
 *
 */

@SuppressWarnings("serial")
public class ReservaAdultosDTO extends AbstractReservaDTO implements Serializable {
	
	private int participantesAdultos_;
	
	public ReservaAdultosDTO() {
		participantesAdultos_ = -1;
	}
	
	
	public int getParticipantesAdultos() {
		return this.participantesAdultos_;
	}
	
	public void setParticipantesAdultos(int participantesAdultos) {
		this.participantesAdultos_ = participantesAdultos;
	}

	@Override
	public String toString() {
		String reservaInfo = (
							   "Usuario: " + this.idUsuario_ 
							 + "\nFecha: " + this.getFecha() 
							 + "\nDuracion: " + this.duracion_ + " minutos"
							 + "\nParticipantes adultos: " + this.participantesAdultos_
							 + "\nDescuento: " + this.descuento_ + " %"
							 + "\nPrecio: " + this.precio_ + " €" 
				             + "\nPista: " + this.idPista_);
		return reservaInfo;
	}
}