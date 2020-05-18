package modelo;

import java.util.HashSet;
import java.util.Iterator;

public class AlmacenCutre extends Almacen{
	
	private HashSet<Ingrediente> ingredientes;
	private HashSet<Bebida> bebidas;
	
	public AlmacenCutre() {
		ingredientes = new HashSet<Ingrediente>();
		bebidas = new HashSet<Bebida>();
	}
	
	public void anadirProducto(Object o) {
		if (o instanceof Ingrediente) {
			Ingrediente i = (Ingrediente) o;
			if (!ingredientes.add(i)) {
				for (Iterator<Ingrediente> it = ingredientes.iterator(); it.hasNext();) {
					Ingrediente in = it.next();
					if (in.getIdIngrediente().equals(i.getIdIngrediente()))
						in.setCantidad(in.getCantidad()+i.getCantidad());
				}
			}	
		}
		else if (o instanceof Bebida) {
			Bebida b = (Bebida) o;
			if (!bebidas.add(b)) {
				for (Iterator<Bebida> it = bebidas.iterator(); it.hasNext();) {
					Bebida be = (Bebida) it.next();
					if (be.getId().equals(b.getId()))
						be.setCantidad(be.getCantidad()+b.getCantidad());
				}
			}	
		}

	}
	@Override
	public void eliminarProducto(String id) {
		if (id.startsWith("I"))
			ingredientes.removeIf((Ingrediente i) -> i.getIdIngrediente().equalsIgnoreCase(id));
		else {
			bebidas.removeIf((Consumible c) -> c.getId().equalsIgnoreCase(id));
		}
	}
	
	public void modificarProducto(String id,String nombre) {
	
	}
	
	
	
}