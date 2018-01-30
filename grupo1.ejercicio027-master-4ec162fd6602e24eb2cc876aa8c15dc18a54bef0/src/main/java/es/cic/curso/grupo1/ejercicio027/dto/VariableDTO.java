package es.cic.curso.grupo1.ejercicio027.dto;

import es.cic.curso.grupo1.ejercicio027.dominio.Tarea;

public class VariableDTO {

	private String nombre;
	
	private String tipo;
	
	private Tarea tarea;
	
	private Long idVariable;
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Tarea getTarea() {
		return tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	public Long getIdVariable() {
		return idVariable;
	}

	public void setIdVariable(Long idVariable) {
		this.idVariable = idVariable;
	}
}