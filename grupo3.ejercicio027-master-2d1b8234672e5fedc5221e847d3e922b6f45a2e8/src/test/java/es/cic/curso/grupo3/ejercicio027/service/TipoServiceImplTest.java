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

import es.cic.curso.grupo3.ejercicio027.domain.Tipo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo3/ejercicio027/applicationContext.xml"
		})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class TipoServiceImplTest {

	@Autowired
	private TipoService sut;
	
	@Before
	public void setUp() throws Exception {
		limpiarTipos();
	}
	

	@Test
	public void testAgregarTipo() {
		Long tipoId = sut.agregarTipo("Tipo01");
		assertNotNull("Error al agregar un Nivel", tipoId);
	}
	
	@Test
	public void testObtenerTipo() {
		Long tipoId = sut.agregarTipo("Tipo02");
		Tipo tipo = sut.obtenerTipo(tipoId);
		assertNotNull("Error. No debería ser nulo", tipoId);
		assertEquals("Error al agregar el tipo", "Tipo02", tipo.getNombre());
	}
	
	@Test
	public void testObtenerListaTipos() {
		List<Tipo> listaNiveles = sut.obtenerListaTipos();
		for (Tipo tipo: listaNiveles) {
			assertNotNull("No debería ser nulo", tipo.getId());
		}
	}
	
	@Test
	public void testActualizarTipo() {
		Long tipoId = sut.agregarTipo("Tipo03");
		Tipo tipo = sut.obtenerTipo(tipoId);
		tipo.setNombre("Modificado");
		sut.actualizarTipo(tipo);
		
		Tipo modificado = sut.obtenerTipo(tipoId);
		assertEquals("Error al modificar el tipo", "Modificado", modificado.getNombre());
		assertTrue("No es el mismo objeto", modificado.equals(tipo));
		assertEquals("No coinciden los Hashes", tipo.hashCode(), modificado.hashCode());
		assertTrue("No son el mismo objeto", tipo.equals(modificado) == true);
	}
	
	@Test
	public void testEliminarTipo() {
		Long tipoId = sut.agregarTipo("Tipo04");
		sut.eliminarTipo(tipoId);
		List<Tipo> listaTipos = sut.obtenerListaTipos();
		assertTrue("Error. Debería ser 0", listaTipos.size() == 0);
	}

	@Test
	public void testObtenerTipoPorNombre() {
		sut.agregarTipo("Tipo03");
		Long tipoId = sut.agregarTipo("Tipo04");
		Tipo tipo = sut.obtenerTipo(tipoId);
		
		Tipo tipoPorNombre = sut.obtenerTipoPorNombre("Tipo04");
		
		assertEquals("No es el mismo objeto", tipoPorNombre, tipo);
	}
	
	private void limpiarTipos() {
		List<Tipo> listaTipos = sut.obtenerListaTipos();
		for (Tipo tipo: listaTipos) {
			sut.eliminarTipo(tipo.getId());
		}
	}
}
