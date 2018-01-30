package es.cic.curso.grupo4.ejercicio027.servicio;

import java.util.List;

import es.cic.curso.grupo4.ejercicio027.dominio.Ejecucion;

public interface EjecucionServicio {

	void agregaEjecucion(Ejecucion ejecucion);
	void eliminaEjecucion(Long id);
	void modificaEjecucion(Ejecucion ejecucion);
	Ejecucion obtenEjecucion(Long id);
	List<Ejecucion> listaEjecuciones();
}
