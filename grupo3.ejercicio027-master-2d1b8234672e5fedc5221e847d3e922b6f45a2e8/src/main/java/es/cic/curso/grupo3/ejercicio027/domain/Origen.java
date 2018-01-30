package es.cic.curso.grupo3.ejercicio027.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import es.cic.curso.grupo3.ejercicio027.repository.Identificable;

@Entity
@Table(name="evento_origen")
public class Origen implements Identificable<Long> {

	private static final long serialVersionUID = -9121426582207667309L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="nombre")
	private String nombre;

	@Column(name="alta")
	private boolean alta;
	
	public Origen() {
		super();
	}

	public Origen(String nombre, boolean alta) {
		super();
		this.nombre = nombre;
		this.alta = alta;
	}

	public Origen(Long id, String nombre, boolean alta) {
		this(nombre,alta);
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Origen other = (Origen) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Origen [id=" + id + ", nombre=" + nombre + ", alta=" + alta + "]";
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	public void setAlta(boolean alta) {
		this.alta = alta;
	}
}
