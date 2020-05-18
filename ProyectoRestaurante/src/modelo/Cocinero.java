package modelo;

import java.util.HashSet;
import java.util.Iterator;

public class Cocinero extends Empleado {

	// Constructores
	public Cocinero(String dni, String nombre) {
		super(dni, nombre);
	}

	// Metodos
	/*
	public void prepararPedido(Pedido pedido, Carta carta, AlmacenCutre almacenCutre) {
		//Iterator<Consumible> it = pedido.getConsumibles().iterator();
		Iterator<String> it=pedido.getConsumibles().keySet().iterator();
		while (it.hasNext()) {
			String key  = it.next();

			if (key instanceof Bebida) {
				
			}



		}

	}
	
	
	
	//Iterator<Consumible> it = pedido.getConsumibles().iterator();
	Iterator<String> it=consumibles.keySet().iterator();
	while (it.hasNext()) {
		String key  = it.next();
		
		if (true) {//si el consumible es un objeto de tipo bebida
			if(almacenCutre.comprobarDisponibilidadBebida(bebida)){
				almacenCutre.eliminarProducto(id);
			}
			
		}
	}

	*/


	@Override
	public String toString() {
		return "Cocinero [" + super.toString() + "]";
	}

}
