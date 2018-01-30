package es.cic.curso.grupo1.ejercicio027.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.cic.curso.grupo1.ejercicio027.repository.Identificable;

@Entity
@Table(name="REGISTRO")
public class Registro implements Identificable<Long>{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name="CAMPO")
	private String campo;
	@Column(name="VALOR")
	private String valor;
	@JoinColumn(name="ID_EJECUCION")
	@ManyToOne(fetch=FetchType.EAGER)
	private Ejecucion ejecucion;
	
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public Ejecucion getEjecucion() {
		return ejecucion;
	}
	public void setEjecucion(Ejecucion ejecucion) {
		this.ejecucion = ejecucion;
	}	
}