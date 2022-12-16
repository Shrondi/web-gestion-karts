package business.reserva;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * @author Carlos Lucena Robles.
 * @author Miguel Raigón Jiménez.
 * @author Pablo Roldán Puebla.
 * @author Paloma Romero Delgado.
 * @author Kamal Abdelkader Haddu.
 * @version 1.0.0
 */

/**
 * Implementación de la clase abstracta de reserva
 *
 */

@SuppressWarnings("serial")
public class AbstractReservaDTO implements Serializable {
	
	protected int idReserva_;
	protected String idUsuario_;
	protected Date fecha_;
	protected int duracion_;
	protected String idPista_;
	protected float precio_;
	protected float descuento_;
	
	public AbstractReservaDTO() {
		idReserva_ = -1;
		idUsuario_ = null;
		fecha_ = new Date();
		duracion_ = 0;
		idPista_ = null;
		precio_ = 0;
		descuento_ = 0;
	}
	
	public int getIdReserva() {
		return this.idReserva_;
	}
	
	public String getIdUsuario() {
		return this.idUsuario_;
	}
	
	public String getFecha() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String retVal = sdf.format(this.fecha_);
		return retVal;
	}
	
	public Date getFechaDate() {
		return this.fecha_;
	}
	
	public int getDuracion() {
		return this.duracion_;
	}
	
	public String getIdPista() {
		return this.idPista_;
	}
	
	public float getPrecio() {
		return this.precio_;
	}
	
	public float getDescuento() {
		return this.descuento_;
	}
	
	public void setIdUsuario(String idUsuario) {
		this.idUsuario_ = idUsuario;
	}
	
	public void setIdReserva(int idReserva) {
		this.idReserva_ = idReserva;
	}
	
	public void setFecha(String fecha) {
		java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		sdf.setLenient(false);
		try{
			this.fecha_ = sdf.parse(fecha);
		}catch(ParseException e){
			e.printStackTrace();
		}
	}
	
	public void setFecha(Date fecha) {
		this.fecha_ = fecha;
	}
	
	
	public void setDuracion(int duracion) {
		this.duracion_ = duracion;
	}
	
	public void setIdPista(String idPista) {
		this.idPista_ = idPista;
	}
	
	public void setPrecio(float precio) {
		this.precio_ = precio;
	}
	
	public void setDescuento(float descuento) {
		this.descuento_ = descuento;
	}
	
}

