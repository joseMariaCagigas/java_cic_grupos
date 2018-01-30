package es.cic.curso.grupo1.ejercicio027.service;

import static org.junit.Assert.assertEquals;
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

import es.cic.curso.grupo1.ejercicio027.dominio.Tarea;
import es.cic.curso.grupo1.ejercicio027.helper.TestHelper;
import es.cic.curso.grupo1.ejercicio027.repository.TareaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo1/ejercicio027/applicationContext.xml"
		})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class TareaServiceTest {

	@Autowired
	private TareaService tareaSer;
	@Autowired
	private TestHelper tHelper;
	@Autowired
	private TareaRepository tareaRepo;

	@PersistenceContext
	private EntityManager em;

	private long clave;

	@Before
	public void setUp() throws Exception {
		clave = tHelper.generaTarea();
	}

	@Test
	public void testNuevaTarea() {
		Tarea tareaNueva = tareaSer.nuevaTarea("Cod12", "Descripción del Cod12");

		Tarea tareaAniadida = tareaRepo.read(tareaNueva.getId());
		
		assertEquals("Cod12", tareaAniadida.getCodigo());
	}
	
	@Test
	public void testModificarTareaNom() {
		Tarea resultado = tareaSer.modificarTareaNom(clave, "AbCd");
		
		Tarea tareaAniadida = tareaRepo.read(resultado.getId());

		assertEquals("AbCd", tareaAniadida.getCodigo());
	}
	
	@Test
	public void testModificarTareaDec() {
		Tarea resultado = tareaSer.modificarTareaDesc(clave, "Desc simple");
		
		Tarea tareaAniadida = tareaRepo.read(resultado.getId());

		assertEquals("Desc simple", tareaAniadida.getDescripcion());
	}
	
	@Test
	public void testBorrarTarea_IntegracionListar() {
		Collection<Tarea> listaTareas = tareaSer.getTareas();
		assertEquals(1, listaTareas.size());
		
		tareaSer.borrarTarea(clave);
		
		listaTareas = tareaSer.getTareas();

		assertEquals(0, listaTareas.size());
	}
	
	@Test
	public void testBuscarTarea_Codigo() {
		Tarea tareaNueva = tareaSer.buscarTarea("Calc01");
		assertEquals("Calculadora simple", tareaNueva.getDescripcion());
		
		tareaNueva = tareaSer.buscarTarea("Calc0001");
		assertNull(tareaNueva.getDescripcion());
	}
	
	@Test
	public void testBuscarTarea() {
		Tarea tareaNueva = tareaSer.buscarTarea(clave);
		assertEquals("Calculadora simple", tareaNueva.getDescripcion());
		
		tareaNueva = tareaSer.buscarTarea(1L);
		assertNull(tareaNueva);
	}
	
	@Test
	public void testAdd() {
		Tarea tareaNueva = new Tarea("A1B2", "Descripción de prueba");
		
		tareaSer.add(tareaNueva);
		
		Collection<Tarea> listaTareas = tareaSer.getTareas();
		assertEquals(2, listaTareas.size());
	}
	
	@Test
	public void testVerificarNombre() {
		boolean resultado = tareaSer.verificarNombre("Calc01");
		assertEquals(false, resultado);
		
		resultado = tareaSer.verificarNombre("Calc01001");
		assertEquals(true, resultado);
		
		resultado = tareaSer.verificarNombre("Calc01");
		assertEquals(false, resultado);
	}	
	
	@Test
	public void testUpdate() {
		Tarea tarNueva = tareaSer.nuevaTarea("Codcod", "Desdcde");
		Tarea tarAniadida = tareaRepo.read(tarNueva.getId());
		
		assertEquals("Codcod", tarAniadida.getCodigo());
		
		tarAniadida.setCodigo("EEEE");
		Tarea tarAniadida2 = tareaSer.update(tarAniadida);
		
		assertEquals("EEEE", tarAniadida2.getCodigo());
	}
}