package es.cic.curso.grupo1.ejercicio027.service;

import java.util.List;

import es.cic.curso.grupo1.ejercicio027.dominio.Tarea;

public interface TareaService {

	List<Tarea> getTareas();

	Tarea buscarTarea(long tareaId);

	void borrarTarea(long tareaId);

	Tarea nuevaTarea(String codigo, String descripcion);

	Tarea add(Tarea t);

	Tarea update(Tarea t);
	
	boolean verificarNombre(String codTarea);

	Tarea modificarTareaDesc(long tareaId, String descripcion);

	Tarea modificarTareaNom(long tareaId, String codigo);

	Tarea buscarTarea(String codigo);

	List<Tarea> findAll(String filtro);
}