package es.cic.curso.grupo4.ejercicio027.repositorio;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo4.ejercicio027.dominio.Ejecucion;
import es.cic.curso.grupo4.ejercicio027.repositorio.AbstractRepositoryImpl;

@Repository
@Transactional
public class EjecucionRepositoryImpl extends AbstractRepositoryImpl<Long, Ejecucion> implements EjecucionRepository{

	@Override
	public Class<Ejecucion> getClassDeT() {
		return Ejecucion.class;
	}

	@Override
	public String getNombreTabla() {
		return "ejecucion";
	}

}
