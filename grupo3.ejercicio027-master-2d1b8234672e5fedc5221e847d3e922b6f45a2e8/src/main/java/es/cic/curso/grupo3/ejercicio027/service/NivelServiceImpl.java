package es.cic.curso.grupo3.ejercicio027.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo3.ejercicio027.domain.Nivel;
import es.cic.curso.grupo3.ejercicio027.repository.NivelRepository;

@Transactional
@Service
public class NivelServiceImpl implements NivelService {
	
	@Autowired
	private NivelRepository nivelRepository;
	
	
	@Override
	public Long agregarNivel(String nombre) {
		Nivel nivel = new Nivel();
		nivel.setNombre(nombre);
		
		Nivel nivelToAdd = agregarNivel(nivel);
		return nivelToAdd.getId();
	}
	
	@Override
	public Nivel agregarNivel(Nivel nivel) {
		return nivelRepository.add(nivel);
	}
	
	@Override
	public Nivel actualizarNivel(Nivel modificado) {
		return nivelRepository.update(modificado);
	}
	
	@Override
	public Nivel obtenerNivel(Long id) {
		return nivelRepository.read(id);
	}
	
	@Override
	public List<Nivel> obtenerListaNiveles() {
		return nivelRepository.list();
	}
	
	@Override
	public Nivel obtenerNivelPorNombre(String nombre) {
		Nivel nivelActual = null;
		List<Nivel> listaNiveles = obtenerListaNiveles();
		for (Nivel nivel: listaNiveles) {
			if (nombre == nivel.getNombre()) {
				nivelActual = nivel;
			}
		}
		return nivelActual;
	}

	@Override
	public void eliminarNivel(Long id) {
		Nivel nivelToDelete = obtenerNivel(id);
		nivelRepository.delete(nivelToDelete);
	}
	
}
