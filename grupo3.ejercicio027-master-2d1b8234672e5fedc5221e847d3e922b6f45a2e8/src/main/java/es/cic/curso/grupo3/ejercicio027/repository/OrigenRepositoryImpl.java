package es.cic.curso.grupo3.ejercicio027.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo3.ejercicio027.domain.Origen;

@Repository
@Transactional
public class OrigenRepositoryImpl extends AbstractRepositoryImpl<Long, Origen> implements OrigenRepository {

	@Override
	public Class<Origen> getClassDeT() {
		return Origen.class;
	}
	
	@Override
	public String getNombreTabla() {
		return "evento_origen";
	}

	@Override
	public Integer consultaEventosDependientes(Origen origen) {
		BigInteger registrosAfectados = (BigInteger)entityManager
				.createNativeQuery("select COUNT(*) from EVENTO where origen_id = ?;")
				.setParameter(1, origen.getId())
				.getSingleResult();
		return registrosAfectados.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Origen> listaOrigenesActivos() {
		return entityManager
				.createNativeQuery("select * from EVENTO_ORIGEN where alta = true;", Origen.class)
				.getResultList();
	}
}