package modelo;

import java.util.Objects;

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
	public Consumible(Consumible c) {
		this.id = c.getId();
		this.nombre = c.getNombre();
		this.precio = c.getPrecio();
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
		return "id=" + id + ", nombre=" + nombre + ", precio=" + precio;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getId(),this.getNombre(),this.getPrecio());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Consumible) {
			Consumible other = (Consumible) obj;
			if (this.getId().equalsIgnoreCase(other.getId()))
				return true;
		}
		return false;
	}
	
	
}
