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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo3/ejercicio027/applicationContext.xml"
		})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class RolServiceImplTest {

	@Autowired
	RolService sut;
	
	@Before
	public void setUp() throws Exception {
		limpiarRoles();
	}

	@Test
	public void testAniadirRol() {
		Long idRol = sut.aniadirRol("Master");
		
		assertNotNull(idRol);
	}

	@Test
	public void testObtenerRol() {
		Long idRol = sut.aniadirRol("Master");
		
		Rol rol = sut.obtenerRol(idRol);
		
		assertNotNull(rol.getId());
		assertTrue(rol.getNombre().equals("Master"));
	}

	@Test
	public void testObtenerRols() {
		List<Rol>roles = sut.obtenerRoles();
		for(Rol rol : roles){
			assertNotNull(rol.getId());
		}
	}

	@Test
	public void testActualizarRol() {
		Long idRol = sut.aniadirRol("Master");
		Rol rol = sut.obtenerRol(idRol);
		rol.setNombre("Administrador");
		sut.actualizarRol(rol);
		
		Rol rolMod = sut.obtenerRol(idRol);
		
		assertTrue(rolMod.getNombre().equals("Administrador"));
		assertTrue("No es el mismo objeto", rolMod.equals(rol));
		assertEquals("No coinciden los Hashes", rol.hashCode(), rolMod.hashCode());
		assertTrue("No son el mismo objeto", rol.equals(rolMod) == true);
	}

	@Test
	public void testBorrarRol() {
		Long idRol = sut.aniadirRol("Master");
		
		sut.borrarRol(idRol);
		
		List<Rol>roles = sut.obtenerRoles();
		
		assertTrue(roles.size() == 0);
	}

	@Test
	public void testObtenerRolPorNombre() {
		sut.aniadirRol("Rol Test Nombre");
		Long idRol = sut.aniadirRol("Rol Test Nombre 2");
		Rol rol = sut.obtenerRol(idRol);
		
		Rol rolPorNombre = sut.obtenerRolPorNombre("Rol Test Nombre 2");
		
		assertEquals("No es el mismo objeto", rolPorNombre, rol);
	}
	
	private void limpiarRoles(){
		List<Rol>roles = sut.obtenerRoles();
		for(Rol rol : roles){
			sut.borrarRol(rol.getId());
		}
	}
}
