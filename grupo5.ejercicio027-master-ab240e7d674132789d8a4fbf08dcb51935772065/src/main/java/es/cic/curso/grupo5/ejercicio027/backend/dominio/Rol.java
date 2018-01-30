package es.cic.curso.grupo5.ejercicio027.backend.dominio;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import es.cic.curso.grupo5.ejercicio027.backend.repository.Identificable;

@Entity
@Table (name="ROL")
public class Rol implements Identificable<Long>{


	/**
	 * 
	 */
	private static final long serialVersionUID = -4312681901279459618L;

	/**
	 * 
	 */
	 
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
	
	@Column(name ="rol")
    String tipoRol;
	

	
	public Rol() {
		super();
	}
	


	public Rol(String tipoRol) {

		this.tipoRol = tipoRol;
	
	}



	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}


	public String getTipoRol() {
		return tipoRol;

	}

	public void setipoRol(String rol) {
		this.tipoRol = rol;
	}



	
	
	
	

}