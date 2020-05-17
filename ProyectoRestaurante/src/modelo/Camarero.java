package modelo;

public class Camarero extends Empleado{
	

	//PRUEBA ADRI
	public Camarero(String dni, String nombre) {
		super(dni, nombre);
	}
	
	//Metodos
	public void tomarNota() {
		
	}

	@Override
	public String toString() {
		return "Camarero ["+super.toString()+"]";
	}
	
}
