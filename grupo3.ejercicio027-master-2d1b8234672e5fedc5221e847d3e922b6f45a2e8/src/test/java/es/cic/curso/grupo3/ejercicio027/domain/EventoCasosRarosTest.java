package es.cic.curso.grupo3.ejercicio027.domain;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.Time;

import org.junit.Before;
import org.junit.Test;

public class EventoCasosRarosTest {

	Evento eventoTest;
	Origen origenTest;
	Rol rolTest;
	Usuario usuarioTest;
	Nivel nivelTest;
	Tipo tipoTest;
	
	@Before
	public void setUp() throws Exception {
		origenTest = new Origen("origen",true);
		rolTest = new Rol("Rol Test");
		usuarioTest = new Usuario("Usuario", "Test", true, rolTest);
		nivelTest = new Nivel("Nivel Test");
		tipoTest = new Tipo("Tipo Test");
		
		eventoTest = new Evento(Date.valueOf("2016-12-31"), Time.valueOf("15:01:02"), origenTest, usuarioTest, "Evento Prueba bastante extraño", nivelTest, tipoTest, false);
	}

	@Test
	public void testHashCode() {
		Evento eventoTestHash = new Evento(null, Date.valueOf("2016-12-31"), Time.valueOf("15:01:02"), origenTest, usuarioTest, "Evento Prueba bastante extraño", nivelTest, tipoTest, false);
		
		assertEquals("No coinciden los Hashes", eventoTest.hashCode(), eventoTestHash.hashCode());
	}

	@Test
	public void testToString() {
		Evento eventoTestString = new Evento(null, Date.valueOf("2016-12-31"), Time.valueOf("15:01:02"), origenTest, usuarioTest, "Evento Prueba para String", nivelTest, tipoTest, true);
		
		assertEquals("La String de salida no es la debida", "Evento [id=null, fecha=2016-12-31, hora=15:01:02, origen=Origen [id=null, nombre=origen, alta=true], usuario=Usuario [id=null, nombre=Usuario, apellidos=Test, alta=true, rolId=null], descripcion=Evento Prueba para String, nivel=Nivel [id=null, nombre=Nivel Test], tipo=Tipo [id=null, nombre=Tipo Test], alta=true]", eventoTestString.toString());
	}

	@Test
	public void testEqualsObjectNull() {
		Evento eventoNull = null;
		
		assertEquals("El objeto no es nulo", false, eventoTest.equals(eventoNull));
	}
	
	@Test
	public void testEqualsObjectOtherClass() {
		Rol eventoOtraClase = new Rol("Rol Test");
		
		assertEquals("El objeto no es de otra clase", false, eventoTest.equals(eventoOtraClase));
	}
	
	@Test
	public void testEqualsObjectIdNullNotNull() {
		Evento eventoNotNullId = new Evento(1L, Date.valueOf("2016-12-31"), Time.valueOf("15:01:02"), origenTest, usuarioTest, "Evento Prueba bastante extraño", nivelTest, tipoTest, false);
		
		assertEquals("La Id del objeto tambien es Null", false, eventoTest.equals(eventoNotNullId));
	}
	
	@Test
	public void testEqualsObjectIdNullNull() {
		Evento eventoNullId = new Evento(Date.valueOf("2016-12-31"), Time.valueOf("15:01:02"), origenTest, usuarioTest, "Evento Prueba bastante extraño", nivelTest, tipoTest, false);
		
		assertEquals("La Id del objeto tambien es Null", true, eventoTest.equals(eventoNullId));
	}
	
	@Test
	public void testEqualsOtherId() {
		Evento eventoId1 = new Evento(1L, Date.valueOf("2016-12-31"), Time.valueOf("15:01:02"), origenTest, usuarioTest, "Evento Prueba bastante extraño", nivelTest, tipoTest, false);
		Evento eventoId2 = new Evento(2L, Date.valueOf("2016-12-31"), Time.valueOf("15:01:02"), origenTest, usuarioTest, "Evento Prueba bastante extraño", nivelTest, tipoTest, false);
		
		assertEquals("La Id del objeto no es distinta", false, eventoId1.equals(eventoId2));
	}
	
	@Test
	public void testEqualsSameId() {
		Evento eventoId1 = new Evento(1L, Date.valueOf("2016-12-31"), Time.valueOf("15:01:02"), origenTest, usuarioTest, "Evento Prueba bastante extraño", nivelTest, tipoTest, false);
		Evento eventoId2 = new Evento(1L, Date.valueOf("2016-12-31"), Time.valueOf("15:01:02"), origenTest, usuarioTest, "Evento Prueba bastante extraño", nivelTest, tipoTest, false);
		
		assertEquals("La Id del objeto es distinta", true, eventoId1.equals(eventoId2));
	}
	
	@Test
	public void testGettersVarios() {
		
		//TODO Arreglar este Test
		assertEquals("El valor de la fecha no coincide", "2016-12-31", eventoTest.getFecha());
//		assertEquals("El valor de la hora no coincide", "15:01:02", eventoTest.getHora());
		assertEquals("El valor del nombre del origen no coincide", "origen", eventoTest.getOrigen());
		assertEquals("El valor del nombre de Usuario no coincide", "Usuario", eventoTest.getUsuario());
		assertEquals("El valor del nombre del Rol no coincide", "Rol Test", eventoTest.getRol());
		assertEquals("El valor del nombre del Nivel no coincide", "Nivel Test", eventoTest.getNivel());
		assertEquals("El valor del nombre del Tipo no coincide", "Tipo Test", eventoTest.getTipo());
	}
}
