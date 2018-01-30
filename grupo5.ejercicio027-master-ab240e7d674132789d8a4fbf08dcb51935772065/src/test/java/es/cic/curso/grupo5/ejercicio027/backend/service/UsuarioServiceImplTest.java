package es.cic.curso.grupo5.ejercicio027.backend.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
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
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Usuario;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo5.ejercicio027/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class UsuarioServiceImplTest {

    @PersistenceContext
	protected EntityManager entityManager;
	
	@Autowired
	private UsuarioService usuarioService;

	private Usuario usuario1;
	private Usuario usuario2;
	private Usuario usuario3;
	
	private Rol rol1;
	private Rol rol2;
	private Rol rol3;
	Operacion operacion1,operacion2,operacion3;
	List<Operacion> listaOperciones=new ArrayList<>();
	
	
	@Before
	public void setUp() throws Exception {
		inicializaBaseDeDatos();
	}

	@Test
	public void testAniadirUuario() {
		usuarioService.aniadirUsuario(usuario1);
		usuarioService.aniadirUsuario(usuario2);
		usuarioService.aniadirUsuario(usuario3);
		
		assertNotNull(usuario1.getId());
		assertNotNull(usuario2.getId());
		assertNotNull(usuario3.getId());
	}

	@Test
	public void testModificarUsuario() {
		
		usuario2.setNombre("Jose");
		usuarioService.modificarUsuario(usuario2);
		assertEquals(usuario2.getNombre(), "Jose");
	}

	@Test
	public void testBorrarUsuario() {
		Usuario usuarioABorrar = new Usuario("juan", "juan", rol1, "juan@hotmail.com",true);
		usuarioService.aniadirUsuario(usuarioABorrar);
		usuarioService.borrarUsuario(usuarioABorrar.getId());
		List<Usuario> listaUsuario = usuarioService.listarUsuario();
		assertEquals(listaUsuario.size(), 3);
	}

	@Test
	public void testListarUsuario() {
		List<Usuario> listaUsuario = usuarioService.listarUsuario();
		for (Usuario u : listaUsuario) {
			assertNotNull(u.getId());
		}

	}
	
	private void inicializaBaseDeDatos() {
		 operacion1 = new Operacion("Copiar");
          
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

	}


}