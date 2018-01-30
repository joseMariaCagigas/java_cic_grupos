package es.cic.curso.grupo2.ejercicio027.modelo;

import java.io.Serializable;

public interface Identificable<K> extends Serializable {

	K getId();

	void setId(K id);

}