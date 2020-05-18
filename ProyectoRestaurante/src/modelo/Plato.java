package modelo;

import java.util.HashSet;
import java.util.Objects;

public class Plato extends Consumible{
	
	private HashSet<Ingrediente> ingredientes;
	private TIPO_PLATO tipo;
	
	//Constructores
	public Plato(String id, String nombre, double precio, HashSet<Ingrediente> ingredientes, TIPO_PLATO tipo) {
		super(id, nombre, precio);
		this.ingredientes = ingredientes;
		this.tipo = tipo;
	}
	public Plato(String id, String nombre, double precio, TIPO_PLATO tipo) {
		super(id, nombre, precio);
		this.ingredientes = new HashSet<Ingrediente>();
		this.tipo = tipo;
	}


	//Metodos
	/**
	 * Añade un ingrediente al hashSet con los ingredientes que forman el plato
	 * @param ingrediente
	 * 
	 */
	public void anadirIngrediente(Ingrediente ingrediente) {
		
		ingredientes.add(ingrediente);
	
	}
	
	/**
	 * Busca en la lista de ingredientes el id pasado como parametro y lo elimina
	 * @param id
	 * @return true si encuentra y elimina el ingrediente, false si no encuentra el id de ingrediente
	 */
	public boolean eliminarIngrediente(String id) {
		
		return ingredientes.removeIf((Ingrediente i) -> i.getIdIngrediente().equalsIgnoreCase(id));
	
	}
	
	//get
	public HashSet<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public TIPO_PLATO getTipo() {
		return tipo;
	}
	
	//set
	public void setIngredientes(HashSet<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public void setTipo(TIPO_PLATO tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Plato) {
			Plato other = (Plato) obj;
			if (this.getId().equalsIgnoreCase(other.getId()))
				return true;
		}
		return false;
	}

}
