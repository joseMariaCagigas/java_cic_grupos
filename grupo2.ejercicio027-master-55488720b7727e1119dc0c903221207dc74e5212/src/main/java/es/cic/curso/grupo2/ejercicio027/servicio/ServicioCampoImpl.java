package es.cic.curso.grupo2.ejercicio027.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo2.ejercicio027.modelo.Campo;
import es.cic.curso.grupo2.ejercicio027.modelo.Plantilla;
import es.cic.curso.grupo2.ejercicio027.repositorio.RepositorioCampo;


@Service
@Transactional
public class ServicioCampoImpl implements ServicioCampo {
	
	@Autowired
	private RepositorioCampo repositorioCampo;
	
	@Autowired
	private ServicioPlantilla plantillaService;
	
	@Override
	public void anadirCampo(Long idPlantilla,Campo campo){
		Plantilla p = plantillaService.obtenPlantilla(idPlantilla);
		campo.setPlantilla(p);
		repositorioCampo.add(campo);
	}
	
	@Override
	public Campo obtenCampo(Long id){
		Campo campo = repositorioCampo.read(id);
		if (campo == null) {
			throw new IllegalArgumentException("No hay campo en BBDD con ese id: " + ": " + id);
		}
		return campo;
	}
	
	@Override
	public List<Campo> listarCampos(){
		return repositorioCampo.list();
	}
	
	@Override
	public List<Campo> listaCamposPlantilla(Long id) {		// Id plantilla
/*		Campo campo = repositorioCampo.read(id);			//Id campo
		if (campo == null) {
			throw new IllegalArgumentException("No hay campo en BBDD con ese id: " + ": " + id);
		}		*/
		return repositorioCampo.listCamposPlantilla(id);
	}
	
	@Override
	public void borrarCampo(Long id){
		repositorioCampo.delete(id);
	}
	
	@Override
	public void eliminaCamposPlantilla(Long id) {
		if (listaCamposPlantilla(id).isEmpty()) {
			throw new IllegalStateException("No hay campos en la plantilla");
		}
		repositorioCampo.deleteCamposPlantilla(id);
	}
	
	@Override
	public void actualizarCampo(Campo campo){
			repositorioCampo.update(campo);
		
	}
}
