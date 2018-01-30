package es.cic.curso.grupo6.ejercicio027.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FICHERO")
public class Fichero implements Identificable<Long> {
	private static final long serialVersionUID = -8800715225024553533L;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/** Referencia al directorio del que cuelga el fichero. */
	@JoinColumn(name = "id_directorio")
	@ManyToOne(fetch = FetchType.EAGER)
	private Directorio directorio;

	/** Nombre del fichero. */
	@Column(name = "nombre")
	private String nombre;

	/** Descripción <em>opcional</em>. */
	@Column(name = "descripcion")
	private String descripcion;

	/** Código con la versión del fichero. */
	@Column(name = "version")
	private Double version;

	public Fichero() {
		
	}
	
	public Fichero(Directorio directorio, String nombre, String descripcion, Double version) {
		this.directorio = directorio;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.version = version;
	}

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the directorio
	 */
	public Directorio getDirectorio() {
		return directorio;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @return the version
	 */
	public Double getVersion() {
		return version;
	}

	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param directorio the directorio to set
	 */
	public void setDirectorio(Directorio directorio) {
		this.directorio = directorio;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Double version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((directorio == null) ? 0 : directorio.hashCode());
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
		Fichero other = (Fichero) obj;
		if (directorio == null) {
			if (other.directorio != null)
				return false;
		} else if (!directorio.equals(other.directorio))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Fichero [id=" + id + ", directorio=" + directorio.getId() + ", nombre=" + nombre + ", descripcion="
				+ descripcion + ", version=" + version + "]";
	}

}
