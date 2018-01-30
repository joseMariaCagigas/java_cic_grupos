package es.cic.curso.grupo3.ejercicio027.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo3.ejercicio027.domain.Origen;
import es.cic.curso.grupo3.ejercicio027.repository.OrigenRepository;

@Transactional
@Service
public class OrigenServiceImpl implements OrigenService {
	
	@Autowired
	private OrigenRepository origenRepository;
	
	
	@Override
	public Long agregarOrigen(String nombre, boolean alta) {
		Origen origen = new Origen();
		origen.setNombre(nombre);
		origen.setAlta(alta);
		Origen origenToAdd = agregarOrigen(origen);
		return origenToAdd.getId();
	}
	
	@Override
	public Origen agregarOrigen(Origen origen) {
		return origenRepository.add(origen);
	}
	
	@Override
	public Origen actualizarOrigen(Origen modificado) {
		return origenRepository.update(modificado);
	}
	
	@Override
	public Origen obtenerOrigen(Long id) {
		return origenRepository.read(id);
	}
	
	@Override
	public List<Origen> obtenerListaOrigenes() {
		return origenRepository.list();
	}
	
	@Override
	public Origen obtenerOrigenPorNombre(String nombre) {
		Origen origenActual = null;
		List<Origen> listaOrigenes = obtenerListaOrigenes();
		for (Origen origen: listaOrigenes) {
			if (nombre == origen.getNombre()) {
				origenActual = origen;
			}
		}
		return origenActual;
	}
	
	@Override
	public void eliminarOrigen(Long id) {
		Origen origenToDelete = obtenerOrigen(id);
		origenRepository.delete(origenToDelete);
	}

	@Override
	public Integer consultaEventosDependientes(Origen origen) {
		return origenRepository.consultaEventosDependientes(origen);
	}

	@Override
	public List<Origen> listaOrigenesActivos() {
		return origenRepository.listaOrigenesActivos();
	}

}
