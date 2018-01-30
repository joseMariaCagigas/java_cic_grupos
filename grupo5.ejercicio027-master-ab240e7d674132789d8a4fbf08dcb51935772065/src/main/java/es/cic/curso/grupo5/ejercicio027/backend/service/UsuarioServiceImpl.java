package es.cic.curso.grupo5.ejercicio027.backend.service;

 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Operacion;
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Rol;
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Usuario;
import es.cic.curso.grupo5.ejercicio027.backend.repository.OperacionRepository;
import es.cic.curso.grupo5.ejercicio027.backend.repository.RolRepository;
import es.cic.curso.grupo5.ejercicio027.backend.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RolRepository rolRepository;
	
	@Autowired
	private OperacionRepository operacionRepository;

    @Override
	public Usuario aniadirUsuario(Usuario usuario) {
        return usuarioRepository.add(usuario);
	}

    @Override
    public Usuario modificarUsuario(Usuario usuario) {
    	return usuarioRepository.update(usuario);
    }
   
    @Override
    public void borrarUsuario(Long id) {
    	Usuario usuarioABorrar = obtenerUsuario(id);
    	usuarioRepository.delete(usuarioABorrar);
    }

    @Override
    public Usuario obtenerUsuario(Long id) {
        return usuarioRepository.read(id);
    }

    @Override
    public List<Usuario> listarUsuario() {
        return usuarioRepository.list();
    }
    
    @Override
    public void generaBBDD(){
   	Operacion operacion1 = new Operacion("Añadir usuarios",true,"administrador");
	Operacion operacion2 = new Operacion("Modificar usuarios",true,"editor");
	Operacion operacion3 = new Operacion("Eliminar usuarios",true,"administrador");
	Operacion operacion4= new Operacion("Modificar archivos",true,"personal de mantenimiento");
	Operacion operacion5 = new Operacion("Borrar archivos",true,"personal de mantenimiento");
	Operacion operacion6 = new Operacion("Calcular costes de empresa",true,"supervisor");
	Operacion operacion7 = new Operacion("Realizar venta por internet",true,"invitado");
	Operacion operacion8 = new Operacion("Listar ventas",true,"supervisor");
	Operacion operacion9 = new Operacion("ingresar nóminas",true,"personal RR.HH");
	

   	operacionRepository.add(operacion1);
   	operacionRepository.add(operacion2);
   	operacionRepository.add(operacion3);
   	operacionRepository.add(operacion4);
   	operacionRepository.add(operacion5);
   	operacionRepository.add(operacion6);
   	operacionRepository.add(operacion7);
   	operacionRepository.add(operacion8);
   	operacionRepository.add(operacion9);
   	
   
    	
    	Rol rol1= new Rol("administrador");
    	Rol rol2= new Rol("invitado");
    	Rol rol3= new Rol("supervisor");
    	Rol rol4= new Rol("editor");
    	Rol rol5= new Rol("personal de mantenimiento");
    	Rol rol6= new Rol("personal RR.HH");
     
    	
    	  	
    	rolRepository.add(rol1);
    	rolRepository.add(rol2);
    	rolRepository.add(rol3);
    	rolRepository.add(rol4);
    	rolRepository.add(rol5);
    	rolRepository.add(rol6);
    	  
    	
    	Usuario usuario1 = new Usuario("Juan González del Olmo", "juan", rol1, "juan@hotmail.com",true);
		Usuario usuario2 = new Usuario("Jose Giménez Sánchez", "pepe", rol2, "pepe@hotmail.com",true);
		Usuario usuario3 = new Usuario("Pedro de la torre García", "pedro", rol3, "pedro@hotmail.com",true);
		Usuario usuario4 = new Usuario("María Suarez Fernandez", "mery", rol4, "laMery@hotmail.com",true);
		
		usuarioRepository.add(usuario1);
		usuarioRepository.add(usuario2);
		usuarioRepository.add(usuario3);
		usuarioRepository.add(usuario4);
    }
    
}