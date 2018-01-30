package es.cic.curso.grupo2.ejercicio027.servicio;

import java.util.List;

import es.cic.curso.grupo2.ejercicio027.modelo.Campo;

public interface ServicioCampo {

	void anadirCampo(Long idPlantilla, Campo campo);

	Campo obtenCampo(Long id);

	List<Campo> listarCampos();

	List<Campo> listaCamposPlantilla(Long id);

	void borrarCampo(Long id);

	void eliminaCamposPlantilla(Long id);

	void actualizarCampo(Campo campo);



}