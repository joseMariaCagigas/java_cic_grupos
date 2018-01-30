package es.cic.curso.grupo3.ejercicio027.service;

import java.util.List;

import es.cic.curso.grupo3.ejercicio027.domain.Usuario;

public interface UsuarioService {

	Long aniadirUsuario(Long rolId, String nombre, String apellidos, boolean alta);

	Usuario obtenerUsuario(Long id);

	List<Usuario> obtenerUsuarios();

	Usuario actualizarUsuario(Usuario modificado);

	void borrarUsuario(Long id);

	Usuario aniadirUsuario(Usuario nuevo);

	Usuario obtenerUsuarioPorNombre(String nombre);

	int consultaEventosDependientes(Usuario usuario);
	
	List<Usuario> listaUsuariosActivos();
}