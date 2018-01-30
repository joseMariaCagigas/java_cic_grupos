package es.cic.curso.grupo4.ejercicio027.repositorio;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import es.cic.curso.grupo4.ejercicio027.dominio.Flujo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo4/ejercicio027/applicationContext.xml"
		})
public class FlujoRepositoryTest extends AbstractRepositoryImplTest<Long, Flujo> {


	@Autowired 
	FlujoRepository sut;
	
	@Override
	public IRepository<Long, Flujo> getRepository() {
		return sut;
	}

	@Override
	public Flujo getInstanceDeTParaNuevo() {
		Flujo flujo = new Flujo();
		flujo.setNombre("Flujo1");
		flujo.setConectores("1;1;0;");
		flujo.setHabilitado(true);
		return flujo;
	}

	@Override
	public Flujo getInstanceDeTParaLectura() {
		Flujo flujo = new Flujo();
		flujo.setNombre("Flujo1");
		flujo.setConectores("1;1;0;");
		flujo.setHabilitado(true);
		return flujo;
	}

	@Override
	public boolean sonDatosIguales(Flujo t1, Flujo t2) {
		if (t1 == null || t2 == null) {
			throw new UnsupportedOperationException("No puedo comparar nulos");
		}
		if (!t1.getNombre().equalsIgnoreCase(t2.getNombre())) {
			return false;
		}	
		if (!t1.getConectores().equalsIgnoreCase(t2.getConectores())) {
			return false;
		}
		if (!t1.isHabilitado() == (t2.isHabilitado())) {
			return false;
		}
		
		return true;
	}

	@Override
	public Long getClavePrimariaNoExistente() {
		return Long.MAX_VALUE;
	}

	@Override
	public Flujo getInstanceDeTParaModificar(Long clave) {
		Flujo flujo = getInstanceDeTParaLectura();
		flujo.setId(clave);
		return flujo;
	}

}
