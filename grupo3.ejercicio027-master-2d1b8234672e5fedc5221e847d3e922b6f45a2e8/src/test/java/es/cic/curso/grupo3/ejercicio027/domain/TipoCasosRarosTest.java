package es.cic.curso.grupo3.ejercicio027.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TipoCasosRarosTest {

	Tipo tipoTest;
	
	@Before
	public void setUp() throws Exception {
		tipoTest = new Tipo("Tipo Test");
	}

	@Test
	public void testHashCode() {
		Tipo tipoTestHash = new Tipo(null, "Tipo Test");
		
		assertEquals("No coinciden los Hashes", tipoTest.hashCode(), tipoTestHash.hashCode());
	}

	@Test
	public void testToString() {
		Tipo tipoTestString = new Tipo(null, "Tipo Test");
		
		assertEquals("La String de salida no es la debida", "Tipo [id=null, nombre=Tipo Test]", tipoTestString.toString());
	}
	
	@Test
	public void testEqualsObjectNull() {
		Tipo tipoNull = null;
		
		assertEquals("El objeto no es nulo", false, tipoTest.equals(tipoNull));
	}

	@Test
	public void testEqualsObjectOtherClass() {
		Origen tipoOtraClase = new Origen("Origen Test",true);
		
		assertEquals("El objeto no es de otra clase", false, tipoTest.equals(tipoOtraClase));
	}

	@Test
	public void testEqualsObjectIdNullNotNull() {
		Tipo tipoNotNullId = new Tipo(1L, "Tipo Test");
		
		assertEquals("La Id del objeto tambien es Null", false, tipoTest.equals(tipoNotNullId));
	}

	@Test
	public void testEqualsObjectIdNullNull() {
		Tipo tipoNullId = new Tipo("Tipo Test");
		
		assertEquals("La Id del objeto tambien es Null", true, tipoTest.equals(tipoNullId));
	}

	@Test
	public void testEqualsOtherId() {
		Tipo tipoId1 = new Tipo(1L, "Tipo Test");
		Tipo tipoId2 = new Tipo(2L, "Tipo Test");
		
		assertEquals("La Id del objeto no es distinta", false, tipoId1.equals(tipoId2));
	}

	@Test
	public void testEqualsSameId() {
		Tipo tipoId1 = new Tipo(1L, "Tipo Test");
		Tipo tipoId2 = new Tipo(1L, "Tipo Test");
		
		assertEquals("La Id del objeto es distinta", true, tipoId1.equals(tipoId2));
	}
}
