package es.cic.curso.grupo3.ejercicio027.repository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.cic.curso.grupo3.ejercicio027.AbstractRepositoryImplTest;
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
public class EventoRepositoryTest  extends AbstractRepositoryImplTest<Long, Evento>{

	@Autowired
	private EventoRepository sut;
	
	private Origen origen;
	private Rol rol;
	private Usuario usuario;
	private Nivel nivel;
	private Tipo tipo;
	private Date date;
	private Time time;
	
	@Before
	public void setUp() {
		super.setUp();

		origen = new Origen("Seguridad",true);
		em.persist(origen);
		
		rol = new Rol("Administrador");
		em.persist(rol);
		
		usuario = new Usuario("Pablo", "Vega Pérez", true, rol);
		em.persist(usuario);
		
		nivel = new Nivel("Medio");
		em.persist(nivel);
		
		tipo = new Tipo("Tipo 1");
		em.persist(tipo);
		
		LocalDateTime localDateTime1 = LocalDateTime.of(2016, 12, 29, 20, 50, 12);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter ltf = DateTimeFormatter.ofPattern("HH:mm:ss");
		date = Date.valueOf(localDateTime1.format(dtf));
		time = Time.valueOf(localDateTime1.format(ltf));
	}

	@Override
	public IRepository<Long, Evento> getRepository() {
		return sut;
	}

	@Override
	public Evento getInstanceDeTParaNuevo() {
		Evento evento = new Evento();
		evento.setOrigen(origen);
		evento.setUsuario(usuario);
		evento.setNivel(nivel);
		evento.setTipo(tipo);
		evento.setDescripcion("Esta es la descripción del evento");
		evento.setAlta(true);
		evento.setFecha(date);
		evento.setHora(time);
		
		return evento;
	}

	@Override
	public Evento getInstanceDeTParaLectura() {
		Evento evento = new Evento();
		evento.setOrigen(origen);
		evento.setUsuario(usuario);
		evento.setNivel(nivel);
		evento.setTipo(tipo);
		evento.setDescripcion("Esta es la descripción del evento");
		evento.setFecha(date);
		evento.setHora(time);
		evento.setAlta(true);
		
		return evento;
	}

	@Override
	public boolean sonDatosIguales(Evento t1, Evento t2) {
		if (t1 == null || t2 == null) {
			throw new UnsupportedOperationException("No puedo comparar nulos");
		}
		if (!t1.getDescripcion().equals(t2.getDescripcion())) {
			return false;
		}
		if (!t1.getOrigenObj().equals(t2.getOrigenObj())) {
			return false;
		}
		if (!t1.getUsuarioObj().equals(t2.getUsuarioObj())) {
			return false;
		}
		if (!t1.getNivelObj().equals(t2.getNivelObj())) {
			return false;
		}
		if (!t1.getTipoObj().equals(t2.getTipoObj())) {
			return false;
		}
	
		
		return true;
	}

	@Override
	public Long getClavePrimariaNoExistente() {
		return Long.MAX_VALUE;
	}

	@Override
	public Evento getInstanceDeTParaModificar(Long clave) {
		Evento evento = getInstanceDeTParaLectura();
		evento.setId(clave);
		evento.setDescripcion("Esta es la nueva descripción del evento");
		
		return evento;
	}
}