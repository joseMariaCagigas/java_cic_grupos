package es.cic.curso.grupo4.ejercicio027.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractRepositoryImpl<K extends Number, T extends Identificable<K>> implements IRepository<K, T>  {

	@PersistenceContext
	protected EntityManager entityManager;

	public AbstractRepositoryImpl() {
		super();
	}

	public T add(T nuevo) {
		entityManager.persist(nuevo);
		entityManager.flush();
		return nuevo;
	}	
	
		
	public T read(K id) {
		T resultado = entityManager.find(getClassDeT(), id);
		return resultado;
	}	

	@SuppressWarnings("unchecked")
	public List<T> list() {
		return entityManager
				.createNativeQuery("select * from " + getNombreTabla(), getClassDeT())
				.getResultList();
	}	

	public void delete(K id) {
		T aBorrar = entityManager.find(getClassDeT(), id);
		delete(aBorrar);
	}		
	
	public void delete(T aBorrar) {
		entityManager.remove(aBorrar);
	}
	
	public T update(T modificado) {
		modificado = entityManager.merge(modificado);
		return modificado;
	}		
}