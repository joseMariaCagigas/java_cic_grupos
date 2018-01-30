package es.cic.curso.grupo4.ejercicio027.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import es.cic.curso.grupo4.ejercicio027.repositorio.Identificable;

@Entity
public class Flujo  implements Identificable<Long>
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String nombre;
	
	@Column(name = "conectores")	
	private String conectores ;
	
	@Column(name = "habilitado")
	private boolean habilitado;
	
	public Flujo(){
		
	}
	
	public Flujo(String nombre, String conectores){
		this.nombre = nombre;
		this.conectores = conectores;
		this.habilitado = true;
	}
	
	@Override
	public Long getId() {
		return id;
	}
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	public String getConectores() {
		return conectores;
	}
	public void setConectores(String conectores) {
		this.conectores = conectores;
	}
	public boolean isHabilitado() {
		return habilitado;
	}
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
}