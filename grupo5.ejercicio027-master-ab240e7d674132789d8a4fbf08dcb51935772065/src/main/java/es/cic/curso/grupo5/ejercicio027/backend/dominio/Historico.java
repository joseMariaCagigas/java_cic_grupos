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
@Table(name="HISTORICO")
public class Historico implements Identificable<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1532438376478048782L;

	/**
	 * 
	 */

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
	
	@Column(name ="operacion")
	String operacion;
	
	@Column(name ="hora")
	String hora;
	
	@Column(name ="permitido")
	boolean permitido;

	
	
    @JoinColumn(name = "idusuario")
    @ManyToOne(fetch = FetchType.LAZY) //@ManyToOne(fetch = FetchType.EAGER)//modificado para probar sin dtos
    private Usuario usuario;
    
	public Historico() {
		super();
	}

	public Historico(String operacion, String hora, Usuario usuario, boolean permitido) {
		super();
		this.operacion = operacion;
		this.hora = hora;
		this.usuario = usuario;
		this.permitido = permitido;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isPermitido() {
		return permitido;
	}

	public void setPermitido(boolean permitido) {
		this.permitido = permitido;
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
		Historico other = (Historico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Historico [id=" + id + ", operacion=" + operacion + ", hora=" + hora + ", permitido=" + permitido
				+ ", usuario=" + usuario + "]";
	}

	
}
	
	