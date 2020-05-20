package modelo;

public class Ingrediente {
	private String id;
	private String nombre;
	private int cantidad;

	// constructor
	public Ingrediente(String id, String nombre, int cantidad) {
		if (validarId(id)) {
			this.id = id;
		}

		if (this.id != null) {// solo guardamos el resto de datos si el id es correcto
			this.nombre = nombre;
			this.cantidad = cantidad;
		}
	}

	// Métodos
	public boolean validarId(String id) {
		return id.matches("^([I][0-9]{2})$");
	}

	// get
	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public int getCantidad() {
		return cantidad;
	}

	// set
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
		return "\tIngrediente [idIngrediente=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + "]\n";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Ingrediente) {
			Ingrediente other = (Ingrediente) obj;
			if (this.getId().equals(other.getId()))
				return true;
		}
		return false;
	}

}
