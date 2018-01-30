package es.cic.curso.grupo1.ejercicio027.dominio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import es.cic.curso.grupo1.ejercicio027.repository.Identificable;

@Entity
@Table(name="TAREA")
public class Tarea implements Cloneable, Identificable<Long> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="CODIGO")
	private String codigo;
	
	@Column(name="DESCRIPCION")
	private String descripcion;
	
	@OneToMany(mappedBy="tarea") 
	private List<VariableTexto> listaTareasTexto;
	
	@OneToMany(mappedBy="tarea") 
	private List<VariableNumero> listaTareasTNumero;
	
	@OneToMany(mappedBy="tarea") 
	private List<Ejecucion> listaEjecuciones;
	
	public Tarea() {
		super();
		listaTareasTexto  = new ArrayList<>();
		listaTareasTNumero  = new ArrayList<>();
	}

	public Tarea(String codigo, String descripcion) {
		super();
		listaTareasTexto  = new ArrayList<>();
		listaTareasTNumero  = new ArrayList<>();
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Override
	public Tarea clone() throws CloneNotSupportedException {
		return (Tarea) super.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((listaEjecuciones == null) ? 0 : listaEjecuciones.hashCode());
		result = prime * result + ((listaTareasTNumero == null) ? 0 : listaTareasTNumero.hashCode());
		result = prime * result + ((listaTareasTexto == null) ? 0 : listaTareasTexto.hashCode());
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
		Tarea other = (Tarea) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (listaEjecuciones == null) {
			if (other.listaEjecuciones != null)
				return false;
		} else if (!listaEjecuciones.equals(other.listaEjecuciones))
			return false;
		if (listaTareasTNumero == null) {
			if (other.listaTareasTNumero != null)
				return false;
		} else if (!listaTareasTNumero.equals(other.listaTareasTNumero))
			return false;
		if (listaTareasTexto == null) {
			if (other.listaTareasTexto != null)
				return false;
		} else if (!listaTareasTexto.equals(other.listaTareasTexto))
			return false;
		return true;
	}
}