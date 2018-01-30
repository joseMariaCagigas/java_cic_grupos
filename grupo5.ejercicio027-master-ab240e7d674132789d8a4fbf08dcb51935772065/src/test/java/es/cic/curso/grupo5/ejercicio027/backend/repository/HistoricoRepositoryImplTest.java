package es.cic.curso.grupo5.ejercicio027.backend.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Historico;
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Rol;
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Usuario;
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Operacion;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:es/cic/curso/grupo5.ejercicio027/applicationContext.xml"}
)

public class HistoricoRepositoryImplTest extends AbstractRepositoryImplTest<Long, Historico> {

    @Autowired
    private HistoricoRepository sut;
    
    Usuario usuario,usuario2,usuario3;
    Rol rol;
    Operacion operacion1,operacion2,operacion3;
    List<Operacion> listaOperciones=new ArrayList<>();
    @Before
    public void setUp() throws Exception {
        super.setUp();

	    operacion1 = new Operacion("Copiar"); 
        em.persist(operacion1);
     
        
        rol = new Rol("administrador");

        em.persist(rol);
        usuario = new Usuario("Christian","curso18",rol,"maquina1995@gmail.com",false);
        em.persist(usuario);
        usuario2 = new Usuario("Christian2","curso18",rol,"maquina1995@gmail.com",true);
        em.persist(usuario2);
        usuario3 = new Usuario("Christian3","curso18",rol,"maquina1995@gmail.com",true);
        em.persist(usuario3);
    }

    @Override
    public Historico getInstanceDeTParaNuevo() {
    	
        Historico claseHistorico = new Historico();
        
        
        claseHistorico.setOperacion("Tirar la basura");
        claseHistorico.setHora("16:56");
        claseHistorico.setUsuario(usuario);

        return claseHistorico;
    }

    @Override
    public Historico getInstanceDeTParaLectura() {
    	
        Historico claseHistorico = new Historico();
        
        claseHistorico.setOperacion("Tirar la basura 2");
        claseHistorico.setHora("16:56");
        claseHistorico.setUsuario(usuario2);

        return claseHistorico;
    }

    @Override
    public Long getClavePrimariaNoExistente() {
        return Long.MAX_VALUE;
    }

    @Override
    public Historico getInstanceDeTParaModificar(Long clave) {
    	
        Historico claseHistorico = getInstanceDeTParaLectura();
        
        claseHistorico.setId(clave);
        claseHistorico.setOperacion("Tirar la basura 3");
        claseHistorico.setHora("16:56");
        claseHistorico.setUsuario(usuario3);
        
        return claseHistorico;
    }

    @Override
    public IRepository<Long, Historico> getRepository() {
        return sut;
    }

    @Override
    public boolean sonDatosIguales(Historico t1, Historico t2) {
        if (t1 == null || t2 == null) {
            throw new UnsupportedOperationException("No puedo comparar nulos");
        }
        
        if (!t1.getOperacion().equals(t2.getOperacion())) {
            return false;
        }
        
        if (!t1.getHora().equals(t2.getHora())) {
            return false;
        }
        
        if (!t1.getUsuario().equals(t2.getUsuario())) {
            return false;
        }
        
        return true;
    }
}
