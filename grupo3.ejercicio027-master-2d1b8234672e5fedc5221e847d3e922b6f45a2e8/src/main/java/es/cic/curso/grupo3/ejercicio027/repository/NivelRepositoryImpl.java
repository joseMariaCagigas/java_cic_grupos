package es.cic.curso.grupo3.ejercicio027.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo3.ejercicio027.domain.Nivel;

@Repository
@Transactional
public class NivelRepositoryImpl extends AbstractRepositoryImpl<Long, Nivel> implements NivelRepository {
	
	@Override
	public Class<Nivel> getClassDeT() {
		return Nivel.class;
	}

	@Override
	public String getNombreTabla() {
		return "evento_nivel";
	}

}