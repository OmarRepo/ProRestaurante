package modelo;

public class Ingrediente {
	private int idIngrediente;
	private String nombre;
	private int cantidad;
	
	//constructor
	public Ingrediente(int idIngrediente, String nombre, int cantidad) {
		this.idIngrediente = idIngrediente;
		this.nombre = nombre;
		this.cantidad = cantidad;
	}
	//get
	public int getIdIngrediente() {
		return idIngrediente;
	}
	public String getNombre() {
		return nombre;
	}
	public int getCantidad() {
		return cantidad;
	}
	
	//set
	public void setIdIngrediente(int idIngrediente) {
		this.idIngrediente = idIngrediente;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	@Override
	public String toString() {
		return "Ingrediente [idIngrediente=" + idIngrediente + ", nombre=" + nombre + ", cantidad=" + cantidad + "]";
	}
	
	
	
	
}
