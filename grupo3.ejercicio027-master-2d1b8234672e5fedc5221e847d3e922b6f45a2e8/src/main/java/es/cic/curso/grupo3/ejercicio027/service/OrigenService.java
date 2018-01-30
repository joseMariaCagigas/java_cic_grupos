package es.cic.curso.grupo3.ejercicio027.service;

import java.util.List;

import es.cic.curso.grupo3.ejercicio027.domain.Origen;

public interface OrigenService {

	Long agregarOrigen(String nombre, boolean alta);

	Origen agregarOrigen(Origen origen);

	Origen actualizarOrigen(Origen modificado);

	Origen obtenerOrigen(Long id);

	List<Origen> obtenerListaOrigenes();

	void eliminarOrigen(Long id);

	Origen obtenerOrigenPorNombre(String nombre);

	Integer consultaEventosDependientes(Origen origen);
	
	List<Origen> listaOrigenesActivos();
}
