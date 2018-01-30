package es.cic.curso.grupo4.ejercicio027.servicio;

import static org.junit.Assert.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import es.cic.curso.grupo4.ejercicio027.dominio.Conector;
import es.cic.curso.grupo4.ejercicio027.dominio.Ejecucion;
import es.cic.curso.grupo4.ejercicio027.servicio.ConectorServicio;
import es.cic.curso.grupo4.ejercicio027.servicio.EjecucionServicio;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo4/ejercicio027/applicationContext.xml"
		})
public class EjecucionServiceTest {

	Conector conector;
	Ejecucion e;
	
	@Autowired
	private EjecucionServicio es;
	
	@Autowired
	private ConectorServicio cs;
	
	@Before
	public void setUp(){
		conector = new Conector();
		conector.setNombre("Conector1");
		conector.setDescripcion("Prueba");
		conector.setTipo("Fichero");
		conector.setOrigen("origen");
		conector.setDestino("destino");
		cs.agregaConector(conector);
		
		
		e = new Ejecucion();
		Timestamp hora = Timestamp.valueOf(LocalDateTime.now());
		e.setFecha(hora);
		e.setConector(conector);
	}

	@Test
	public void AgregarEjecucion(){
	
		es.agregaEjecucion(e);
		assertEquals(1,es.listaEjecuciones().size());
	}
	
	@Test
	public void ModificarEjecucionTest(){
		es.agregaEjecucion(e);
		
		Ejecucion leeEjecucion = es.obtenEjecucion(e.getId());
		leeEjecucion.setFecha(Timestamp.valueOf(LocalDateTime.now()));
		es.modificaEjecucion(leeEjecucion);
		assertTrue(leeEjecucion.getFecha().after(es.obtenEjecucion(e.getId()).getFecha()));
		
	}
	
	@Test
	public void EliminarEjecucionTest(){
		es.agregaEjecucion(e);
		es.eliminaEjecucion(e.getId());
		
		assertEquals(0,es.listaEjecuciones().size());
	}
}
