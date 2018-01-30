package es.cic.curso.grupo3.ejercicio027.frontend.admin;

import java.util.List;
import org.springframework.web.context.ContextLoader;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import es.cic.curso.grupo3.ejercicio027.domain.Rol;
import es.cic.curso.grupo3.ejercicio027.domain.Usuario;
import es.cic.curso.grupo3.ejercicio027.frontend.Controles;
import es.cic.curso.grupo3.ejercicio027.service.RolService;
import es.cic.curso.grupo3.ejercicio027.service.UsuarioService;

public class CapaUsuario extends VerticalLayout{

	private static final String STRING_CREAR = "crear";
	private static final long serialVersionUID = 6374703230260445209L;
	private Controles controles = new Controles();
	private UsuarioForm usuarioForm = new UsuarioForm();
	private Grid maestro = usuarioForm.getMaestro();
	private TextField nombre = usuarioForm.getNombre();
	private TextField apellidos = usuarioForm.getApellidos();
	private Button crear = usuarioForm.getCrear();
	private Button modificar = usuarioForm.getModificar();
	private Button borrar = usuarioForm.getBorrar();
	private Button cancelar = usuarioForm.getCancelar();
	private ComboBox selectRol = usuarioForm.getSelectRol();
	private ComboBox select = usuarioForm.getSelect();
	private Usuario usuario;
	private Long usuarioId;
	private UsuarioService usuarioService;
	private RolService rolService;
	
	
	public VerticalLayout creaCapa(){
		VerticalLayout layout = controles.crearVerticalLayout();
		rolService = ContextLoader.getCurrentWebApplicationContext().getBean(RolService.class);
		usuarioService = ContextLoader.getCurrentWebApplicationContext().getBean(UsuarioService.class);
		cargaUsuarios();
		cargaSelect();
		cargaSelectRol();
		reiniciarControles();
		
		maestro.addSelectionListener(e -> {
			controlesParaMaestro();
			usuario = null;
			if(!e.getSelected().isEmpty()){
				usuario = (Usuario) e.getSelected().iterator().next();
				maestro();
			}
		});
		
		crear.addClickListener(e -> {
			controlesParaCrear();
			if(STRING_CREAR.equals(crear.getCaption())) {
				crear.setCaption("aceptar");
				select.setValue("true");
				selectRol.setValue("Analista");
			}else{
				if("".equals(nombre.getValue().toString()) || "".equals(apellidos.getValue().toString())){
					Notification.show("Debes de rellenar todos los campos");
				}else{
					String nombreParaObj = nombre.getValue().toString();
					String apellidosParaObj = apellidos.getValue().toString();
					Rol rolParaObj = rolService.obtenerRolPorNombre(selectRol.getValue().toString());
					String altaParaObj = select.getValue().toString();
					if(!usuarioExiste(nombreParaObj)){
						usuarioService.aniadirUsuario(rolParaObj.getId(), nombreParaObj, apellidosParaObj, Boolean.parseBoolean(altaParaObj));
						ocultarCampos();
						crear.setCaption(STRING_CREAR);
					}else{
						Notification.show("Ya hay un origen con ese nombre");
					}
				}
			
				cargaUsuarios();
			}
		});
		
		modificar.addClickListener(e -> modificar());
		borrar.addClickListener(e -> borrar());
		cancelar.addClickListener(e -> cancelar());
		
		layout.addComponents(usuarioForm);
		return layout;
	}
	
	
	private void maestro(){
		usuarioId = usuario.getId();
		nombre.setValue(usuario.getNombre());
		apellidos.setValue(usuario.getApellidos());
		selectRol.setValue(usuario.getRol());
		select.setValue(Boolean.toString(usuario.isAlta()));
	}
	
	private void modificar(){
		usuario = usuarioService.obtenerUsuario(usuario.getId());
		String nombreParaObj = nombre.getValue().toString();
		String apellidosParaObjeto = apellidos.getValue().toString();
		boolean altaParaObj = Boolean.parseBoolean(select.getValue().toString());
		Rol rolParaObj = rolService.obtenerRolPorNombre(selectRol.getValue().toString());
		usuario.setNombre(nombreParaObj);
		usuario.setApellidos(apellidosParaObjeto);
		usuario.setRol(rolParaObj);
		usuario.setAlta(altaParaObj);
		usuarioService.actualizarUsuario(usuario);
		cargaUsuarios();
		reiniciarControles();
	}
	
	private void borrar(){
		usuario = usuarioService.obtenerUsuario(usuarioId);
		if(usuarioService.consultaEventosDependientes(usuario) > 0){
			usuario.setAlta(false);
			Notification.show("Este usuario tiene eventos relacionados,"
					+ " se ha procedido a su desactivación, lo podra dar de alta desde el panel de administración");
		}else{
			usuarioService.borrarUsuario(usuario.getId());
		}
		cargaUsuarios();
		reiniciarControles();
	}
	
	private boolean usuarioExiste(String nombre){
		List<Usuario>usuarios = usuarioService.obtenerUsuarios();
		for(Usuario usuarioLista : usuarios){
			if(nombre.equals(usuarioLista.getNombre())){
				return true;
			}
		}
		return false;
	}
	
	private void cancelar(){
		reiniciarControles();
		resetearCampos();
		crear.setCaption(STRING_CREAR);
	}
	private void cargaSelect(){
		select.addItem("true");
		select.addItem("false");
	}
	
	private void cargaSelectRol(){
		List<Rol>roles = rolService.obtenerRoles();
		for(Rol rol : roles){
			selectRol.addItem(rol.getNombre());
		}
	}
	
	private void cargaUsuarios(){
		maestro.setContainerDataSource(
    			new BeanItemContainer<>(Usuario.class, usuarioService.obtenerUsuarios())	
    		);
		maestro.setHeightByRows(maestro.getContainerDataSource().size());
		maestro.setHeightMode(HeightMode.ROW);
	}
	
	private void ocultarCampos(){
		nombre.setVisible(false);
		apellidos.setVisible(false);
		select.setVisible(false);
		selectRol.setVisible(false);
	}
	
	private void mostrarCampos(){
		nombre.setVisible(true);
		apellidos.setVisible(true);
		select.setVisible(true);
		selectRol.setVisible(true);
	}
	
	private void resetearCampos(){
		nombre.setValue("");
		apellidos.setValue("");
		select.setValue("true");
		selectRol.setValue("Analista");
	}
	
	private void reiniciarControles(){
		ocultarCampos();
		crear.setEnabled(true);
		modificar.setEnabled(false);
		borrar.setEnabled(false);
		cancelar.setEnabled(false);
	}
	
	private void controlesParaMaestro(){
		resetearCampos();
		mostrarCampos();
		crear.setEnabled(false);
		modificar.setEnabled(true);
		borrar.setEnabled(true);
		cancelar.setEnabled(true);
	}
	
	private void controlesParaCrear(){
		mostrarCampos();
		modificar.setEnabled(false);
		borrar.setEnabled(false);
		cancelar.setEnabled(true);
	}
}
