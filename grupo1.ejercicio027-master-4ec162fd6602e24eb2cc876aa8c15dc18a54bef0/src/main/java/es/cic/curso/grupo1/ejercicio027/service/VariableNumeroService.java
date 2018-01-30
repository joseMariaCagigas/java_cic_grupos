package es.cic.curso.grupo1.ejercicio027.service;

import java.util.List;

import es.cic.curso.grupo1.ejercicio027.dominio.Tarea;
import es.cic.curso.grupo1.ejercicio027.dominio.VariableNumero;

public interface VariableNumeroService {

	List<VariableNumero> getVarNums();

	VariableNumero buscarVarNum(long varNumId);
	VariableNumero add(VariableNumero n);
	void borrarVarNum(long varNumId);
	VariableNumero update(VariableNumero v);
	VariableNumero nuevaVarNum(Tarea tarea, String nombre, double valor);

	VariableNumero modificarTarea(long varId, String nombre, double valor);
}