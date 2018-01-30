package es.cic.curso.grupo4.ejercicio027.repositorio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import es.cic.curso.grupo4.ejercicio027.dominio.Flujo;

@Repository
@Transactional
public class FlujoRepositoryImpl extends AbstractRepositoryImpl<Long, Flujo> implements FlujoRepository {

	@Override
	public Class<Flujo> getClassDeT() {
		return Flujo.class;
	}

	@Override
	public String getNombreTabla() {
		return "flujo";
	}

}
