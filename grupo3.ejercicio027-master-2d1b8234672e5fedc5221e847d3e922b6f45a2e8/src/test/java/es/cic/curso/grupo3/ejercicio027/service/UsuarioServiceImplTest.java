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

import es.cic.curso.grupo3.ejercicio027.domain.Rol;
import es.cic.curso.grupo3.ejercicio027.domain.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo3/ejercicio027/applicationContext.xml"
		})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class UsuarioServiceImplTest {

	@Autowired
	UsuarioService sut;
	
	@Autowired
	RolService rolSerivce;
		
		private Long rolId;
		private Rol rol;
		
		@Before
		public void setUp() throws Exception {
			limpiarRoles();
			limpiarUsuarios();
	
			rolId = rolSerivce.aniadirRol("Administrador");	
			rol = rolSerivce.obtenerRol(rolId);
		}

	@Test
	public void testAniadirUsuario() {
		Long idUsuario = sut.aniadirUsuario(rol.getId(), "Pablo", "Vega Pérez", true);
		
		assertNotNull(idUsuario);	
	}

	@Test
	public void testObtenerUsuario() {
		Long idUsuario = sut.aniadirUsuario(rol.getId(), "Pablo", "Vega Pérez", true);
		Usuario Usuario = sut.obtenerUsuario(idUsuario);
		
		assertNotNull(idUsuario);
		assertTrue(Usuario.getId() == idUsuario);
	}

	@Test
	public void testObtenerUsuarios() {
		List<Usuario>usuarios = sut.obtenerUsuarios();
		
		for(Usuario usuario : usuarios){
			assertNotNull(usuario.getId());
		}
	}

	@Test
	public void testActualizarUsuario() {
		Long idUsuario = sut.aniadirUsuario(rol.getId(), "Pablo", "Vega Pérez", true);
		Usuario usuario = sut.obtenerUsuario(idUsuario);
		usuario.setNombre("Luis");
		sut.actualizarUsuario(usuario);
		
		Usuario usuarioMod = sut.obtenerUsuario(idUsuario);
		
		assertTrue(usuarioMod.getNombre().equals("Luis"));
		assertTrue("No es el mismo objeto", usuarioMod.equals(usuario));
		assertEquals("No coinciden los Hashes", usuario.hashCode(), usuarioMod.hashCode());
		assertTrue("No son el mismo objeto", usuario.equals(usuarioMod) == true);
	}

	@Test
	public void testBorrarUsuario() {
		Long idUsuario = sut.aniadirUsuario(rol.getId(), "Pablo", "Vega Pérez", true);
		
		sut.borrarUsuario(idUsuario);
		
		List<Usuario>usuarios = sut.obtenerUsuarios();
		
		assertTrue(usuarios.size() == 0);
	}
	
	@Test
	public void testObtenerUsuarioPorNombre() {
		sut.aniadirUsuario(rol.getId(), "Luis", "Barquín", true);
		Long idUsuario = sut.aniadirUsuario(rol.getId(), "Pablo", "Vega Pérez", true);
		Usuario usuario = sut.obtenerUsuario(idUsuario);
		
		Usuario usuarioPorNombre = sut.obtenerUsuarioPorNombre("Pablo");
		
		assertEquals("No es el mismo objeto", usuarioPorNombre, usuario);
	}
	
	@Test
	public void testListarActivos() {
		sut.aniadirUsuario(rol.getId(), "Luis", "Barquín", true);
		
		assertTrue("La lista de Usuarios activos no tiene miembros", sut.listaUsuariosActivos().size() >= 1);
	}
	
	private void limpiarRoles(){
		List<Rol>roles = rolSerivce.obtenerRoles();
		for(Rol rol : roles){
			rolSerivce.borrarRol(rol.getId());
		}
	}

	private void limpiarUsuarios(){
		List<Usuario>usuarios = sut.obtenerUsuarios();
		for(Usuario usuario : usuarios){
			sut.borrarUsuario(usuario.getId());
		}
	}
}
