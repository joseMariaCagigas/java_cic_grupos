package es.cic.curso.grupo3.ejercicio027.repository;

import java.util.List;

import es.cic.curso.grupo3.ejercicio027.domain.Usuario;

public interface UsuarioRepository extends IRepository<Long, Usuario> {

	Integer consultaEventosDependientes(Usuario usuario);
	
	List<Usuario> listaUsuariosActivos();
}
