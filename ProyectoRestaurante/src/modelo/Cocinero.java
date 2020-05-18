package modelo;

import java.util.HashSet;
import java.util.Iterator;

public class Cocinero extends Empleado {

	// Constructores
	public Cocinero(String dni, String nombre) {
		super(dni, nombre);
	}

	// Metodos
	public void prepararPedido(Pedido pedido) {
		Iterator<Consumible> it = pedido.getConsumibles().iterator();
		while (it.hasNext()) {
			Consumible consumible = it.next();
			// precio += consumible.getPrecio();

			if (consumible instanceof Bebida) {
				
			}

			if (consumible instanceof Plato) {

			}
			
			if (consumible instanceof Menu) {

			}

		}

	}

	
	public void comprobarBebida(AlmacenCutre almacenCutre, Bebida bebida) {
		
		HashSet<Bebida> bebidas=almacenCutre.getBebidas();
		
		
		
	}

	@Override
	public String toString() {
		return "Cocinero [" + super.toString() + "]";
	}

}
