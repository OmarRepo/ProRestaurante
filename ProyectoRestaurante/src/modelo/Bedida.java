package modelo;

public class Bedida extends Consumible {
	private int cantidad;

	public Bedida(String id, String nombre, double precio, int cantidad) {
		super(id, nombre, precio);
		this.cantidad = cantidad;
	}
	//get

	public int getCantidad() {
		return cantidad;
	}
	//set
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "Bedida [cantidad=" + cantidad + ", toString()=" + super.toString() + "]";
	}
	
	
}
