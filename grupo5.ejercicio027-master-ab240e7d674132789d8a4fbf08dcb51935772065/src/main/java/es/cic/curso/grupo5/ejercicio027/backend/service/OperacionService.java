package es.cic.curso.grupo5.ejercicio027.backend.service;

import java.util.List;

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Operacion;

public interface OperacionService {

	Operacion aniadirOperacion(Operacion operacion);

	List<Operacion> listarOperacion();

	Operacion obtenerOperacion(Long id);

	void borrarOperacion(Long id);

	Operacion modificarOperacion(Operacion operacion);

}
