package modelo;

public class Cocinero extends Empleado{
	
	//Constructores
	public Cocinero(String dni, String nombre) {
		super(dni, nombre);
	}
	
	//Metodos
	public void prepararPedido() {
		
	}

	@Override
	public String toString() {
		return "Cocinero ["+super.toString()+"]";
	}
	
}
