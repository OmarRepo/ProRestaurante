package modelo;

public class Bebida extends Consumible {

	private int cantidad;

	// Constructor
	public Bebida(String id, String nombre, double precio, int cantidad) {
		super(id, nombre, precio);
		if (super.getId() != null) {// solo guardamos el resto de datos si el id es correcto
			this.cantidad = cantidad;
		}
	}

	public Bebida(Bebida b) {
		super(b);
		this.cantidad = b.getCantidad();

	}

	// Métodos
	/*
	 * @Override public boolean validarId(String id) { return
	 * id.matches("^([B][0-9]{2})$"); }
	 */

	// get
	public int getCantidad() {
		return cantidad;
	}

	// set
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "\tBebida [" + super.toString() + "cantidad=" + cantidad + "]\n";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Bebida) {
			Bebida other = (Bebida) obj;
			if (this.getId().equals(other.getId()))
				return true;
		}
		return false;
	}

}
