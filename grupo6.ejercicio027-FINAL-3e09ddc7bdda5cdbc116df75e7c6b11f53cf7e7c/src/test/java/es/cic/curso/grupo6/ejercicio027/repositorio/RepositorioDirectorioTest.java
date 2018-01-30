package es.cic.curso.grupo6.ejercicio027.repositorio;

import static org.junit.Assert.*;

import java.util.List;

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

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo6/ejercicio027/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class RepositorioDirectorioTest {
	
	public static final int NUMERO_ELEMENTOS_PRUEBA = 10;
	public static final String RUTA_PRUEBA_1 = "/directorio/prueba";
	public static final String RUTA_PRUEBA_2 = "/prueba/directorio";
	
	@Autowired
	private RepositorioDirectorio sut;

	private Directorio generaElementoPrueba() {
		Directorio elemento = new Directorio();
		elemento.setRuta(RUTA_PRUEBA_1);
		
		sut.create(elemento);
		return elemento;
	}

	@Test
	public void testCreate() {
		Directorio elemento;
		
		elemento = new Directorio();
		assertNull(elemento.getId());

		elemento = generaElementoPrueba();
		assertNotNull(elemento.getId());
	}

	@Test
	public void testRead() {
		Directorio elemento1 = generaElementoPrueba();
		Directorio elemento2 = sut.read(elemento1.getId());

		assertTrue(elemento1.getId().equals(elemento2.getId()));
		assertTrue(elemento1.getRuta().equals(elemento2.getRuta()));

		try {
			@SuppressWarnings("unused")
			Directorio elemento3 = sut.read(Long.MIN_VALUE);
			fail("No deberían existir elementos con el ID pasado");
		} catch (PersistenceException pe) {

		}
	}

	@Test
	public void testUpdate() {
		Directorio original = generaElementoPrueba();
		Directorio clon = new Directorio();
		clon.setId(original.getId());
		clon.setRuta(original.getRuta());

		original.setRuta(RUTA_PRUEBA_2);
		sut.update(original);

		Directorio modificado = sut.read(original.getId());
		assertTrue(original.getRuta().equals(modificado.getRuta()));
		assertFalse(clon.getRuta().equals(modificado.getRuta()));
	}

	@Test
	public void testDelete() {
		Directorio elemento = generaElementoPrueba();
		sut.delete(elemento.getId());

		Directorio resultado = sut.read(elemento.getId());
		assertNull(resultado);
	}

	@Test
	public void testList() {
		for (int i = 0; i < NUMERO_ELEMENTOS_PRUEBA; i++) {
			generaElementoPrueba();
		}

		List<Directorio> lista = sut.list();
		assertEquals(NUMERO_ELEMENTOS_PRUEBA, lista.size());
	}

}
