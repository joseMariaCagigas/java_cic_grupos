package es.cic.curso.grupo2.ejercicio027.repositorio;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo2.ejercicio027.modelo.Campo;

@Repository
@Transactional
public class RespositorioCampoImpl extends AbstractRepositoryImpl<Long, Campo> implements RepositorioCampo {
	@Override
	public Class<Campo> getClassDeT() {
		return Campo.class;
	}

	@Override
	public String getNombreTabla() {
		return "campo";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Campo> listCamposPlantilla(Long idPlantilla) {
		return entityManager.createNativeQuery("SELECT * FROM campo WHERE id_plantilla = ?", getClassDeT())
				.setParameter(1, idPlantilla).getResultList();
	}

	@Override
	public void deleteCamposPlantilla(Long idPlantilla) {
		entityManager.createNativeQuery("DELETE FROM campo WHERE id_plantilla = ?").setParameter(1, idPlantilla).executeUpdate();
		
	}
}
