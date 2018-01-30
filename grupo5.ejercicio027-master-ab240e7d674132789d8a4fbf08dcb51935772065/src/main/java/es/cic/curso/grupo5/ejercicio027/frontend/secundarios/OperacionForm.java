package es.cic.curso.grupo5.ejercicio027.frontend.secundarios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Operacion;
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Rol;
import es.cic.curso.grupo5.ejercicio027.backend.service.RolService;
public class OperacionForm extends FormLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3416021602328437461L;
	 
	@SuppressWarnings("unused")
	private GestionOperacion padre;
	
	@PropertyId("nombreRol")
	protected ComboBox nombreRol;
	

	private List<String> listaRoles;
	private RolService rolService;
	List<Rol> lisRoles = new ArrayList<>();
	 
	private Operacion operacion;
	private NativeButton confirmar;
	private NativeButton cancelar;
	
	
	public OperacionForm(GestionOperacion padre) {
		
		
		final VerticalLayout vertical1 = new VerticalLayout();
		vertical1.setSpacing(true);
		  
		
		rolService = ContextLoader.getCurrentWebApplicationContext().getBean(RolService.class);	
		
		
		listaRoles = new ArrayList<>();	
		lisRoles=rolService.listarRol();
		
		for(Rol rol :lisRoles){	
			listaRoles.add(rol.getTipoRol());
		}
		
		nombreRol = new ComboBox("Rol",listaRoles);
		nombreRol.setNullSelectionAllowed(false);
		nombreRol.select(1);
		nombreRol.setImmediate(true);
		nombreRol.setWidth(300, Unit.PIXELS);
		nombreRol.setInputPrompt("seleccione rol");
		
		confirmar = new NativeButton("confirmar");
		confirmar.setIcon(FontAwesome.SAVE);
		cancelar = new NativeButton("Cancelar");
		cancelar.setIcon(FontAwesome.REPLY);
		
		confirmar.addClickListener(e->{
			
			if(nombreRol.getValue()==null){	
				Notification sample = new Notification("Rellene todos los campos");
				sample.setDelayMsec(2000);
				sample.show(Page.getCurrent()); 
			}
			else{
				
				
				operacion.setNombreRol(nombreRol.getValue().toString());
				padre.cargaGridOperaciones(operacion);
				setOperacion(null);
				
			}
		
		});
		
		cancelar.addClickListener(e->padre.cargaGridOperaciones(null));
		
		vertical1.addComponents(nombreRol,confirmar,cancelar);
		addComponents(vertical1);

		setOperacion(null);
		
	}
	public void setOperacion(Operacion operacion) {
		this.setVisible(operacion != null);
		this.operacion = operacion;

		if (operacion != null) {
			BeanFieldGroup.bindFieldsUnbuffered(operacion, this);
		} else {
			BeanFieldGroup.bindFieldsUnbuffered(new Operacion(), this);
		}
	}
}
