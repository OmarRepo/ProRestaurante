package modelo;

import java.util.Scanner;

public class Jefe extends Empleado{
	

	//constructor
	public Jefe(String id,String dni, String nombre) {
		super(id, dni, nombre);
	}
	
	//Metodos
	

	@Override
	public String toString() {
		return "Camarero ["+super.toString()+"]";
	}
	
}
