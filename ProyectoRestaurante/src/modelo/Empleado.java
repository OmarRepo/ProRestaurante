package modelo;

public class Empleado {
	
	private String dni;
	private String nombre;
	
	//Constructores
	public Empleado(String dni, String nombre) {
		this.dni = dni;
		this.nombre = nombre;
	}
	
	
	//Get
	public String getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}
	
	//Set
	public void setDni(String dni) {
		this.dni = dni;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	@Override
	public String toString() {
		return "dni=" + dni + ", nombre=" + nombre;
	}
	
	
	
}
