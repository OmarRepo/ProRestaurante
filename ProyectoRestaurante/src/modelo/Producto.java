package modelo;

public class Producto implements Consumibles{
	
	private int idProducto;
	private String nombre;
	private int cantidad;
	
	//Contructores
	public Producto(int idProducto, String nombre, int cantidad) {
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.cantidad = cantidad;
	}
	
	public Producto(Producto p) {
		this.idProducto = p.getIdProducto();
		this.nombre = p.getNombre();
		this.cantidad = p.getCantidad();
	}
	
	//Get
	public int getIdProducto() {
		return idProducto;
	}

	public String getNombre() {
		return nombre;
	}

	public int getCantidad() {
		return cantidad;
	}
	
	//Set
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "Producto [idProducto=" + idProducto + ", nombre=" + nombre + ", cantidad=" + cantidad + "]";
	}
}
