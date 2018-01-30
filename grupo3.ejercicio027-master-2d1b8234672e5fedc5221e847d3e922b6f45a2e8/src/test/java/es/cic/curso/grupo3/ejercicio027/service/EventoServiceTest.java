package es.cic.curso.grupo3.ejercicio027.service;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo3.ejercicio027.domain.Evento;
import es.cic.curso.grupo3.ejercicio027.domain.Nivel;
import es.cic.curso.grupo3.ejercicio027.domain.Origen;
import es.cic.curso.grupo3.ejercicio027.domain.Rol;
import es.cic.curso.grupo3.ejercicio027.domain.Tipo;
import es.cic.curso.grupo3.ejercicio027.domain.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo3/ejercicio027/applicationContext.xml"
		})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class EventoServiceTest {


	@Autowired
	EventoService sut;
	
	@Autowired
	OrigenService origenService;
	
	@Autowired
	RolService rolService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	NivelService nivelService;
	
	@Autowired
	TipoService tipoService;
	
	Long origenId;
	Origen origen;

	Long rolId;
	Rol rol;
	
	Long usuarioId;
	Usuario usuario;
	
	Long nivelId;
	Nivel nivel;
	
	Long tipoId;
	Tipo tipo;
	
	Date ahora;
	Time hora;
	
	@Before
	public void setUp() throws Exception {

		origenId = origenService.agregarOrigen("Seguridad",true);
		origen = origenService.obtenerOrigen(origenId);
		
		rolId = rolService.aniadirRol("Administrador");	
		rol = rolService.obtenerRol(rolId);
		
		usuarioId = usuarioService.aniadirUsuario(rolId, "Pablo", "Vega Pérez", true);
		usuario = usuarioService.obtenerUsuario(usuarioId);
		
		nivelId = nivelService.agregarNivel("Medio");
		nivel = nivelService.obtenerNivel(nivelId);
		
		tipoId = tipoService.agregarTipo("Error");
		tipo = tipoService.obtenerTipo(tipoId);
	}


	@Test
	public void testAniadirEvento() {
		Long idEvento = sut.aniadirEvento("descripción", origenId, usuarioId, nivelId, tipoId, true);
		
		assertNotNull(idEvento);
	}

	@Test
	public void testObtenerEvento() {
		Long idEvento = sut.aniadirEvento("descripción", origenId, usuarioId, nivelId, tipoId, true);
		Evento evento = sut.obtenerEvento(idEvento);
		
		assertNotNull(idEvento);
		assertTrue(evento.isAlta() == true);
	}

	@Test
	public void testObtenerEventos() {
		List<Evento>eventos = sut.obtenerEventos();
		
		for(Evento evento: eventos){
			assertNotNull(evento.getId());
		}	
	}

	@Test
	public void testActualizarEvento() {
		Long idEvento = sut.aniadirEvento("descripción", origenId, usuarioId, nivelId, tipoId, true);
		Evento evento = sut.obtenerEvento(idEvento);
		evento.setAlta(false);
		sut.actualizarEvento(evento);
		
		Evento eventoMod = sut.obtenerEvento(idEvento);
		
		assertTrue(eventoMod.isAlta() == false);
		assertEquals("No coinciden los Hashes", evento.hashCode(), eventoMod.hashCode());
		assertTrue("No es el mismo objeto", eventoMod.equals(evento) == true);
	}

	@Test
	public void testBorrarEvento() {
		Long idEvento = sut.aniadirEvento("descripción", origenId, usuarioId, nivelId, tipoId, true);
		
		sut.borrarEvento(idEvento);
		
		List<Evento>eventos = sut.obtenerEventos();
		
		assertTrue(eventos.size() == 0);
	}
	
	@Test
	public void testConsultaEventosDependientesUsuario() {
		sut.aniadirEvento("descripción 1", origenId, usuarioId, nivelId, tipoId, true);
		sut.aniadirEvento("descripción 2", origenId, usuarioId, nivelId, tipoId, true);
		
		assertTrue("No se detectan correctamente los eventos dependientes del Usuario", usuarioService.consultaEventosDependientes(usuario) >= 1);
	}
	
	@Test
	public void testConsultaEventosDependientesOrigen() {
		sut.aniadirEvento("descripción 1", origenId, usuarioId, nivelId, tipoId, true);
		sut.aniadirEvento("descripción 2", origenId, usuarioId, nivelId, tipoId, true);
		
		assertTrue("No se detectan correctamente los eventos dependientes del Origen", origenService.consultaEventosDependientes(origenService.obtenerOrigen(origenId)) >= 1);
	}
}
