package es.cic.curso.grupo2.ejercicio027.repositorio;

import java.util.List;

import es.cic.curso.grupo2.ejercicio027.modelo.Campo;

public interface RepositorioCampo extends IRepository<Long, Campo> {

	List<Campo> listCamposPlantilla(Long idPlantilla);

	void deleteCamposPlantilla(Long idPlantilla);
}
