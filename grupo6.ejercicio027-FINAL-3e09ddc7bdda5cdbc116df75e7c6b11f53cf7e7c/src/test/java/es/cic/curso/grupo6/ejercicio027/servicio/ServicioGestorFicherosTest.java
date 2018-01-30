package es.cic.curso.grupo6.ejercicio027.servicio;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;

/**
 * Tests de integración sobre la implementación de
 * <code>ServicioGestorFicheros</code>.
 * 
 * <p>
 * <strong>IMPORTANTE:</strong> Se puede dar el caso (improbable) de que la
 * aplicación contenga un documento con el mismo nombre utilizado como
 * directorio de prueba: <em>RUTA_TEST</em> y se produzca un error. Ante esta
 * situación, debería modificarse la ruta de este directorio para poder lanzar
 * las pruebas correctamente.
 * 
 * 
 * @author J. Francisco Martín
 * @author José María Cagigas
 * @serial 1.0
 * @version 2017/02/27
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo6/ejercicio027/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class ServicioGestorFicherosTest {

	public static final int NUMERO_ELEMENTOS_PRUEBA = 10;
	public static final String RUTA_TEST = "1842474819854513394test";
	public static final String DIRECTORIO_RUTA_1 = RUTA_TEST + "/directorio1";
	public static final String DIRECTORIO_RUTA_2 = RUTA_TEST + "/directorio2";
	public static final String NOMBRE_FICHERO = "fichero";

	@Autowired
	private ServicioGestorFicheros sut;

	// /////////////////////////////////////////////////////////////////////////

	@Before
	public void setUp() {
		Path rootPath = Paths.get(ServicioGestorFicheros.DIRECTORIO_BASE + RUTA_TEST);
		try {
			System.out.println("create dir: " + rootPath.toString());
			Files.createDirectory(rootPath);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	@After
	public void tearDown() {
		Path rootPath = Paths.get(ServicioGestorFicheros.DIRECTORIO_BASE + RUTA_TEST);
		try {
			Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					System.out.println("delete file: " + file.toString());
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					System.out.println("delete dir: " + dir.toString());
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
	
	private Directorio generaDirectorio(String ruta) {
		Directorio directorio = new Directorio();
		directorio.setRuta(ruta);
		return directorio;
	}
	
	private Fichero generaFichero(String nombre) {
		Fichero fichero = new Fichero();
		fichero.setNombre(nombre);
		fichero.setDescripcion("Descripción de " + nombre);
		fichero.setVersion(1.0);
		return fichero;
	}

	// /////////////////////////////////////////////////////////////////////////

	@Test
	public void testAgregaDirectorio() {
		Directorio directorio;

		// 1) Introduce un directorio válido
		directorio = generaDirectorio(DIRECTORIO_RUTA_1);
		assertNull(directorio.getId());
		sut.agregaDirectorio(directorio);
		assertNotNull(directorio.getId());

		// 2) Introduce un directorio inválido
		directorio = generaDirectorio(DIRECTORIO_RUTA_1);
		try {
			sut.agregaDirectorio(directorio);
			fail("No se debería poder crear un directorio en esa ruta");
		} catch (IllegalArgumentException iae) {

		}
	}

	@Test
	public void testAgregaFichero() {
		Fichero fichero = generaFichero(NOMBRE_FICHERO);

		// 1) Fichero sobre un directorio no registrado en BB.DD.
		try {
			sut.agregaFichero(0L, fichero);
			fail("No se debería poder añadir un fichero con un ID de directorio inválido");
		} catch (IllegalArgumentException iae) {

		}

		// 2) Fichero sobre un directorio registrado en BB.DD.
		Directorio directorio = generaDirectorio(DIRECTORIO_RUTA_1);
		sut.agregaDirectorio(directorio);
		sut.agregaFichero(directorio.getId(), fichero);
	}

	@Test
	public void testObtenDirectorio() {
		Directorio resultado;

		// 1) Obtener un directorio que no está en BB.DD.
		try {
			resultado = sut.obtenDirectorio(0L);
			fail("El directorio no existe en BB.DD.");
		} catch (IllegalArgumentException iae) {

		}

		// 2) Obtener un directorio que está en BB.DD.
		Directorio directorio = generaDirectorio(DIRECTORIO_RUTA_1);
		sut.agregaDirectorio(directorio);
		resultado = sut.obtenDirectorio(directorio.getId());
		assertEquals(directorio, resultado);
	}

	@Test
	public void testObtenFichero() {
		Fichero fichero;

		// 1) Fichero no registrado en BB.DD.
		try {
			fichero = sut.obtenFichero(0L);
			fail("Fichero no registrado en BB.DD.");
		} catch (IllegalArgumentException iae) {

		}

		// 2) Fichero registrado en BB.DD.
		Directorio directorio = generaDirectorio(DIRECTORIO_RUTA_1);
		sut.agregaDirectorio(directorio);
		fichero = generaFichero(NOMBRE_FICHERO);
		sut.agregaFichero(directorio.getId(), fichero);		
		Fichero resultado = sut.obtenFichero(fichero.getId());
		assertEquals(fichero, resultado);
	}

	@Test
	public void testModificaFichero() {
		Directorio directorio1 = generaDirectorio(DIRECTORIO_RUTA_1);
		sut.agregaDirectorio(directorio1);
		Directorio directorio2 = generaDirectorio(DIRECTORIO_RUTA_2);
		sut.agregaDirectorio(directorio2);

		Fichero original = generaFichero(NOMBRE_FICHERO);
		sut.agregaFichero(directorio1.getId(), original);
		
		Fichero clon = new Fichero();
		clon.setId(original.getId());
		clon.setDirectorio(original.getDirectorio());
		clon.setNombre(original.getNombre());
		clon.setDescripcion(original.getDescripcion());
		clon.setVersion(original.getVersion());
		assertEquals(original, clon);

		clon.setDirectorio(directorio2);
		sut.modificaFichero(original.getId(), clon);
		Fichero modificado = sut.obtenFichero(original.getId());
		assertEquals(clon, modificado);
	}


	@Test
	public void testEliminarDirectorio() {
		Directorio resultado;

		// 1) Eliminar un directorio vacío que está en BB.DD.
		Directorio directorio = generaDirectorio(DIRECTORIO_RUTA_1);
		sut.agregaDirectorio(directorio);
		resultado = sut.eliminaDirectorio(directorio.getId());
		assertNotNull(resultado);
		
		// 2) Eliminar un directorio no vacío que está en BB.DD.
		// TODO

		// 3) Eliminar un directorio que no está en BB.DD.
		try {
			resultado = sut.eliminaDirectorio(directorio.getId());
			fail("El directorio no existe en BB.DD.");
		} catch (IllegalArgumentException iae) {

		}
	}

	@Test
	public void testEliminaFichero() {
		Directorio directorio = generaDirectorio(DIRECTORIO_RUTA_1);
		sut.agregaDirectorio(directorio);
		Fichero fichero = generaFichero(NOMBRE_FICHERO);
		sut.agregaFichero(directorio.getId(), fichero);
		
		assertTrue(sut.listaFicheros().size() == 1);
		sut.eliminaFichero(fichero.getId());
		assertTrue(sut.listaFicheros().isEmpty());
		
		// TODO - Otros casos de prueba
	}

	@Test
	public void testEliminaFicherosPorDirectorio() {
		Directorio directorio1 = generaDirectorio(DIRECTORIO_RUTA_1);
		Directorio directorio2 = generaDirectorio(DIRECTORIO_RUTA_2);
		Fichero fichero1, fichero2, fichero3;
		sut.agregaDirectorio(directorio1);
		sut.agregaDirectorio(directorio2);
		for (int i = 0; i < NUMERO_ELEMENTOS_PRUEBA; i++) {
			fichero1 = generaFichero(NOMBRE_FICHERO + i);
			sut.agregaFichero(directorio1.getId(), fichero1);
			fichero2 = generaFichero(NOMBRE_FICHERO + i + "a");
			sut.agregaFichero(directorio2.getId(), fichero2);
			fichero3 = generaFichero(NOMBRE_FICHERO + i + "b");
			sut.agregaFichero(directorio2.getId(), fichero3);
		}
		List<Fichero> ficheros;
		
		ficheros = sut.listaFicheros();
		assertEquals(NUMERO_ELEMENTOS_PRUEBA * 3, ficheros.size());

		sut.eliminaFicherosPorDirectorio(directorio2.getId());
		ficheros = sut.listaFicheros();
		assertEquals(NUMERO_ELEMENTOS_PRUEBA, ficheros.size());
	}

	@Test
	public void testListaDirectorios() {
		Directorio directorio;
		for (int i = 0; i < NUMERO_ELEMENTOS_PRUEBA; i++) {
			directorio = generaDirectorio(DIRECTORIO_RUTA_1 + i);
			sut.agregaDirectorio(directorio);
		}

		List<Directorio> lista = sut.listaDirectorios();
		assertEquals(NUMERO_ELEMENTOS_PRUEBA, lista.size());
	}

	@Test
	public void testListaFicheros() {
		Directorio directorio1 = generaDirectorio(DIRECTORIO_RUTA_1);
		Directorio directorio2 = generaDirectorio(DIRECTORIO_RUTA_2);
		Fichero fichero1, fichero2, fichero3;
		sut.agregaDirectorio(directorio1);
		sut.agregaDirectorio(directorio2);
		for (int i = 0; i < NUMERO_ELEMENTOS_PRUEBA; i++) {
			fichero1 = generaFichero(NOMBRE_FICHERO + i);
			sut.agregaFichero(directorio1.getId(), fichero1);
			fichero2 = generaFichero(NOMBRE_FICHERO + i + "a");
			sut.agregaFichero(directorio2.getId(), fichero2);
			fichero3 = generaFichero(NOMBRE_FICHERO + i + "b");
			sut.agregaFichero(directorio2.getId(), fichero3);
		}
		List<Fichero> ficheros;
		
		ficheros = sut.listaFicheros();
		assertEquals(NUMERO_ELEMENTOS_PRUEBA * 3, ficheros.size());
		ficheros = sut.listaFicherosPorDirectorio(directorio1.getId());
		assertEquals(NUMERO_ELEMENTOS_PRUEBA, ficheros.size());
		ficheros = sut.listaFicherosPorDirectorio(directorio2.getId());
		assertEquals(NUMERO_ELEMENTOS_PRUEBA * 2, ficheros.size());
	}

}
