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
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Rol;
 

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo5.ejercicio027/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class RolServiceImplTest {

    @PersistenceContext
	protected EntityManager entityManager;
	
	@Autowired
	private RolService rolService;
	
	private Rol rol1;
	private Rol rol2;
	private Rol rol3;
	Operacion operacion1,operacion2,operacion3;
	List<Operacion> listaOperciones;


	@Before
	public void setUp() throws Exception {
		inicializaBaseDeDatos();
	}

	@Test
	public void testAniadirRol() {
		Rol historicoCreado = rolService.aniadirRol(rol2);
		assertNotNull(historicoCreado.getId());
	}

	@Test
	public void testModificarRol() {
		rol2.setipoRol("Copiar");
		rolService.modificarRol(rol2);
		assertEquals(rol2.getTipoRol(), "Copiar");
	}

	@Test
	public void testBorrarHistorico() {
		Rol rolABorrar = new Rol("Eliminar");
		rolService.aniadirRol(rolABorrar);
		rolService.borrarRol(rolABorrar.getId());
		List<Rol> listaRol = rolService.listarRol();
		assertEquals(listaRol.size(), 3);
	}

	@Test
	public void testListarRol() {
		List<Rol> listaRol = rolService.listarRol();
		for (Rol u : listaRol) {
			assertNotNull(u.getId());
		}

	}
	
	private void inicializaBaseDeDatos() {
		Operacion operacion1 = new Operacion("Copiar");
 
		entityManager.persist(operacion1);
	 
 
		
		rol1 = new Rol("administrador");
		rol2 = new Rol("invitado");
		rol3 = new Rol("invitado");
		entityManager.persist(rol1);
		entityManager.persist(rol2);
		entityManager.persist(rol3);
	}


}