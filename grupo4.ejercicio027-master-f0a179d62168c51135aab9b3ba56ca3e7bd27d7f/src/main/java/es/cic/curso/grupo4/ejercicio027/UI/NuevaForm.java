package es.cic.curso.grupo4.ejercicio027.UI;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo4.ejercicio027.dominio.Conector;

public class NuevaForm extends FormLayout
{
	private static final long serialVersionUID = 7854103764337669112L;

	//Select
	protected ComboBox selectTipo;
	
	//Botones
	protected Button nuevo;
	protected Button aceptar;
	protected Button actualizar;
	protected Button borrar;
	protected Button cancelar;
	
	//Campos de Texto
	private TextField nombre;
	private TextField descripcion;
	protected TextField origen;
	private TextField destino;
	private TextField vacio;
	protected Label conectorActual;
    
	//Notificaciones
	Notification selectNull;
	Notification valorIncorrecto;
	
	//Conector Actual
	private Conector conector;
	
	//Caja - Modo Visualización
	VerticalLayout cajaVisualizacion;

    //Formulario Nuevo Conector
	public NuevaForm(MyUI padre)
	{
		selectNull = new Notification("¡ERROR!","¡Introduzca un 'Tipo de Conector'!");
		selectNull.setDelayMsec(5000);
		
		valorIncorrecto = new Notification("¡ERROR!","¡Valores Incorrectos!");
		valorIncorrecto.setDelayMsec(5000);
			
		cajaVisualizacion = new VerticalLayout();
		
	    selectTipo = new ComboBox("Tipo de Conector:");
	    selectTipo.setNullSelectionAllowed(false);
	    selectTipo.setImmediate(true);
        selectTipo.addItem("Fichero");
        selectTipo.addItem("FTP");
        selectTipo.addItem("Rest");
        selectTipo.setValue(null);
        
        nombre = new TextField("Nombre:");
        descripcion = new TextField("Descripción:");
        origen = new TextField("Origen:");
        destino = new TextField("Destino:");
        vacio = new TextField("Origen:");
        vacio.setValue("Sin Origen");
        vacio.setReadOnly(true);
        vacio.setVisible(false);
		
		nombre.setNullRepresentation("");
		descripcion.setNullRepresentation("");
		origen.setNullRepresentation("");
		destino.setNullRepresentation("");
		
        conectorActual = new Label("Nuevo Conector");
        conectorActual.setCaption("Conector Actual:");
        
	    selectTipo.addValueChangeListener(e -> {
	    	if(selectTipo.getValue()!=null){
	    		nombre.setVisible(true);
	    		descripcion.setVisible(true);
	    		if(selectTipo.getValue().toString().equals("Rest")){
	    			origen.setVisible(false);
	    			vacio.setVisible(true);
	    		}else{
	    			origen.setVisible(true);
	    			vacio.setVisible(false);
	    		}
	    		resetVisualizacion(false);
	    		destino.setVisible(true);
	    	}
		});
	    
		nuevo = new Button("Crear Nuevo");
		nuevo.setIcon(FontAwesome.CHECK);
		nuevo.addClickListener(e -> {		
			resetInicio();
			nuevo.setVisible(false);
			aceptar.setVisible(true);
			cancelar.setVisible(true);
			selectTipo.setVisible(true);
    		nombre.setVisible(true);
    		descripcion.setVisible(true);
    		origen.setVisible(true);
    		destino.setVisible(true);
    		padre.ejecutar.setVisible(false);
    		padre.modificar.setVisible(false);
    		padre.verHistorico.setVisible(false);
    		padre.ocultarLogingForm();
			resetVisualizacion(true);
			selectTipo.setReadOnly(false);
		});
	
		aceptar = new Button("Añadir Nuevo");
		aceptar.setIcon(FontAwesome.CHECK);
		aceptar.addClickListener(e -> {
			if(selectTipo.getValue()!=null)
			{
				if(compruebaDatos()){
					if(selectTipo.getValue().toString().equals("Rest")){
				    	conector=padre.creaConector(new Conector(selectTipo.getValue().toString(),nombre.getValue(), descripcion.getValue(), 
								"Sin Origen", destino.getValue()));
					}else{
				    	conector=padre.creaConector(new Conector(selectTipo.getValue().toString(),nombre.getValue(), descripcion.getValue(), 
								origen.getValue(), destino.getValue()));
					}
			    	Notification.show("CREADO: Conector ID="+conector.getId());
			    	resetInicio();
					padre.cargaGridConectoresHabilitados();
				}else{
					valorIncorrecto.show(Page.getCurrent());
				}
			}else{
	    		selectNull.show(Page.getCurrent());	
	    	}
		});
		
		cancelar = new Button("Cancelar");
		cancelar.setIcon(FontAwesome.CLOSE);
		cancelar.addClickListener(e -> {		
			resetInicio();
			aceptar.setVisible(false);
			cancelar.setVisible(false);
			selectTipo.setVisible(false);
    		nombre.setVisible(false);
    		descripcion.setVisible(false);
    		origen.setVisible(false);
    		destino.setVisible(false);
			padre.cargaGridConectoresHabilitados();
		});
		
		HorizontalLayout hLayout = new HorizontalLayout();
		
		actualizar = new Button("Actualizar");
		actualizar.setIcon(FontAwesome.REFRESH);
		actualizar.addClickListener(e -> {
			if(compruebaDatos()){
				Notification.show("ACTUALIZADO: Conector ID="+conector.getId());
				Conector conectorModificado = new Conector(selectTipo.getValue().toString(),nombre.getValue(), descripcion.getValue(), 
						origen.getValue(), destino.getValue());
				if(selectTipo.getValue().toString().equals("Rest")){
					conectorModificado.setOrigen("Sin Origen");
				}
				conectorModificado.setHabilitado(conector.isHabilitado());
				conectorModificado.setId(conector.getId());
				padre.actualizaConector(conectorModificado);
				padre.cargaGridConectoresHabilitados();
				resetInicio();		
			}else{
				valorIncorrecto.show(Page.getCurrent());
			}
		});
		
		borrar = new Button("Borrar");
		borrar.setIcon(FontAwesome.ERASER);
		borrar.addClickListener(e -> {
			if(compruebaDatos()){
				Notification.show("BORRADO: Conector ID="+conector.getId());
				padre.borraConector(conector.getId());		
				padre.cargaGridConectoresHabilitados();
				resetInicio();
			}else{
				valorIncorrecto.show(Page.getCurrent());
			}
		});
		
		resetInicio();
		
		hLayout.addComponents(aceptar,actualizar);
		
		addComponents(nuevo,conectorActual,selectTipo,nombre,descripcion,origen,vacio,destino,hLayout,cancelar);
		setConector(conector);
	}

	public void resetInicio(){
		resetVisualizacion(false);
		setConector(null);
		conectorActual.setVisible(false);
		selectTipo.setVisible(false);
		conectorActual.setValue("Nuevo Conector");
		selectTipo.setValue(null);
		nombre.setValue("");
		descripcion.setValue("");
		origen.setValue("");
		destino.setValue("");
		actualizar.setVisible(false);
		borrar.setVisible(false);
		if(!nuevo.isVisible()){nuevo.setVisible(true);}
		nombre.setVisible(false);
		descripcion.setVisible(false);
		origen.setVisible(false);
		vacio.setVisible(false);	
		destino.setVisible(false);
		aceptar.setVisible(false);
		cancelar.setVisible(false);	
	}
	
	public void resetEdicion(){
		conectorActual.setVisible(true);
		selectTipo.setVisible(true);
		nombre.setVisible(true);
		descripcion.setVisible(true);
		origen.setVisible(true);
		destino.setVisible(true);
		borrar.setVisible(true);
		cancelar.setVisible(true);
		nuevo.setVisible(false);
		aceptar.setVisible(false);
		selectTipo.setValue(conector.getTipo());
		conectorActual.setValue("ID = "+conector.getId().toString());
	}
	
	public void resetVisualizacion(boolean editable){
		selectTipo.setReadOnly(editable);
		nombre.setReadOnly(editable);
		descripcion.setReadOnly(editable);
		origen.setReadOnly(editable);
		destino.setReadOnly(editable);
	}
	
	public boolean compruebaDatos(){
		if(selectTipo.getValue()==null){
			return false;
		}
		
		if(nombre.getValue()==null || descripcion.getValue()==null || origen.getValue()==null || destino.getValue()==null){
			return false;
		}
		
		if(nombre.getValue()=="" || descripcion.getValue()=="" || destino.getValue()==""){
			return false;
		}
		
		if((!selectTipo.getValue().toString().equals("Rest")) && origen.getValue()==""){
			return false;
		}			
		
		return true;
	}
	
	public void setConector(Conector conector)
	{  
		this.conector=conector;
		
		if(conector!=null)
		{
		    BeanFieldGroup.bindFieldsUnbuffered(conector, this);			
		} else {
			BeanFieldGroup.bindFieldsUnbuffered(new Conector(), this);
		}
	}
	
	
}