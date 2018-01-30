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
import es.cic.curso.grupo1.ejercicio027.dominio.VariableNumero;
import es.cic.curso.grupo1.ejercicio027.helper.TestHelper;
import es.cic.curso.grupo1.ejercicio027.repository.TareaRepository;
import es.cic.curso.grupo1.ejercicio027.repository.VariableNumeroRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo1/ejercicio027/applicationContext.xml"
		})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class VariableNumeroServiceTest {

	@Autowired
	private VariableNumeroService varSer;
	@Autowired
	private TareaService tarSer;
	@Autowired
	private TestHelper tHelper;
	@Autowired
	private VariableNumeroRepository varRepo;
	@Autowired
	private TareaRepository tarRepo;

	@PersistenceContext
	private EntityManager em;

	private long clave;

	private long claveTarea;
	private Tarea tarea;
	
	private VariableNumero varNum;

	@Before
	public void setUp() throws Exception {
		clave = tHelper.generaVariableNumero();
		varNum = varRepo.read(clave);
		
		claveTarea = varNum.getTarea().getId();
		tarea = tarRepo.read(claveTarea);
	}

	@Test
	public void testNuevaVariableNumero() {
		VariableNumero varNueva = varSer.nuevaVarNum(tarea, "Variable 1", 0.1);

		VariableNumero varAniadida = varRepo.read(varNueva.getId());
		
		assertEquals("Variable 1", varAniadida.getNombreVarNum());
		assertEquals(tarea, varAniadida.getTarea());
	}
	
	@Test
	public void testModificarVariableNumero() {
		VariableNumero resultado = varSer.modificarTarea(clave, "Numero variable 2", 1.2);
		
		VariableNumero aniadida = varRepo.read(resultado.getId());

		assertEquals(1.2, aniadida.getValorVarNum(), 0.001);
	}
	
	@Test
	public void testBorrarVariableNumero_IntegracionListar() {
		Collection<VariableNumero> listaVarNums = varSer.getVarNums();
		assertEquals(1, listaVarNums.size());
		
		varSer.borrarVarNum(clave);
		
		listaVarNums = varSer.getVarNums();

		assertEquals(0, listaVarNums.size());
	}
	
	@Test
	public void testBorrarTarea() {
		Collection<VariableNumero> listaVarNums = varSer.getVarNums();
		assertEquals(1, listaVarNums.size());
		
		tarSer.borrarTarea(varNum.getTarea().getId());
		
		listaVarNums = varSer.getVarNums();

		assertEquals(0, listaVarNums.size());
	}
	
	@Test
	public void testBuscarVariableNumero() {
		VariableNumero varNueva = varSer.buscarVarNum(clave);
		
		assertEquals("Numero 1", varNueva.getNombreVarNum());
	}
	
	@Test
	public void testAdd() {
		VariableNumero varNueva = new VariableNumero(tarea, "Nombre 1", 2.2);
		
		varSer.add(varNueva);
		
		Collection<VariableNumero> listaVarNums = varSer.getVarNums();
		assertEquals(2, listaVarNums.size());
	}
	
	@Test
	public void testUpdate() {
		VariableNumero varNueva = varSer.nuevaVarNum(tarea, "Variable 1", 2.3);
		VariableNumero varAniadida = varRepo.read(varNueva.getId());
		
		assertEquals("Variable 1", varAniadida.getNombreVarNum());
		
		varAniadida.setNombreVarNum("nombrenombre");
		VariableNumero varAniadida2 = varSer.update(varAniadida);
		
		assertEquals("nombrenombre", varAniadida2.getNombreVarNum());
	}
}