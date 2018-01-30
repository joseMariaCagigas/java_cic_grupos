package es.cic.curso.grupo1.ejercicio027.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo1.ejercicio027.dominio.VariableTexto;

@Repository
@Transactional
public class VariableTextoRepositoryImpl extends AbstractRepositoryImpl<Long, VariableTexto> implements VariableTextoRepository {

	@Override
	public Class<VariableTexto> getClassDeT() {
		return VariableTexto.class;
	}

	@Override
	public String getNombreTabla() {
		return "VARIABLE_TEXTO";
	}
}