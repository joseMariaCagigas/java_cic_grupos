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

import es.cic.curso.grupo3.ejercicio027.domain.Origen;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo3/ejercicio027/applicationContext.xml"
		})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class OrigenServiceImplTest {

	@Autowired
	private OrigenService sut;
	
	@Before
	public void setUp() throws Exception {
		limpiarOrigen();
	}
	

	@Test
	public void testAgregarOrigen() {
		Long origenId = sut.agregarOrigen("Origen01",true);
		assertNotNull("Error al agregar un Origen", origenId);
	}
	
	@Test
	public void testObtenerOrigen() {
		Long origenId = sut.agregarOrigen("Origen02",true);
		Origen origen = sut.obtenerOrigen(origenId);
		assertNotNull("Error. No debería ser nulo", origenId);
		assertEquals("Error al agregar el tipo", "Origen02", origen.getNombre());
	}
	
	@Test
	public void testObtenerListaOrigenes() {
		List<Origen> listaOrigenes = sut.obtenerListaOrigenes();
		for (Origen origen: listaOrigenes) {
			assertNotNull("No debería ser nulo", origen.getId());
		}
	}
	
	@Test
	public void testActualizarOrigen() {
		Long origenId = sut.agregarOrigen("Origen03", true);
		Origen origen = sut.obtenerOrigen(origenId);
		origen.setNombre("Modificado");
		sut.actualizarOrigen(origen);
		
		Origen modificado = sut.obtenerOrigen(origenId);
		
		assertEquals("Error al modificar el Origen", "Modificado", modificado.getNombre());
		assertTrue("No es el mismo objeto", modificado.equals(origen));
		assertTrue("El origen no está activo", origen.isAlta());
		assertEquals("No coinciden los Hashes", origen.hashCode(), modificado.hashCode());
		assertTrue("No son el mismo objeto", origen.equals(modificado) == true);
	}
	
	@Test
	public void testEliminarOrigen() {
		Long origenId = sut.agregarOrigen("Origen04",true);
		sut.eliminarOrigen(origenId);
		List<Origen> listaOrigenes = sut.obtenerListaOrigenes();
		assertTrue("Error. Debería ser 0", listaOrigenes.size() == 0);
	}

	@Test
	public void testObtenerOrigenPorNombre() {
		sut.agregarOrigen("Origen Test", true);
		Long idOrigen = sut.agregarOrigen("Origen Test 2", true);
		Origen origen = sut.obtenerOrigen(idOrigen);
		
		Origen origenPorNombre = sut.obtenerOrigenPorNombre("Origen Test 2");
		
		assertEquals("No es el mismo objeto", origenPorNombre, origen);
	}
	
	@Test
	public void testListarActivos() {
		sut.agregarOrigen("Origen 1", true);
		
		assertTrue("La lista de Origenes activos no tiene miembros", sut.listaOrigenesActivos().size() >= 1);
	}
	
	private void limpiarOrigen() {
		List<Origen> listaOrigen = sut.obtenerListaOrigenes();
		for (Origen origen: listaOrigen) {
			sut.eliminarOrigen(origen.getId());
		}
	}
}
