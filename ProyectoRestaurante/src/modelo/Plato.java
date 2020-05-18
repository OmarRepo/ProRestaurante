package modelo;

import java.util.HashMap;

public class Plato extends Consumible{
	
	private HashMap<String,Integer> ingredientes;
	private TIPO_PLATO tipo;
	
	//Constructores
	public Plato(String id, String nombre, double precio, HashMap<String,Integer> ingredientes, TIPO_PLATO tipo) {
		super(id, nombre, precio);
		this.ingredientes = ingredientes;
		this.tipo = tipo;
	}
	public Plato(String id, String nombre, double precio, TIPO_PLATO tipo) {
		super(id, nombre, precio);
		this.ingredientes = new HashMap<String,Integer>();
		this.tipo = tipo;
	}
	public Plato(Plato p) {
		super(p);
		this.ingredientes = p.getIngredientes();
		this.tipo = p.getTipo();
	}


	//Metodos
	/**
	 * Añade un ingrediente al hashMap con los ingredientes que forman el plato
	 * @param id identificador del ingrediente que vas a añadir
	 * @param cantidad cantidad del ingrediente que necesita el plato
	 * 
	 */
	public void anadirIngrediente(String id, int cantidad) {
		
		ingredientes.put(id, cantidad);
	
	}
	
	/**
	 * Busca en la lista de ingredientes el id pasado como parametro y lo elimina
	 * @param id
	 * @return true si encuentra y elimina el ingrediente, false si no encuentra el id de ingrediente
	 */
	public boolean eliminarIngrediente(String id) {
		
		if (ingredientes.remove(id) != null)
			return true;
		return false;
		
	}
	
	//get
	public HashMap<String,Integer> getIngredientes() {
		return ingredientes;
	}

	public TIPO_PLATO getTipo() {
		return tipo;
	}
	
	//set
	public void setIngredientes(HashMap<String,Integer> ingredientes) {
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
	@Override
	public String toString() {
		return "Plato ["+super.toString()+", tipo=" + tipo + "]";
	}

}
