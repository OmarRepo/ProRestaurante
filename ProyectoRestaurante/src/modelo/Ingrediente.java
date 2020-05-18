package modelo;

public class Ingrediente {
	private String id;
	private String nombre;
	private int cantidad;
	
	//constructor
	public Ingrediente(String id, String nombre, int cantidad) {
		this.id = id;
		this.nombre = nombre;
		this.cantidad = cantidad;
	}
	//get
	public String getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public int getCantidad() {
		return cantidad;
	}
	
	//set
	public void setId(String id) {
		this.id = id;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	@Override
	public String toString() {
		return "Ingrediente [idIngrediente=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + "]";
	}
	
	
	
	
}
