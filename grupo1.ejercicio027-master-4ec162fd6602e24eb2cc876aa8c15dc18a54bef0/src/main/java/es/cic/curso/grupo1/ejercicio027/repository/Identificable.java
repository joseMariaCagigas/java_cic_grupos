package es.cic.curso.grupo1.ejercicio027.repository;

import java.io.Serializable;

public interface Identificable<K> extends Serializable {

	K getId();

	void setId(K id);
}