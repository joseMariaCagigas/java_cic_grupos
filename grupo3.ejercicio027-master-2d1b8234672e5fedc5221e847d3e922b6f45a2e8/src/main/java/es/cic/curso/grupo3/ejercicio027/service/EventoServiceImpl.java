package es.cic.curso.grupo3.ejercicio027.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo3.ejercicio027.domain.Evento;
import es.cic.curso.grupo3.ejercicio027.domain.Nivel;
import es.cic.curso.grupo3.ejercicio027.domain.Origen;
import es.cic.curso.grupo3.ejercicio027.domain.Tipo;
import es.cic.curso.grupo3.ejercicio027.domain.Usuario;
import es.cic.curso.grupo3.ejercicio027.repository.EventoRepository;
import es.cic.curso.grupo3.ejercicio027.repository.NivelRepository;
import es.cic.curso.grupo3.ejercicio027.repository.OrigenRepository;
import es.cic.curso.grupo3.ejercicio027.repository.TipoRepository;
import es.cic.curso.grupo3.ejercicio027.repository.UsuarioRepository;

@Transactional
@Service
public class EventoServiceImpl implements EventoService {

	@Autowired
	private OrigenRepository origenRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private NivelRepository nivelRepository;
	
	@Autowired
	private TipoRepository tipoRepository;
	
	@Autowired
	private EventoRepository eventoRepository;

	
	@Override
	public Long aniadirEvento(String descripcion, Long origenId, Long usuarioId, Long nivelId, Long tipoId, boolean alta){
		Evento evento = new Evento();
		evento.setDescripcion(descripcion);
		Origen origen = origenRepository.read(origenId);
		evento.setOrigen(origen);
		Usuario usuario = usuarioRepository.read(usuarioId);
		evento.setUsuario(usuario);
		Nivel nivel = nivelRepository.read(nivelId);
		evento.setNivel(nivel);
		Tipo tipo = tipoRepository.read(tipoId);
		evento.setTipo(tipo);
		evento.setAlta(alta);
		
		LocalDateTime localDateTime = LocalDateTime.now();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String stringFecha = localDateTime.format(dtf);
		DateTimeFormatter ltf = DateTimeFormatter.ofPattern("HH:mm:ss");
		String stringHora = localDateTime.format(ltf);
		
		Date fecha = Date.valueOf(stringFecha);
		Time hora = Time.valueOf(stringHora);
		
		evento.setFecha(fecha);
		evento.setHora(hora);
		
		Evento aniadido = aniadirEvento(evento);
		
		return aniadido.getId();
	}

	@Override
	public Evento aniadirEvento(Evento nueva) {
		return eventoRepository.add(nueva);
	}

	@Override
	public Evento obtenerEvento(Long id){
		return eventoRepository.read(id);
	}
	

	@Override
	public List<Evento> obtenerEventos(){
		return eventoRepository.list();
	}

	@Override
	public Evento actualizarEvento(Evento modificado){
		return eventoRepository.update(modificado);
	}

	@Override
	public void borrarEvento(Long id){
		Evento aBorrar = obtenerEvento(id);
		eventoRepository.delete(aBorrar);
	}
}
