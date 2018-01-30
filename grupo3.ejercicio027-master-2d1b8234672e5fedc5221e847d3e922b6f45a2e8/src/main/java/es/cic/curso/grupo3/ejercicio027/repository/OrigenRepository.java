package es.cic.curso.grupo3.ejercicio027.repository;

import java.util.List;

import es.cic.curso.grupo3.ejercicio027.domain.Origen;

public interface OrigenRepository extends IRepository<Long, Origen> {

	Integer consultaEventosDependientes(Origen origen);
	
	List<Origen> listaOrigenesActivos();
}
