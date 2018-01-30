package es.cic.curso.grupo3.ejercicio027.repository;

import java.util.List;

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
