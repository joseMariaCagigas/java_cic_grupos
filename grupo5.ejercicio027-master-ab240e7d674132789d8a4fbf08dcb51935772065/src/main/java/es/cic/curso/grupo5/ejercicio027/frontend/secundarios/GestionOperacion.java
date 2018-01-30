package es.cic.curso.grupo5.ejercicio027.frontend.secundarios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Operacion;
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Rol;
import es.cic.curso.grupo5.ejercicio027.backend.service.OperacionService;
import es.cic.curso.grupo5.ejercicio027.frontend.principal.MyUI;

public class GestionOperacion extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3915064377142680846L;

	@SuppressWarnings("unused")
	private MyUI padre;
	private Grid grigOperaciones;
	private OperacionService operacionService;
	 
	private ComboBox operaciones = new ComboBox();
	private NativeButton cambiarRol;
	private NativeButton cancelar;
	private OperacionForm detalleOperracion;
	List<Rol> lisRoles = new ArrayList<>();
	List<String> listaDescripciones = new ArrayList<>(); 
	List<Operacion> lisOperaciones = new ArrayList<>();
	
	private Operacion operacion;
	
	public GestionOperacion(MyUI padre){
		this.padre = padre;
		
		operacionService = ContextLoader.getCurrentWebApplicationContext().getBean(OperacionService.class);	
		
		
		final VerticalLayout  vertical = new VerticalLayout();
		vertical.setSpacing(true);
		final VerticalLayout  extra = new VerticalLayout();
		extra.setSpacing(true);
		
		
		grigOperaciones = new Grid();
		grigOperaciones.setWidth(820, Unit.PIXELS);	
		grigOperaciones.setColumns("descripcion","nombreRol");	
		grigOperaciones.setFrozenColumnCount(1);
		grigOperaciones.setSelectionMode(SelectionMode.NONE);	
		
		cambiarRol = new NativeButton("Reasignar rol");
		cambiarRol.setIcon(FontAwesome.PENCIL);
		
		cancelar = new NativeButton("Cancelar");
		cancelar.setIcon(FontAwesome.REPLY);
		
		detalleOperracion = new OperacionForm(this);
		
		cambiarRol.addClickListener(e->{
			
			lisOperaciones = operacionService.listarOperacion();
			listaDescripciones.clear();
			for(Operacion ope :lisOperaciones){			
			 
				listaDescripciones.add(ope.getDescripcion());
				 
			}
			operaciones = new ComboBox("Operación",listaDescripciones);
			operaciones.setInputPrompt("Seleccione operación a modificar");
			operaciones.setNullSelectionAllowed(false);
			operaciones.select(1);
			operaciones.setImmediate(true);
			operaciones.setWidth(300, Unit.PIXELS);
 
			cambiarRol.setVisible(false);
			operaciones.setVisible(true);
			cancelar.setVisible(true);

			extra.addComponents(operaciones,cancelar);

			operaciones.addValueChangeListener(a->{


				for(Operacion ope :lisOperaciones){
					if(operaciones.getValue()==(ope.getDescripcion())){
						detalleOperracion.setVisible(true);
						detalleOperracion.setOperacion(ope);
						cancelar.setVisible(false);
					}					
				}		
			});

			cancelar.addClickListener(a->{
	
				detalleOperracion.setVisible(false);
				operaciones.setVisible(false);
				cancelar.setVisible(false);
				cambiarRol.setVisible(true);
							
			});
						
		});
			
		 
			
		cargaGridOperaciones(null);
		
		vertical.addComponents(cambiarRol,extra,detalleOperracion);
		addComponents(grigOperaciones,vertical);	
	
	}

	public void cargaGridOperaciones(Operacion operacion) {
	 	
		cambiarRol.setVisible(true);
		detalleOperracion.setVisible(false);
		operaciones.setVisible(false);
		
		
		if(operacion != null){
			operacionService.modificarOperacion(operacion);
		}	
		
			
		grigOperaciones.setContainerDataSource(
				new BeanItemContainer<>(Operacion.class, operacionService.listarOperacion())
				);	
		detalleOperracion.setOperacion(null);
	}
	
}
