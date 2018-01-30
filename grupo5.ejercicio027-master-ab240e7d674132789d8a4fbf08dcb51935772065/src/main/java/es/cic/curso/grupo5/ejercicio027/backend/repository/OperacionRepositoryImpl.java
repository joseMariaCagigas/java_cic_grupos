package es.cic.curso.grupo5.ejercicio027.backend.repository;

import org.springframework.stereotype.Repository;

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Operacion;
 
 

@Repository
public class OperacionRepositoryImpl extends AbstractRepositoryImpl <Long, Operacion> implements OperacionRepository {

	@Override
    public Class<Operacion> getClassDeT() {
        return Operacion.class;
    }

	@Override
    public String getNombreTabla() {
        return "OPERACION";
    }
	
	
}
