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

import es.cic.curso.grupo1.ejercicio027.dominio.VariableTexto;
import es.cic.curso.grupo1.ejercicio027.helper.TestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo1/ejercicio027/applicationContext.xml"
		})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class VariableTextoRepositoryTest {
	
	@Autowired
	private VariableTextoRepository varRepository;
	@Autowired
	private TestHelper tHelper;

	@PersistenceContext
	private EntityManager em;
	
	@Test
	public void testAdd() {
		VariableTexto varNum = new VariableTexto();
		varNum.setNombreVarTex("Variable Tex1");
		assertNull(varNum.getId());

		varRepository.add(varNum);

		assertEquals("Variable Tex1", varNum.getNombreVarTex());
	}

	@Test
	public void testRead() {
		Long clavePrimaria = tHelper.generaVariableTexto();

		VariableTexto resultado = varRepository.read(clavePrimaria);

		assertEquals("Texto 1", resultado.getNombreVarTex());
	}

	@Test(expected=PersistenceException.class)
	public void testRead_noExiste() {
		Long clavePrimaria = Long.MIN_VALUE;

		VariableTexto resultado = varRepository.read(clavePrimaria);

		assertEquals("Avellanas", resultado.getNombreVarTex());
	}

	@Test
	public void testList() {
		tHelper.generaVariableTexto();
		VariableTexto varNum = new VariableTexto();
		varRepository.add(varNum);
		varNum = new VariableTexto();
		varRepository.add(varNum);
		
		List<VariableTexto> resultado = varRepository.list();

		assertTrue(resultado.size()>= 3);
	}

	@Test
	public void testDelete() {
		Long clavePrimaria = tHelper.generaVariableTexto();

		varRepository.delete(clavePrimaria);
		VariableTexto v;
		try {
			v  = em.find(VariableTexto.class, clavePrimaria);
		} catch (PersistenceException pe){
			return;
		}
		assertNull(v);
	}

	@Test
	public void testUpdate() {
		Long clavePrimaria = tHelper.generaVariableTexto();

		VariableTexto var2 = new VariableTexto();
		var2.setId(clavePrimaria);
		var2.setValorVarTex("valor");

		VariableTexto resultado = varRepository.update(var2);

		VariableTexto enBBDD = em.find(VariableTexto.class, clavePrimaria);
		assertEquals("valor", enBBDD.getValorVarTex());
		assertEquals("valor", resultado.getValorVarTex());
	}
}