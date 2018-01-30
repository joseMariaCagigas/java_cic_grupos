package es.cic.curso.grupo5.ejercicio027.frontend.secundarios;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Rol;
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Usuario;
import es.cic.curso.grupo5.ejercicio027.backend.service.RolService;
import es.cic.curso.grupo5.ejercicio027.frontend.utilities.Validador;


public class UsuarioForm extends FormLayout {
	 
	private static final long serialVersionUID = -8212581707579739708L;

	@PropertyId("nombre")
	private TextField nombre;
	@PropertyId("password")
	protected PasswordField password;
	@PropertyId("email")
	private TextField email;	
	@PropertyId("rol")
	protected Rol rol;
	@PropertyId("activo")
	private CheckBox activo;
	
	private NativeButton confirmar;
	private NativeButton cancelar;	
	private Usuario usuario;
	private ComboBox roles;
	
	@SuppressWarnings("unused")
	private GestionUsuarios padre;
	private RolService rolService;
	List<Rol> lisRoles = new ArrayList<>();
			
	public UsuarioForm(GestionUsuarios padre) {
		this.padre = padre;

		rolService = ContextLoader.getCurrentWebApplicationContext().getBean(RolService.class);	
		
		final HorizontalLayout horizontal1 = new HorizontalLayout();
		final HorizontalLayout horizontal2 = new HorizontalLayout();
		final HorizontalLayout horizontal3 = new HorizontalLayout();
		final HorizontalLayout espacio = new HorizontalLayout();
		final HorizontalLayout horizontal5 = new HorizontalLayout();		
		final HorizontalLayout horizontal6 = new HorizontalLayout();
	    
		horizontal1.setSpacing(true);
		horizontal2.setSpacing(true);
		horizontal3.setSpacing(true);
		horizontal5.setSpacing(true);
		horizontal6.setSpacing(true);

		List<String> listaRoles = new ArrayList<>();
	
		lisRoles=rolService.listarRol();
		
		for(Rol rol :lisRoles){	
			listaRoles.add(rol.getTipoRol());
		}
					
		nombre = new TextField("Nombre");
		nombre.setWidth(300, Unit.PIXELS);
		
		password = new PasswordField("Password");
		password.setWidth(300, Unit.PIXELS);
		
		email = new TextField("Email");
		email.setWidth(300, Unit.PIXELS);
		
		roles = new ComboBox("Rol",listaRoles);
		roles.setNullSelectionAllowed(false);
		roles.select(1);
		roles.setImmediate(true);
		roles.setWidth(300, Unit.PIXELS);
		
		confirmar = new NativeButton();
		confirmar.setIcon(FontAwesome.SAVE);

		cancelar = new NativeButton("Cancelar");
		cancelar.setIcon(FontAwesome.REPLY);
			
		activo = new CheckBox("Activo");	
		activo.addValueChangeListener(e->{
			
			if(activo.getValue().equals(true)){
				
				usuario.setActivo(true);
			}		
			
		}); 
		
		roles.addValueChangeListener(a->{
			for(Rol role :lisRoles){
				if(roles.getValue()==(role.getTipoRol())){
					usuario.setRol(role);			
				}
			}			
		});
			
confirmar.addClickListener(e->{
			
			Validador v = new Validador();
			
			boolean noNulo=
					roles.getValue()==null&&
					"".equals(nombre.getValue())&&
					"".equals(password.getValue())&&
					"".equals(email.getValue());
			
			boolean bienEscrito=
					roles.getValue()!=null &&
					v.validarNombreApellidos(nombre.getValue())&&
					v.validarEmail(email.getValue())&&
					v.validarLongitudTexto(password.getValue());
			
			if(noNulo){
				Notification notificacionRellenaTodo = new Notification("Rellene todos los campos");
				mostrarNotificacion(notificacionRellenaTodo);	
			}else if (bienEscrito){
				Notification notificacionOperacion = 
						new Notification("El usuario : "+ nombre.getValue()+" ha sido dado de alta/modificado");
				
				mostrarNotificacion(notificacionOperacion);
				padre.cargaGridUsuarios(usuario);

			} else {
				String camposMal="Los Siguientes Campos Están Mal Escritos o Imcompletos: ";
				
				if(!v.validarNombreApellidos(nombre.getValue()))
					camposMal = camposMal.concat("Nombre ");
				
				if(!v.validarEmail(email.getValue()))
					camposMal = camposMal.concat("Email ");
					
				if(!v.validarLongitudTexto(password.getValue()))
					camposMal = camposMal.concat("ContraseÃ±a ");
				
				if(roles.getValue().toString()=="")
					camposMal = camposMal.concat("Roles ");
				
				if ((nombre.getValue().toCharArray().length>50)
						||	(email.getValue().toCharArray().length>50)
						||	password.getValue().toCharArray().length>50)
				{
					camposMal = camposMal.concat("Recuerda Que El Limite De CarÃ¡cteres Es 50 ");
				}
				
				Notification notificacionCampoMalEscrito = new Notification(camposMal);
				mostrarNotificacion(notificacionCampoMalEscrito);
			}
		});

		cancelar.addClickListener(e->{
		padre.cargaGridUsuarios(null);

		});

 		
		horizontal1.addComponents(nombre);
		horizontal6.addComponent(password);
		horizontal2.addComponents(roles);
		horizontal3.addComponents(email);
		horizontal5.addComponents(confirmar);
		espacio.addComponent(activo);
		addComponents(horizontal1,horizontal6,horizontal2,horizontal3,espacio,horizontal5,cancelar);	

		setUsuario(null);	
	}
	private void mostrarNotificacion(Notification notificacionMostrar) {
		notificacionMostrar.setDelayMsec(2000);
		notificacionMostrar.show(Page.getCurrent());
	}
	public void setUsuario(Usuario usuario) {
		this.setVisible(usuario != null);
		this.usuario = usuario;

		if (usuario != null) {
			BeanFieldGroup.bindFieldsUnbuffered(usuario, this);
		} else {
			BeanFieldGroup.bindFieldsUnbuffered(new Usuario(), this);
		}
	}
 
	public void ocultaCheck(){		
		activo.setVisible(false);	
	}
	public void muestraCheck(){
		activo.setVisible(true);	
	}
	public void cambiarTextoAniadir(){
		confirmar.setCaption("Añadir");	
	}
	public void cambiarTextoModifi(){
		confirmar.setCaption("Modificar");	
	}
	 public void rellenaRol(Usuario user){
		 
		 roles.setValue(user.getRol().getTipoRol());
	 }
		
}