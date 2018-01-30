package es.cic.curso.grupo3.ejercicio027.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NivelCasosRarosTest {

	Nivel nivelTest;
	
	@Before
	public void setUp() throws Exception {
		nivelTest = new Nivel("Nivel Test");
	}

	@Test
	public void testHashCode() {
		Nivel nivelTestHash = new Nivel(null, "Nivel Test");
		
		assertEquals("No coinciden los Hashes", nivelTest.hashCode(), nivelTestHash.hashCode());
	}

	@Test
	public void testToString() {
		Nivel nivelTestString = new Nivel(null, "Nivel Test");
		
		assertEquals("La String de salida no es la debida", "Nivel [id=null, nombre=Nivel Test]", nivelTestString.toString());
	}
	
	@Test
	public void testEqualsObjectNull() {
		Nivel nivelNull = null;
		
		assertEquals("El objeto no es nulo", false, nivelTest.equals(nivelNull));
	}

	@Test
	public void testEqualsObjectOtherClass() {
		Tipo nivelOtraClase = new Tipo("Tipo Test");
		
		assertEquals("El objeto no es de otra clase", false, nivelTest.equals(nivelOtraClase));
	}

	@Test
	public void testEqualsObjectIdNullNotNull() {
		Nivel nivelNotNullId = new Nivel(1L, "Nivel Test");
		
		assertEquals("La Id del objeto tambien es Null", false, nivelTest.equals(nivelNotNullId));
	}

	@Test
	public void testEqualsObjectIdNullNull() {
		Nivel nivelNullId = new Nivel("Nivel Test");
		
		assertEquals("La Id del objeto tambien es Null", true, nivelTest.equals(nivelNullId));
	}

	@Test
	public void testEqualsOtherId() {
		Nivel nivelId1 = new Nivel(1L, "Nivel Test");
		Nivel nivelId2 = new Nivel(2L, "Nivel Test");
		
		assertEquals("La Id del objeto no es distinta", false, nivelId1.equals(nivelId2));
	}

	@Test
	public void testEqualsSameId() {
		Nivel nivelId1 = new Nivel(1L, "Nivel Test");
		Nivel nivelId2 = new Nivel(1L, "Nivel Test");
		
		assertEquals("La Id del objeto es distinta", true, nivelId1.equals(nivelId2));
	}
}
