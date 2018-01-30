package es.cic.curso.grupo2.ejercicio027.servicio;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo2.ejercicio027.modelo.Plantilla;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo2/ejercicio027/applicationContext.xml"
				})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class ServicioPlantillaTest {

	@Autowired
	private ServicioPlantilla sut;
	
	@PersistenceContext
	private EntityManager em;
	
	private Plantilla generaPlantilla(String nombre){
		Plantilla plantilla = new Plantilla();
		plantilla.setNombrePlantilla(nombre);
		em.persist(plantilla);
		em.flush();
		return plantilla;
	}
	
	@Test
	public void aniadirPlantillaTest(){
		Plantilla p = new Plantilla("prueba");
		
		assertNull(p.getId());
		
		sut.aniadirPlantilla(p);
		assertNotNull(p.getId());
	}
	
	@Test
	public void listaPlantillaTest(){
		for (int i=0; i<5; i++){
			generaPlantilla("prueba");
		}
		
		List<Plantilla> plantillas =sut.listaPlantillas();
		assertTrue(plantillas.size()==5);
	}
	
	@Test
	public void obtenPlantillaTest(){
		Plantilla p = generaPlantilla("prueba");
		Plantilla p2=sut.obtenPlantilla(p.getId());
		
		assertEquals(p.getNombrePlantilla(),p2.getNombrePlantilla());
		
		try{
			Plantilla p3 = sut.obtenPlantilla(Long.MAX_VALUE);
			assertNull(p3);
		} catch (PersistenceException pe){
			
		}
	}
	
	
	@Test
	public void actualizarPlantillaTest(){
		Plantilla p = generaPlantilla("prueba");
		
		Plantilla mod = sut.obtenPlantilla(p.getId());
		
		mod.setNombrePlantilla("redo");
		sut.actualizaPlantilla(mod);
		
		assertTrue(p.getNombrePlantilla()==mod.getNombrePlantilla());
			
		
	}
}
