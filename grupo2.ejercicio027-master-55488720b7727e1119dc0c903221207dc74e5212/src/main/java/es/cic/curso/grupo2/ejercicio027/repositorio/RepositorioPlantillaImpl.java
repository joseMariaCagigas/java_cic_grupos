package es.cic.curso.grupo2.ejercicio027.repositorio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo2.ejercicio027.modelo.Plantilla;

@Repository
@Transactional
public class RepositorioPlantillaImpl extends AbstractRepositoryImpl<Long, Plantilla> implements RepositorioPlantilla {

	@Override
	public Class<Plantilla> getClassDeT() {
		return Plantilla.class;
	}

	@Override
	public String getNombreTabla() {
		return "plantilla";
	}

}
