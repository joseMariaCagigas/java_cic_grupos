package es.cic.curso.grupo5.ejercicio027.backend.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

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

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Operacion;

 

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo5.ejercicio027/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class OperacionServiceImplTest {

	@PersistenceContext
	protected EntityManager entityManager;
	
	@Autowired
	private OperacionService operacionService;

	private Operacion operacion1;
	private Operacion operacion2;
	private Operacion operacion3;
	
	
	@Before
	public void setUp() throws Exception {
		inicializaBaseDeDatos();
	}

	@Test
	public void testAniadirUuario() {
		operacionService.aniadirOperacion(operacion1);
		operacionService.aniadirOperacion(operacion2);
		operacionService.aniadirOperacion(operacion3);
		
		assertNotNull(operacion1.getId());
		assertNotNull(operacion2.getId());
		assertNotNull(operacion3.getId());
	}

	@Test
	public void testModificarUsuario() {
		
		operacion2.setDescripcion("operacion");
		operacionService.modificarOperacion(operacion2);
		assertEquals(operacion2.getDescripcion(), "operacion");
	}

	@Test
	public void testBorrarUsuario() {
		Operacion operacionABorrar = new Operacion("operacion");
		operacionService.aniadirOperacion(operacionABorrar);
		operacionService.borrarOperacion(operacionABorrar.getId());
		List<Operacion> listaUsuario = operacionService.listarOperacion();
		assertEquals(listaUsuario.size(), 3);
	}

	@Test
	public void testListarUsuario() {
		List<Operacion> listaOp = operacionService.listarOperacion();
		for (Operacion u : listaOp) {
			assertNotNull(u.getId());
		}

	}
	
	private void inicializaBaseDeDatos() {
		operacion1 = new Operacion("operacion1");
		operacion2 = new Operacion("operacion2");
		operacion3 = new Operacion("operacion3");

		entityManager.persist(operacion1);
		entityManager.persist(operacion2);
		entityManager.persist(operacion3);

	}


}
