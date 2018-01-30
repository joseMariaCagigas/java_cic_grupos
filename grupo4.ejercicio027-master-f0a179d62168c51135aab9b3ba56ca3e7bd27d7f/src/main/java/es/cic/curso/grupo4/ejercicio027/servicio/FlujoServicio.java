package es.cic.curso.grupo4.ejercicio027.servicio;

import java.util.List;

import es.cic.curso.grupo4.ejercicio027.dominio.Flujo;

public interface FlujoServicio {
	void agregaFlujo(Flujo conector);
	void eliminaFlujo(Long id);
	void modificaFlujo(Flujo conector);
	Flujo obtenFlujo(Long id);
	List<Flujo> listaFlujos();
}
