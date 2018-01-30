package es.cic.curso.grupo3.ejercicio027.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo3.ejercicio027.domain.Rol;
import es.cic.curso.grupo3.ejercicio027.repository.RolRepository;

@Transactional
@Service
public class RolServiceImpl implements RolService {

	@Autowired
	private RolRepository rolRepository;

	
	@Override
	public Long aniadirRol(String nombre){
		Rol rol= new Rol();
		rol.setNombre(nombre);
		Rol aniadido = aniadirRol(rol);
		
		return aniadido.getId();
	}

	@Override
	public Rol aniadirRol(Rol nuevo) {
		return rolRepository.add(nuevo);
	}

	@Override
	public Rol obtenerRol(Long id){
		return rolRepository.read(id);
	}

	@Override
	public List<Rol> obtenerRoles(){
		return rolRepository.list();
	}

	@Override
	public Rol actualizarRol(Rol modificada){
		return rolRepository.update(modificada);
	}

	@Override
	public void borrarRol(Long id){
		Rol aBorrar = obtenerRol(id);
		rolRepository.delete(aBorrar);
	}

	@Override
	public Rol obtenerRolPorNombre(String nombre) {
		Rol rolActual = null;
		List<Rol> listaRoles = obtenerRoles();
		for (Rol rol: listaRoles) {
			if (nombre == rol.getNombre()) {
				rolActual = rol;
			}
		}
		return rolActual;
	}
}
