package es.cic.curso.grupo4.ejercicio027.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo4.ejercicio027.dominio.Ejecucion;
import es.cic.curso.grupo4.ejercicio027.repositorio.EjecucionRepository;

@Service
@Transactional
public class EjecucionServicioImpl implements EjecucionServicio{

	@Autowired
	private EjecucionRepository ejecucionRepositorio;
	
	@Override
	public void agregaEjecucion(Ejecucion ejecucion) {
		ejecucionRepositorio.add(ejecucion);
	}

	@Override
	public void eliminaEjecucion(Long id) {
		ejecucionRepositorio.delete(id);
	}

	@Override
	public void modificaEjecucion(Ejecucion ejecucion) {
		ejecucionRepositorio.update(ejecucion);
	}

	@Override
	public Ejecucion obtenEjecucion(Long id) {
		return ejecucionRepositorio.read(id);
	}

	@Override
	public List<Ejecucion> listaEjecuciones() {
		return ejecucionRepositorio.list();
	}
	
}
