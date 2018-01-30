package es.cic.curso.grupo4.ejercicio027.servicio;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.cic.curso.grupo4.ejercicio027.dominio.Flujo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo4/ejercicio027/applicationContext.xml"
		})
public class FlujoServicioTest {

	Flujo flujo;
	
	@Autowired
	private FlujoServicio flujoServicio;
	
	@Before
	public void setUp() {
		flujo = new Flujo();
		flujo.setNombre("FLujo1");
		flujo.setConectores("0;1;");
		flujo.setHabilitado(true);
	}
	
	@Test
	public void agregarFlujoTest(){
		int valor = flujoServicio.listaFlujos().size();
		valor += 1;
		flujoServicio.agregaFlujo(flujo);
		assertEquals(valor, flujoServicio.listaFlujos().size());
	}
	@Test
	public void borraFlujoTest(){
		int valor = flujoServicio.listaFlujos().size();
		
		flujoServicio.agregaFlujo(flujo);
		flujoServicio.eliminaFlujo(flujo.getId());
		assertEquals(valor, flujoServicio.listaFlujos().size());
	}
	
	@Test
	public void ModificarFlujoTest(){
		
		flujoServicio.agregaFlujo(flujo);
		flujo.setNombre("flujo2");
		flujoServicio.modificaFlujo(flujo);
		assertEquals(flujoServicio.obtenFlujo(flujo.getId()).getNombre(), flujo.getNombre());
	}

}
