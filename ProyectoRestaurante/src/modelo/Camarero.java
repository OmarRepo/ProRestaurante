package modelo;

import java.util.Scanner;

public class Camarero extends Empleado{
	

	//constructor
	public Camarero(String id,String dni, String nombre) {
		super(id, dni, nombre);
	}
	
	//Metodos
	

	@Override
	public String toString() {
		return "Camarero ["+super.toString()+"]";
	}
	
}
