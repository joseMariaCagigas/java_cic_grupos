package es.cic.curso.grupo6.ejercicio027;

public class ExcepcionES extends RuntimeException {
	private static final long serialVersionUID = -4455115446338468077L;


	public ExcepcionES() {
		super();
	}
	
	public ExcepcionES(Exception e) {
		super(e);
	}
}
