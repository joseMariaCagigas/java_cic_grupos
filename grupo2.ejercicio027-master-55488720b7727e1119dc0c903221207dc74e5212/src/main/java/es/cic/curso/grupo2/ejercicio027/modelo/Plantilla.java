package es.cic.curso.grupo2.ejercicio027.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="plantilla")
public class Plantilla implements Cloneable,Identificable<Long>{

	private static final long serialVersionUID = -4367456045851982595L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="nombrePlantilla")
	private String nombrePlantilla;

	@OneToMany(mappedBy="plantilla")
	private List<Campo> campos = new ArrayList<>();
	
	public Plantilla(){
		super();
	}
	
	public Plantilla(String nombrePlantilla){
		super();
		this.nombrePlantilla=nombrePlantilla;
	}

	public Long getId() {
		return id;
	}

	public String getNombrePlantilla() {
		return nombrePlantilla;
	}

	public List<Campo> getCampos() {
		return campos;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNombrePlantilla(String nombrePlantilla) {
		this.nombrePlantilla = nombrePlantilla;
	}

	public void setCampos(List<Campo> campos) {
		this.campos = campos;
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Plantilla other = (Plantilla) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public Plantilla clone() throws CloneNotSupportedException{
		return (Plantilla) super.clone();
	}
	
	@Override
	public String toString() {
		return "Plantilla [id=" + id + ", nombrePlantilla=" + nombrePlantilla + "]";
	}
	


}
