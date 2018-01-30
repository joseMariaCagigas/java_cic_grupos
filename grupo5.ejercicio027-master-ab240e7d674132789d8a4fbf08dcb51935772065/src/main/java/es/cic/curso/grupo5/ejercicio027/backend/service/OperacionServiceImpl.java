package es.cic.curso.grupo5.ejercicio027.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Operacion;
import es.cic.curso.grupo5.ejercicio027.backend.repository.OperacionRepository;
 

@Service
@Transactional
public class OperacionServiceImpl implements OperacionService{
	@Autowired
	private OperacionRepository operacionRepository;

    @Override
	public Operacion aniadirOperacion(Operacion operacion) {
        return operacionRepository.add(operacion);
	}

    @Override
    public Operacion modificarOperacion(Operacion operacion) {
    	return operacionRepository.update(operacion);
    }
   
    @Override
    public void borrarOperacion(Long id) {
    	Operacion operacionABorrar = obtenerOperacion(id);
    	operacionRepository.delete(operacionABorrar);
    }

    @Override
    public Operacion obtenerOperacion(Long id) {
        return operacionRepository.read(id);
    }

    @Override
    public List<Operacion> listarOperacion() {
        return operacionRepository.list();
    }
}
