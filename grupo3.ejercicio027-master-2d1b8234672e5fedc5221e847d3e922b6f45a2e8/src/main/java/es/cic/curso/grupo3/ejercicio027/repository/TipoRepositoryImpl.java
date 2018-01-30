package es.cic.curso.grupo3.ejercicio027.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo3.ejercicio027.domain.Tipo;

@Repository
@Transactional
public class TipoRepositoryImpl extends AbstractRepositoryImpl<Long, Tipo> implements TipoRepository {

	@Override
	public Class<Tipo> getClassDeT() {
		return Tipo.class;
	}
	
	@Override
	public String getNombreTabla() {
		return "evento_tipo";
	}
}