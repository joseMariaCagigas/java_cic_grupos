package es.cic.curso.grupo3.ejercicio027.service;

import java.util.List;

import es.cic.curso.grupo3.ejercicio027.domain.Rol;

public interface RolService {

	Long aniadirRol(String nombre);

	Rol obtenerRol(Long id);

	List<Rol> obtenerRoles();

	Rol actualizarRol(Rol modificada);

	void borrarRol(Long id);

	Rol aniadirRol(Rol nuevo);

	Rol obtenerRolPorNombre(String nombre);
}