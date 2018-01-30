package es.cic.curso.grupo4.ejercicio027.dominio;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.cic.curso.grupo4.ejercicio027.repositorio.Identificable;


@Entity
@Table(name = "EJECUCION")
public class Ejecucion  implements Identificable<Long>
{
	private static final long serialVersionUID = -7838104740241164664L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "fecha")	
	private Timestamp fecha;

	@JoinColumn(name = "id_conector")
	@ManyToOne(fetch=FetchType.EAGER)
	private Conector conector;
	
	private boolean correcta;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp hora) {
		this.fecha = hora;
	}
	public Conector getConector() {
		return conector;
	}
	public void setConector(Conector conector) {
		this.conector = conector;
	}
	
	public boolean isCorrecta() {
		return correcta;
	}
	public void setCorrecta(boolean correcta) {
		this.correcta = correcta;
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
		Ejecucion other = (Ejecucion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}
