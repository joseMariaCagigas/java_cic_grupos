package es.cic.curso.grupo4.ejercicio027.dominio;

import java.util.Date;

public class EjecucionDTO {
	
	private Long id;
	private String tipoConector;
	private String nombreConector;
	private String descripcionConector;
	private Date fecha;
	private boolean Correcta;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getNombreConector() {
		return nombreConector;
	}
	public void setNombreConector(String nombreConector) {
		this.nombreConector = nombreConector;
	}
	public String getDescripcionConector() {
		return descripcionConector;
	}
	public void setDescripcionConector(String descripcionConector) {
		this.descripcionConector = descripcionConector;
	}
	public String getTipoConector() {
		return tipoConector;
	}
	public void setTipoConector(String tipoConector) {
		this.tipoConector = tipoConector;
	}
	public boolean isCorrecta() {
		return Correcta;
	}
	public void setCorrecta(boolean correcta) {
		Correcta = correcta;
	}
}
