package es.cic.curso.grupo4.ejercicio027.repositorio;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.cic.curso.grupo4.ejercicio027.dominio.Conector;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo4/ejercicio027/applicationContext.xml"
		})
public class ConectorRepositoryTest extends AbstractRepositoryImplTest<Long, Conector>{

	@Autowired 
	ConectorRepository sut;
	
	@Override
	public IRepository<Long, Conector> getRepository() {
		return sut;
	}

	@Override
	public Conector getInstanceDeTParaNuevo() {
		Conector conector = new Conector();
		conector.setNombre("Conector1");
		conector.setDescripcion("Prueba");
		conector.setTipo("Fichero");
		conector.setOrigen("origen");
		conector.setDestino("destino");
		return conector;
	}

	@Override
	public Conector getInstanceDeTParaLectura() {
		Conector conector = new Conector();
		conector.setNombre("Conector1");
		conector.setDescripcion("Prueba");
		conector.setTipo("Fichero");
		conector.setOrigen("origen");
		conector.setDestino("destino");
		return conector;
	}

	@Override
	public boolean sonDatosIguales(Conector t1, Conector t2) {
		if (t1 == null || t2 == null) {
			throw new UnsupportedOperationException("No puedo comparar nulos");
		}
		if (!t1.getNombre().equalsIgnoreCase(t2.getNombre())) {
			return false;
		}	
		if (!t1.getDescripcion().equalsIgnoreCase(t2.getDescripcion())) {
			return false;
		}
		if (!t1.getOrigen().equalsIgnoreCase(t2.getOrigen())) {
			return false;
		}
		if (!t1.getDestino().equalsIgnoreCase(t2.getDestino())) {
			return false;
		}
		if (!t1.getTipo().equalsIgnoreCase(t2.getTipo())) {
			return false;
		}
		
		return true;
	}

	@Override
	public Long getClavePrimariaNoExistente() {
		return Long.MAX_VALUE;
	}

	@Override
	public Conector getInstanceDeTParaModificar(Long clave) {
		Conector conector = getInstanceDeTParaLectura();
		conector.setId(clave);
		return conector;
	}

}
