package es.cic.curso.grupo6.ejercicio027.servicio;

import java.util.List;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;

/**
 * Define operaciones CRUD sobre los <em>ficheros</em> y <em>directorios</em>.
 * 
 * 
 * @author J. Francisco Martín
 * @author José María Cagigas
 * @serial 1.0
 * @version 2017/02/27
 *
 */
public interface ServicioGestorFicheros {

	public static final String DIRECTORIO_BASE = "src/main/webapp/documentos/";

	/**
	 * Añade un nuevo directorio al sistema. Dos directorios no pueden tener una
	 * misma ruta.
	 * 
	 * @param directorio
	 *            Nuevo directorio que se añade al sistema
	 * @throws IllegalArgumentException
	 *             Si el directorio que se intenta añadir al sistema tiene la
	 *             misma ruta de un directorio ya existente
	 * @throws RuntimeException
	 *             Si se produce un error de entrada/salida inesperado al tratar
	 *             de crear el directorio
	 */
	void agregaDirectorio(Directorio directorio);

	/**
	 * Añade un nuevo fichero al sistema que cuelgue del directorio que se
	 * corresponda con el identificador pasado como parámetro.
	 * 
	 * @param idDirectorio
	 *            Identificador del directorio del que cuelga el nuevo fichero
	 * @param fichero
	 *            Nuevo fichero que se añade al sistema
	 * @throws IllegalArgumentException
	 *             Si el identificador dado no se corresponde con ningún
	 *             directorio registrado en el sistema, o si ya existe un
	 *             fichero en el sistema con el mismo nombre
	 */
	void agregaFichero(Long idDirectorio, Fichero fichero);

	/**
	 * Retorna el directorio registrado en el sistema que se corresponde con el
	 * identificador pasado como parámetro.
	 * 
	 * @param idDirectorio
	 *            Identificador del directorio
	 * @return Directorio que se corresponde con el identificador dado
	 */
	Directorio obtenDirectorio(Long idDirectorio);

	/**
	 * Retorna el fichero registrado en el sistema que se corresponde con el
	 * identificador pasado como parámetro.
	 * 
	 * @param idFichero
	 *            Identificador del fichero
	 * @return Fichero que se corresponde con el identificador dado
	 * @throws IllegalArgumentException
	 *             Si el identificador dado no se corresponde con ningún fichero
	 *             registrado en el sistema
	 */
	Fichero obtenFichero(Long idFichero);

	/**
	 * Indica si el directorio que se corresponda con el identificador pasado
	 * como parámetro tiene o no ficheros colgando de él.
	 * 
	 * @param idDirectorio
	 *            Identificador del directorio dado
	 * @return Verdadero si no cuelgan ficheros del directorio; falso en caso
	 *         contrario
	 * @throws IllegalArgumentException
	 *             Si el identificador dado no se corresponde con ningún
	 *             directorio registrado en el sistema
	 */
	boolean esHoja(Long idDirectorio);

	/**
	 * Reemplaza en el sistema el directorio que se corresponde con el
	 * identificador pasado como parámetro por el nuevo directorio indicado.
	 * 
	 * @param idDirectorio
	 *            Identificador del directorio
	 * @param directorio
	 *            Directorio por el que se reemplaza el directorio original
	 * @return Directorio original registrado en el sistema antes del reemplazo
	 * @throws IllegalArgumentException
	 *             Si el identificador dado no se corresponde con ningún
	 *             directorio registrado en el sistema
	 * @throws IllegalStateException
	 *             Si existen ficheros que cuelgan del directorio dado, o si ya
	 *             existe un directorio en el sistema con el mismo nombre del
	 *             directorio tal y como se pretende modificar
	 */
	Directorio modificaDirectorio(Long idDirectorio, Directorio directorio);

	/**
	 * Reemplaza en el sistema el fichero que se corresponde con el
	 * identificador pasado como parámetro por el nuevo fichero indicado.
	 * 
	 * @param idFichero
	 *            Identificador del fichero
	 * @param fichero
	 *            Fichero por el que se reemplaza el fichero original
	 * @return Fichero original registrado en el sistema antes del reemplazo
	 * @throws IllegalArgumentException
	 *             Si el identificador dado no se corresponde con ningún fichero
	 *             registrado en el sistema, o si ya existe un fichero en el
	 *             sistema con el mismo nombre y en el mismo directorio del
	 *             fichero tal y como se pretende modificar
	 */
	Fichero modificaFichero(Long idFichero, Fichero fichero);

	/**
	 * Elimina del sistema el directorio que se corresponde con el identificador
	 * pasado como parámetro. Si el directorio no está vacío, se eliminan además
	 * todos los ficheros que cuelgan de él.
	 * 
	 * @param idDirectorio
	 *            Identificador del directorio
	 * @return Directorio que se elimina del sistema
	 * @throws IllegalArgumentException
	 *             Si el identificador dado no se corresponde con ningún
	 *             directorio registrado en el sistema
	 * @throws RuntimeException
	 *             Si se produce un error de entrada/salida inesperado al tratar
	 *             de eliminar el directorio y sus ficheros
	 */
	Directorio eliminaDirectorio(Long idDirectorio);

	/**
	 * Elimina del sistema el fichero que se corresponde con el identificador
	 * pasado como parámetro.
	 * 
	 * @param idFichero
	 *            Identificador del fichero
	 * @return Fichero que se elimina del sistema
	 * @throws IllegalArgumentException
	 *             Si el identificador dado no se corresponde con ningún fichero
	 *             registrado en el sistema
	 */
	Fichero eliminaFichero(Long idFichero);

	/**
	 * Elimina del sistema todos los ficheros que cuelguen del directorio que se
	 * corresponde con el identificador pasado como parámetro.
	 * 
	 * @param idDirectorio
	 *            Identificador del directorio
	 * @return Lista con con todos los ficheros que se eliminan del sistema
	 * @throws IllegalArgumentException
	 *             Si el identificador dado no se corresponde con ningún
	 *             directorio registrado en el sistema
	 */
	List<Fichero> eliminaFicherosPorDirectorio(Long idDirectorio);

	/**
	 * Retorna una lista con todos los directorios registrados en el sistema.
	 * 
	 * @return Lista con todos los directorios registrados en el sistema
	 */
	List<Directorio> listaDirectorios();

	/**
	 * Retorna una lista con todos los ficheros registrados en el sistema.
	 * 
	 * @return Lista con todos los ficheros registrados en el sistema
	 */
	List<Fichero> listaFicheros();

	/**
	 * Retorna una lista con todos los ficheros registrados en el sistema que
	 * cuelguen del directorio que se corresponde con el identificador pasado
	 * como parámetro.
	 * 
	 * @param idDirectorio
	 *            Identificador del directorio
	 * @return Lista con todos los ficheros que cuelgan del directorio dado
	 * @throws IllegalArgumentException
	 *             Si el identificador dado no se corresponde con ningún
	 *             directorio registrado en el sistema
	 */
	List<Fichero> listaFicherosPorDirectorio(Long idDirectorio);

}
