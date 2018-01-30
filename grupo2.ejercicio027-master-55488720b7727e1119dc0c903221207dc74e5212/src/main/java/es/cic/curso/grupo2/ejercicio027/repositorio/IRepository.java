package es.cic.curso.grupo2.ejercicio027.repositorio;

import java.util.List;

import es.cic.curso.grupo2.ejercicio027.modelo.Identificable;

public interface IRepository<K extends Number, T extends Identificable<K>> {
	T add(T nuevo);
	T read(K id);
	T read(T nuevo);
	List<T> list();	
	T update(T modificado);
	void delete(K id);
	void delete(T aBorrar);
	
	public abstract Class<T> getClassDeT();
	public abstract String getNombreTabla();
}
