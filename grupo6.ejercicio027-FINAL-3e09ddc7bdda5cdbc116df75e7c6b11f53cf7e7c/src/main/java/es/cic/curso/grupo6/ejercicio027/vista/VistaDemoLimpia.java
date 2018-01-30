package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.List;

import org.springframework.web.context.ContextLoader;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorFicheros;

public class VistaDemoLimpia extends VerticalLayout implements View {
	private static final long serialVersionUID = -8229167069516384540L;

	/** Permite navegar entre las vistas de la aplicación. */
	private Navigator navegador;

	/** Lógica de negocio con acceso a BB.DD. */
	private ServicioGestorFicheros servicioGestorFicheros;

	public VistaDemoLimpia(Navigator navegador) {
		this.navegador = navegador;
		this.servicioGestorFicheros = ContextLoader.getCurrentWebApplicationContext()
				.getBean(ServicioGestorFicheros.class);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		List<Directorio> directorios = servicioGestorFicheros.listaDirectorios();
		for (Directorio directorio : directorios) {
			servicioGestorFicheros.eliminaDirectorio(directorio.getId());
		}
		Notification.show("Limpiados datos de DEMOSTRACIÓN");

		navegador.navigateTo("");
	}

}
