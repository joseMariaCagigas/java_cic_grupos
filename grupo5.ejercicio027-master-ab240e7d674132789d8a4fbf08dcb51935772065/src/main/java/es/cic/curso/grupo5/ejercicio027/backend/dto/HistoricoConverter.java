package es.cic.curso.grupo5.ejercicio027.backend.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Historico;
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Usuario;



@Component
public class HistoricoConverter {
	
	
	private UsuarioConverter usuarioConverter;
	
	public HistoricoDTO entityToDto(Historico historico, Usuario u) {
		HistoricoDTO resultado = new HistoricoDTO();
		resultado.setUsuario(u.getNombre());
		resultado.setOperacion(historico.getOperacion());
		resultado.setHora(historico.getHora());
		resultado.setPermitido(historico.isPermitido());
		
		return resultado;
		
	}

	
	public Historico DTO2Entity(HistoricoDTO historicoDTO, UsuarioDTO usuarioDTO) {
		Historico resultado = new Historico();
		resultado.setUsuario(usuarioConverter.DTO2Entity(usuarioDTO));
		resultado.setOperacion(historicoDTO.getOperacion());
		resultado.setHora(historicoDTO.getHora());
		resultado.setPermitido(historicoDTO.isPermitido());
		return resultado;		
	}
	
	public List<HistoricoDTO> entity2DTO(List<Historico> historicos, List<Usuario> usuarios) {
		List<HistoricoDTO> resultado = new ArrayList<HistoricoDTO>();
		for(Historico historico: historicos) {
			for(Usuario usuario : usuarios){
				resultado.add(entityToDto(historico, usuario));
			}
		}
		return resultado;
	}
	
	public List<Historico> DTO2Entity(List<HistoricoDTO> historicosDTO, List<UsuarioDTO> usuariosDTO) {
		List<Historico> resultado = new ArrayList<Historico>();
		for(HistoricoDTO historico: historicosDTO) {
			for(UsuarioDTO usuario : usuariosDTO){
			resultado.add(DTO2Entity(historico, usuario));
			}
		};
		return resultado;		
	}	

}
