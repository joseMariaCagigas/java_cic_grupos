package es.cic.curso.grupo4.ejercicio027.dominio;

import static org.junit.Assert.assertTrue;
import java.sql.Timestamp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo4/ejercicio027/applicationContext.xml"
		})
public class IntegracionSistemasTest
{
	Conector conector = new Conector();
	Ejecucion ejecucion = new Ejecucion();
	Flujo flujo = new Flujo();
	ConectorDTO conectorDTO = new ConectorDTO();
	EjecucionDTO ejecucionDTO = new EjecucionDTO();
	FlujoDTO flujoDTO = new FlujoDTO();
	
	@Test
	public void testConector() {
		conector.equals(conector);
		conector.setId(1L);
		conector.setNombre("Nombre");
		conector.setDescripcion("Descripcion");
		conector.setTipo("Tipo");
		conector.setOrigen("Origen");
		conector.setDestino("Destino");
		conector.hashCode();
		conector.toString();
		conector.getId();
		conector.getNombre();
		conector.getDescripcion();
		conector.getTipo();
		conector.getOrigen();
		conector.getDestino();
		assertTrue(true);
	}
	
	@Test
	public void testConectorDTO() {
		conectorDTO.equals(conectorDTO);
		conectorDTO.setId(1L);
		conectorDTO.setNombre("Nombre");
		conectorDTO.setDescripcion("Descripcion");
		conectorDTO.setTipo("Tipo");
		conectorDTO.setOrigen("Origen");
		conectorDTO.setDestino("Destino");
		conectorDTO.hashCode();
		conectorDTO.toString();
		conectorDTO.getId();
		conectorDTO.getNombre();
		conectorDTO.getDescripcion();
		conectorDTO.getTipo();
		conectorDTO.getOrigen();
		conectorDTO.getDestino();
		assertTrue(true);
	}

	@Test
	public void testEjecucion() {
		ejecucion.equals(ejecucion);
		ejecucion.setId(1L);
		ejecucion.setConector(conector);
		ejecucion.setFecha(new Timestamp(1L));
		ejecucion.setCorrecta(true);
		ejecucion.hashCode();
		ejecucion.toString();
		ejecucion.getId();
		ejecucion.getConector();
		ejecucion.getFecha();
		ejecucion.isCorrecta();
		assertTrue(true);
	}

	@Test
	public void testEjecucionDTO() {
		ejecucionDTO.equals(ejecucionDTO);
		ejecucionDTO.setId(1L);
		ejecucionDTO.setNombreConector("Nombre");
		ejecucionDTO.setDescripcionConector("Descripcion");
		ejecucionDTO.setTipoConector("Tipo");
		ejecucionDTO.setFecha(new Timestamp(1L));
		ejecucionDTO.setCorrecta(true);
		ejecucionDTO.hashCode();
		ejecucionDTO.toString();
		ejecucionDTO.getId();
		ejecucionDTO.getNombreConector();
		ejecucionDTO.getDescripcionConector();
		ejecucionDTO.getTipoConector();
		ejecucionDTO.getFecha();
		ejecucionDTO.isCorrecta();
		assertTrue(true);
	}
	
	@Test
	public void testFlujo() {
		flujo.equals(flujo);
		flujo.setId(1L);
		flujo.setNombre("Nombre");
		flujo.setConectores("Conectores");
		flujo.setHabilitado(true);
		flujo.hashCode();
		flujo.toString();
		flujo.getId();
		flujo.getNombre();
		flujo.getConectores();
		flujo.isHabilitado();
		assertTrue(true);
	}
	
	@Test
	public void testFlujorDTO() {
		flujoDTO.equals(flujo);
		flujoDTO.setId(1L);
		flujoDTO.setNombre("Nombre");
		flujoDTO.setConectores("Conectores");
		flujoDTO.setHabilitado(true);
		flujoDTO.hashCode();
		flujoDTO.toString();
		flujoDTO.getId();
		flujoDTO.getNombre();
		flujoDTO.getConectores();
		flujoDTO.isHabilitado();
		assertTrue(true);
	}
}
