package es.cic.curso.grupo2.ejercicio027.modelo;

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
@Table(name="campo")
public class Campo implements Identificable<Long> {

	private static final long serialVersionUID = 898572028792055724L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id; 
	
	@Column(name="nombreCampo")
	private String nombreCampo;
	
	@Column(name="tipoCampo")
	private String tipoCampo;
	
	@Column(name="texto")
	private String texto;
	
	@Column(name="numero")
	private double numero;
	
	@JoinColumn(name="id_plantilla")
	@ManyToOne(fetch=FetchType.LAZY)
	private Plantilla plantilla;
	
	public Campo(){
		super();
	}

	public Campo(String nombreCampo, String tipoCampo, double numero, Plantilla plantilla) {
		super();
		this.nombreCampo = nombreCampo;
		this.tipoCampo = tipoCampo;
		this.numero = numero;
		this.plantilla = plantilla;
	}

	public Campo(String nombreCampo, String tipoCampo, String texto, Plantilla plantilla) {
		super();
		this.nombreCampo = nombreCampo;
		this.tipoCampo = tipoCampo;
		this.texto = texto;
		this.plantilla = plantilla;
	}
	
	public Campo(String nombreCampo, String tipoCampo) {
		super();
		this.nombreCampo = nombreCampo;
		this.tipoCampo = tipoCampo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreCampo() {
		return nombreCampo;
	}

	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}

	public String getTipoCampo() {
		return tipoCampo;
	}

	public void setTipoCampo(String tipoCampo) {
		this.tipoCampo = tipoCampo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public double getNumero() {
		return numero;
	}

	public void setNumero(double numero) {
		this.numero = numero;
	}

	public Plantilla getPlantilla() {
		return plantilla;
	}

	public void setPlantilla(Plantilla plantilla) {
		this.plantilla = plantilla;
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
		Campo other = (Campo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
