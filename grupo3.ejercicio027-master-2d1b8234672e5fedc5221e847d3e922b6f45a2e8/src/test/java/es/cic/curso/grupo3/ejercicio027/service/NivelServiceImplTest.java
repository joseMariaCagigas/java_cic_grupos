package es.cic.curso.grupo3.ejercicio027.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

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

import es.cic.curso.grupo3.ejercicio027.domain.Nivel;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo3/ejercicio027/applicationContext.xml"
		})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class NivelServiceImplTest {
	
	@Autowired
	private NivelService sut;
	
	@Before
	public void setUp() throws Exception {
		limpiarNiveles();
	}
	

	@Test
	public void testAgregarNivel() {
		Long nivelId = sut.agregarNivel("Nivel01");
		assertNotNull("Error al agregar un Nivel", nivelId);
	}
	
	@Test
	public void testObtenerNivel() {
		Long nivelId = sut.agregarNivel("Nivel02");
		Nivel nivel = sut.obtenerNivel(nivelId);
		assertNotNull("Error. No debería ser nulo", nivelId);
		assertEquals("Error al agregar el nivel", "Nivel02", nivel.getNombre());
	}
	
	@Test
	public void testObtenerListaNiveles() {
		List<Nivel> listaNiveles = sut.obtenerListaNiveles();
		for (Nivel nivel: listaNiveles) {
			assertNotNull("No debería ser nulo", nivel.getId());
		}
	}
	
	@Test
	public void testActualizarNivel() {
		Long nivelId = sut.agregarNivel("Nivel03");
		Nivel nivel = sut.obtenerNivel(nivelId);
		nivel.setNombre("NivelMod");
		sut.actualizarNivel(nivel);
		
		Nivel nivelMod = sut.obtenerNivel(nivelId);
		
		assertEquals("Error al modificar el nivel", "NivelMod", nivelMod.getNombre());
		assertTrue("No es el mismo objeto", nivelMod.equals(nivel));
		assertEquals("No coinciden los Hashes", nivel.hashCode(), nivelMod.hashCode());
		assertTrue("No son el mismo objeto", nivel.equals(nivelMod) == true);
	}
	
	@Test
	public void testEliminarNivel() {
		Long nivelId = sut.agregarNivel("Nivel04");
		sut.eliminarNivel(nivelId);
		List<Nivel> listaNiveles = sut.obtenerListaNiveles();
		assertTrue("Error. Debería ser 0", listaNiveles.size() == 0);
	}
	
	@Test
	public void testObtenerNivelPorNombre() {
		sut.agregarNivel("Nivel03");
		Long nivelId = sut.agregarNivel("Nivel04");
		Nivel nivel = sut.obtenerNivel(nivelId);
		
		Nivel nivelPorNombre = sut.obtenerNivelPorNombre("Nivel04");
		
		assertEquals("No es el mismo objeto", nivelPorNombre, nivel);
	}
	
	private void limpiarNiveles() {
		List<Nivel> listaNiveles = sut.obtenerListaNiveles();
		for (Nivel nivel: listaNiveles) {
			sut.eliminarNivel(nivel.getId());
		}
	}
}
