package es.cic.curso.grupo5.ejercicio027.backend.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import es.cic.curso.grupo5.ejercicio027.backend.repository.Identificable;

@Entity
@Table (name="USUARIO")
public class Usuario implements Identificable<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7475082818754761276L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
	
	@Column(name ="nombre")
    String nombre;
	
	@Column(name ="password")
    String password;
    

    @JoinColumn(name = "idrol")
    @OneToOne(fetch = FetchType.EAGER) //@ManyToOne(fetch = FetchType.EAGER)//modificado para probar sin dtos
    private Rol rol;
    
	@Column(name ="email")
    String email;
	
	@Column(name ="activo")
	boolean activo;

	public Usuario() {
		super();
	}
	
	public Usuario(String nombre, String password, Rol rol, String email,boolean activo) {
		super();
		this.nombre = nombre;
		this.password = password;
		this.rol = rol;
		this.email = email;
		this.activo=activo;
	}
	
	@Override
	public Long getId() {
		return this.id;
	}
	@Override
	public void setId(Long id) {
		this.id=id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (activo ? 1231 : 1237);
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
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
		Usuario other = (Usuario) obj;
		if (activo != other.activo)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (rol == null) {
			if (other.rol != null)
				return false;
		} else if (!rol.equals(other.rol))
			return false;
		return true;
	}
	//para probar
	public String getTipoRol(){
		return rol.getTipoRol();
	 
	}
		
	}
	
	
