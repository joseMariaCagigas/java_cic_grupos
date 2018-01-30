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
import es.cic.curso.grupo3.ejercicio027.domain.Tipo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo3/ejercicio027/applicationContext.xml"
				})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	 TransactionalTestExecutionListener.class})
@Transactional
public class TipoRepositoryTest extends AbstractRepositoryImplTest<Long, Tipo> {

	@Autowired
	private TipoRepository sut;
	
	@Override
	public IRepository<Long, Tipo> getRepository() {
		return sut;
	}

	@Override
	public Tipo getInstanceDeTParaNuevo() {
		Tipo tipo = new Tipo();
		tipo.setNombre("Tipo01");
		return tipo;
	}

	@Override
	public Tipo getInstanceDeTParaLectura() {
		Tipo tipo = new Tipo();
		tipo.setNombre("Tipo02");
		return tipo;
	}
	
	@Override
	public boolean sonDatosIguales(Tipo t1, Tipo t2) {
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
	public Tipo getInstanceDeTParaModificar(Long clave) {
		Tipo tipo = getInstanceDeTParaLectura();
		tipo.setId(clave);
		tipo.setNombre("Tipo03");
		return tipo;
	}

	@Test
	public void buscarIdEntradaTest() {
		Tipo tipo = new Tipo();
		tipo.setNombre("Tipo prueba");
		em.persist(tipo);
		Long idClase = sut.read(tipo).getId();
		
		assertNotNull(idClase);
	}
}

