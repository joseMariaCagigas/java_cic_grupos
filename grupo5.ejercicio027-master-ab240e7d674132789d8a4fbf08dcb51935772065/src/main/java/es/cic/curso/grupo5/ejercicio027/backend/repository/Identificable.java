package es.cic.curso.grupo5.ejercicio027.backend.repository;

import java.io.Serializable;

public interface Identificable<K> extends Serializable {

	K getId();

	void setId(K id);

}