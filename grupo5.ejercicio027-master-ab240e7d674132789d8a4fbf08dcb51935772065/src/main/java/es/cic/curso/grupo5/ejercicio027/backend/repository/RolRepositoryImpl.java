package es.cic.curso.grupo5.ejercicio027.backend.repository;

import org.springframework.stereotype.Repository;

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Rol;

@Repository
public class RolRepositoryImpl extends AbstractRepositoryImpl <Long, Rol> implements RolRepository {

	@Override
    public Class<Rol> getClassDeT() {
        return Rol.class;
    }

	@Override
    public String getNombreTabla() {
        return "ROL";
    }
}
