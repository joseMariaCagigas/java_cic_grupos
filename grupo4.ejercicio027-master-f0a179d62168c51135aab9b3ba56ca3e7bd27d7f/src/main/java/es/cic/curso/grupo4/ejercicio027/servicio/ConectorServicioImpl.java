package es.cic.curso.grupo4.ejercicio027.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.cic.curso.grupo4.ejercicio027.dominio.Conector;
import es.cic.curso.grupo4.ejercicio027.repositorio.ConectorRepository;

@Service
@Transactional
public class ConectorServicioImpl implements ConectorServicio{

	@Autowired
	private ConectorRepository conectorRepositorio;
	
	@Override
	public void agregaConector(Conector conector) {
		conectorRepositorio.add(conector);
	}

	@Override
	public void eliminaConector(Long id) {
		conectorRepositorio.delete(id);
	}

	@Override
	public void modificaConector(Conector conector) {
		conectorRepositorio.update(conector);
	}

	@Override
	public Conector obtenConector(Long id) {
		return conectorRepositorio.read(id);
	}

	@Override
	public List<Conector> listaConectores() {
		return conectorRepositorio.list();
	}

}
