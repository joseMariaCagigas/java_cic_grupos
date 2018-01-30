package es.cic.curso.grupo5.ejercicio027.backend.repository;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Operacion;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:es/cic/curso/grupo5.ejercicio027/applicationContext.xml"}
)
public class OperacionRepositoryImplTest extends AbstractRepositoryImplTest<Long, Operacion> {

	
	 @Autowired
	    private OperacionRepository sut;

	    @Before
	    @Override
	    public void setUp() throws Exception {
	        super.setUp();
	    }

	    @Override
	    public Operacion getInstanceDeTParaNuevo() {
	    	
	    	Operacion op = new Operacion();
	        
	        op.setDescripcion("operacion");
	      
	        
	        return op;
	    }

	    @Override
	    public Operacion getInstanceDeTParaLectura() {
	    	
	    	Operacion op = new Operacion();
	        
	        op.setDescripcion("operacion");
	      

	        return op;
	    }

	    @Override
	    public Long getClavePrimariaNoExistente() {
	        return Long.MAX_VALUE;
	    }

	    @Override
	    public Operacion getInstanceDeTParaModificar(Long clave) {
	    	Operacion op = getInstanceDeTParaLectura();
	        op.setId(clave);
	        op.setDescripcion("operacion");
	       
	        return op;
	    }

	    @Override
	    public IRepository<Long, Operacion> getRepository() {
	        return sut;
	    }

	    @Override
	    public boolean sonDatosIguales(Operacion t1, Operacion t2) {
	        if (t1 == null || t2 == null) {
	            throw new UnsupportedOperationException("No puedo comparar nulos");
	        }
	        
			if (!t1.getDescripcion().equals(t2.getDescripcion())) {
				return false;
			}
			
		 
	        
	        return true;
	    }
	}