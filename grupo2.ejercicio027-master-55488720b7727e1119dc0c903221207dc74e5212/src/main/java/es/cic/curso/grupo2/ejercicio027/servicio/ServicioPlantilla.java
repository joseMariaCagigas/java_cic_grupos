package es.cic.curso.grupo2.ejercicio027.servicio;

import java.util.List;

import es.cic.curso.grupo2.ejercicio027.modelo.Plantilla;

public interface ServicioPlantilla {

	void aniadirPlantilla(Plantilla plantilla);

	List<Plantilla> listaPlantillas();

	Plantilla obtenPlantilla(Long id);

	void actualizaPlantilla(Plantilla plantilla);

	void borraPlantilla(Long id);

	List<Plantilla> findAll(String filtro);

}