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
	 * Implementación de la clase reserva Infantil
	 *
	 */

	@SuppressWarnings("serial")
	public class ReservaInfantilDTO extends AbstractReservaDTO implements Serializable{
		
		private int participantesInfantiles_;
		
		public ReservaInfantilDTO(){
			participantesInfantiles_ =-1;
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
									 + "\nDescuento: " + this.descuento_ + " %"
									 + "\nPrecio: " + this.precio_ + " €" 
						             + "\nPista: " + this.idPista_);
			return reservaInfo;
		}
		
		
	}
