package es.cic.curso.grupo5.ejercicio027.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Rol;
 
import es.cic.curso.grupo5.ejercicio027.backend.repository.RolRepository;

@Service
@Transactional
public class RolServiceImpl implements RolService{

	@Autowired
	private RolRepository rolRepository;

    @Override
	public Rol aniadirRol(Rol rol) {
        return rolRepository.add(rol);
	}

    @Override
    public Rol modificarRol(Rol rol) {
    	return rolRepository.update(rol);
    }
   
    @Override
    public void borrarRol(Long id) {
    	Rol rolABorrar = obtenerRol(id);
        rolRepository.delete( rolABorrar);
    }

    @Override
    public Rol obtenerRol(Long id) {
        return rolRepository.read(id);
    }

    @Override
    public List<Rol> listarRol() {
        return rolRepository.list();
    }

}