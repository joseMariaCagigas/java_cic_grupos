package es.cic.curso.grupo1.ejercicio027.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import es.cic.curso.grupo1.ejercicio027.dominio.Ejecucion;
import es.cic.curso.grupo1.ejercicio027.helper.TestHelper;
import es.cic.curso.grupo1.ejercicio027.repository.EjecucionRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo1/ejercicio027/applicationContext.xml"
		})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class EjecucionServiceTest {

	@Autowired
	private EjecucionService ser;
	@Autowired
	private TestHelper tHelper;
	@Autowired
	private EjecucionRepository repo;

	@PersistenceContext
	private EntityManager em;

	private long clave;
	private Ejecucion eje;

	@Before
	public void setUp() throws Exception {
		clave = tHelper.generaEjecucion();
		eje = repo.read(clave);
	}

	@Test
	public void testAdd() {
		Ejecucion nueva = ser.addEjecucion(eje);

		Ejecucion aniadida = repo.read(nueva.getId());
		
		assertNotNull(aniadida);
	}
	
	@Test
	public void testUpdate() {
		Ejecucion nueva = ser.addEjecucion(eje);
		Ejecucion aniadida = repo.read(nueva.getId());
		
		assertNotNull(aniadida);
		
		aniadida.getTarea().setCodigo("Código");
		aniadida.setFecha("12/01/2016");
		Ejecucion aniadida2 = ser.updateEjecucion(aniadida);
		
		assertEquals("12/01/2016", aniadida2.getFecha());
	}
	
	@Test
	public void testList_IntegracionDelete() {
		Collection<Ejecucion> lista = ser.listaEjecucion();
		assertEquals(1, lista.size());
		
		ser.deleteEjecucion(eje);
		
		lista = ser.listaEjecucion();

		assertEquals(0, lista.size());
	}
	
	@Test
	public void testBuscar_Ejecucion() {
		Ejecucion nueva = ser.buscaEjecucion(eje);
		assertEquals("Calc01", nueva.getTarea().getCodigo());
	}
	
	@Test
	public void testBuscar_Id() {
		Ejecucion nueva = ser.buscaEjecucion(clave);
		assertEquals("Calc01", nueva.getTarea().getCodigo());
		
		nueva = ser.buscaEjecucion(43L);
		assertNull(nueva);
	}
}