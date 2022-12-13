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
 * Implementación de la clase Reserva Familiar
 *
 */

@SuppressWarnings("serial")
public class ReservaFamiliarDTO extends AbstractReservaDTO implements Serializable{

	private int participantesAdultos_ ;
	private int participantesInfantiles_;
	
	
	public ReservaFamiliarDTO() {
		participantesAdultos_ = -1;
		participantesInfantiles_ = -1;
	}
	
	public int getParticipantesAdultos() {
		return participantesAdultos_;
	}

	public void setParticipantesAdultos(int participantesAdultos) {
		this.participantesAdultos_ = participantesAdultos;
	}

	public int getParticipantesInfantiles() {
		return participantesInfantiles_;
	}

	public void setParticipantesInfantiles(int participantesInfantiles) {
		this.participantesInfantiles_ = participantesInfantiles;
	}

	@Override
	public String toString() {
		String reservaInfo = (
								"Usuario: " + this.idUsuario_ 
								 + "\nFecha: " + this.getFecha() 
								 + "\nDuracion: " + this.duracion_ + " minutos"
								 + "\nParticipantes infantiles: " + this.participantesInfantiles_
								 + "\nParticipantes adultos: " + this.participantesAdultos_
								 + "\nDescuento: " + this.descuento_ + " %"
								 + "\nPrecio: " + this.precio_ + " €" 
					             + "\nPista: " + this.idPista_);
		return reservaInfo;
	}

}