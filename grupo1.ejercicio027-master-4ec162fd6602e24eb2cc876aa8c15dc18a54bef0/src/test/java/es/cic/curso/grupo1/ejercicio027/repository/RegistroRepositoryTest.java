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

import es.cic.curso.grupo1.ejercicio027.dominio.Registro;
import es.cic.curso.grupo1.ejercicio027.helper.TestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo1/ejercicio027/applicationContext.xml"
		})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class RegistroRepositoryTest {

	@Autowired
	private RegistroRepository repo;
	@Autowired
	private TestHelper tHelper;

	@PersistenceContext
	private EntityManager em;

	@Test
	public void testAdd() {
		Registro ejec = new Registro();
		assertNull(ejec.getId());

		repo.add(ejec);

		assertNotNull(ejec.getId());
	}

	@Test
	public void testRead() {
		Long clavePrimaria = tHelper.generaRegistro();

		Registro resultado = repo.read(clavePrimaria);

		assertNotNull(resultado);
	}

	@Test(expected=PersistenceException.class)
	public void testRead_noExiste() {
		Long clavePrimaria = Long.MIN_VALUE;

		Registro resultado = repo.read(clavePrimaria);

		assertEquals("Avellanas", resultado.getId());
	}

	@Test
	public void testList() {
		tHelper.generaRegistro();
		Registro ejec = new Registro();
		repo.add(ejec);
		ejec = new Registro();
		repo.add(ejec);

		List<Registro> resultado = repo.list();

		assertEquals(3, resultado.size());
	}

	@Test
	public void testDelete() {
		Long clavePrimaria = tHelper.generaRegistro();

		repo.delete(clavePrimaria);
		Registro r;
		try {
			r  = em.find(Registro.class, clavePrimaria);
		} catch (PersistenceException pe){
			return;
		}
		assertNull(r);
	}

	@Test
	public void testUpdate() {
		Long clavePrimaria = tHelper.generaRegistro();

		Registro eje2 = new Registro();
		eje2.setId(clavePrimaria);

		Registro resultado = repo.update(eje2);

		Registro enBBDD = em.find(Registro.class, clavePrimaria);
		assertNotNull(resultado);
		assertNotNull(enBBDD);
	}
}