package es.cic.curso.grupo1.ejercicio027.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo1/ejercicio027/applicationContext.xml"
		})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class TareaRepositoryTest {

	@Autowired
	private TareaRepository tareaRepository;
	@Autowired
	private TestHelper tHelper;

	@PersistenceContext
	private EntityManager em;

	@Test
	public void testAdd() {
		Tarea tarea = new Tarea();
		tarea.setDescripcion("Esta es una tarea nueva");
		assertNull(tarea.getId());

		tareaRepository.add(tarea);

		assertEquals("Esta es una tarea nueva", tarea.getDescripcion());
	}

	@Test
	public void testRead() {
		Long clavePrimaria = tHelper.generaTarea();

		Tarea resultado = tareaRepository.read(clavePrimaria);

		assertEquals("Calc01", resultado.getCodigo());
	}

	@Test(expected=PersistenceException.class)
	public void testRead_noExiste() {
		Long clavePrimaria = Long.MIN_VALUE;

		Tarea resultado = tareaRepository.read(clavePrimaria);

		assertEquals("Avellanas", resultado.getCodigo());
	}

	@Test
	public void testList() {
		tHelper.generaTarea();
		Tarea tarea = new Tarea();
		tareaRepository.add(tarea);
		tarea = new Tarea();
		tareaRepository.add(tarea);

		List<Tarea> resultado = tareaRepository.list();

		assertTrue(resultado.size()>= 3);
	}

	@Test
	public void testDelete() {
		Long clavePrimaria = tHelper.generaTarea();

		tareaRepository.delete(clavePrimaria);
		Tarea t;
		try {
			t  = em.find(Tarea.class, clavePrimaria);
		} catch (PersistenceException pe){
			return;
		}
		assertNull(t);
	}

	@Test
	public void testUpdate() {
		Long clavePrimaria = tHelper.generaTarea();

		Tarea tarea2 = new Tarea();
		tarea2.setId(clavePrimaria);
		tarea2.setDescripcion("Cambio de tarea");

		Tarea resultado = tareaRepository.update(tarea2);

		Tarea enBBDD = em.find(Tarea.class, clavePrimaria);
		assertEquals("Cambio de tarea", enBBDD.getDescripcion());
		assertEquals("Cambio de tarea", resultado.getDescripcion());
	}
}