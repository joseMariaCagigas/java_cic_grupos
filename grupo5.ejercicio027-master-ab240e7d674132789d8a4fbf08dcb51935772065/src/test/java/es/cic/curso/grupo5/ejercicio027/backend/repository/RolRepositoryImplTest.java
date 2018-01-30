package es.cic.curso.grupo5.ejercicio027.backend.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
 
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Rol;
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Usuario;
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Operacion;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:es/cic/curso/grupo5.ejercicio027/applicationContext.xml"}
)

public class RolRepositoryImplTest extends AbstractRepositoryImplTest<Long, Rol> {

    @Autowired
    private RolRepository sut;
    
    Usuario usuario,usuario2,usuario3;
    Rol rol1;
    Rol rol2;
    Rol rol3;



    @Before
    public void setUp() throws Exception {
        super.setUp();
        rol1 = new Rol("administrador");
        rol2 = new Rol("invitado");
        rol3 = new Rol("editor");
        
        em.persist(rol1);
        em.persist(rol2);
        em.persist(rol3);
        

    }

    @Override
    public Rol getInstanceDeTParaNuevo() {
    	
    	Rol claseRol = new Rol();
        claseRol.setipoRol("administrador");
       

        return claseRol;
    }

    @Override
    public Rol getInstanceDeTParaLectura() {
    	
    	Rol claseRol = new Rol();
        claseRol.setipoRol("administrador");
       

        return claseRol;
    }

    @Override
    public Long getClavePrimariaNoExistente() {
        return Long.MAX_VALUE;
    }

    @Override
    public Rol getInstanceDeTParaModificar(Long clave) {
    	
    	Rol claseRol = getInstanceDeTParaLectura();
        
        claseRol.setId(clave);
        claseRol.setipoRol("invitado");
        
        
        return claseRol;
    }

    @Override
    public IRepository<Long, Rol> getRepository() {
        return sut;
    }

    @Override
    public boolean sonDatosIguales(Rol t1, Rol t2) {
        if (t1 == null || t2 == null) {
            throw new UnsupportedOperationException("No puedo comparar nulos");
        }
        if (!t1.getTipoRol().equals(t2.getTipoRol())) {
            return false;
        }
        
      
       
        
        return true;
    }
}
