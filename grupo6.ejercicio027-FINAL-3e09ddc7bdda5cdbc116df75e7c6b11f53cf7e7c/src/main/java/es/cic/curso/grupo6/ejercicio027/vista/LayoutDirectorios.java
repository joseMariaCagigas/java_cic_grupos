package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.Collection;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.SelectionMode;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorFicheros;

public class LayoutDirectorios extends VerticalLayout {
	private static final long serialVersionUID = -514197825792558255L;

	/** Lógica de negocio con acceso a BB.DD. */
	private ServicioGestorFicheros servicioGestorFicheros;

	/** Componente de tipo grid. */
	private Grid gridDirectorios;

	/** Referencia al directorio seleccionado en el grid. */
	private Directorio directorioSeleccionado;

	/** Botón para la acción: añadir directorio. */
	private Button botonAgregar;
	
	/** Botón para la acción: borrar directorio. */
	private Button botonBorrar;
	
	/** Botón para la acción: renombrar directorio. */
	private Button botonRenombrar;
	
	/** Botón para la acción: cancelar selección del grid. */
	private Button botonCancelar;

	@PropertyId("ruta")
	private TextField textFieldRutaDirectorio;

	public LayoutDirectorios(VistaDocumentos padre, ServicioGestorFicheros servicioGestorFicheros) {
		this.servicioGestorFicheros = servicioGestorFicheros;

		Label titulo = new Label("Directorios:");
		titulo.setContentMode(ContentMode.HTML);

		// GRID DIRECTORIOS
		gridDirectorios = new Grid();
		gridDirectorios.setColumns("ruta");
		gridDirectorios.setSizeFull();
		gridDirectorios.setSelectionMode(SelectionMode.SINGLE);
		gridDirectorios.addSelectionListener(e -> {
			directorioSeleccionado = null;
			if (!e.getSelected().isEmpty()) {
				directorioSeleccionado = (Directorio) e.getSelected().iterator().next();
			}
			muestraBotonesParaSeleccion(directorioSeleccionado);
			padre.activaGridFicheros(directorioSeleccionado);
		});

		// TEXTFIELD RUTA DIRECTORIO
		textFieldRutaDirectorio = new TextField();
		textFieldRutaDirectorio.setWidth(100.0F, Unit.PERCENTAGE);
		textFieldRutaDirectorio.setCaption("Nombre:");
		textFieldRutaDirectorio.setInputPrompt("Nombre del directorio");

		// BUTTON AGREGAR DIRECTORIO
		botonAgregar = new Button("Añadir Directorio");
		botonAgregar.setWidth(100.0F, Unit.PERCENTAGE);
		botonAgregar.setIcon(FontAwesome.PLUS);
		botonAgregar.setVisible(true);
		botonAgregar.setEnabled(true);
		botonAgregar.addClickListener(e -> {
			if (textFieldRutaDirectorio.getValue().equals("")) {
				Notification.show("Introduce un nombre válido");
			} else {
				Directorio nuevoDirectorio = new Directorio();
				nuevoDirectorio.setRuta(textFieldRutaDirectorio.getValue());
				servicioGestorFicheros.agregaDirectorio(nuevoDirectorio);
				textFieldRutaDirectorio.clear();
				cargaGridDirectorios();
				Notification.show("Directorio \"" + nuevoDirectorio.getRuta() + "\" añadido con éxito.");
			}
		});

		// BUTTON RENOMBRAR DIRECTORIO
		botonRenombrar = new Button("Renombrar");
		botonRenombrar.setWidth(100.0F, Unit.PERCENTAGE);
		botonRenombrar.setIcon(FontAwesome.REFRESH);
		botonRenombrar.setVisible(false);
		botonRenombrar.setEnabled(true);
		botonRenombrar.addClickListener(e -> {
			if (!textFieldRutaDirectorio.isEnabled()) {
				Window ventana = UtilidadesVista.creaVentanaModal(
						"No se puede renombrar un directorio mientras tenga ficheros colgando de él.");
				this.getUI().getUI().addWindow(ventana);
			} else if (textFieldRutaDirectorio.getValue() == "") {
				Notification.show("Introduce una ruta válida");
			} else {
				Directorio renombrado = new Directorio();
				renombrado.setRuta(textFieldRutaDirectorio.getValue());
				servicioGestorFicheros.modificaDirectorio(directorioSeleccionado.getId(), renombrado);
				textFieldRutaDirectorio.clear();
				cargaGridDirectorios();
				Notification.show("Directorio renombrado como \"" + renombrado.getRuta() + "\".");
			}
		});

		// BUTTON BORRAR DIRECTORIO
		botonBorrar = new Button("Borrar");
		botonBorrar.setWidth(100.0F, Unit.PERCENTAGE);
		botonBorrar.setIcon(FontAwesome.ERASER);
		botonBorrar.setVisible(false);
		botonBorrar.addClickListener(
				e -> this.getUI().getUI().addWindow(creaVentanaConfirmacionBorrado(directorioSeleccionado.getRuta())));

		botonCancelar = new Button("Cancelar");
		botonCancelar.setWidth(100.0F, Unit.PERCENTAGE);
		botonCancelar.setVisible(false);
		botonCancelar.addClickListener(e -> cargaGridDirectorios());

		// LAYOUT BOTONES DIRECTORIOS
		FormLayout layoutBotonesDirectorios = new FormLayout();
		layoutBotonesDirectorios.setWidth(280.0F, Unit.PIXELS);
		layoutBotonesDirectorios.setMargin(false);
		layoutBotonesDirectorios.setSpacing(true);
		layoutBotonesDirectorios.setResponsive(true);
		layoutBotonesDirectorios.addComponents(textFieldRutaDirectorio, botonAgregar, botonRenombrar, botonBorrar,
				botonCancelar);

		this.setMargin(new MarginInfo(false, true, true, true));
		this.setSpacing(true);
		this.addComponents(titulo, gridDirectorios, layoutBotonesDirectorios);
	}

	public Directorio obtenDirectorioSeleccionado() {
		return directorioSeleccionado;
	}

	public void modificaDirectorioSeleccionado(Directorio directorioSeleccionado) {
		this.directorioSeleccionado = directorioSeleccionado;
		actualizaTextFieldRutaDirectorio();
	}

	public void cargaGridDirectorios() {
		Collection<Directorio> directorios = servicioGestorFicheros.listaDirectorios();
		gridDirectorios.setContainerDataSource(new BeanItemContainer<>(Directorio.class, directorios));
	}
	
	private void actualizaTextFieldRutaDirectorio() {
		boolean esHoja = servicioGestorFicheros.esHoja(directorioSeleccionado.getId());
		textFieldRutaDirectorio.setEnabled(esHoja);
	}

	public void bloqueaTextFieldRutaDirectorio() {
		textFieldRutaDirectorio.setEnabled(false);
	}
	
	private void muestraBotonesParaSeleccion(Directorio directorio) {		
		if (directorio != null) {
			textFieldRutaDirectorio.setValue(directorio.getRuta());
			botonAgregar.setVisible(false);
			botonRenombrar.setVisible(true);
			botonBorrar.setVisible(true);
			botonCancelar.setVisible(true);
			boolean esHoja = servicioGestorFicheros.esHoja(directorio.getId());
			textFieldRutaDirectorio.setEnabled(esHoja);
		} else {
			botonAgregar.setVisible(true);
			botonRenombrar.setVisible(false);
			botonBorrar.setVisible(false);
			botonCancelar.setVisible(false);
			textFieldRutaDirectorio.clear();
			textFieldRutaDirectorio.setEnabled(true);
		}
	}

	private Window creaVentanaConfirmacionBorrado(String directorioRuta) {
		Window resultado = new Window();
		resultado.setWidth(350.0F, Unit.PIXELS);
		resultado.setModal(true);
		resultado.setClosable(false);
		resultado.setResizable(false);
		resultado.setDraggable(false);

		Label label = new Label("¿Está seguro de que desea borrar el directorio: <strong>\"" + directorioRuta
				+ "\"</strong>, junto con todos sus ficheros?");
		label.setContentMode(ContentMode.HTML);

		Button botonAceptar = new Button("Aceptar");
		botonAceptar.addClickListener(e -> {
			servicioGestorFicheros.eliminaDirectorio(directorioSeleccionado.getId());
			textFieldRutaDirectorio.clear();
			cargaGridDirectorios();
			resultado.close();
		});

		Button botonCancelar = new Button("Cancelar");
		botonCancelar.addClickListener(e -> resultado.close());

		HorizontalLayout layoutBotones = new HorizontalLayout();
		layoutBotones.setMargin(true);
		layoutBotones.setSpacing(true);
		layoutBotones.setWidth(100.0F, Unit.PERCENTAGE);
		layoutBotones.addComponents(botonAceptar, botonCancelar);

		final FormLayout content = new FormLayout();
		content.setMargin(true);
		content.addComponents(label, layoutBotones);
		resultado.setContent(content);
		resultado.center();
		return resultado;
	}

}
