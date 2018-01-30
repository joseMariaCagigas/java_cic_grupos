package es.cic.curso.grupo4.ejercicio027.repositorio;

import org.springframework.stereotype.Repository;

import es.cic.curso.grupo4.ejercicio027.dominio.Conector;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ConectorRepositoryImpl extends AbstractRepositoryImpl<Long, Conector> implements ConectorRepository{

	@Override
	public Class<Conector> getClassDeT() {
		return Conector.class;
	}

	@Override
	public String getNombreTabla() {
		return "conector";
	}

}
