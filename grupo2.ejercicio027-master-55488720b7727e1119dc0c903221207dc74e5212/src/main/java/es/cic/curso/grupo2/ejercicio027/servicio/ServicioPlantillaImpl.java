package es.cic.curso.grupo2.ejercicio027.servicio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo2.ejercicio027.modelo.Plantilla;
import es.cic.curso.grupo2.ejercicio027.repositorio.RepositorioPlantilla;


@Service
@Transactional
public class ServicioPlantillaImpl implements ServicioPlantilla {

	@Autowired
	private RepositorioPlantilla plantillaRepo;
	
	@Autowired
	private ServicioCampo campoService;
	
	private final List<Plantilla> listaPlantillas = new ArrayList<>();
	
	/* (non-Javadoc)
	 * @see es.cic.curso.grupo2.ejercicio027.servicio.ServicioPlantilla#aniadirPlantilla(es.cic.curso.grupo2.ejercicio027.modelo.Plantilla)
	 */
	@Override
	public void aniadirPlantilla(Plantilla plantilla){
		plantillaRepo.add(plantilla);
	}
	
	/* (non-Javadoc)
	 * @see es.cic.curso.grupo2.ejercicio027.servicio.ServicioPlantilla#listaPlantillas()
	 */
	@Override
	public List<Plantilla> listaPlantillas(){
		return plantillaRepo.list();
	}
	
	/* (non-Javadoc)
	 * @see es.cic.curso.grupo2.ejercicio027.servicio.ServicioPlantilla#obtenPlantilla(java.lang.Long)
	 */
	@Override
	public Plantilla obtenPlantilla(Long id) {
		Plantilla plantilla = plantillaRepo.read(id);
		if (plantilla == null) {
			throw new IllegalArgumentException("No existe plantilla en BBDD con esa id: " + ": " + id);
		}
		return plantilla;
	}
	
	/* (non-Javadoc)
	 * @see es.cic.curso.grupo2.ejercicio027.servicio.ServicioPlantilla#actualizaPlantilla(java.lang.Long, es.cic.curso.grupo2.ejercicio027.modelo.Plantilla)
	 */
	@Override
	public void actualizaPlantilla(Plantilla plantilla) {
		plantillaRepo.update(plantilla);

	}
	
	
	@Override
    public void borraPlantilla(Long id){        
        Plantilla aBorrar = obtenPlantilla(id);
        if(!aBorrar.getCampos().isEmpty()){
            campoService.eliminaCamposPlantilla(id);
        }            
        plantillaRepo.delete(aBorrar);
        
    }
	
	/**
	 * metodo para filtrar plantillas
	 * @param filtro
	 * @param inicio
	 * @param maximo
	 * @return
	 */
	@Override
	public synchronized List<Plantilla> findAll(String filtro){
		List<Plantilla> lista = new ArrayList<>();
		for(Plantilla p:listaPlantillas()){
			try{
				boolean pasoFiltro = (filtro==null||filtro.isEmpty())
						||p.getNombrePlantilla().toLowerCase().contains(filtro.toLowerCase());
				if(pasoFiltro){
					lista.add(p.clone());
				}
			}catch(CloneNotSupportedException ex) {
				Logger.getLogger(ServicioPlantillaImpl.class.getName()).log(null, ex);
			}
		}
		Collections.sort(lista, new Comparator<Plantilla>() {

			@Override
			public int compare(Plantilla o1, Plantilla o2) {
				return (int) (o2.getId() - o1.getId());
			}});
		
		return lista;
	}	

}
