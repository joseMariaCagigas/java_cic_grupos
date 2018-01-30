package es.cic.curso.grupo1.ejercicio027.ui;

import java.util.List;
import java.util.Set;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.cic.curso.grupo1.ejercicio027.dominio.Tarea;
import es.cic.curso.grupo1.ejercicio027.service.TareaService;

public class VistaCrear  extends HorizontalLayout {

	private CrearTareaForm ctForm;
	private Grid maestro;
	private List<Tarea> listatareas;
	private TareaService tareaService;
	private Button borrar;
	private Button nuevaTarea;
	private Button ejecutar;
	private Button ejecuciones;
	private Tarea tarea;
	private Tarea tarea2;
	private Label l;

	private TextField filtrarTareas;

	private ModificarTareaForm modificaForm;
	private VerticalLayout layoutH2;

	private LayoutVistaVariables lvVariable;

	@SuppressWarnings("serial")
	public VistaCrear() {
		tareaService = ContextLoader.getCurrentWebApplicationContext().getBean(TareaService.class);
		tarea = new Tarea();

		HorizontalLayout losbotones = new HorizontalLayout();
		VerticalLayout layoutV1 = new VerticalLayout();
		final HorizontalLayout base = new HorizontalLayout();

		layoutH2 = new VerticalLayout();

		base.setMargin(true);
		base.setSizeFull();
		
		filtrarTareas = new TextField();
		
		maestro = new Grid();

		maestro.setColumns("codigo", "descripcion");
		maestro.setSizeFull();
		maestro.setFrozenColumnCount(1);
		maestro.setSelectionMode(SelectionMode.SINGLE);
		l = new Label("Lista de tareas");
		
		maestro.setHeaderVisible(true);

		borrar = new Button("Borrar tarea");
		borrar.setVisible(false);

		modificaForm = new ModificarTareaForm(this);
		modificaForm.setVisible(false);
		
		filtrarTareas.setInputPrompt("Buscador de tareas");
 		filtrarTareas.addTextChangeListener(e->
 			maestro.setContainerDataSource(new BeanItemContainer<>(Tarea.class,  
 					tareaService.findAll(e.getText())))
 		);
 		
 		Button clearFilterBtn = new Button("Limpiar");
 		clearFilterBtn.addClickListener(e->{
 			filtrarTareas.clear();
 			listatareas = tareaService.getTareas();
 			maestro.setContainerDataSource(new BeanItemContainer<>(Tarea.class, listatareas));			
 		});
 		
 		CssLayout filtrado = new CssLayout();
 		filtrado.addComponents(filtrarTareas,clearFilterBtn);
		filtrado.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		maestro.addSelectionListener(new SelectionListener() {

			@Override
			public void select(SelectionEvent event) {
				Set<Object> selected = event.getSelected();
				Tarea filaSeleccionada =  (Tarea) maestro.getSelectedRow();
				if(filaSeleccionada!=null){
					tarea = filaSeleccionada;
					tarea2 = filaSeleccionada;
					borrar.setVisible(true);
					ejecutar.setVisible(true);
					ejecuciones.setVisible(true);
					modificaForm.setTarea(tarea);
					modificaForm.setVisible(true);
					removeComponent(layoutH2);
					lvVariable = new LayoutVistaVariables(tarea);
					layoutH2 = lvVariable;
					addComponents(layoutH2);
					ctForm.setVariable(null);
					cargaGrid();
				}else{
					borrar.setVisible(false);
					ejecuciones.setVisible(false);
					ejecutar.setVisible(false);
					modificaForm.setVisible(false);
					layoutH2.setVisible(false);

				}
			}
		});

		borrar.addClickListener(e -> {
			
				Confirmar comp = new Confirmar(this, tarea);
				UI.getCurrent().addWindow(comp);
			
		});

		base.addComponents(maestro);
		layoutV1.setMargin(true);
		addComponents(base);

		nuevaTarea = new Button("Nueva Tarea");
		nuevaTarea.addClickListener( e -> {
			maestro.deselectAll();
			modificaForm.setVisible(false);
			ctForm.setVariable(new Tarea("",""));
		});

		ctForm = new CrearTareaForm(this);

		ejecutar = new Button("Ejecutar");
		ejecutar.setVisible(false);
		ejecutar.addClickListener( e -> {
			MySub sub = new MySub(tarea);
			UI.getCurrent().addWindow(sub);
		});
		
		ejecuciones = new Button("Ejecuciones");
		ejecuciones.setVisible(false);
		ejecuciones.addClickListener( e -> {
			VentanaEjecuciones vent = new VentanaEjecuciones(tarea);
			UI.getCurrent().addWindow(vent);
		});

		cargaGridTareas();

		losbotones.addComponents(nuevaTarea,borrar, ejecutar, ejecuciones);
		layoutV1.addComponents(filtrado, l, maestro,losbotones, modificaForm);
		layoutV1.addComponent(ctForm);
		setMargin(true);
		setSpacing(true);
		addComponents(layoutV1);

	}

	public VerticalLayout getLayoutH2() {
		return layoutH2;
	}

	public void setLayoutH2(VerticalLayout layoutH2) {
		this.layoutH2 = layoutH2;
	}

	public void addTarea(Tarea tarea) {
		removeComponent(layoutH2);
		lvVariable = new LayoutVistaVariables(tarea);
		layoutH2 = lvVariable;
		cargaGridTareas();
		addComponent(layoutH2);
	}
	public void cargaGridTareas() {
		listatareas = tareaService.getTareas();

		maestro.setContainerDataSource(
				new BeanItemContainer<>(Tarea.class, listatareas)
				);
	}
	public void cargaGrid() {
		lvVariable.cargaGrid();

	}
	public void borrarTarea(long tareaId){
		tareaService.borrarTarea(tareaId);
	}

	public void modificarTareaNom(Tarea tarea, String cod){
		tareaService.modificarTareaNom(tarea.getId(), cod);
	}

	public void modificarTareaDesc(Tarea tarea, String desc){
		tareaService.modificarTareaDesc(tarea.getId(), desc);
	}

	public boolean isDisponible(String codTarea){
		return tareaService.verificarNombre(codTarea);
	}
	
	public void seleccionar(){
		maestro.select(listatareas.get(listatareas.size()-1));
	}
	
	public void seleccionarTarea(){
		maestro.select(tarea2);	
	}


	public void borrarTarea(Tarea t) {
		borrarTarea(t.getId());
		borrar.setVisible(false);
		modificaForm.setVisible(false);
		cargaGridTareas();
		layoutH2.removeAllComponents();
		Notification.show("Tarea borrada.");
	}
}
