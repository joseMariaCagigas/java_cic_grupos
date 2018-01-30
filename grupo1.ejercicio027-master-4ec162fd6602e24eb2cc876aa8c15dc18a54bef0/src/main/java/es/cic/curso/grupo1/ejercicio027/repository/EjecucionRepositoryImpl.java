package es.cic.curso.grupo1.ejercicio027.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo1.ejercicio027.dominio.Ejecucion;

@Repository
@Transactional
public class EjecucionRepositoryImpl extends AbstractRepositoryImpl<Long, Ejecucion> implements EjecucionRepository{

	@Override
	public Class<Ejecucion> getClassDeT() {
		return Ejecucion.class;
	}

	@Override
	public String getNombreTabla() {
		return "EJECUCION";
	}

	

}
