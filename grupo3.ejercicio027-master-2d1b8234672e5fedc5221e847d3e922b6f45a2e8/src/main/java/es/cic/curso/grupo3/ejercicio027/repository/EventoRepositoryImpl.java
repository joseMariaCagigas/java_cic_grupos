package es.cic.curso.grupo3.ejercicio027.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo3.ejercicio027.domain.Evento;


@Repository
@Transactional
public class EventoRepositoryImpl extends AbstractRepositoryImpl<Long, Evento> implements EventoRepository {

	@Override
	public Class<Evento> getClassDeT() {
		return Evento.class;
	}

	@Override
	public String getNombreTabla() {
		return "evento";
	}
}
