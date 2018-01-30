package es.cic.curso.grupo3.ejercicio027.repository;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo3.ejercicio027.AbstractRepositoryImplTest;
import es.cic.curso.grupo3.ejercicio027.domain.Nivel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo3/ejercicio027/applicationContext.xml"
				})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	 TransactionalTestExecutionListener.class})
@Transactional
public class NivelRepositoryTest extends AbstractRepositoryImplTest<Long, Nivel>{

	@Autowired
	private NivelRepository sut;
	
	@Override
	public IRepository<Long, Nivel> getRepository() {
		return sut;
	}

	@Override
	public Nivel getInstanceDeTParaNuevo() {
		Nivel nivel = new Nivel();
		nivel.setNombre("Nivel01");
		return nivel;
	}

	@Override
	public Nivel getInstanceDeTParaLectura() {
		Nivel nivel = new Nivel();
		nivel.setNombre("Nivel02");
		return nivel;
	}
	
	@Override
	public boolean sonDatosIguales(Nivel t1, Nivel t2) {
		if (t1 == null || t2 == null) {
			throw new UnsupportedOperationException("No puedo comparar nulos");
		}
		if (t1.getNombre() != t2.getNombre()) {
			return false;
		}
		return true;
	}

	@Override
	public Long getClavePrimariaNoExistente() {
		return Long.MAX_VALUE;
	}

	@Override
	public Nivel getInstanceDeTParaModificar(Long clave) {
		Nivel nivel = getInstanceDeTParaLectura();
		nivel.setId(clave);
		nivel.setNombre("Nivel03");
		return nivel;
	}

	@Test
	public void buscarIdEntradaTest() {
		Nivel nivel = new Nivel();
		nivel.setNombre("Nivel prueba");
		em.persist(nivel);
		Long idClase = sut.read(nivel).getId();
		
		assertNotNull(idClase);
	}
}
