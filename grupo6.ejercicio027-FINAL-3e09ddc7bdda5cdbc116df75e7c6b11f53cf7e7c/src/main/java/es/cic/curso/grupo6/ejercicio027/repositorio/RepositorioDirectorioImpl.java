package es.cic.curso.grupo6.ejercicio027.repositorio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;

@Repository
@Transactional
public class RepositorioDirectorioImpl extends RepositorioAbstractoImpl<Long, Directorio> implements RepositorioDirectorio {

	@Override
	public Class<Directorio> obtenClaseT() {
		return Directorio.class;
	}

	@Override
	public String obtenNombreTabla() {
		return Directorio.class.getSimpleName().toUpperCase();
	}

}
