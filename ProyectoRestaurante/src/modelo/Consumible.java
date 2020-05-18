package modelo;

public abstract class Consumible {
	private String id;
	private String nombre;
	private double precio;
	
	//constructor
	public Consumible(String id, String nombre, double precio) {
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
	}
	
	//get
	public String getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public double getPrecio() {
		return precio;
	}
	
	//set
	public void setId(String id) {
		this.id = id;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Consumible [id=" + id + ", nombre=" + nombre + ", precio=" + precio + "]";
	}
	
	
}
