package es.cic.curso.grupo4.ejercicio027.servicio;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import es.cic.curso.grupo4.ejercicio027.dominio.Conector;
import es.cic.curso.grupo4.ejercicio027.servicio.ConectorServicio;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo4/ejercicio027/applicationContext.xml"
		})
public class ConectorServicioTest {

	Conector conector;
	
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
	}

	@Test
	public void testEliminaConector() {
		int tamanio = cs.listaConectores().size();
		cs.agregaConector(conector);
		cs.eliminaConector(conector.getId());
		assertEquals(tamanio,cs.listaConectores().size());
	}

	@Test
	public void testModificaConector() {
		cs.agregaConector(conector);
		Conector leeConector = cs.obtenConector(conector.getId());
		leeConector.setNombre("Modificado");
		assertTrue("Modificado"==leeConector.getNombre());
	}

	@Test
	public void testObtenConector() {
		cs.agregaConector(conector);
		assertNotNull(cs.obtenConector(conector.getId()));
	}
}
