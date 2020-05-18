package modelo;

public class Camarero extends Empleado{
	

	//constructor
	public Camarero(String dni, String nombre) {
		super(dni, nombre);
	}
	
	//Metodos
	public void tomarNota(int idMesa) {
		
	}
	
	public void mostrarCarta() {
		
	}

	@Override
	public String toString() {
		return "Camarero ["+super.toString()+"]";
	}
	
}
