package es.cic.curso.grupo3.ejercicio027.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo3.ejercicio027.domain.Rol;

@Repository
@Transactional
public class RolRepositoryImpl extends AbstractRepositoryImpl<Long, Rol> implements RolRepository {

	@Override
	public Class<Rol> getClassDeT() {
		return Rol.class;
	}

	@Override
	public String getNombreTabla() {
		return "usuario_rol";
	}
	
}
