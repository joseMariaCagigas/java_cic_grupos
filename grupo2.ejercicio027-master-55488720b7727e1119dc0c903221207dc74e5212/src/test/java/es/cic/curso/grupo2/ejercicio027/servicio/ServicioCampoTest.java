package es.cic.curso.grupo2.ejercicio027.servicio;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

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

import es.cic.curso.grupo2.ejercicio027.modelo.Campo;
import es.cic.curso.grupo2.ejercicio027.modelo.Plantilla;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo2/ejercicio027/applicationContext.xml"
				})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class ServicioCampoTest {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ServicioCampo servicioCampo;

	Plantilla plantilla;
	Campo campo;
	
	@Before
	public void setUp() throws Exception {
		plantilla = new Plantilla("Plantilla prueba");
		
		em.persist(plantilla);
		
		campo = new Campo("Apellido", "Texto", "Fernandez", plantilla);
		
		em.persist(campo);
		
		campo = new Campo("Edad", "Numero", 35, plantilla);
		
		em.persist(campo);
	}

	@Test
	public void testAnadirCampo(){
		Campo campo2 = generarCampo();
		
		assertNull(campo2.getId());
		
		servicioCampo.anadirCampo(campo2.getPlantilla().getId(), campo2);

		assertNotNull(campo2.getId());
	}
	
	@Test
	public void tesListarCampos(){
		Campo campo2 = generarCampo();
		
		servicioCampo.anadirCampo(campo2.getPlantilla().getId(), campo2);
		
		List<Campo> listaCampos = servicioCampo.listarCampos();
		
		assertTrue(listaCampos.size() > 2);
	}
	
	@Test
	public void testBorrarCampo(){
		Campo campo2 = generarCampo();
		
		servicioCampo.anadirCampo(campo2.getPlantilla().getId(), campo2);
		
		Long clave = campo2.getId();
		
		servicioCampo.borrarCampo(clave);
		
		//assertNull(campo2.getId());
		
		try{
			Campo c = em.find(Campo.class, clave);
		}catch (PersistenceException pe){
			return;
		}
	}
	
	@Test
	public void testActualizarCampo(){
		Campo campo2 = generarCampo();
		
		servicioCampo.anadirCampo(campo2.getPlantilla().getId(), campo2);
		
		Long claveInicial = campo2.getId();
		
		assertEquals(campo2.getTexto(), "Fernandez");
		
		campo2.setTexto("Rodriguez");
		servicioCampo.actualizarCampo(campo2);
		em.flush();
		
		assertEquals(campo2.getTexto(), "Rodriguez");
		
		Long clave = campo2.getId();
		
		Campo campoEnBBDD = em.find(Campo.class, clave);
		
		assertEquals(clave, claveInicial);
		
		assertEquals(campoEnBBDD.getTexto(), "Rodriguez");
		assertNotEquals(campoEnBBDD.getTexto(), "Fernandez");
	}
	
	@Test
	public void testListaCamposPlantilla(){
		campo = generarCampo();
		
		Long idPlantilla = campo.getPlantilla().getId();
		
		servicioCampo.anadirCampo(idPlantilla, campo);
		
		Campo campo3 = new Campo("Apellido", "Texto", "Fernandez", plantilla);
		
		servicioCampo.anadirCampo(idPlantilla, campo3);
		
		campo3 = new Campo("Apellido", "Texto", "Fernandez", plantilla);
		
		servicioCampo.anadirCampo(idPlantilla, campo3);
		
		List <Campo> listaCampos = servicioCampo.listaCamposPlantilla(idPlantilla);
		
		assertTrue(listaCampos.size() == 3);
	}
	
	@Test
	public void testEliminaCamposPlantilla(){
		campo = generarCampo();
		
		Long idPlantilla = campo.getPlantilla().getId();
		
		servicioCampo.anadirCampo(idPlantilla, campo);
		
		Campo campo3 = new Campo("Apellido", "Texto", "Fernandez", plantilla);
		
		servicioCampo.anadirCampo(idPlantilla, campo3);
		
		campo3 = new Campo("Apellido", "Texto", "Fernandez", plantilla);
		
		servicioCampo.anadirCampo(idPlantilla, campo3);
		
		List <Campo> listaCampos = servicioCampo.listaCamposPlantilla(idPlantilla);
		
		assertTrue(listaCampos.size() == 3);
		
		servicioCampo.eliminaCamposPlantilla(idPlantilla);
		
		listaCampos = servicioCampo.listaCamposPlantilla(idPlantilla);
		
		assertTrue(listaCampos.size() == 0);
		
	}
	
	private Campo generarCampo(){
		plantilla = generarPlantilla();
		
		campo = new Campo("Apellido", "Texto", "Fernandez", plantilla);
		
		return campo;
	}
	
	private Plantilla generarPlantilla(){
		plantilla = new Plantilla("Plantilla prueba");
		
		em.persist(plantilla);
		
		return plantilla;
	}

}
