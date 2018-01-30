package es.cic.curso.grupo3.ejercicio027.domain;

import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.cic.curso.grupo3.ejercicio027.repository.Identificable;

@Entity
@Table(name="evento")
public class Evento implements Identificable<Long>  {

	private static final long serialVersionUID = -5822477611355947260L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="fecha")
	private Date fecha;
	
	@Column(name="hora")
	private Time hora;
	
	@JoinColumn(name="origen_id")
	@ManyToOne(fetch=FetchType.EAGER)
	private Origen origen;
	
	@JoinColumn(name="usuario_id")
	@ManyToOne(fetch=FetchType.EAGER)
	private Usuario usuario;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@JoinColumn(name="nivel_id")
	@ManyToOne(fetch=FetchType.EAGER)
	private Nivel nivel;
	
	@JoinColumn(name="tipo_id")
	@ManyToOne(fetch=FetchType.EAGER)
	private Tipo tipo;

	@Column(name="alta")
	private boolean alta;
	
	public Evento() {
		super();
	}

	public Evento(Date fecha, Time hora, Origen origen, Usuario usuario, String descripcion, Nivel nivel, Tipo tipo, boolean alta) {
		super();
		this.fecha = fecha;
		this.hora = hora;
		this.origen = origen;
		this.usuario = usuario;
		this.descripcion = descripcion;
		this.nivel = nivel;
		this.tipo = tipo;
		this.alta = alta;
	}

	public Evento(Long id, Date fecha, Time hora, Origen origen, Usuario usuario, String descripcion, Nivel nivel, Tipo tipo, boolean alta) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.hora = hora;
		this.origen = origen;
		this.usuario = usuario;
		this.descripcion = descripcion;
		this.nivel = nivel;
		this.tipo = tipo;
		this.alta = alta;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getFecha() {
		LocalDate fechaLocal = fecha.toLocalDate();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		return fechaLocal.format(dtf);
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return "00:00:00";
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public Origen getOrigenObj() {
		return origen;
	}

	public void setOrigen(Origen origen) {
		this.origen = origen;
	}

	public Usuario getUsuarioObj() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Nivel getNivelObj() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	public Tipo getTipoObj() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	public String getOrigen(){
		return origen.getNombre();
	}
	
	public String getUsuario(){
		return usuario.getNombre();
	}
	
	public String getRol(){
		return usuario.getRolObj().getNombre();
	}
	
	public String getNivel(){
		return nivel.getNombre();
	}
	
	public String getTipo(){
		return tipo.getNombre();
	}

	public boolean isAlta() {
		return alta;
	}
	
	@Override
	public String toString() {
		return "Evento [id=" + id + ", fecha=" + fecha + ", hora=" + hora + ", origen=" + origen + ", usuario="
				+ usuario + ", descripcion=" + descripcion + ", nivel=" + nivel + ", tipo=" + tipo + ", alta=" + alta
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public void setAlta(boolean alta) {
		this.alta = alta;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
