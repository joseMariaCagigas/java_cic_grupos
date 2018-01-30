package es.cic.curso.grupo3.ejercicio027.service;

import java.util.List;

import es.cic.curso.grupo3.ejercicio027.domain.Nivel;

public interface NivelService {

	Long agregarNivel(String nombre);

	Nivel agregarNivel(Nivel nivel);

	Nivel actualizarNivel(Nivel modificado);

	Nivel obtenerNivel(Long id);

	List<Nivel> obtenerListaNiveles();

	void eliminarNivel(Long id);

	Nivel obtenerNivelPorNombre(String nombre);

}