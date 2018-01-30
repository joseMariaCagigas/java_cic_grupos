package es.cic.curso.grupo1.ejercicio027.service;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import es.cic.curso.grupo1.ejercicio027.dominio.Tarea;
import es.cic.curso.grupo1.ejercicio027.dominio.VariableTexto;
import es.cic.curso.grupo1.ejercicio027.helper.TestHelper;
import es.cic.curso.grupo1.ejercicio027.repository.TareaRepository;
import es.cic.curso.grupo1.ejercicio027.repository.VariableTextoRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo1/ejercicio027/applicationContext.xml"
		})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class VariableTextoServiceTest {

	@Autowired
	private VariableTextoService varSer;
	@Autowired
	private TareaService tarSer;
	@Autowired
	private TestHelper tHelper;
	@Autowired
	private VariableTextoRepository varRepo;
	@Autowired
	private TareaRepository tarRepo;

	@PersistenceContext
	private EntityManager em;

	private long clave;

	private long claveTarea;
	private Tarea tarea;
	
	private VariableTexto varTex;

	@Before
	public void setUp() throws Exception {
		clave = tHelper.generaVariableTexto();
		varTex = varRepo.read(clave);
		
		claveTarea = varTex.getTarea().getId();
		tarea = tarRepo.read(claveTarea);
	}

	@Test
	public void testNuevaVariableTexto() {
		VariableTexto varNueva = varSer.nuevaVarTex(tarea, "Variable 1", "Valor 2 prueba");

		VariableTexto varAniadida = varRepo.read(varNueva.getId());
		
		assertEquals("Variable 1", varAniadida.getNombreVarTex());
		assertEquals(tarea, varAniadida.getTarea());
	}
	
	@Test
	public void testModificarVariableTexto() {
		VariableTexto resultado = varSer.modificarTarea(clave, "Numero variable 2", "1.2");
		
		VariableTexto aniadida = varRepo.read(resultado.getId());

		assertEquals("1.2", aniadida.getValorVarTex());
	}
	
	@Test
	public void testBorrarVariableTexto_IntegracionListar() {
		Collection<VariableTexto> listaVarTex = varSer.getVarTex();
		assertEquals(1, listaVarTex.size());
		
		varSer.borrarVarTex(clave);
		
		listaVarTex = varSer.getVarTex();

		assertEquals(0, listaVarTex.size());
	}
	
	@Test
	public void testBorrarTarea() {
		Collection<VariableTexto> listaVarNums = varSer.getVarTex();
		assertEquals(1, listaVarNums.size());
		
		tarSer.borrarTarea(varTex.getTarea().getId());
		
		listaVarNums = varSer.getVarTex();

		assertEquals(0, listaVarNums.size());
	}
	
	@Test
	public void testBuscarVariableTexto() {
		VariableTexto varNueva = varSer.buscarVarTex(clave);
		
		assertEquals("Texto 1", varNueva.getNombreVarTex());
	}
	
	@Test
	public void testAdd() {
		VariableTexto varNueva = new VariableTexto(tarea, "Nombre 1", "2.2");
		
		varSer.add(varNueva);
		
		Collection<VariableTexto> listaVarNums = varSer.getVarTex();
		assertEquals(2, listaVarNums.size());
	}
	
	@Test
	public void testUpdate() {
		VariableTexto varNueva = varSer.nuevaVarTex(tarea, "Variable 1", "Valor 2 prueba");
		VariableTexto varAniadida = varRepo.read(varNueva.getId());
		
		assertEquals("Variable 1", varAniadida.getNombreVarTex());
		
		varAniadida.setNombreVarTex("nombrenombre");
		VariableTexto varAniadida2 = varSer.update(varAniadida);
		
		assertEquals("nombrenombre", varAniadida2.getNombreVarTex());
	}
}