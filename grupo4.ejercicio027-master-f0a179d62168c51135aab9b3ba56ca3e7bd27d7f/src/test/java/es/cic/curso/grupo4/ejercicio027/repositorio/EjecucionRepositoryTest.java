package es.cic.curso.grupo4.ejercicio027.repositorio;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import es.cic.curso.grupo4.ejercicio027.dominio.Conector;
import es.cic.curso.grupo4.ejercicio027.dominio.Ejecucion;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo4/ejercicio027/applicationContext.xml"
		})
public class EjecucionRepositoryTest extends AbstractRepositoryImplTest<Long, Ejecucion> {
	
	@Autowired
	EjecucionRepository sut;
	
	Conector conector;
	@Before
	public void setUp() throws Exception {
		conector = new Conector();
		conector.setNombre("Conector1");
		conector.setDescripcion("Prueba");
		conector.setTipo("Fichero");
		conector.setOrigen("origen");
		conector.setDestino("destino");
		em.persist(conector);
	}
	
	@Override
	public IRepository<Long, Ejecucion> getRepository() {
		return sut;
	}

	@Override
	public Ejecucion getInstanceDeTParaNuevo() {
		Ejecucion ejecucion = new Ejecucion();
		ejecucion.setConector(conector);
		Timestamp hora = Timestamp.valueOf(LocalDateTime.now());
		ejecucion.setFecha(hora);
		return ejecucion;
	}

	@Override
	public Ejecucion getInstanceDeTParaLectura() {
		Ejecucion ejecucion = new Ejecucion();
		ejecucion.setConector(conector);
		Timestamp hora = Timestamp.valueOf(LocalDateTime.now());
		ejecucion.setFecha(hora);
		return ejecucion;
	}

	@Override
	public boolean sonDatosIguales(Ejecucion t1, Ejecucion t2) {
		if (t1 == null || t2 == null) {
			throw new UnsupportedOperationException("No puedo comparar nulos");
		}
		if (!t1.getConector().equals(t2.getConector())) {
			return false;
		}	
		return true;
	}

	@Override
	public Long getClavePrimariaNoExistente() {
		return Long.MAX_VALUE;
	}

	@Override
	public Ejecucion getInstanceDeTParaModificar(Long clave) {
		Ejecucion ejecucion = getInstanceDeTParaLectura();
		ejecucion.setId(clave);
		return ejecucion;
	}

}
