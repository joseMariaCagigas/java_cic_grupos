package es.cic.curso.grupo5.ejercicio027.backend.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Usuario;


@Component
public class UsuarioConverter {
	public UsuarioDTO entity2DTO(Usuario usuario) {
		
		UsuarioDTO resultado = new UsuarioDTO();
		resultado.setId(usuario.getId());
		resultado.setNombre(usuario.getNombre());
		resultado.setPassword(usuario.getPassword());
		resultado.setRol(usuario.getRol());
		resultado.setEmail(usuario.getPassword());
		return resultado;
		
	}
	public Usuario DTO2Entity(UsuarioDTO usuarioDTO) {
		Usuario resultado = new Usuario();
		resultado.setId(usuarioDTO.getId());
		resultado.setNombre(usuarioDTO.getNombre());
		resultado.setPassword(usuarioDTO.getPassword());
		resultado.setRol(usuarioDTO.getRol());
		resultado.setEmail(usuarioDTO.getPassword());
		return resultado;		
	}
	
	public List<UsuarioDTO> entity2DTO(List<Usuario> usuarios) {
		List<UsuarioDTO> resultado = new ArrayList<UsuarioDTO>();
		for(Usuario usuario: usuarios) {
			resultado.add(entity2DTO(usuario));
		}
		return resultado;
	}
	
	public List<Usuario> DTO2Entity(List<UsuarioDTO> usuariosDTO) {
		List<Usuario> resultado = new ArrayList<Usuario>();
		for(UsuarioDTO usuario: usuariosDTO) {
			resultado.add(DTO2Entity(usuario));
		};
		return resultado;		
	}	
	
}
