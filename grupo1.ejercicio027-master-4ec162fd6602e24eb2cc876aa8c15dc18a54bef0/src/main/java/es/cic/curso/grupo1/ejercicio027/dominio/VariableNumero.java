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
@Table(name="VARIABLE_NUMERO")
public class VariableNumero implements Identificable<Long> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@JoinColumn(name="ID_TAREA")
	@ManyToOne(fetch=FetchType.EAGER)
	private Tarea tarea;
	
	@Column(name="NOMBRE_VARIABLE_NUMERO")
	private String nombreVarNum;
	
	@Column(name="VALOR_VARIABLE_NUMERO")
	private double valorVarNum;
	
	public VariableNumero() {
		super();
	}

	public VariableNumero(Tarea tarea, String nombreVarNum, double valorVarNum) {
		super();
		this.tarea = tarea;
		this.nombreVarNum = nombreVarNum;
		this.valorVarNum = valorVarNum;
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

	public String getNombreVarNum() {
		return nombreVarNum;
	}

	public void setNombreVarNum(String nombreVarNum) {
		this.nombreVarNum = nombreVarNum;
	}

	public double getValorVarNum() {
		return valorVarNum;
	}

	public void setValorVarNum(double valorVarNum) {
		this.valorVarNum = valorVarNum;
	}
}