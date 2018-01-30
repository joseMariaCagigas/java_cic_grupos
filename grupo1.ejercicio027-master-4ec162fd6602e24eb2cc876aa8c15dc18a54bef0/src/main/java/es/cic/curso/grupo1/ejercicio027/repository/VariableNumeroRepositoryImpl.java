package es.cic.curso.grupo1.ejercicio027.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo1.ejercicio027.dominio.VariableNumero;

@Repository
@Transactional
public class VariableNumeroRepositoryImpl extends AbstractRepositoryImpl<Long, VariableNumero> implements VariableNumeroRepository {

	@Override
	public Class<VariableNumero> getClassDeT() {
		return VariableNumero.class;
	}

	@Override
	public String getNombreTabla() {
		return "VARIABLE_NUMERO";
	}
}