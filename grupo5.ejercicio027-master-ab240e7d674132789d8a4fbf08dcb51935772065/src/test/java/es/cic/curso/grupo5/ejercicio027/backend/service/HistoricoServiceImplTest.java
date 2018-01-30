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

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Historico;
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Operacion;
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Rol;
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo5.ejercicio027/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class HistoricoServiceImplTest {

    @PersistenceContext
	protected EntityManager entityManager;
	
	@Autowired
	private HistoricoService historicoService;
	 

	private Historico historico1;
	private Historico historico2;
	private Historico historico3;
	

	private Usuario usuario1;
	private Usuario usuario2;
	private Usuario usuario3;
	
	private Rol rol1;
	private Rol rol2;
	private Rol rol3;
 
	
	
	@Before
	public void setUp() throws Exception {
		inicializaBaseDeDatos();
	}

	@Test
	public void testAniadirHistorico() {
		Historico historicoCreado = historicoService.aniadirHistorico(historico2);
		assertNotNull(historicoCreado.getId());
	}

	@Test
	public void testModificarHistorico() {
		historico2.setOperacion("Actualizar");
		historicoService.modificarHistorico(historico2);
		assertEquals(historico2.getOperacion(), "Actualizar");
	}

	@Test
	public void testBorrarHistorico() {
		Historico historicoABorrar = new Historico("abrir","24/02/2017 12:55", usuario1,true);
		historicoService.aniadirHistorico(historicoABorrar);
		historicoService.borrarHistorico(historicoABorrar.getId());
		List<Historico> listaHistorico = historicoService.listarHistorico();
		assertEquals(listaHistorico.size(), 3);
	}

	@Test
	public void testListarHistorico() {
		List<Historico> listaHistorico = historicoService.listarHistorico();
		for (Historico u : listaHistorico) {
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
		
		
		usuario1 = new Usuario("juan", "juan", rol1, "juan@hotmail.com",true);
		usuario2 = new Usuario("pepe", "pepe", rol2, "pepe@hotmail.com",true);
		usuario3 = new Usuario("pedro", "pedro", rol3, "pedro@hotmail.com",true);

		entityManager.persist(usuario1);
		entityManager.persist(usuario2);
		entityManager.persist(usuario3);
		
		historico1 = new Historico("abrir","24/02/2017 12:55", usuario1,true);
		historico2 = new Historico("cerrar","25/02/2017 12:55", usuario2, true);
		historico3 = new Historico("modificar","26/02/2017 12:55", usuario3, true);

		entityManager.persist(historico1);
		entityManager.persist(historico2);
		entityManager.persist(historico3);

	}


}