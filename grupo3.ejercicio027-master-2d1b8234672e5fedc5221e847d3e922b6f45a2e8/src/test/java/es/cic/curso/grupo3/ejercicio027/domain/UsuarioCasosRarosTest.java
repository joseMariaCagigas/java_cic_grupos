package es.cic.curso.grupo3.ejercicio027.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UsuarioCasosRarosTest {

	Rol rolTest;
	Usuario usuarioTest;
	
	@Before
	public void setUp() throws Exception {
		rolTest = new Rol("Rol Test");
		usuarioTest = new Usuario("Pablo", "Vega Perez", true, rolTest);
	}

	@Test
	public void testHashCode() {
		Usuario usuarioTestHash = new Usuario(null, "Pablo", "Vega Perez", true, rolTest);
		
		assertEquals("No coinciden los Hashes", usuarioTest.hashCode(), usuarioTestHash.hashCode());
	}

	@Test
	public void testToString() {
		Usuario usuarioTestString = new Usuario("Pablo", "Vega Perez", true, rolTest);
		
		assertEquals("La String de salida no es la debida", "Usuario [id=null, nombre=Pablo, apellidos=Vega Perez, alta=true, rolId=null]", usuarioTestString.toString());
	}
	
	@Test
	public void testEqualsObjectNull() {
		Usuario usuarioNull = null;
		
		assertEquals("El objeto no es nulo", false, usuarioTest.equals(usuarioNull));
	}

	@Test
	public void testEqualsObjectOtherClass() {
		Origen usuarioOtraClase = new Origen("Origen Test",true);
		
		assertEquals("El objeto no es de otra clase", false, usuarioTest.equals(usuarioOtraClase));
	}

	@Test
	public void testEqualsObjectIdNullNotNull() {
		Usuario usuarioNotNullId = new Usuario(1L, "Pablo", "Vega Perez", true, rolTest);
		
		assertEquals("La Id del objeto tambien es Null", false, usuarioTest.equals(usuarioNotNullId));
	}

	@Test
	public void testEqualsObjectIdNullNull() {
		Usuario usuarioNullId = new Usuario("Pablo", "Vega Perez", true, rolTest);
		
		assertEquals("La Id del objeto tambien es Null", true, usuarioTest.equals(usuarioNullId));
	}

	@Test
	public void testEqualsOtherId() {
		Usuario usuarioId1 = new Usuario(1L, "Pablo", "Vega Perez", true, rolTest);
		Usuario usuarioId2 = new Usuario(2L, "Pablo", "Vega Perez", true, rolTest);
		
		assertEquals("La Id del objeto no es distinta", false, usuarioId1.equals(usuarioId2));
	}

	@Test
	public void testEqualsSameId() {
		Usuario usuarioId1 = new Usuario(1L, "Pablo", "Vega Perez", true, rolTest);
		Usuario usuarioId2 = new Usuario(1L, "Pablo", "Vega Perez", true, rolTest);
		
		assertEquals("La Id del objeto es distinta", true, usuarioId1.equals(usuarioId2));
	}
	
	@Test
	public void testGettersVariados() {
		Usuario usuarioId1 = new Usuario(1L, "Pablo", "Vega Perez", true);
		usuarioId1.setRol(rolTest);
		
		assertTrue("El estado del alta no se lee correctamente", usuarioId1.isAlta() == true);
		assertEquals("El nombre del Rol no se lee correctamente", "Rol Test", usuarioId1.getRol());
	}
}
