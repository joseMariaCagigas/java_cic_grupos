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
@Table(name="VARIABLE_TEXTO")
public class VariableTexto implements Identificable<Long> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@JoinColumn(name="ID_TAREA")
	@ManyToOne(fetch=FetchType.EAGER)
	private Tarea tarea;
	
	@Column(name="NOMBRE_VARIABLE_TEXTO")
	private String nombreVarTex;
	
	@Column(name="VALOR_VARIABLE_TEXTO")
	private String valorVarTex;

	public VariableTexto() {
		super();
	}

	public VariableTexto(Tarea tarea, String nombreVarTex, String valorVarTex) {
		super();
		this.tarea = tarea;
		this.nombreVarTex = nombreVarTex;
		this.valorVarTex = valorVarTex;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Tarea getTarea() {
		return tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	public String getNombreVarTex() {
		return nombreVarTex;
	}

	public void setNombreVarTex(String nombreVarTex) {
		this.nombreVarTex = nombreVarTex;
	}

	public String getValorVarTex() {
		return valorVarTex;
	}

	public void setValorVarTex(String valorVarTex) {
		this.valorVarTex = valorVarTex;
	}
}