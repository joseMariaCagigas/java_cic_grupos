package es.cic.curso.grupo3.ejercicio027.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo3.ejercicio027.domain.Tipo;
import es.cic.curso.grupo3.ejercicio027.repository.TipoRepository;

@Transactional
@Service
public class TipoServiceImpl implements TipoService {
	
	@Autowired
	private TipoRepository tipoRepositoty;
	
	
	@Override
	public Long agregarTipo(String nombre) {
		Tipo tipo = new Tipo();
		tipo.setNombre(nombre);
		
		Tipo tipoToAdd = agregarTipo(tipo);
		return tipoToAdd.getId();
	}

	@Override
	public Tipo agregarTipo(Tipo tipo) {
		return tipoRepositoty.add(tipo);
	}
	
	@Override
	public Tipo actualizarTipo(Tipo modificado) {
		return tipoRepositoty.update(modificado);
	}
	
	@Override
	public Tipo obtenerTipo(Long id) {
		return tipoRepositoty.read(id);
	}
	
	@Override
	public List<Tipo> obtenerListaTipos() {
		return tipoRepositoty.list();
	}
	
	@Override
	public Tipo obtenerTipoPorNombre(String nombre) {
		Tipo tipoActual = null;
		List<Tipo> listaTipos = obtenerListaTipos();
		for (Tipo tipo: listaTipos) {
			if (nombre == tipo.getNombre()) {
				tipoActual = tipo;
			}
		}
		return tipoActual;
	}
	
	@Override
	public void eliminarTipo(Long id) {
		Tipo tipoToDelete = obtenerTipo(id);
		tipoRepositoty.delete(tipoToDelete);
	}
}
