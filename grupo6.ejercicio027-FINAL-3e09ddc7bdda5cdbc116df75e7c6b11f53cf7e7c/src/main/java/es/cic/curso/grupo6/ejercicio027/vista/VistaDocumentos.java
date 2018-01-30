package es.cic.curso.grupo6.ejercicio027.vista;

import java.io.File;

import org.springframework.web.context.ContextLoader;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorFicheros;

public class VistaDocumentos extends VerticalLayout implements View {
	private static final long serialVersionUID = 6070082071055226969L;

	/** Lógica de negocio con acceso a BB.DD. */
	private ServicioGestorFicheros servicioGestorFicheros;

	/** Controles para la manipulación de los directorios del sistema. */
	private LayoutDirectorios layoutDirectorios;

	/** Controles para la manipulación de los ficheros del sistema. */
	private LayoutFicheros layoutFicheros;

	public VistaDocumentos() {
		servicioGestorFicheros = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorFicheros.class);

		// layout. ENCABEZADO
		HorizontalLayout layoutEncabezado = inicializaLayoutEncabezado();
		// layout. DIRECTORIOS
		layoutDirectorios = new LayoutDirectorios(this, servicioGestorFicheros);
		// layout. FICHEROS
		layoutFicheros = new LayoutFicheros(this, servicioGestorFicheros);
		// splitPanel. PRINCIPAL
		HorizontalSplitPanel splitPanelPrincipal = new HorizontalSplitPanel();
		splitPanelPrincipal.setSizeFull();
		splitPanelPrincipal.setSplitPosition(30.0F, Unit.PERCENTAGE);
		splitPanelPrincipal.setMinSplitPosition(10.0F, Unit.PERCENTAGE);
		splitPanelPrincipal.setMaxSplitPosition(90.0F, Unit.PERCENTAGE);
		splitPanelPrincipal.setLocked(false);
		splitPanelPrincipal.setFirstComponent(layoutDirectorios);
		splitPanelPrincipal.setSecondComponent(layoutFicheros);

		addComponents(layoutEncabezado, splitPanelPrincipal);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Inicialización de componentes gráficos:

	private HorizontalLayout inicializaLayoutEncabezado() {
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/cic_logo.png"));
		Image imagen = new Image(null, resource);
		imagen.setHeight(60.0F, Unit.PIXELS);
		
		Label titulo = new Label("<span style=\"font-size: 175%;\">Gestor de Documentos</span>");
		titulo.setContentMode(ContentMode.HTML);

		HorizontalLayout layoutEncabezado = new HorizontalLayout();
		layoutEncabezado.setMargin(new MarginInfo(true, true, true, true));
		layoutEncabezado.setSpacing(false);
		layoutEncabezado.addComponents(imagen, titulo);
		layoutEncabezado.setComponentAlignment(titulo, Alignment.MIDDLE_LEFT);
		return layoutEncabezado;
	}

	// /////////////////////////////////////////////////////////////////////////

	@Override
	public void enter(ViewChangeEvent event) {
		layoutDirectorios.cargaGridDirectorios();
	}

	public void activaGridFicheros(Directorio directorio) {
		layoutFicheros.modificaDirectorioActual(directorio);
		layoutFicheros.muestraBotonAgregarFichero(directorio == null ? false : true);
		layoutFicheros.cargaGridFicheros(directorio);
	}
	
	public void actualizaDirectorioActual(Long idDirectorio) {
		Directorio directorio = servicioGestorFicheros.obtenDirectorio(idDirectorio);
		layoutDirectorios.modificaDirectorioSeleccionado(directorio);
		layoutFicheros.modificaDirectorioActual(directorio);
	}
	
	public void bloqueaTextFieldRutaDirectorio() {
		layoutDirectorios.bloqueaTextFieldRutaDirectorio();
	}

}
