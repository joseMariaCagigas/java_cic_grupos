package es.cic.curso.grupo3.ejercicio027.repository;

import static org.junit.Assert.assertNotNull;

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

import es.cic.curso.grupo3.ejercicio027.AbstractRepositoryImplTest;
import es.cic.curso.grupo3.ejercicio027.domain.Rol;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo3/ejercicio027/applicationContext.xml"
		})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class RolRepositoryTest extends AbstractRepositoryImplTest<Long, Rol>{

	@Autowired
	RolRepository sut;
	
	@Before
	public void setUp(){
	}
	
	@Override
	public IRepository<Long, Rol> getRepository() {
		return sut;
	}

	@Override
	public Rol getInstanceDeTParaNuevo() {
		Rol rol = new Rol();
		rol.setNombre("Administrador");
		
		return rol;
	}

	@Override
	public Rol getInstanceDeTParaLectura() {
		Rol rol = new Rol();
		rol.setNombre("Administrador");
		
		return rol;
	}

	@Override
	public boolean sonDatosIguales(Rol t1, Rol t2) {
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
	public Rol getInstanceDeTParaModificar(Long clave) {
		Rol rol = getInstanceDeTParaLectura();
		rol.setId(clave);
		rol.setNombre("Master");
		
		return rol;
	}
	
	@Test
	public void buscarIdEntradaTest() {
		Rol rol = new Rol();
		rol.setNombre("Master");
		em.persist(rol);
		Long idClase = sut.read(rol).getId();
		
		assertNotNull(idClase);
	}
}
