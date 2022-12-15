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
 * Implementación de la clase bono
 *
 */

@SuppressWarnings("serial")
public class BonoDTO implements Serializable {
	
	protected int idBono_;
	protected String idUsuario_;
	protected Date fechaCaducidad_;
	protected String tipo_;
	protected int sesiones_;
	
	public BonoDTO() {
		idBono_ = -1;
		idUsuario_ = null;
		fechaCaducidad_ = new Date();
		tipo_ = null;
		sesiones_ = -1;
	}
	
	public int getBonoId() {
		return this.idBono_;
	}
	
	public String getUsuario() {
		return this.idUsuario_;
	}
	
	public String getFechaCaducidad() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
		String retVal = sdf.format(this.fechaCaducidad_);
		return retVal;
	}
	
	public Date getFechaDate() {
		return this.fechaCaducidad_;
	}
	
	public int getSesiones() {
		return this.sesiones_;
	}
	
	public String getTipo() {
		return this.tipo_;
	}
	
	
	public void setUsuario(String idUsuario) {
		this.idUsuario_ = idUsuario;
	}
	
	public void setBonoId(int idBono) {
		this.idBono_ = idBono;
	}
	
	public void setFechaCaducidad(String fechaCaducidad) {
		java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd'T'HH:mm");
		sdf.setLenient(false);
		try{
			this.fechaCaducidad_ = sdf.parse(fechaCaducidad);
		}catch(ParseException e){
			e.printStackTrace();
		}
	}
	
	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad_ = fechaCaducidad;
	}
	
	
	public void setSesiones(int sesiones) {
		this.sesiones_ = sesiones;
	}
	
	public void setTipo(String tipo) {
		this.tipo_ = tipo;
	}
	
	
}

