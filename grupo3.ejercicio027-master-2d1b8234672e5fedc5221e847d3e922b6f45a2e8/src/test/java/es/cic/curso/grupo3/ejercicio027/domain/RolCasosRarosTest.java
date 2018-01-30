package es.cic.curso.grupo3.ejercicio027.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class RolCasosRarosTest {

	Rol rolTest;
	
	@Before
	public void setUp() throws Exception {
		rolTest = new Rol("Rol Test");
	}

	@Test
	public void testHashCode() {
		Rol rolTestHash = new Rol(null, "Rol Test");
		
		assertEquals("No coinciden los Hashes", rolTest.hashCode(), rolTestHash.hashCode());
	}

	@Test
	public void testToString() {
		Rol rolTestString = new Rol(null, "Rol Test");
		
		assertEquals("La String de salida no es la debida", "Rol [id=null, nombre=Rol Test]", rolTestString.toString());
	}
	
	@Test
	public void testEqualsObjectNull() {
		Rol rolNull = null;
		
		assertEquals("El objeto no es nulo", false, rolTest.equals(rolNull));
	}

	@Test
	public void testEqualsObjectOtherClass() {
		Tipo rolOtraClase = new Tipo("Tipo Test");
		
		assertEquals("El objeto no es de otra clase", false, rolTest.equals(rolOtraClase));
	}

	@Test
	public void testEqualsObjectIdNullNotNull() {
		Rol rolNotNullId = new Rol(1L, "Rol Test");
		
		assertEquals("La Id del objeto tambien es Null", false, rolTest.equals(rolNotNullId));
	}

	@Test
	public void testEqualsObjectIdNullNull() {
		Rol rolNullId = new Rol("Rol Test");
		
		assertEquals("La Id del objeto tambien es Null", true, rolTest.equals(rolNullId));
	}

	@Test
	public void testEqualsOtherId() {
		Rol rolId1 = new Rol(1L, "Rol Test");
		Rol rolId2 = new Rol(2L, "Rol Test");
		
		assertEquals("La Id del objeto no es distinta", false, rolId1.equals(rolId2));
	}

	@Test
	public void testEqualsSameId() {
		Rol rolId1 = new Rol(1L, "Rol Test");
		Rol rolId2 = new Rol(1L, "Rol Test");
		
		assertEquals("La Id del objeto es distinta", true, rolId1.equals(rolId2));
	}
}
