package es.cic.curso.grupo3.ejercicio027.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OrigenCasosRarosTest {

	Origen origenTest;
	
	@Before
	public void setUp() throws Exception {
		origenTest = new Origen("Origen Test",true);
	}

	@Test
	public void testHashCode() {
		Origen origenTestHash = new Origen(null, "Origen Test",true);
		
		assertEquals("No coinciden los Hashes", origenTest.hashCode(), origenTestHash.hashCode());
	}

	@Test
	public void testToString() {
		Origen origenTestString = new Origen(null, "Origen Test",true);
		
		assertEquals("La String de salida no es la debida", "Origen [id=null, nombre=Origen Test, alta=true]", origenTestString.toString());
	}
	
	@Test
	public void testEqualsObjectNull() {
		Origen origenNull = null;
		
		assertEquals("El objeto no es nulo", false, origenTest.equals(origenNull));
	}

	@Test
	public void testEqualsObjectOtherClass() {
		Tipo origenOtraClase = new Tipo("Tipo Test");
		
		assertEquals("El objeto no es de otra clase", false, origenTest.equals(origenOtraClase));
	}

	@Test
	public void testEqualsObjectIdNullNotNull() {
		Origen origenNotNullId = new Origen(1L, "Origen Test",true);
		
		assertEquals("La Id del objeto tambien es Null", false, origenTest.equals(origenNotNullId));
	}

	@Test
	public void testEqualsObjectIdNullNull() {
		Origen origenNullId = new Origen("Origen Test",true);
		
		assertEquals("La Id del objeto tambien es Null", true, origenTest.equals(origenNullId));
	}

	@Test
	public void testEqualsOtherId() {
		Origen origenId1 = new Origen(1L, "Origen Test",true);
		Origen origenId2 = new Origen(2L, "Origen Test",true);
		
		assertEquals("La Id del objeto no es distinta", false, origenId1.equals(origenId2));
	}

	@Test
	public void testEqualsSameId() {
		Origen origenId1 = new Origen(1L, "Origen Test",true);
		Origen origenId2 = new Origen(1L, "Origen Test",true);
		
		assertEquals("La Id del objeto es distinta", true, origenId1.equals(origenId2));
	}
}
