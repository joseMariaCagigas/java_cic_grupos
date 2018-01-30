package es.cic.curso.grupo6.ejercicio027.servicio;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo6.ejercicio027.ExcepcionES;
import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;
import es.cic.curso.grupo6.ejercicio027.repositorio.RepositorioDirectorio;
import es.cic.curso.grupo6.ejercicio027.repositorio.RepositorioFichero;

/**
 * Implementación de la interfaz <code>ServicioGestorFicheros</code>.
 * 
 * 
 * @author J. Francisco Martín
 * @author José María Cagigas
 * @serial 1.0
 * @version 2017/02/27
 *
 */
@Service
@Transactional
public class ServicioGestorFicherosImpl implements ServicioGestorFicheros {

	private static final String ERROR_ID_DIRECTORIO = "No existe ningún directorio en BB.DD. con ese ID";
	private static final String ERROR_RUTA_DIRECTORIO = "Ya existe un directorio con esa ruta";
	private static final String ERROR_ESTADO_DIRECTORIO = "Existen ficheros colgando del directorio";
	private static final String ERROR_ID_FICHERO = "No existe ningún fichero en BB.DD. con ese ID";
	private static final String ERROR_RUTA_FICHERO = "Ya existe un fichero con ese nombre";

	@Autowired
	private RepositorioDirectorio repositorioDirectorio;

	@Autowired
	private RepositorioFichero repositorioFichero;

	@Override
	public void agregaDirectorio(Directorio directorio) {
		Path ruta = Paths.get(DIRECTORIO_BASE + directorio.getRuta());
		try {
			Files.createDirectory(ruta);
			repositorioDirectorio.create(directorio);
		} catch (FileAlreadyExistsException faee) {
			throw new IllegalArgumentException(ERROR_RUTA_DIRECTORIO, faee);
		} catch (IOException ioe) {
			// Error en la creación del directorio nuevo
			throw new ExcepcionES(ioe);
		}
	}

	@Override
	public void agregaFichero(Long idDirectorio, Fichero fichero) {
		Directorio directorio = obtenDirectorio(idDirectorio);
		Path ruta = Paths.get(DIRECTORIO_BASE + directorio.getRuta() + "/" + fichero.getNombre());
		try {
			Files.createFile(ruta);
			fichero.setDirectorio(directorio);
			repositorioFichero.create(fichero);
		} catch (FileAlreadyExistsException faee) {
			throw new IllegalArgumentException(ERROR_RUTA_FICHERO, faee);
		} catch (IOException ioe) {
			// Error en la creación del fichero nuevo
			throw new ExcepcionES(ioe);
		}
	}

	@Override
	public Directorio obtenDirectorio(Long idDirectorio) {
		Directorio directorio = repositorioDirectorio.read(idDirectorio);
		if (directorio == null) {
			throw new IllegalArgumentException(ERROR_ID_DIRECTORIO + ": " + idDirectorio);
		}
		return directorio;
	}

	@Override
	public Fichero obtenFichero(Long idFichero) {
		Fichero fichero = repositorioFichero.read(idFichero);
		if (fichero == null) {
			throw new IllegalArgumentException(ERROR_ID_FICHERO + ": " + idFichero);
		}
		return fichero;
	}

	@Override
	public Directorio modificaDirectorio(Long idDirectorio, Directorio directorio) {
		Directorio original = obtenDirectorio(idDirectorio);
		if (!listaFicherosPorDirectorio(idDirectorio).isEmpty()) {
			throw new IllegalStateException(ERROR_ESTADO_DIRECTORIO);
		}
		Path rutaOriginal = Paths.get(DIRECTORIO_BASE + original.getRuta());
		Path rutaNueva = Paths.get(DIRECTORIO_BASE + directorio.getRuta());
		if (!rutaOriginal.equals(rutaNueva) && Files.exists(rutaNueva)) {
			throw new IllegalArgumentException(ERROR_RUTA_DIRECTORIO);
		}
		try {
			Files.move(rutaOriginal, rutaNueva, StandardCopyOption.REPLACE_EXISTING);
			directorio.setId(idDirectorio);
			repositorioDirectorio.update(directorio);
			return original;
		} catch (IOException ioe) {
			// Error al renombrar el directorio
			throw new ExcepcionES(ioe);
		}
	}

	@Override
	public Fichero modificaFichero(Long idFichero, Fichero fichero) {
		Fichero original = obtenFichero(idFichero);
		Path rutaOriginal = Paths
				.get(DIRECTORIO_BASE + original.getDirectorio().getRuta() + "/" + original.getNombre());
		Path rutaNueva = Paths.get(DIRECTORIO_BASE + fichero.getDirectorio().getRuta() + "/" + fichero.getNombre());
		if (!rutaOriginal.equals(rutaNueva) && Files.exists(rutaNueva)) {
			throw new IllegalArgumentException(ERROR_RUTA_FICHERO);
		}
		try {
			Files.move(rutaOriginal, rutaNueva, StandardCopyOption.REPLACE_EXISTING);
			fichero.setId(idFichero);
			repositorioFichero.update(fichero);
			return original;
		} catch (IOException ioe) {
			// Error al mover el fichero
			throw new ExcepcionES(ioe);
		}
	}

	@Override
	public Directorio eliminaDirectorio(Long idDirectorio) {
		Directorio directorio = obtenDirectorio(idDirectorio);
		Path ruta = Paths.get(DIRECTORIO_BASE + directorio.getRuta());
		try {
			Files.walkFileTree(ruta, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					// System.out.println("delete file: " + file.toString());
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					// System.out.println("delete dir: " + dir.toString());
					return FileVisitResult.CONTINUE;
				}
			});
			repositorioFichero.deleteByDirectory(idDirectorio);
			repositorioDirectorio.delete(directorio);
			return directorio;
		} catch (IOException ioe) {
			throw new ExcepcionES(ioe);
		}
	}

	@Override
	public Fichero eliminaFichero(Long idFichero) {
		Fichero fichero = obtenFichero(idFichero);
		Path ruta = Paths.get(DIRECTORIO_BASE + fichero.getDirectorio().getRuta() + "/" + fichero.getNombre());
		try {
			Files.delete(ruta);
			repositorioFichero.delete(fichero);
			return fichero;
		} catch (IOException ioe) {
			// Error al eliminar el fichero
			throw new ExcepcionES(ioe);
		}
	}

	@Override
	public List<Fichero> eliminaFicherosPorDirectorio(Long idDirectorio) {
		List<Fichero> ficheros = listaFicherosPorDirectorio(idDirectorio);
		Directorio directorio = eliminaDirectorio(idDirectorio);
		agregaDirectorio(directorio);
		return ficheros;
	}

	@Override
	public List<Directorio> listaDirectorios() {
		return repositorioDirectorio.list();
	}

	@Override
	public List<Fichero> listaFicheros() {
		return repositorioFichero.list();
	}

	@Override
	public List<Fichero> listaFicherosPorDirectorio(Long idDirectorio) {
		// Comprueba que el directorio esté registrado en el sistema
		obtenDirectorio(idDirectorio);
		// Retorna la lista de ficheros para el directorio dado
		return repositorioFichero.listByDirectory(idDirectorio);
	}

	@Override
	public boolean esHoja(Long idDirectorio) {
		return listaFicherosPorDirectorio(idDirectorio).isEmpty();
	}

}
