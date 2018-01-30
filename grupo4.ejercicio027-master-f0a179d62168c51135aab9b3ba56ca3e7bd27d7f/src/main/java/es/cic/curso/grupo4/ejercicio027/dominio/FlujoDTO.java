package es.cic.curso.grupo4.ejercicio027.dominio;

public class FlujoDTO
{	
	private Long id;
	private String nombre;
	private String conectores;
	private boolean habilitado;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
}
