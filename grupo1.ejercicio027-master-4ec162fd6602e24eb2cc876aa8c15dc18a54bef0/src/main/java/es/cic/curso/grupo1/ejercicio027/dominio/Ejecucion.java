package es.cic.curso.grupo1.ejercicio027.dominio;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import es.cic.curso.grupo1.ejercicio027.repository.Identificable;

@Entity
@Table(name="EJECUCION")
public class Ejecucion implements Identificable<Long> {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@JoinColumn(name="ID_TAREA")
	@ManyToOne(fetch=FetchType.EAGER)
	private Tarea tarea;
	
	@Column(name="FECHA")
	private String fecha;

	@OneToMany(mappedBy="ejecucion") 
	private List<Registro> listaRegistros;
	
	public Ejecucion() {
		super();
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
	
	
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public List<Registro> getListaRegistros() {
		return listaRegistros;
	}

	public void setListaRegistros(List<Registro> listaRegistros) {
		this.listaRegistros = listaRegistros;
	}
}