package es.cic.curso.grupo1.ejercicio027.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

import es.cic.curso.grupo1.ejercicio027.dominio.Ejecucion;
import es.cic.curso.grupo1.ejercicio027.helper.TestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo1/ejercicio027/applicationContext.xml"
		})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class EjecucionRepositoryTest {

	@Autowired
	private EjecucionRepository ejeRepository;
	@Autowired
	private TestHelper tHelper;

	@PersistenceContext
	private EntityManager em;

	@Test
	public void testAdd() {
		Ejecucion ejec = new Ejecucion();
		assertNull(ejec.getId());

		ejeRepository.add(ejec);

		assertNotNull(ejec.getId());
	}

	@Test
	public void testRead() {
		Long clavePrimaria = tHelper.generaEjecucion();

		Ejecucion resultado = ejeRepository.read(clavePrimaria);

		assertNotNull(resultado);
	}

	@Test(expected=PersistenceException.class)
	public void testRead_noExiste() {
		Long clavePrimaria = Long.MIN_VALUE;

		Ejecucion resultado = ejeRepository.read(clavePrimaria);

		assertEquals("Avellanas", resultado.getId());
	}

	@Test
	public void testList() {
		tHelper.generaEjecucion();
		Ejecucion ejec = new Ejecucion();
		ejeRepository.add(ejec);
		ejec = new Ejecucion();
		ejeRepository.add(ejec);

		List<Ejecucion> resultado = ejeRepository.list();

		assertEquals(3, resultado.size());
	}

	@Test
	public void testDelete() {
		Long clavePrimaria = tHelper.generaEjecucion();

		ejeRepository.delete(clavePrimaria);
		Ejecucion e;
		try {
			e  = em.find(Ejecucion.class, clavePrimaria);
		} catch (PersistenceException pe){
			return;
		}
		assertNull(e);
	}

	@Test
	public void testUpdate() {
		Long clavePrimaria = tHelper.generaEjecucion();

		Ejecucion eje2 = new Ejecucion();
		eje2.setId(clavePrimaria);

		Ejecucion resultado = ejeRepository.update(eje2);

		Ejecucion enBBDD = em.find(Ejecucion.class, clavePrimaria);
		assertNotNull(resultado);
		assertNotNull(enBBDD);
	}
}