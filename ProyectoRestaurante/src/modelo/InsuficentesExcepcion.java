package modelo;

public class InsuficentesExcepcion extends RuntimeException {

	public InsuficentesExcepcion() {
		super();
	}

	public InsuficentesExcepcion(String message) {
		super(message);
	}

}
