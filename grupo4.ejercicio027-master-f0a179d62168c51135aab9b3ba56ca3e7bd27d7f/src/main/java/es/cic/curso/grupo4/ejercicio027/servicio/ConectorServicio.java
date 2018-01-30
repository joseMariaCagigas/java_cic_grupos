package es.cic.curso.grupo4.ejercicio027.servicio;

import java.util.List;
import es.cic.curso.grupo4.ejercicio027.dominio.Conector;

public interface ConectorServicio {

	void agregaConector(Conector conector);
	void eliminaConector(Long id);
	void modificaConector(Conector conector);
	Conector obtenConector(Long id);
	List<Conector> listaConectores();
}
