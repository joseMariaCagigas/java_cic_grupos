package es.cic.curso.grupo4.ejercicio027.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.cic.curso.grupo4.ejercicio027.dominio.Flujo;
import es.cic.curso.grupo4.ejercicio027.repositorio.FlujoRepository;

@Service
@Transactional
public class FlujoServicioImpl implements FlujoServicio{
	
	@Autowired
	private FlujoRepository flujoRepositorio;
	
	@Override
	public void agregaFlujo(Flujo flujo) {
		flujoRepositorio.add(flujo);
		
	}

	@Override
	public void eliminaFlujo(Long id) {
		flujoRepositorio.delete(id);
	}

	@Override
	public void modificaFlujo(Flujo flujo) {
		flujoRepositorio.update(flujo);
	}

	@Override
	public Flujo obtenFlujo(Long id) {
		return flujoRepositorio.read(id);
	}

	@Override
	public List<Flujo> listaFlujos() {
		return flujoRepositorio.list();
	}

}
