package modelo;

import java.util.HashSet;

public class Plato implements Consumibles{
	
	private int idPlato;
	private String nombre;
	private HashSet<Producto> productos;
	private double precio;
	private TIPO_PLATO tipo;
	
	//Constructores
	public Plato(int idPlato, String nombre, double precio, TIPO_PLATO tipo) {
		this.idPlato = idPlato;
		this.nombre = nombre;
		this.productos = new HashSet<Producto>();
		this.precio = precio;
		this.tipo = tipo;
	}
	public Plato(int idPlato, String nombre, HashSet<Producto> productos, double precio, TIPO_PLATO tipo) {
		this.idPlato = idPlato;
		this.nombre = nombre;
		this.productos = productos;
		this.precio = precio;
		this.tipo = tipo;
	}
	
	//Metodos
	
	public void anadirProducto(Producto producto) {
		
	}
	
	public void eliminarProducto(int id) {
		
	}
	
	
	//Get
	public int getIdPlato() {
		return idPlato;
	}

	public String getNombre() {
		return nombre;
	}

	public HashSet<Producto> getProductos() {
		return productos;
	}

	public double getPrecio() {
		return precio;
	}

	public TIPO_PLATO getTipo() {
		return tipo;
	}
	
	//Set
	public void setIdPlato(int idPlato) {
		this.idPlato = idPlato;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setProductos(HashSet<Producto> productos) {
		this.productos = productos;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public void setTipo(TIPO_PLATO tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Plato [idPlato=" + idPlato + ", nombre=" + nombre + ", productos=" + productos
				+ ", precio=" + precio + ", tipo=" + tipo + "]";
	}
	
	
	
	
}
