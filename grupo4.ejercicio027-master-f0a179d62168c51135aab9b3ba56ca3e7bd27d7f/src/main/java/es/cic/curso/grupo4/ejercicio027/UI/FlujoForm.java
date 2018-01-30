package es.cic.curso.grupo4.ejercicio027.UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo4.ejercicio027.dominio.Conector;
import es.cic.curso.grupo4.ejercicio027.dominio.Flujo;
import es.cic.curso.grupo4.ejercicio027.servicio.FlujoServicio;

public class FlujoForm extends FormLayout
{
	private static final long serialVersionUID = 2535814166945491343L;
	
	//Botones
	protected  Grid maestroConectoresDeFlujo;
	
	protected Button nuevo;
	protected Button aceptar;
	protected Button actualizar;
	protected Button borrar;
	protected Button cancelar;

	protected Button cambiaHabilitar;
	protected Button ejecutar;
	protected Button aniadePaso;
	protected Button eliminaPaso;
	protected VerticalLayout cajaCambiaHabilitar;
	protected VerticalLayout cajaAniadePaso;
	protected HorizontalLayout cajaConectoresLibres;
	protected VerticalLayout cajaEliminaPaso;
	protected VerticalLayout cajaEjecutar;
	private VerticalLayout cajaMaestroBorrar;
	
	//Campos de Texto
	private TextField nombre;
	private TextField conectores;
	protected TextField habilitado;
	protected Label flujoActual;
    
	private FlujoServicio flujoServicio2;
	//Notificaciones
	Notification selectNull;
	Notification valorIncorrecto;
	
	//ComboBox
	protected ComboBox conectoresLibres;
	
	private Conector c;
	//Padre
	private MyUI padre;
	//Flujo Actual
	protected Flujo flujo;
	//Lista Conectores
	List<Conector> listaConectores = new ArrayList<>();
    //Formulario Flujos
	Button borrarConector;
	protected Button confirmar;

	public FlujoForm(MyUI padre)
	{
		
		flujoServicio2 = ContextLoader.getCurrentWebApplicationContext().getBean(FlujoServicio.class);
		borrarConector = new Button("Borrar");
		borrarConector.setIcon(FontAwesome.ERASER);
		maestroConectoresDeFlujo = new Grid();
		maestroConectoresDeFlujo.setColumns("tipo", "nombre", "descripcion");
		maestroConectoresDeFlujo.setFrozenColumnCount(1);
		maestroConectoresDeFlujo.setSelectionMode(SelectionMode.SINGLE);
		maestroConectoresDeFlujo.addSelectionListener(e->{
			c = null;
			if (!e.getSelected().isEmpty()) {
				c = (Conector) e.getSelected().iterator().next();

				borrarConector.setVisible(true);
			}else{
				borrarConector.setVisible(false);
			}
		});
		
		borrarConector.addClickListener(e->{
			String lista = flujo.getConectores();
			String[] listaIds = lista.split(";");
			
			List<String> listaVolcado = new ArrayList<>();
			for(String string :listaIds){
//				if(!string.equals(c.getId().toString())){
					listaVolcado.add(string);
//				}
			}
			listaVolcado.remove(c.getId().intValue());
			String resultado = "";
			for(String l: listaVolcado){
				resultado += l +";";
			}
			
			flujo.setConectores(resultado);
			flujoServicio2.modificaFlujo(flujo);
			padre.cargaGridFlujos();
			resetInicio();
		});
		selectNull = new Notification("¡ERROR!","¡Introduzca un 'Tipo de Conector'!");
		selectNull.setDelayMsec(5000);
		
		valorIncorrecto = new Notification("¡ERROR!","¡Valores Incorrectos!");
		valorIncorrecto.setDelayMsec(5000);
		
		conectoresLibres = new ComboBox();
		conectoresLibres.setNullSelectionAllowed(false);
		conectoresLibres.setImmediate(true);
        
		aniadePaso = new Button("Añade Paso");
		aniadePaso.setIcon(FontAwesome.PLUS);
		aniadePaso.setVisible(false);
		aniadePaso.addClickListener(e->{
			conectoresLibres.removeAllItems();
			conectoresLibres.setVisible(true);
			confirmar.setVisible(true);
			padre.setConectores();
			borrarConector.setVisible(false);
			maestroConectoresDeFlujo.setVisible(false);
		});
		
		confirmar = new Button("Confirmar");
		confirmar.setIcon(FontAwesome.CHECK);
		confirmar.addClickListener(e->{
			if(validarAnadirPaso()){
//				Conector c = (Conector) conectoresLibres.getValue();
				List<Conector> l = padre.getConectoresLibres();
				for(Conector c: l){
					if(c.getNombre().equals( conectoresLibres.getValue().toString())){
						padre.anadirConectorAFlujo(c);
					}
				}
				
				resetInicio();
			}else{
				padre.notificar("Alerta!", "No has seleccionado valor");
			}
		});
		
		eliminaPaso = new Button("Elimina Paso");
		eliminaPaso.setVisible(false);
		eliminaPaso.setIcon(FontAwesome.REMOVE);


		eliminaPaso.addClickListener(e->{	
			if(padre.validarEliminaPaso()){
				padre.cargaGridConectoresDeFlujo();
				maestroConectoresDeFlujo.setVisible(true);
				conectoresLibres.setVisible(false);
				confirmar.setVisible(false);
			}else{
				padre.notificar("Alerta!", "No hay conectores en ese Flujo");
			}
			
			
		});
		
		cambiaHabilitar = new Button("Deshabilitar");
		cambiaHabilitar.setIcon(FontAwesome.TOGGLE_ON);
		cambiaHabilitar.addClickListener(e->{
			if(flujo.isHabilitado()){
				padre.deshabilitaFlujo(flujo);
			}else{
				padre.habilitaFlujo(flujo);
			}			
			padre.cargaGridFlujos();
		});

        nombre = new TextField("Nombre:");
        conectores = new TextField("Conectores:");
        habilitado = new TextField("Habilitado:");
		
		nombre.setNullRepresentation("");
		conectores.setNullRepresentation("");
		habilitado.setNullRepresentation("");
		
        flujoActual = new Label("Nuevo Flujo");
        flujoActual.setCaption("Flujo Actual:");
	    
		ejecutar = new Button("Ejecutar");
		ejecutar.setIcon(FontAwesome.MAGIC);
		ejecutar.addClickListener(e -> {
			if(!flujo.getConectores().equals("")){
				try {
					padre.ejecutarFlujo();
					padre.notificar("Flujo ejecutado con éxito", "Enhorabuena");
					padre.cargaGridFlujos();
					resetInicio();
				} catch (IOException e1) {
					padre.notificar("Alerta", "No se ha podido ejecutar");			
				}
			}else{
				padre.notificar("Alerta!", "No hay conectores");
			}
			
			
		});
		
		nuevo = new Button("Crear Nuevo");
		nuevo.setIcon(FontAwesome.CHECK);
		nuevo.addClickListener(e -> {		
			resetInicio();
			nuevo.setVisible(false);
			aceptar.setVisible(true);
			cancelar.setVisible(true);
    		nombre.setVisible(true);
    		flujoActual.setVisible(true);
    		padre.ejecutar.setVisible(false);
		});
	
		aceptar = new Button("Añadir Nuevo");
		aceptar.setIcon(FontAwesome.CHECK);
		aceptar.addClickListener(e -> {
			if(compruebaDatos()){
		    	flujo=padre.creaFlujo(new Flujo(nombre.getValue().toString(),""));
		    	Notification.show("CREADO: Flujo ID="+flujo.getId());
		    	resetInicio();
				padre.cargaGridFlujos();
			}else{
				valorIncorrecto.show(Page.getCurrent());
			}
		});
		
		cancelar = new Button("Cancelar");
		cancelar.setIcon(FontAwesome.CLOSE);
		cancelar.addClickListener(e -> {		
			resetInicio();
			aceptar.setVisible(false);
			cancelar.setVisible(false);
    		nombre.setVisible(false);
    		conectores.setVisible(false);
    		habilitado.setVisible(false);
			padre.cargaGridFlujos();
		});
		
		HorizontalLayout hLayout = new HorizontalLayout();
		
		actualizar = new Button("Actualizar");
		actualizar.setIcon(FontAwesome.REFRESH);
		actualizar.addClickListener(e -> {
			if(compruebaDatos()){
				Notification.show("ACTUALIZADO: Flujo ID="+flujo.getId());
				Flujo flujoModificado = new Flujo();
				flujoModificado.setId(flujo.getId());
				padre.actualizaFlujo(flujoModificado);
				padre.cargaGridFlujos();
				resetInicio();		
			}else{
				valorIncorrecto.show(Page.getCurrent());
			}
		});
		
		borrar = new Button("Borrar");
		borrar.setIcon(FontAwesome.ERASER);
		borrar.addClickListener(e -> {
			if(compruebaDatos()){
				Notification.show("BORRADO: Flujo ID="+flujo.getId());
				padre.borraConector(flujo.getId());		
				padre.cargaGridFlujos();
				resetInicio();
			}else{
				valorIncorrecto.show(Page.getCurrent());
			}
		});
		
		
		resetInicio();
		
		hLayout.addComponents(aceptar);
		
		VerticalLayout vLayout = new VerticalLayout();
		VerticalLayout cajaNuevo = new VerticalLayout();
		cajaNuevo.addComponent(nuevo);
		cajaNuevo.setMargin(true);
		vLayout.addComponents(cajaNuevo,flujoActual,nombre,conectores,habilitado,hLayout,cancelar);	

		
		cajaConectoresLibres = new HorizontalLayout();
		cajaAniadePaso = new VerticalLayout();
		cajaEliminaPaso = new VerticalLayout();
		cajaCambiaHabilitar = new VerticalLayout();
		cajaEjecutar = new VerticalLayout();
		cajaMaestroBorrar = new VerticalLayout();
		
		cajaConectoresLibres.setMargin(true);
		cajaConectoresLibres.setSpacing(true);
		cajaAniadePaso.setMargin(true);
		cajaEliminaPaso.setMargin(true);
		cajaCambiaHabilitar.setMargin(true);
		cajaEjecutar.setMargin(true);
		cajaMaestroBorrar.setMargin(true);
		cajaMaestroBorrar.setSpacing(true);
		
		cajaMaestroBorrar.addComponents(maestroConectoresDeFlujo, borrarConector);
		cajaConectoresLibres.addComponents(conectoresLibres, confirmar, cajaMaestroBorrar);
		cajaAniadePaso.addComponent(aniadePaso);
		cajaEliminaPaso.addComponent(eliminaPaso);
		cajaCambiaHabilitar.addComponent(cambiaHabilitar);
		cajaEjecutar.addComponent(ejecutar);
		
		
		HorizontalLayout lateralLayout = new HorizontalLayout();
		lateralLayout.addComponents(cajaEjecutar,cajaCambiaHabilitar,cajaAniadePaso,cajaEliminaPaso);

		
		
		VerticalLayout vLayout2 = new VerticalLayout();
		vLayout2.addComponents(lateralLayout, cajaConectoresLibres);
		
		HorizontalLayout botonesLayout = new HorizontalLayout();		
		botonesLayout.addComponents(vLayout,vLayout2);
		
		addComponent(botonesLayout);
		
		setFlujo(flujo);
		
		
		
	}
	///////////////////////////VALIDADORES///////////////////////////////////
	public boolean validarAnadirPaso(){
		if(conectoresLibres.getValue() != null){
			return true;
		}
		return false;
	}
	
	//////////////////////MOSTRAR Y OCULTAR BOTONES/////////////////////////
	
	public void mostrarBotonesPaso(){
		aniadePaso.setVisible(true);
		eliminaPaso.setVisible(true);
	}
	
	public void ocultarBotonesPaso(){
		aniadePaso.setVisible(false);
		eliminaPaso.setVisible(false);
	}

	public void resetInicio(){
		resetVisualizacion(false);
		setFlujo(null);
		flujoActual.setVisible(false);
		flujoActual.setValue("Nuevo Flujo");
		nombre.setValue("");
		actualizar.setVisible(false);
		borrar.setVisible(false);
		if(!nuevo.isVisible()){nuevo.setVisible(true);}
		nombre.setVisible(false);
		conectores.setVisible(false);
		habilitado.setVisible(false);
		cambiaHabilitar.setVisible(false);
		ejecutar.setVisible(false);
		aceptar.setVisible(false);
		cancelar.setVisible(false);	
		conectoresLibres.setVisible(false);
		confirmar.setVisible(false);
		borrarConector.setVisible(false);
		eliminaPaso.setVisible(false);
		aniadePaso.setVisible(false);
		maestroConectoresDeFlujo.setVisible(false);
	}
	
	public void resetEdicion(){
		flujoActual.setVisible(true);
		nombre.setVisible(true);
		conectores.setVisible(true);
		habilitado.setVisible(true);
		borrar.setVisible(true);
		cancelar.setVisible(true);
		nuevo.setVisible(false);
		aceptar.setVisible(false);
		flujoActual.setValue("ID = "+flujo.getId().toString());
	}
	
	public void resetVisualizacion(boolean editable){
		nombre.setReadOnly(editable);
		conectores.setReadOnly(editable);
		habilitado.setReadOnly(editable);
	}
	
	//////////////////////////DATOS///////////////////////////
	
	public boolean compruebaDatos()
	{
		if(nombre.getValue()==null){
			return false;
		}
		
		if(nombre.getValue()==""){
			return false;
		}	
		
		return true;
	}
	
	public void setFlujo(Flujo flujo)
	{  
		this.flujo=flujo;
		
		if(flujo!=null)
		{
		    BeanFieldGroup.bindFieldsUnbuffered(flujo, this);			
		} else {
			BeanFieldGroup.bindFieldsUnbuffered(new Flujo(), this);
		}
	}
	
	//////////////////////////METODOS DE CONECTORES//////////////////////////////////
	

	
	public void borrarConectorDeFlujo(Conector c){
		Flujo f = flujo;
		Long id = c.getId();
		String ids = flujo.getConectores();
		List<Conector> lista = padre.escribeLista(ids);
		for(Conector conector: lista){
			if(id == conector.getId()){
				lista.remove(conector);
			}
		}
		ids = padre.escribeConectores(lista);
		f.setConectores(ids);
		flujoServicio2.modificaFlujo(f);
	}
	
	//////////////////////////METODOS DE LISTA DE CONECTORES/////////////////////////// 


}