package es.cic.curso.grupo5.ejercicio027.backend.service;

import java.util.List;

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Rol;

public interface RolService {

	Rol aniadirRol(Rol rol);

    void borrarRol(Long id);
    
    Rol modificarRol(Rol rol);

    Rol obtenerRol(Long id);

    List<Rol> listarRol();

	
}
