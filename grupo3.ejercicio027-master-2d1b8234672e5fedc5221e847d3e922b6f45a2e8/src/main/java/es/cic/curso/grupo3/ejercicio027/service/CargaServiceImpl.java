package es.cic.curso.grupo3.ejercicio027.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo3.ejercicio027.domain.Evento;
import es.cic.curso.grupo3.ejercicio027.domain.Nivel;
import es.cic.curso.grupo3.ejercicio027.domain.Origen;
import es.cic.curso.grupo3.ejercicio027.domain.Rol;
import es.cic.curso.grupo3.ejercicio027.domain.Tipo;
import es.cic.curso.grupo3.ejercicio027.domain.Usuario;


@Transactional
@Service
public class CargaServiceImpl implements CargaService {
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private NivelService nivelService;
	
	@Autowired
	private OrigenService origenService;
	
	@Autowired
	private TipoService tipoService;
	
	@Autowired
	private EventoService eventoService;
	
	
	
	@Override
	public void cargarEventos() {
		
		Rol analista = new Rol("Analista");
		Rol administrador = new Rol("Administrador");
		Rol desarrollador = new Rol("Desarrollador");
		
		rolService.aniadirRol(analista);
		rolService.aniadirRol(administrador);
		rolService.aniadirRol(desarrollador);

		Usuario pablo = new Usuario("Pablo", "Ape01 Ape02", false, analista);
		Usuario aitor = new Usuario("Aitor", "Ape03 Ape04", true, administrador);
		Usuario luis = new Usuario("Luis", "Ape05 Ape06", true, desarrollador);
		
		usuarioService.aniadirUsuario(pablo);
		usuarioService.aniadirUsuario(aitor);
		usuarioService.aniadirUsuario(luis);
		
		Origen seguridad = new Origen("Seguridad", true);
		Origen recursosHumanos = new Origen("Recursos humanos", true);
		Origen administracion = new Origen("Administración", true);
		
		origenService.agregarOrigen(seguridad);
		origenService.agregarOrigen(recursosHumanos);
		origenService.agregarOrigen(administracion);
		
		Nivel bajo = new Nivel("Bajo");
		Nivel medio = new Nivel("Medio");
		Nivel alto = new Nivel("Alto");
		Nivel critico = new Nivel("Crítico");
		Nivel bloqueante = new Nivel("Bloqueante");
		
		nivelService.agregarNivel(bajo);
		nivelService.agregarNivel(medio);
		nivelService.agregarNivel(alto);
		nivelService.agregarNivel(critico);
		nivelService.agregarNivel(bloqueante);
		
		Tipo informacion = new Tipo("Información");
		Tipo aviso = new Tipo("Aviso");
		Tipo error = new Tipo("Error");
	
		tipoService.agregarTipo(informacion);
		tipoService.agregarTipo(aviso);
		tipoService.agregarTipo(error);
		
		LocalDateTime localDateTime1 = LocalDateTime.of(2016, 12, 29, 20, 50, 12);
		LocalDateTime localDateTime2 = LocalDateTime.of(2017, 1, 6, 6, 30, 01);
		LocalDateTime localDateTime3 = LocalDateTime.of(2016, 12, 31, 23, 59, 59);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter ltf = DateTimeFormatter.ofPattern("HH:mm:ss");
		
		Date date01 = Date.valueOf(localDateTime1.format(dtf));
		Date date02 = Date.valueOf(localDateTime2.format(dtf));
		Date date03 = Date.valueOf(localDateTime3.format(dtf));
		
		Time time01 = Time.valueOf(localDateTime1.format(ltf));
		Time time02 = Time.valueOf(localDateTime2.format(ltf));
		Time time03 = Time.valueOf(localDateTime3.format(ltf));

		Evento evento01 = new Evento();
		evento01.setFecha(date01);
		evento01.setHora(time01);
		evento01.setOrigen(seguridad);
		evento01.setUsuario(pablo);
		evento01.setDescripcion("Descripción del evento número 1.");
		evento01.setNivel(bloqueante);
		evento01.setTipo(error);
		evento01.setAlta(true);
		
		Evento evento02 = new Evento();
		evento02.setFecha(date02);
		evento01.setHora(time02);
		evento02.setOrigen(recursosHumanos);
		evento02.setUsuario(aitor);
		evento02.setDescripcion("Descripción del evento número 2.");
		evento02.setNivel(critico);
		evento02.setTipo(aviso);
		evento02.setAlta(true);
		
		Evento evento03 = new Evento();
		evento03.setFecha(date03);
		evento01.setHora(time03);
		evento03.setOrigen(administracion);
		evento03.setUsuario(luis);
		evento03.setDescripcion("Descripción del evento número 3.");
		evento03.setNivel(alto);
		evento03.setTipo(informacion);
		evento03.setAlta(true);
		
		eventoService.aniadirEvento(evento01);
		eventoService.aniadirEvento(evento02);
		eventoService.aniadirEvento(evento03);
	}

}
