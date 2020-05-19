package modelo;

import java.util.HashSet;
import java.util.Iterator;

public class Cocinero extends Empleado {

	// Constructores
	public Cocinero(String dni, String nombre) {
		super(dni, nombre);
	}

	// Metodos

	public void prepararPedido(Pedido pedido, Carta carta, AlmacenCutre almacenCutre) { // Iterator<Consumible> it =
		if(pedido.comprobarPedido(carta, almacenCutre)) {
			
		}
	}

	@Override
	public String toString() {
		return "Cocinero [" + super.toString() + "]";
	}

}
