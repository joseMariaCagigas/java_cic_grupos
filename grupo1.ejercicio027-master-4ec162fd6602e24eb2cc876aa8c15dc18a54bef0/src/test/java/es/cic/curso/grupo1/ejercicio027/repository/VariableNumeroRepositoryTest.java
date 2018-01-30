package es.cic.curso.grupo1.ejercicio027.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

import es.cic.curso.grupo1.ejercicio027.dominio.VariableNumero;
import es.cic.curso.grupo1.ejercicio027.helper.TestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo1/ejercicio027/applicationContext.xml"
		})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class VariableNumeroRepositoryTest {

	private static final double DELTA = 0.001;
	
	@Autowired
	private VariableNumeroRepository varRepository;
	@Autowired
	private TestHelper tHelper;

	@PersistenceContext
	private EntityManager em;

	@Test
	public void testAdd() {
		VariableNumero varNum = new VariableNumero();
		varNum.setNombreVarNum("Variable Num1");
		assertNull(varNum.getId());

		varRepository.add(varNum);

		assertEquals("Variable Num1", varNum.getNombreVarNum());
	}

	@Test
	public void testRead() {
		Long clavePrimaria = tHelper.generaVariableNumero();

		VariableNumero resultado = varRepository.read(clavePrimaria);

		assertEquals("Numero 1", resultado.getNombreVarNum());
	}

	@Test(expected=PersistenceException.class)
	public void testRead_noExiste() {
		Long clavePrimaria = Long.MIN_VALUE;

		VariableNumero resultado = varRepository.read(clavePrimaria);

		assertEquals("Avellanas", resultado.getNombreVarNum());
	}

	@Test
	public void testList() {
		tHelper.generaVariableNumero();
		VariableNumero varNum = new VariableNumero();
		varRepository.add(varNum);
		varNum = new VariableNumero();
		varRepository.add(varNum);

		List<VariableNumero> resultado = varRepository.list();

		assertTrue(resultado.size()>= 3);
	}

	@Test
	public void testDelete() {
		Long clavePrimaria = tHelper.generaVariableNumero();

		varRepository.delete(clavePrimaria);
		VariableNumero v;
		try {
			v  = em.find(VariableNumero.class, clavePrimaria);
		} catch (PersistenceException pe){
			return;
		}
		assertNull(v);
	}

	@Test
	public void testUpdate() {
		Long clavePrimaria = tHelper.generaVariableNumero();

		VariableNumero var2 = new VariableNumero();
		var2.setId(clavePrimaria);
		var2.setValorVarNum(0.2);

		VariableNumero resultado = varRepository.update(var2);

		VariableNumero enBBDD = em.find(VariableNumero.class, clavePrimaria);
		assertEquals(0.2, enBBDD.getValorVarNum(), DELTA);
		assertEquals(0.2, resultado.getValorVarNum(), DELTA);
	}
}