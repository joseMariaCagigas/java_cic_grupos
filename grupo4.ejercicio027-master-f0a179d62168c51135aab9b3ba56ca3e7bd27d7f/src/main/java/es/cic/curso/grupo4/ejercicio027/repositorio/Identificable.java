package es.cic.curso.grupo4.ejercicio027.repositorio;

import java.io.Serializable;

public interface Identificable<K> extends Serializable {

	K getId();

	void setId(K id);

}