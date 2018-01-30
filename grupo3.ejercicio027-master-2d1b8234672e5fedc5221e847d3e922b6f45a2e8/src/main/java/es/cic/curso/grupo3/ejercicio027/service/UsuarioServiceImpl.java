package es.cic.curso.grupo3.ejercicio027.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo3.ejercicio027.domain.Rol;
import es.cic.curso.grupo3.ejercicio027.domain.Usuario;
import es.cic.curso.grupo3.ejercicio027.repository.RolRepository;
import es.cic.curso.grupo3.ejercicio027.repository.UsuarioRepository;

@Transactional
@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RolRepository rolRepository;

	@Override
	public Long aniadirUsuario(Long rolId, String nombre, String apellidos, boolean alta){
		Usuario usuario = new Usuario();
		Rol rol = rolRepository.read(rolId);
		usuario.setRol(rol);
		usuario.setNombre(nombre);
		usuario.setApellidos(apellidos);
		usuario.setAlta(alta);
		Usuario aniadido = aniadirUsuario(usuario);
		
		return aniadido.getId();
	}

	@Override
	public Usuario aniadirUsuario(Usuario nuevo) {
		return usuarioRepository.add(nuevo);
	}
	
	@Override
	public Usuario obtenerUsuario(Long id){
		return usuarioRepository.read(id);
	}

	@Override
	public List<Usuario> obtenerUsuarios(){
		return usuarioRepository.list();
	}
	
	@Override
	public Usuario obtenerUsuarioPorNombre(String nombre) {
		Usuario usuarioActual = null;
		List<Usuario> listaUsuarios = obtenerUsuarios();
		for (Usuario usuario: listaUsuarios) {
			if (nombre == usuario.getNombre()) {
				usuarioActual = usuario;
			}
		}
		return usuarioActual;
	}

	@Override
	public Usuario actualizarUsuario(Usuario modificado){
		return usuarioRepository.update(modificado);
	}

	@Override
	public void borrarUsuario(Long id){
		Usuario aBorrar = obtenerUsuario(id);
		usuarioRepository.delete(aBorrar);
	}

	@Override
	public int consultaEventosDependientes(Usuario usuario) {
		return usuarioRepository.consultaEventosDependientes(usuario);
	}

	@Override
	public List<Usuario> listaUsuariosActivos() {
		return usuarioRepository.listaUsuariosActivos();
	}
	
}