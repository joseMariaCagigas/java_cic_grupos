package es.cic.curso.grupo5.ejercicio027.backend.dto;

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Rol;

public class UsuarioDTO {

    Long id;
    String nombre;
    String password;
    Rol rol;
    String email;

	public UsuarioDTO() {
		super();
	}
	
	public UsuarioDTO(String nombre, String password, Rol rol, String email) {
		super();
		this.nombre = nombre;
		this.password = password;
		this.rol = rol;
		this.email = email;
	}
	
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id=id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}