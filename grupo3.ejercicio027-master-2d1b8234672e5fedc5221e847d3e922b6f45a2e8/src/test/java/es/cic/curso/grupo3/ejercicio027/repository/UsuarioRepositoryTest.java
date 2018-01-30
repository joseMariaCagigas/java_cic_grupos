package es.cic.curso.grupo3.ejercicio027.repository;

import org.junit.Before;
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
import es.cic.curso.grupo3.ejercicio027.domain.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo3/ejercicio027/applicationContext.xml"
		})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class UsuarioRepositoryTest extends AbstractRepositoryImplTest<Long, Usuario>{

	@Autowired
	UsuarioRepository sut;
	
	private Rol rol;
	private Rol rol2;
	
	@Before
	public void setUp() {
		rol = new Rol("Master");
		em.persist(rol);
		
		rol2 = new Rol("Administrador");
		em.persist(rol);
	}

	@Override
	public IRepository<Long, Usuario> getRepository() {
		return sut;
	}

	@Override
	public Usuario getInstanceDeTParaNuevo() {
		Usuario usuario = new Usuario();
		usuario.setNombre("Pablo");
		usuario.setApellidos("Vega Pérez");
		usuario.setAlta(true);
		usuario.setRol(rol);
		
		return usuario;
	}

	@Override
	public Usuario getInstanceDeTParaLectura() {
		Usuario usuario = new Usuario();
		usuario.setNombre("Pablo");
		usuario.setApellidos("Vega Pérez");
		usuario.setAlta(true);
		usuario.setRol(rol);
		
		return usuario;
	}

	@Override
	public boolean sonDatosIguales(Usuario t1, Usuario t2) {
		if (t1 == null || t2 == null) {
			throw new UnsupportedOperationException("No puedo comparar nulos");
		}
		if (!t1.getNombre().equals(t2.getNombre())) {
			return false;
		}
		if (!t1.getApellidos().equals(t2.getApellidos())) {
			return false;
		}
		if (!t1.getRolObj().equals(t2.getRolObj())) {
			return false;
		}
		
		return true;
	}

	@Override
	public Long getClavePrimariaNoExistente() {
		return Long.MAX_VALUE;
	}

	@Override
	public Usuario getInstanceDeTParaModificar(Long clave) {
		Usuario usuario = getInstanceDeTParaLectura();
		usuario.setId(clave);
		usuario.setNombre("Luis");
		usuario.setApellidos("Barquín Arce");
		usuario.setAlta(true);
		usuario.setRol(rol2);
		
		return usuario;
	}

}
