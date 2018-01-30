package es.cic.curso.grupo2.ejercicio027.ui;

import java.util.ArrayList;

import java.util.List;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import es.cic.curso.grupo2.ejercicio027.modelo.Campo;
import es.cic.curso.grupo2.ejercicio027.modelo.Plantilla;
import es.cic.curso.grupo2.ejercicio027.servicio.ServicioCampo;
import es.cic.curso.grupo2.ejercicio027.servicio.ServicioPlantilla;

public class VistaPlantilla extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

//	private Demo demo;
	private ServicioCampo campoService;
	private ServicioPlantilla plantillaService;
	protected TextField nombreCampo;
	protected ComboBox tipoCampo;
	private Button nuevo;
	private Button actualizaCampo;
	
	private Grid gridPlantillas;
	private Grid gridCampos;

	List<Campo> listaCampos = new ArrayList<>();
	List<Plantilla> listaPlantillas = new ArrayList<>();
	private Campo campo;
	private Plantilla plantilla;

	@PropertyId("nombrePlantilla")
	protected TextField nombrePlantilla;

	private Button agregaCampo;
	private Button eliminaCampo;
	private Label generaCamposLabel;
	private Button cancelarAgregarCampo;
	private Button ejecutar;

	private TextField filtrarPlantillas = new TextField();


	public VistaPlantilla(Navigator navegador, ServicioCampo campoService, ServicioPlantilla plantillaService) {
		
		this.plantillaService = plantillaService;
		this.campoService = campoService;
		MenuBar menuNavegacion = new MenuBar();
		menuNavegacion.setWidth(100.0F, Unit.PERCENTAGE);
		menuNavegacion.setHeight(100.0F, Unit.PERCENTAGE);
		MenuItem menuItemVistaPlantilla = menuNavegacion.addItem("Gestor Plantillas", null);
		menuItemVistaPlantilla.setEnabled(false);

		addComponent(menuNavegacion);
				
		filtrarPlantillas.setInputPrompt("Filtrar plantillas...");
		filtrarPlantillas.addTextChangeListener(e->{
			gridPlantillas.setContainerDataSource(new BeanItemContainer<>(Plantilla.class,  
				plantillaService.findAll(e.getText())));

		});
		
		Button clearFilterBtn=new Button(FontAwesome.TIMES);
		clearFilterBtn.setDescription("Limpiar filtro");
		clearFilterBtn.addClickListener(e->{
			filtrarPlantillas.clear();
			listaPlantillas = plantillaService.listaPlantillas();
			gridPlantillas.setContainerDataSource(new BeanItemContainer<>(Plantilla.class, listaPlantillas));			
		});
		
		CssLayout filtrado = new CssLayout();
		filtrado.addComponents(filtrarPlantillas,clearFilterBtn);
		filtrado.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		nombrePlantilla = new TextField("Nombre de la nueva plantilla: ");
		nombrePlantilla.setInputPrompt("Nombre");

		VerticalLayout contentLayout = new VerticalLayout();
		VerticalLayout v1 = new VerticalLayout();
		HorizontalLayout toolbar1 = new HorizontalLayout();
		VerticalLayout v2 = new VerticalLayout();
		VerticalLayout v3 = new VerticalLayout();
		VerticalLayout v4 = new VerticalLayout();
		v1.setSpacing(true);
		v1.setSpacing(true);
		contentLayout.setMargin(true);
		contentLayout.setSpacing(true);
		toolbar1.setSpacing(true);
		v2.setMargin(true);
		v2.setSpacing(true);
		v3.setMargin(true);
		v3.setSpacing(true);
		v4.setMargin(true);
		v4.setSpacing(true);

		v2.setWidth("50%");
		
		
		toolbar1.addComponent(filtrado);
		
		generaCamposLabel = new Label("Generar nuevo campo");
		generaCamposLabel.setVisible(false);
		
		ejecutar = new Button("Ejecutar");
		ejecutar.setWidth("230px");
		ejecutar.setIcon(FontAwesome.PLAY_CIRCLE_O);
		ejecutar.setVisible(false);

		// Agregar Plantilla:
		Label insertaPlantillaLabel = new Label(
				"Pulse para añadir una plantilla");
		insertaPlantillaLabel.setWidth("230px");
		Label agrearNuevaPlantillaLabel = new Label("Agregar nueva plantilla");
		agrearNuevaPlantillaLabel.setVisible(false);
		Button agregaPlantilla = new Button("Agregar plantilla");
		agregaPlantilla.setIcon(FontAwesome.FILE);
		agregaPlantilla.setWidth("230px");
		nombrePlantilla.setWidth("230px");
		nombrePlantilla.setVisible(false);


		Button insertarNuevaPlantilla = new Button("Añadir Plantilla");
		insertarNuevaPlantilla.setIcon(FontAwesome.PLUS);
		insertarNuevaPlantilla.setVisible(false);
		insertarNuevaPlantilla.setWidth("230px");

		Button cancelar = new Button("Cancelar");
		cancelar.setIcon(FontAwesome.CLOSE);
		cancelar.setVisible(false);
		cancelar.setWidth("230px");

		Button editarPlantilla = new Button("Editar Plantilla");
		editarPlantilla.setIcon(FontAwesome.REFRESH);
		editarPlantilla.setVisible(false);
		editarPlantilla.setWidth("230px");

		Button borrarPlantilla = new Button("Eliminar Plantilla");
		borrarPlantilla.setIcon(FontAwesome.TRASH_O);
		borrarPlantilla.setVisible(false);
		borrarPlantilla.setWidth("230px");

		insertarNuevaPlantilla.addClickListener(e -> {
			Plantilla plantilla = new Plantilla();
			plantilla.setNombrePlantilla(nombrePlantilla.getValue().toString());

			String nombrePlantillaString = plantilla.getNombrePlantilla().trim();
			if (nombrePlantillaString.isEmpty()) {
				Notification.show("Introduzca un nombre correcto para la plantilla");
			} else {
				plantillaService.aniadirPlantilla(plantilla);
				Notification.show("Nueva plantilla añadida");
				listaPlantillas = plantillaService.listaPlantillas();
				gridPlantillas.setContainerDataSource(new BeanItemContainer<>(Plantilla.class, listaPlantillas));
			}

			nombrePlantilla.setValue("");
			nombrePlantilla.setVisible(false);
			insertarNuevaPlantilla.setVisible(false);
			agregaPlantilla.setVisible(true);
			cancelar.setVisible(false);
			insertaPlantillaLabel.setVisible(true);
			agrearNuevaPlantillaLabel.setVisible(false);
		});

		cancelar.addClickListener(e -> {
			nombrePlantilla.setVisible(false);
			insertarNuevaPlantilla.setVisible(false);
			agregaPlantilla.setVisible(true);
			insertaPlantillaLabel.setVisible(true);
			cancelar.setVisible(false);
			nombrePlantilla.setValue("");
			nombrePlantilla.setInputPrompt("Nombre");
			agrearNuevaPlantillaLabel.setVisible(false);
			editarPlantilla.setVisible(false);
			borrarPlantilla.setVisible(false);
		});

		editarPlantilla.addClickListener(e -> {
			plantilla.setNombrePlantilla(nombrePlantilla.getValue().toString());
			nombrePlantilla.setVisible(false);
			cancelar.setVisible(false);
			agregaPlantilla.setVisible(true);
			insertaPlantillaLabel.setVisible(true);

			if (nombrePlantilla.isEmpty()) {
				Notification.show("Debes rellenar el nuevo nombre de la plantilla correctamente.");
			} else {
				plantillaService.actualizaPlantilla(plantilla);
				listaPlantillas = plantillaService.listaPlantillas();
				gridPlantillas.setContainerDataSource(new BeanItemContainer<>(Plantilla.class, listaPlantillas));
			}
		});

		borrarPlantilla.addClickListener(e -> {
			VentanaBorradoPlantilla window = new VentanaBorradoPlantilla
					(this, plantilla.getId(), plantillaService, gridCampos, gridPlantillas);
			
			MyUI.getCurrent().addWindow(window);
		});

		// Grid Plantilla

		gridPlantillas = new Grid("Lista de plantillas");
		gridPlantillas.setWidth("150%");
		gridPlantillas.setColumns("nombrePlantilla");
		gridPlantillas.setSelectionMode(SelectionMode.SINGLE);

		agregaPlantilla.addClickListener(e -> {
			nombrePlantilla.setVisible(true);
			nombrePlantilla.setCaption("Nombre de la nueva plantilla: ");
			nombrePlantilla.setValue("");
			insertarNuevaPlantilla.setVisible(true);
			agregaPlantilla.setVisible(false);
			cancelar.setVisible(true);
			insertaPlantillaLabel.setVisible(false);
			agrearNuevaPlantillaLabel.setVisible(true);
		});

		gridPlantillas.addSelectionListener(e -> {
			
			if (!e.getSelected().isEmpty()) {
				Plantilla plantillaSeleccionada = null;
				plantillaSeleccionada = (Plantilla) e.getSelected().iterator().next();
				plantilla = plantillaSeleccionada;
				editarPlantilla.setVisible(true);
				nombrePlantilla.setVisible(true);
				nombrePlantilla.setCaption("Nombre de plantilla");
				nombrePlantilla.setValue(plantilla.getNombrePlantilla());
				cancelar.setVisible(true);
				borrarPlantilla.setVisible(true);
				agrearNuevaPlantillaLabel.setVisible(false);
				agregaPlantilla.setVisible(false);
				gridCampos.setVisible(true);
				insertaPlantillaLabel.setVisible(false);
				gridCampos.setCaption("Campos de la plantilla: " + plantilla.getNombrePlantilla());
				listaCampos = campoService.listaCamposPlantilla(plantilla.getId());
				gridCampos.setContainerDataSource(new BeanItemContainer<>(Campo.class, listaCampos));
				insertarNuevaPlantilla.setVisible(false);
		
				if(!listaCampos.isEmpty()){
					ejecutar.setVisible(true);
				}else{
					ejecutar.setVisible(false);
				}
				
				agregaCampo.setVisible(true);
			} else {
				editarPlantilla.setVisible(false);
				nombrePlantilla.setVisible(false);
				borrarPlantilla.setVisible(false);
				gridCampos.setVisible(false);
				agregaCampo.setVisible(false);
				agregaPlantilla.setVisible(true);
				insertaPlantillaLabel.setVisible(true);
				cancelar.setVisible(false);
				
				eliminaCampo.setVisible(false);
				actualizaCampo.setVisible(false);
				nombreCampo.setVisible(false);
				tipoCampo.setVisible(false);
				cancelarAgregarCampo.setVisible(false);
				generaCamposLabel.setVisible(false);
				nuevo.setVisible(false);
				generaCamposLabel.setVisible(false);
				ejecutar.setVisible(false);
			}
		});
		
		cargarDemo(gridPlantillas);

		// Grid campo
		gridCampos = new Grid();

		gridCampos.setColumns("nombreCampo", "tipoCampo");
		gridCampos.setSizeFull();
		gridCampos.setSelectionMode(SelectionMode.SINGLE);
		gridCampos.setVisible(false);

		agregaCampo = new Button("Agregar campo");
		agregaCampo.setIcon(FontAwesome.FILE_O);
		agregaCampo.setWidth("230px");
		agregaCampo.setResponsive(true);
		eliminaCampo = new Button("Eliminar campo");
		eliminaCampo.setIcon(FontAwesome.TRASH);
		eliminaCampo.setWidth("230px");
		actualizaCampo = new Button("Editar Campo");
		actualizaCampo.setIcon(FontAwesome.REFRESH);
		actualizaCampo.setWidth("230px");
		nombreCampo = new TextField("Nombre del campo: ");
		nombreCampo.setWidth("230px");
		tipoCampo = new ComboBox("Tipo de Campo");
		tipoCampo.setWidth("230px");
		nuevo = new Button("Añadir campo");
		nuevo.setIcon(FontAwesome.PLUS);
		nuevo.setWidth("230px");
		cancelarAgregarCampo = new Button("Cancelar");
		cancelarAgregarCampo.setWidth("230px");
		cancelarAgregarCampo.setIcon(FontAwesome.CLOSE);


		tipoCampo.setVisible(false);
		nombreCampo.setVisible(false);
		generaCamposLabel.setVisible(false);
		eliminaCampo.setVisible(false);
		actualizaCampo.setVisible(false);
		nombreCampo.setVisible(false);
		tipoCampo.setVisible(false);
		nuevo.setVisible(false);
		cancelarAgregarCampo.setVisible(false);
		agregaCampo.setVisible(false);

		gridCampos.addSelectionListener(e -> {

			nombreCampo.getValue();
			tipoCampo.getValue();
			actualizaCampo.setVisible(true);

			if (!e.getSelected().isEmpty()) {
				Campo campoSeleccionado = null;
				campoSeleccionado = (Campo) e.getSelected().iterator().next();
				campo = campoSeleccionado;
				eliminaCampo.setVisible(true);
				nombreCampo.setVisible(true);
				nombreCampo.setValue(campo.getNombreCampo());
				tipoCampo.setVisible(true);
				tipoCampo.setValue(campo.getTipoCampo());
				cancelarAgregarCampo.setVisible(true);
				agregaCampo.setVisible(false);
				nuevo.setVisible(false);

			} else {
				eliminaCampo.setVisible(false);
				actualizaCampo.setVisible(false);
				agregaCampo.setVisible(true);
				nombreCampo.setVisible(false);
				tipoCampo.setVisible(false);
				cancelarAgregarCampo.setVisible(false);
				generaCamposLabel.setVisible(false);
			}

		});

		tipoCampo.addItem("Numérico");
		tipoCampo.addItem("Texto");

		tipoCampo.setNullSelectionAllowed(false);
		tipoCampo.setImmediate(true);


		tipoCampo.addItem("Numérico");
		tipoCampo.addItem("Texto");

		tipoCampo.setNullSelectionAllowed(false);
		tipoCampo.setImmediate(true);

		// Generar formulario para agregar el campo
		agregaCampo.addClickListener(e -> {
			
			agregaCampo.setVisible(false);
			nombreCampo.setVisible(true);
			tipoCampo.setVisible(true);
			nuevo.setVisible(true);
			cancelarAgregarCampo.setVisible(true);
			generaCamposLabel.setVisible(true);
			eliminaCampo.setVisible(false);
			nombreCampo.setValue(" ");
			tipoCampo.clear();
		});

		// Agregar nuevo campo
		nuevo.addClickListener(e -> {

			Campo campo = new Campo();
			if (tipoCampo.getValue() == null) {
				Notification.show("Debes introducir el tipo de campo");
			} else if (nombreCampo.getValue()==(" ")) {
				Notification.show("Debes introducir el nombre de campo");
			} else {
				campo.setNombreCampo(nombreCampo.getValue());
				campo.setTipoCampo(tipoCampo.getValue().toString());
				if (tipoCampo.getInputPrompt() == "error") {
					Notification.show("Debes rellenar el nombre del campo correctamente.");
				} else {
					aniadirGridCampos(plantilla, gridCampos, campo);
					Notification.show("Has añadido un campo correctamente");
					cargarGridCampos(gridCampos, plantilla);
				}
			}
			agregaCampo.setVisible(true);
			nuevo.setVisible(false);
			tipoCampo.setVisible(false);
			cancelarAgregarCampo.setVisible(false);
			nombreCampo.setVisible(false);
			generaCamposLabel.setVisible(false);

		});

		// Botón actualizar Campos

		actualizaCampo.addClickListener(e -> {

			campo.setNombreCampo(nombreCampo.getValue().toString());
			campo.setTipoCampo(tipoCampo.getValue().toString());

			if (tipoCampo.getValue() == null) {
				Notification.show("Debes introducir un tipo de campo.");
			} else {
				campo.setTipoCampo(tipoCampo.getValue().toString());
				String nombreCampoString = campo.getNombreCampo().trim();

				if (tipoCampo.getInputPrompt() == "error" || nombreCampoString.isEmpty()) {
					Notification.show("Debes rellenar el nombre del campo correctamente.");

				} else {

					actualizarCampos(gridCampos, campo, plantilla);

					Notification.show("Campo modificado correctamente");
					cargarGridCampos(gridCampos, plantilla);

				}
			}

			tipoCampo.setVisible(false);
			nombreCampo.setVisible(false);
			agregaCampo.setVisible(true);


		});

		// Cancelar campo
		cancelarAgregarCampo.addClickListener(e -> {
			agregaCampo.setVisible(true);
			nombreCampo.setVisible(false);
			tipoCampo.setVisible(false);
			nuevo.setVisible(false);
			cancelarAgregarCampo.setVisible(false);
			generaCamposLabel.setVisible(false);
			nombreCampo.setValue("");
			eliminaCampo.setVisible(false);

			actualizaCampo.setVisible(false);
		});

		// Botón eliminar Campo

		eliminaCampo.addClickListener(f -> {
			VentanaBorradoCampo window = new VentanaBorradoCampo(listaCampos, campo, gridCampos, campoService, ejecutar, this);
			
			MyUI.getCurrent().addWindow(window);
			
			/*
			listaCampos.remove(campo);
			gridCampos.setContainerDataSource(new BeanItemContainer<>(Campo.class, listaCampos));
			Long campoId = campo.getId();
			campoService.borrarCampo(campoId);
			
			
			
			if(!listaCampos.isEmpty()){
				ejecutar.setVisible(true);
			}else{
				ejecutar.setVisible(false);
			}
*/

			nombreCampo.setVisible(true);
			tipoCampo.setVisible(true);
			agregaCampo.setVisible(false);
		});
		
		ejecutar.addClickListener(e -> {
			VentanaContenidoCampos window = new VentanaContenidoCampos(listaCampos);
			
			MyUI.getCurrent().addWindow(window);
		});
		

		v1.addComponents(insertaPlantillaLabel, agrearNuevaPlantillaLabel, agregaPlantilla, nombrePlantilla,
				insertarNuevaPlantilla, cancelar, editarPlantilla, borrarPlantilla, ejecutar);
		v2.addComponents(filtrado, gridPlantillas);
		v3.addComponent(gridCampos);
		v4.addComponents(generaCamposLabel, nombreCampo, tipoCampo, nuevo, cancelarAgregarCampo,
				actualizaCampo, eliminaCampo, agregaCampo);
		toolbar1.addComponents(v1, v2, v3, v4);
		toolbar1.setComponentAlignment(v3, Alignment.BOTTOM_CENTER);
		contentLayout.addComponent(toolbar1);

		addComponent(contentLayout);
	}

	public void cargarGridCampos(Grid gridCampos, Plantilla plantilla) {

		listaCampos = campoService.listaCamposPlantilla(plantilla.getId());
		gridCampos.setContainerDataSource(new BeanItemContainer<>(Campo.class, listaCampos));
		
		if(!listaCampos.isEmpty()){
			ejecutar.setVisible(true);
		}else{
			ejecutar.setVisible(false);
		}
	}

	public void aniadirGridCampos(Plantilla plantilla, Grid gridCampos, Campo campo) {

		Long idPlantilla = plantilla.getId();
		campoService.anadirCampo(idPlantilla, campo);
		cargarGridCampos(gridCampos, plantilla);

	}

	public void actualizarCampos(Grid gridCampos, Campo campo, Plantilla plantilla) {

		campoService.actualizarCampo(campo);
		cargarGridCampos(gridCampos, plantilla);
	}

	public void limpiarCampos() {
		campoService.listarCampos().clear();
	}
	

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
	public void actualizarDatos(Grid gridCampos, Grid gridPlantilla){
		listaPlantillas = plantillaService.listaPlantillas();
		gridPlantillas.setContainerDataSource(new BeanItemContainer<>(Plantilla.class, listaPlantillas));
		listaCampos = campoService.listaCamposPlantilla(plantilla.getId());
		gridCampos.setContainerDataSource(new BeanItemContainer<>(Campo.class, listaCampos));	
	}
	
	public String conjuntoCampos(String todosCampos){
		return todosCampos;
	}
	
	private void cargarDemo(Grid gridPlantilla){
		Campo nombre = new Campo("Nombre", "Texto");
		Campo edad =new Campo("Edad", "Numérico");
		Campo apellido =new Campo("Apellido", "Texto");;
		Campo ciudad=new Campo("Ciudad", "Texto");
		Campo pais=new Campo("País", "Texto");
		Campo telf =new Campo("Teléfono", "Numérico");
		
		Plantilla ubicacion = new Plantilla("Ubicacion");
		Plantilla cliente = new Plantilla("Clientes");
		
		telf.setPlantilla(cliente);
		nombre.setPlantilla(cliente);
		edad.setPlantilla(cliente);
		apellido.setPlantilla(cliente);
		
		pais.setPlantilla(ubicacion);
		ciudad.setPlantilla(ubicacion);
		

		plantillaService.aniadirPlantilla(cliente);
		plantillaService.aniadirPlantilla(ubicacion);
		
		Long idCliente = cliente.getId();
		Long idUbicacion = ubicacion.getId();
		
		campoService.anadirCampo(idCliente, nombre);
		campoService.anadirCampo(idCliente, apellido);
		campoService.anadirCampo(idCliente, edad);
		campoService.anadirCampo(idCliente, telf);


		campoService.anadirCampo(idUbicacion, pais);
		campoService.anadirCampo(idUbicacion, ciudad);		
		
		listaPlantillas = plantillaService.listaPlantillas();
		gridPlantillas.setContainerDataSource(new BeanItemContainer<>(Plantilla.class, listaPlantillas));
		
	}

}