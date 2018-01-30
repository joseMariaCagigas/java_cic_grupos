package es.cic.curso.grupo5.ejercicio027.frontend.utilities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ValidadorTest {
	
	Validador sut;

	@Before
	public void setUp() throws Exception {
		sut = new Validador();
	}

	@Test
	public void validarEmailTest() {
		
		String email= "maquina1995@gmail.com";
		assertTrue(sut.validarEmail(email));
		
		email = "@gmail.com";		
		assertFalse(sut.validarEmail(email));
		
		email= "1995@.com";
		assertFalse(sut.validarEmail(email));
		
		email="maquina1995@gmail.es";
		assertTrue(sut.validarEmail(email));
		
		email="maquina@.com";
		assertFalse(sut.validarEmail(email));
	}
	
	@Test
	public void validarLongitudTexto() {
		String longitudTexto= "55555555555555555555555555555555555555555555555555";
		assertTrue(sut.validarLongitudTexto(longitudTexto));
		
		longitudTexto= "555555555555555555555555555555555555555555555555554";
		assertFalse(sut.validarLongitudTexto(longitudTexto));
	}
	
	@Test
	public void validarNombreApellidos() {
		
		String nombreApellidos="Christian Munoz Ason";
		assertTrue(sut.validarNombreApellidos(nombreApellidos));
		
		nombreApellidos="Christian Muñoz Ason 54";
		assertFalse(sut.validarNombreApellidos(nombreApellidos));
		
		nombreApellidos="Christian Muñoz Ason aaaaptoyuqqqqwwwwaapoiuypoiuyy";
		assertFalse(sut.validarNombreApellidos(nombreApellidos));
	}
}
