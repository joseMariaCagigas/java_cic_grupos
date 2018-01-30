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
import es.cic.curso.grupo1.ejercicio027.dominio.Registro;
import es.cic.curso.grupo1.ejercicio027.dominio.Tarea;
import es.cic.curso.grupo1.ejercicio027.helper.TestHelper;
import es.cic.curso.grupo1.ejercicio027.repository.RegistroRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo1/ejercicio027/applicationContext.xml"
		})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class RegistroServiceTest {

	@Autowired
	private EjecucionService ser;
	@Autowired
	private TestHelper tHelper;
	@Autowired
	private RegistroRepository repo;
	@Autowired
	private TareaService tareaSer;

	@PersistenceContext
	private EntityManager em;

	private long clave;
	private Registro reg;

	@Before
	public void setUp() throws Exception {
		clave = tHelper.generaRegistro();
		reg = repo.read(clave);
	}

	@Test
	public void testAdd() {
		Registro nueva = ser.addRegistro(reg);

		Registro aniadida = repo.read(nueva.getId());
		
		assertNotNull(aniadida);
	}
	
	@Test
	public void testUpdate() {
		Registro nueva = ser.addRegistro(reg);
		Registro aniadida = repo.read(nueva.getId());
		
		assertEquals("Campo", aniadida.getCampo());
		
		aniadida.setCampo("Cambio de campo");
		Registro aniadida2 = ser.updateRegistro(aniadida);
		
		assertEquals("Cambio de campo", aniadida2.getCampo());
		assertEquals("Calc01", aniadida2.getEjecucion().getTarea().getCodigo());
	}
	
	@Test
	public void testList_IntegracionDelete() {
		Collection<Registro> lista = ser.listaRegistros();
		assertEquals(1, lista.size());
		
		ser.deleteRegistro(reg);
		
		lista = ser.listaRegistros();

		assertEquals(0, lista.size());
	}
	
	@Test
	public void testBorrarTarea() {
		Collection<Tarea> listaTareas = tareaSer.getTareas();
		assertEquals(1, listaTareas.size());
		
		Collection<Ejecucion> listaE = ser.listaEjecucion();
		assertEquals(1, listaE.size());
		
		Collection<Registro> listaR = ser.listaRegistros();
		assertEquals(1, listaR.size());
		
		tareaSer.borrarTarea(reg.getEjecucion().getTarea().getId());
		
		listaTareas = tareaSer.getTareas();
		assertEquals(0, listaTareas.size());
		
		listaE = ser.listaEjecucion();

		assertEquals(0, listaE.size());
		
		listaR = ser.listaRegistros();
		assertEquals(0, listaR.size());
	}
	
	@Test
	public void testBuscar_Registro() {
		Registro nueva = ser.buscaRegistro(reg);
		assertEquals("Campo", nueva.getCampo());
	}
	
	@Test
	public void testBuscar_Id() {
		Registro nueva = ser.buscaRegistro(clave);
		assertEquals("Campo", nueva.getCampo());
		assertEquals("valor", nueva.getValor());
		
		nueva = ser.buscaRegistro(43L);
		assertNull(nueva);
	}
}