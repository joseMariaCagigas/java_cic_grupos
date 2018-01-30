package es.cic.curso.grupo3.ejercicio027.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.cic.curso.grupo3.ejercicio027.repository.Identificable;

@Entity
@Table(name="usuario")
public class Usuario  implements Identificable<Long>{

	private static final long serialVersionUID = 5383175767096640859L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="apellidos")
	private String apellidos;
	
	@Column(name="alta")
	private boolean alta;

	@JoinColumn(name="rol_id")
	@ManyToOne(fetch=FetchType.EAGER)
	private Rol rol;
	
	public Usuario() {
		super();
	}

	public Usuario(String nombre, String apellidos, boolean alta, Rol rol) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.rol = rol;
		this.alta = alta;
	}

	public Usuario(Long id, String nombre, String apellidos, boolean alta, Rol rol) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.rol = rol;
		this.alta = alta;
	}

	public Usuario(Long id, String nombre, String apellidos, boolean alta) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.alta = alta;
	}

	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public boolean isAlta() {
		return alta;
	}

	public void setAlta(boolean alta) {
		this.alta = alta;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Rol getRolObj() {
		return rol;
	}

	public String getRol(){
		return rol.getNombre();
	}
	
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", alta=" + alta + ", rolId="
				+ rol.getId() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
