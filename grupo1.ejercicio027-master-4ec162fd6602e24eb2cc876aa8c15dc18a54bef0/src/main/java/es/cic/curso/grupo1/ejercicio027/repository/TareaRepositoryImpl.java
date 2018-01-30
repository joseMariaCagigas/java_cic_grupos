package es.cic.curso.grupo1.ejercicio027.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo1.ejercicio027.dominio.Tarea;

@Repository
@Transactional
public class TareaRepositoryImpl extends AbstractRepositoryImpl<Long, Tarea> implements TareaRepository {

	private static final long serialVersionUID = -4127960114707165023L;

	@Override
	public Class<Tarea> getClassDeT() {
		return Tarea.class;
	}

	@Override
	public String getNombreTabla() {
		return "TAREA";
	}
}