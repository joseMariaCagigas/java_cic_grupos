package es.cic.curso.grupo3.ejercicio027.service;

import java.util.List;

import es.cic.curso.grupo3.ejercicio027.domain.Evento;

public interface EventoService {

	Long aniadirEvento(String descripcion, Long origenId, Long usuarioId, Long nivelId, Long tipoId, boolean alta);

	Evento obtenerEvento(Long id);

	List<Evento> obtenerEventos();

	Evento actualizarEvento(Evento modificado);

	void borrarEvento(Long id);

	//Luis - Para hacerlo public
	Evento aniadirEvento(Evento nueva);
}