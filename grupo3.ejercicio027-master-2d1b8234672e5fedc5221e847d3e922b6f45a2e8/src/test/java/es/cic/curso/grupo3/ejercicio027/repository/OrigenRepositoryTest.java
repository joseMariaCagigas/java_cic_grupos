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
import es.cic.curso.grupo3.ejercicio027.domain.Origen;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo3/ejercicio027/applicationContext.xml"
				})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	 TransactionalTestExecutionListener.class})
@Transactional
public class OrigenRepositoryTest extends AbstractRepositoryImplTest<Long, Origen>{

	@Autowired
	private OrigenRepository sut;
	
	@Override
	public IRepository<Long, Origen> getRepository() {
		return sut;
	}

	@Override
	public Origen getInstanceDeTParaNuevo() {
		Origen origen = new Origen();
		origen.setNombre("Origen01");
		return origen;
	}

	@Override
	public Origen getInstanceDeTParaLectura() {
		Origen origen = new Origen();
		origen.setNombre("Origen02");
		return origen;
	}
	
	@Override
	public boolean sonDatosIguales(Origen t1, Origen t2) {
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
	public Origen getInstanceDeTParaModificar(Long clave) {
		Origen origen = getInstanceDeTParaLectura();
		origen.setId(clave);
		origen.setNombre("Origen03");
		return origen;
	}
	
	@Test
	public void buscarIdEntradaTest() {
		Origen origen = new Origen();
		origen.setNombre("Origen prueba");
		em.persist(origen);
		Long idClase = sut.read(origen).getId();
		
		assertNotNull(idClase);
	}
}


