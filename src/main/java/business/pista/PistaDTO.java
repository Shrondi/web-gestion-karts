package business.pista;

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
 * Implementación de la clase PistaDTO
 */

public class PistaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nombre_; 
	private boolean estado_;
	private Dificultad dif_;
	private int max_karts_;
	private int asoc_karts_infantiles_;
	private int asoc_karts_adultos_;
	
	/**
	 * Constructor vacio (default)
	 */
	
	public PistaDTO() {
		this.nombre_ = null;
		this.estado_ = false;
		this.dif_ = Dificultad.INFANTIL;
		this.max_karts_ = -1;
		this.asoc_karts_infantiles_ = -1;
		this.asoc_karts_adultos_ = -1;
	}
	
	
	/**
	 * Constructor parametrizado
	 * @param nombre Nombre de la pista
	 * @param estado Operatividad de la pista
	 * @param dif Publico al que esta enfocada la pista
	 * @param max_karts Capacidad maxima de karts para la pista
	 */
	
	public PistaDTO(String nombre, boolean estado, Dificultad dif,int max_karts, int asoc_karts_inf, int asoc_karts_adult) {
		this.nombre_= nombre;
		this.estado_= estado;
		this.dif_= dif;
		this.max_karts_= max_karts;
		this.asoc_karts_infantiles_ = asoc_karts_inf;
		this.asoc_karts_adultos_ = asoc_karts_adult;
	}
	
	/**
	 * Getter nombre de la pista.
	 * @return Nombre de la pista
	 */
	public String getNombre(){
		return this.nombre_;
	}
	
	/**
	 * Getter del estado de la pista.
	 * @return True si esta disponible para la reserva. False en caso contrario.
	 */
	public boolean getEstado(){
		return this.estado_;
	}
	
	/**
	 * Getter de la dificultad de la pista.
	 * @return Dificultad de la pista (Infantil, Familiar o Adulto).
	 */
	public Dificultad getDificulty(){
		return this.dif_;
	}
	
	/**
	 * Getter de la cantidad máxima de karts.
	 * @return Cantidad maxima de karts permitidos en pista.
	 */
	public int getMaxAmmount(){
		return this.max_karts_;
	}
	
	
	public int getAsocAmmountInf(){
		return asoc_karts_infantiles_;
	}
	
	public int getAsocAmmountAdult(){
		return asoc_karts_adultos_;
	}
	
	/**
	 * Setter del nombre de la pista.
	 * @param nombre Nuevo nombre para la pista.
	 */
	public void setNombre(String nombre) {
		this.nombre_= nombre;
	}
	
	/**
	 * Setter del estado para su uso de la pista.
	 * @param estado Nuevo estado de la pista.
	 */
	public void setEstado(boolean estado) {
		this.estado_=estado;
	}
	
	/**
	 * Setter de la dificultad de la pista.
	 * @param dif Nueva dificultad de la pista.
	 */
	public void setDificulty(Dificultad dif) {
		this.dif_=dif;
	}
	
	/**
	 * Setter del máximo de karts de la pista.
	 * @param KartsNumber Nuevo máximo de karts admitidos en la pista.
	 */
	public void setMaxAmmount(int KartsNumber) {
		this.max_karts_=KartsNumber;
	}
	
	
	public void setAsocAmmountInf(int KartAsocInf){
		this.asoc_karts_infantiles_ = KartAsocInf;
	}
	
	public void setAsocAmmountAdult(int KartAsocAdult){
		this.asoc_karts_adultos_ = KartAsocAdult;
	}
	
	/**
	 * Convierte todos los atributos de la clase en cadena.
	 * Sobrescribe el método toString de la clase Object.
	 * @return Cadena con los atributos de la clase
	 */
	@Override
 	public String toString() {
		String retValue;
		retValue = "Nombre: " + this.nombre_ + "\n" + "Estado: " + this.estado_ + "\n" + "Dificultad: " + this.dif_ + "\n" + "Maximo de karts: " + this.max_karts_  + "\n" + "Karts asociados infantiles: " + this.asoc_karts_infantiles_ + "\n" + "Karts asociados infantiles: " + this.asoc_karts_adultos_ + "\n";
		return retValue;
	}
}