package es.cic.curso.grupo6.ejercicio027.repositorio;

import java.util.List;

import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;

public interface RepositorioFichero extends Repositorio<Long, Fichero> {
	
	List<Fichero> deleteByDirectory(Long idDirectory);
	
	List<Fichero> listByDirectory(Long idDirectory);

}
