package es.cic.curso.grupo6.ejercicio027.repositorio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo6/ejercicio027/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class RepositorioFicheroTest {
public static final int NUMERO_ELEMENTOS_PRUEBA = 10;
public static final String RUTA_PRUEBA_1 = "/directorio/prueba";
	
	@Autowired
	private RepositorioFichero sut;
	
	@PersistenceContext
	protected EntityManager em;

	private Fichero generaFichero() {
		Directorio directorio = new Directorio();
		directorio.setRuta(RUTA_PRUEBA_1);
		em.persist(directorio);
		em.flush();
		
		Fichero elemento = new Fichero();
		elemento.setNombre("Inicial");
		elemento.setDescripcion("Elemento inicial de prueba");
		elemento.setVersion(2.0);
		elemento.setDirectorio(directorio);

		sut.create(elemento);
		return elemento;

	}

	@Test
	public void testCreate() {
		Fichero elemento;
		
		elemento = new Fichero();
		assertNull(elemento.getId());

		elemento = generaFichero();
		assertNotNull(elemento.getId());
	}

	@Test
	public void testRead() {
		Fichero elemento1 = generaFichero();
		Fichero elemento2 = sut.read(elemento1.getId());

		assertTrue(elemento1.getId().equals(elemento2.getId()));
		assertTrue(elemento1.getNombre().equals(elemento2.getNombre()));
		assertTrue(elemento1.getDescripcion().equals(elemento2.getDescripcion()));
		assertEquals(elemento1.getVersion(), elemento2.getVersion(), 0.0001);

		try {
			@SuppressWarnings("unused")
			Fichero elemento3 = sut.read(Long.MIN_VALUE);
			fail("No deberían existir elementos con el ID pasado");
		} catch (PersistenceException pe) {

		}
	}

	@Test
	public void testUpdate() {
		Fichero original = generaFichero();
		Fichero clon = new Fichero();
		clon.setId(original.getId());
		clon.setNombre(original.getNombre());
		clon.setDescripcion(original.getDescripcion());
		clon.setVersion(original.getVersion());
		clon.setDirectorio(original.getDirectorio());

		original.setNombre("No Principal");
		original.setDescripcion("Caso de prueba");
		sut.update(original);

		Fichero modificado = sut.read(original.getId());
		assertEquals(original.getNombre(), modificado.getNombre());
		assertNotEquals(clon.getDescripcion(), modificado.getDescripcion());
	}

	@Test
	public void testDelete() {
		Fichero elemento = generaFichero();
		sut.delete(elemento.getId());

		Fichero resultado = sut.read(elemento.getId());
		assertNull(resultado);
	}

	@Test
	public void testList() {
		for (int i = 0; i < NUMERO_ELEMENTOS_PRUEBA; i++) {
			generaFichero();
		}

		List<Fichero> lista = sut.list();
		assertEquals(NUMERO_ELEMENTOS_PRUEBA, lista.size());
	}

}
