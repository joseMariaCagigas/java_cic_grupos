package es.cic.curso.grupo5.ejercicio027.backend.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import es.cic.curso.grupo5.ejercicio027.backend.repository.Identificable;

@Entity
@Table (name="OPERACION")
public class Operacion implements Identificable<Long>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4881833264860770866L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
	
	@Column(name ="descripcion")
    String descripcion;

	@Column(name = "habilitada")
	boolean habilitada;
	
	@Column (name = "nombreRol")
	String nombreRol;

	public Operacion(String descripcion, boolean habilitda, String nombreRol) {
		super();
		this.descripcion = descripcion;
		this.habilitada = habilitda;
		this.nombreRol = nombreRol;
	}

	public Operacion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Operacion(String descripcion) {
		super();
		this.descripcion = descripcion;
	}

	@Override
	public Long getId() {
		return this.id;
	}
	@Override
	public void setId(Long id) {
		this.id=id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operacion other = (Operacion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	public boolean isHabilitada() {
		return habilitada;
	}

	public void setHabilitada(boolean habilitada) {
		this.habilitada = habilitada;
	}

 
	
}
