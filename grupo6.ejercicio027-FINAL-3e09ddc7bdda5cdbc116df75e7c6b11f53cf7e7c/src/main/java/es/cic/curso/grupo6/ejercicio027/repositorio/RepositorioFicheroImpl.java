package es.cic.curso.grupo6.ejercicio027.repositorio;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;

@Repository
@Transactional
public class RepositorioFicheroImpl extends RepositorioAbstractoImpl<Long, Fichero> implements RepositorioFichero {

	@Override
	public Class<Fichero> obtenClaseT() {
		return Fichero.class;
	}

	@Override
	public String obtenNombreTabla() {
		return Fichero.class.getSimpleName().toUpperCase();
	}

	@Override
	public List<Fichero> deleteByDirectory(Long idDirectory) {
		List<Fichero> ficheros = listByDirectory(idDirectory);
		entityManager.createNativeQuery("DELETE FROM FICHERO WHERE id_directorio = ?").setParameter(1, idDirectory)
				.executeUpdate();
		return ficheros;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fichero> listByDirectory(Long idDirectory) {
		return entityManager.createNativeQuery("SELECT * FROM FICHERO WHERE id_directorio = ?", Fichero.class)
				.setParameter(1, idDirectory).getResultList();
	}

}
