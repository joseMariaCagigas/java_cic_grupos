package es.cic.curso.grupo5.ejercicio027.backend.repository;

import org.springframework.stereotype.Repository;

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Usuario;

@Repository
public class UsuarioRepositoryImpl extends AbstractRepositoryImpl <Long, Usuario> implements UsuarioRepository {

	@Override
    public Class<Usuario> getClassDeT() {
        return Usuario.class;
    }

	@Override
    public String getNombreTabla() {
        return "USUARIO";
    }
}
