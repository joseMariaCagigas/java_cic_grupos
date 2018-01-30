package es.cic.curso.grupo6.ejercicio027.vista;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.springframework.web.context.ContextLoader;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio027.ExcepcionES;
import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorFicheros;

public class VistaDemoCarga extends VerticalLayout implements View {
	private static final long serialVersionUID = -8229167069516384540L;

	/** Número de directorios que se crean para la demo. */
	public static final int NUM_DIRECTORIOS = 5;

	/** Número de ficheros que se crean por directorio para la demo. */
	public static final int NUM_FICHEROS_INICIAL = 5;

	/** Permite navegar entre las vistas de la aplicación. */
	private Navigator navegador;

	/** Lógica de negocio con acceso a BB.DD. */
	private ServicioGestorFicheros servicioGestorFicheros;

	public VistaDemoCarga(Navigator navegador) {
		this.navegador = navegador;
		this.servicioGestorFicheros = ContextLoader.getCurrentWebApplicationContext()
				.getBean(ServicioGestorFicheros.class);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (servicioGestorFicheros.listaDirectorios().isEmpty()) {
			limpiaDirectorio(ServicioGestorFicheros.DIRECTORIO_BASE);
			for (int i = 1; i <= NUM_DIRECTORIOS; i++) {
				Directorio directorio = new Directorio();
				directorio.setRuta("directorio" + i);
				servicioGestorFicheros.agregaDirectorio(directorio);
				for (int j = 1; j <= (NUM_FICHEROS_INICIAL * i); j++) {
					Fichero fichero = new Fichero();
					fichero.setNombre("fichero" + j);
					fichero.setDescripcion("Fichero de prueba");
					fichero.setVersion(1.0);
					servicioGestorFicheros.agregaFichero(directorio.getId(), fichero);
				}
			}
			Notification.show("Cargados datos de DEMOSTRACIÓN");
		}
		navegador.navigateTo("");
	}

	private void limpiaDirectorio(String ruta) {
		Path rootPath = Paths.get(ruta);
		Path gitKeepPath = Paths.get(ruta + ".gitkeep");
		try {
			Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}
			});
			Files.createDirectory(rootPath);
			Files.createFile(gitKeepPath);
		} catch (IOException ioe) {
			throw new ExcepcionES(ioe);
		}
	}

}
