package modelo;

import java.util.Scanner;

public class Camarero extends Empleado{
	

	//constructor
	public Camarero(String id,String dni, String nombre) {
		super(id, dni, nombre);
	}
	
	//Metodos
	
	public void tomarNota(int idMesa) {
		
		Scanner sc = new Scanner(System.in);
		
		
	}
	


	@Override
	public String toString() {
		return "Camarero ["+super.toString()+"]";
	}
	
}
