package es.cic.curso.grupo1.ejercicio027.service;

import java.util.List;

import es.cic.curso.grupo1.ejercicio027.dominio.Ejecucion;
import es.cic.curso.grupo1.ejercicio027.dominio.Registro;

public interface EjecucionService {

	Ejecucion addEjecucion(Ejecucion e);

	Registro addRegistro(Registro r);

	Ejecucion updateEjecucion(Ejecucion e);

	Registro updateRegistro(Registro r);

	List<Ejecucion> listaEjecucion();

	List<Registro> listaRegistros();

	void deleteEjecucion(Ejecucion e);

	void deleteRegistro(Registro r);

	Ejecucion buscaEjecucion(Ejecucion e);

	Ejecucion buscaEjecucion(Long id);

	Registro buscaRegistro(Registro r);

	Registro buscaRegistro(Long id);
}