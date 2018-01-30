package es.cic.curso.grupo5.ejercicio027.backend.service;

import java.util.List;

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Usuario;

public interface UsuarioService {

	Usuario aniadirUsuario(Usuario usuario);

    void borrarUsuario(Long id);
    
    Usuario modificarUsuario(Usuario usuario);

    Usuario obtenerUsuario(Long id);

    List<Usuario> listarUsuario();

	void generaBBDD();

	
}
