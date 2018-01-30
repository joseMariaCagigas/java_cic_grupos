package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorFicheros;

public class LayoutFicheros extends VerticalLayout implements Component{
	private static final long serialVersionUID = -7013768321773232310L;
	
	@PropertyId("ruta")
	protected TextField ruta;	
	@PropertyId("nombre")
	protected TextField nombre;
	@PropertyId("descripcion")
	protected TextField descripcion;
	@PropertyId("version")
	protected TextField version;
	
	private Button aceptar;
	private Button cancelar;
	private Fichero fichero;

	/** Referencia a la vista padre de la que cuelga el layout. */
	private VistaDocumentos padre;

	/** Referencia al directorio del que se listan sus contenidos. */
	private Directorio directorioActual;

	/** Lógica de negocio con acceso a BB.DD. */
	private ServicioGestorFicheros servicioGestorFicheros;

	/** Referencia al fichero seleccionado en el grid. */
	private Fichero ficheroSeleccionado;

	/** Grid de ficheros. */
	private Grid gridFicheros;

	/** Botón para la acción: añadir fichero. */
	private Button botonAgregarFichero;

	/** Botón para la acción: borrar fichero. */
	private Button botonBorrarFichero;

	/** Botón para la acción: actualizar fichero. */
	private Button botonActualizarFichero;
	
	VerticalLayout verticalPrincipal = new VerticalLayout();

	public LayoutFicheros(VistaDocumentos padre, ServicioGestorFicheros servicioGestorFicheros) {
		this.padre = padre;
		this.servicioGestorFicheros = servicioGestorFicheros;
		this.directorioActual = null;

		Label titulo = new Label("Ficheros:");
		titulo.setContentMode(ContentMode.HTML);

		// GRID FICHEROs
		gridFicheros = new Grid();

		cargarGrid();

		gridFicheros.setColumns("nombre", "descripcion", "version");
		gridFicheros.setSizeFull();
		gridFicheros.setSelectionMode(SelectionMode.SINGLE);
		gridFicheros.addSelectionListener(e -> {
			ficheroSeleccionado = null;
			if (!e.getSelected().isEmpty()) {
				ficheroSeleccionado = (Fichero) e.getSelected().iterator().next();
				botonAgregarFichero.setVisible(false);
				botonBorrarFichero.setVisible(true);
				botonActualizarFichero.setVisible(true);
				verticalPrincipal.setVisible(false);
			} else {
				botonBorrarFichero.setVisible(false);
				botonActualizarFichero.setVisible(false);
				botonAgregarFichero.setVisible(true);
			}
		});

		// BUTTON AGREGAR FICHERO
		botonAgregarFichero = new Button("Añadir fichero");
		botonAgregarFichero.setVisible(false);
		botonAgregarFichero.setEnabled(true);
		botonAgregarFichero.addClickListener(e -> {
			ficheroSeleccionado = new Fichero();
			ficheroSeleccionado.setId((long) 0);
			editarFichero();
			botonAgregarFichero.setVisible(false);
			botonBorrarFichero.setVisible(false);
			botonActualizarFichero.setVisible(false);
			padre.actualizaDirectorioActual(directorioActual.getId());
			padre.bloqueaTextFieldRutaDirectorio();
		});

		// BUTTON BORRAR FICHERO
		botonBorrarFichero = new Button("Borrar");
		botonBorrarFichero.setIcon(FontAwesome.ERASER);
		botonBorrarFichero.setEnabled(true);
		botonBorrarFichero.setVisible(false);
		botonBorrarFichero.addClickListener(e -> this.getUI().getUI()
				.addWindow(creaVentanaConfirmacionBorradoFicheros(ficheroSeleccionado.getNombre())));

		// BUTTON ACTUALIZAR FICHERO
		botonActualizarFichero = new Button("Actualizar fichero");
		botonActualizarFichero.setIcon(FontAwesome.REFRESH);
		botonActualizarFichero.setVisible(false);
		botonActualizarFichero.setEnabled(true);
		botonActualizarFichero.addClickListener(e -> {
			editarFichero();
			nombre.setValue(ficheroSeleccionado.getNombre());
			descripcion.setValue(ficheroSeleccionado.getDescripcion());
			version.setValue(ficheroSeleccionado.getVersion().toString());
			botonAgregarFichero.setVisible(false);
			botonBorrarFichero.setVisible(false);
			botonActualizarFichero.setVisible(false);
		});

		HorizontalLayout layoutBotonesFicheros = new HorizontalLayout();
		layoutBotonesFicheros.setMargin(false);
		layoutBotonesFicheros.setSpacing(true);

		layoutBotonesFicheros.addComponents(botonAgregarFichero, botonActualizarFichero, botonBorrarFichero);

		this.setMargin(new MarginInfo(false, true, false, true));
		this.setSpacing(true);
		this.addComponents(titulo, gridFicheros, layoutBotonesFicheros);
	}
	
	private void editarFichero(){
		verticalPrincipal= new VerticalLayout();
		verticalPrincipal.setMargin(false);
		verticalPrincipal.setSpacing(true);
		
		HorizontalLayout datosLayout = new HorizontalLayout();
		datosLayout.setSpacing(true);
		nombre = new TextField("Nombre: ");
		nombre.setRequired(true);
		nombre.setInputPrompt("Nombre");
		descripcion = new TextField("Descripción: ");
		descripcion.setInputPrompt("Descripción");
		version = new TextField("Versión: ");
		version.setInputPrompt("Versión");

		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.setSpacing(true);
		aceptar = new Button("Aceptar");
		aceptar.setIcon(FontAwesome.SAVE);
		aceptar.addClickListener(e -> {
			double v = 1.0;
			if ("".equals(nombre.getValue())) {
				Notification.show("Debes indicar un nombre para crear un archivo.");
			}else{
				if(!"".equals(version.getValue())) {
					 v = Double.parseDouble(version.getValue());
				}
				fichero = new Fichero(directorioActual, nombre.getValue(), descripcion.getValue(), v);
				if(ficheroSeleccionado.getId() > 0){
					servicioGestorFicheros.modificaFichero(ficheroSeleccionado.getId(), fichero);
				}else{
					servicioGestorFicheros.agregaFichero(directorioActual.getId(), fichero);
				}
				cargarGrid();
				verticalPrincipal.setVisible(false);
				botonAgregarFichero.setVisible(true);
				Notification.show("Fichero \"" + fichero.getNombre() + "\" añadido con éxito.");
			}
		});
		cancelar = new Button("Cancelar");
		cancelar.setIcon(FontAwesome.CLOSE);
		cancelar.addClickListener(e-> {
			verticalPrincipal.setVisible(false);
			botonAgregarFichero.setVisible(true);
			
		});
		
		datosLayout.addComponents(nombre, descripcion, version);
		buttonsLayout.addComponents(aceptar, cancelar);
		verticalPrincipal.addComponents(datosLayout, buttonsLayout);
		addComponents(verticalPrincipal);
	}

	private void cargarGrid(){
	
		gridFicheros.setColumns("nombre", "descripcion", "version");
		gridFicheros.setSizeFull();
		gridFicheros.setSelectionMode(SelectionMode.SINGLE);
		gridFicheros.addSelectionListener(e -> {
			ficheroSeleccionado = null;
			if (!e.getSelected().isEmpty()) {
					ficheroSeleccionado = (Fichero) e.getSelected().iterator().next();
					botonAgregarFichero.setVisible(false);
					botonBorrarFichero.setVisible(true);
					botonActualizarFichero.setVisible(true);
				} else {
					botonBorrarFichero.setVisible(false);
					botonActualizarFichero.setVisible(false);
					botonAgregarFichero.setVisible(true);
				}
		});
		cargaGridFicheros(directorioActual);

	}
	public Directorio getDirectorioSeleccionado(Directorio directorioSeleccionado){
		directorioSeleccionado = ficheroSeleccionado.getDirectorio();
		return directorioSeleccionado;
	}

	public Fichero getFicheroSeleccionado() {
		return ficheroSeleccionado;
	}

	public void setFicheroSeleccionado(Fichero ficheroSeleccionado) {
		this.ficheroSeleccionado = ficheroSeleccionado;
	}
	
	public Directorio obtenDirectorioActual() {
		return directorioActual;
	}
	
	public void modificaDirectorioActual(Directorio directorio) {
		this.directorioActual = directorio;
	}

	public void cargaGridFicheros(Directorio directorio) {
		Collection<Fichero> ficheros = (directorio == null) ? new ArrayList<>()
				: servicioGestorFicheros.listaFicherosPorDirectorio(directorio.getId());
		gridFicheros.setContainerDataSource(new BeanItemContainer<>(Fichero.class, ficheros));
		directorioActual = directorio;
	}

	public void muestraBotonAgregarFichero(boolean visible) {
		botonAgregarFichero.setVisible(visible);
	}

	private Window creaVentanaConfirmacionBorradoFicheros(String nombre) {
		Window resultado = new Window();
		resultado.setWidth(350.0F, Unit.PIXELS);
		resultado.setModal(true);
		resultado.setClosable(false);
		resultado.setResizable(false);
		resultado.setDraggable(false);

		Label label = new Label("¿Está seguro de que desea borrar el archivo: <strong>\"" + nombre + "\"</strong>?");
		label.setContentMode(ContentMode.HTML);

		Button botonAceptar = new Button("Aceptar");
		botonAceptar.addClickListener(e -> {
			servicioGestorFicheros.eliminaFichero(ficheroSeleccionado.getId());
			cargaGridFicheros(ficheroSeleccionado.getDirectorio());
			padre.actualizaDirectorioActual(directorioActual.getId());
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
