package modelo;

import java.util.HashSet;

public class Almacen {
	
	private HashSet<Producto> productos;
	//aaabbb
	//Constructores
	public Almacen() {
		productos = new HashSet<Producto>();
	}
	public Almacen(HashSet<Producto> productos) {
		this.productos = productos;
	}

	public HashSet<Producto> getProductos() {
		return productos;
	}

	public void setProductos(HashSet<Producto> productos) {
		this.productos = productos;
	}
	@Override
	public String toString() {
		return "Almacen [productos=" + productos + "]";
	}
	
	
	
}
