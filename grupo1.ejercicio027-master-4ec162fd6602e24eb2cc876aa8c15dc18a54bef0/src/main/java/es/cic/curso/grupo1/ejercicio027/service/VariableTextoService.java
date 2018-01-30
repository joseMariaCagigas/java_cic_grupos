package es.cic.curso.grupo1.ejercicio027.service;

import java.util.List;

import es.cic.curso.grupo1.ejercicio027.dominio.Tarea;
import es.cic.curso.grupo1.ejercicio027.dominio.VariableTexto;

public interface VariableTextoService {

	List<VariableTexto> getVarTex();

	VariableTexto buscarVarTex(long varId);

	void borrarVarTex(long varId);
	VariableTexto update(VariableTexto t);
	VariableTexto nuevaVarTex(Tarea tarea, String nombre, String valor);
	VariableTexto add(VariableTexto t);
	VariableTexto modificarTarea(long varId, String nombre, String valor);
}