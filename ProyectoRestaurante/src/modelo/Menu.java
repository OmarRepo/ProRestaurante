package modelo;

import java.util.HashSet;

public class Menu extends Consumible{
	
	private HashSet<Consumible> listaConsumibles;
	
	//Constructores
	public Menu(String id, String nombre, double precio) {
		super(id, nombre, precio);
	}
	
	//Metodos
	
	/**
	 * Añade un consumible al menu
	 * @param consumible
	 * 
	 */
	public void anadirConsumible(Consumible consumible) {
		listaConsumibles.add(consumible);
	}
	
	/**
	 * Busca en la lista de consumibles el id pasado como parametro y lo elimina
	 * @param id
	 * @return true si encuentra y elimina el consumible, false si no encuentra el id de consumible
	 */
	public void eliminarConsumible(String id) {
		
		listaConsumibles.removeIf((Consumible c) -> c.getId().equalsIgnoreCase(id));

	}
	
	public HashSet<Consumible> getListaConsumibles() {
		return listaConsumibles;
	}
	public void setListaConsumibles(HashSet<Consumible> listaConsumibles) {
		this.listaConsumibles = listaConsumibles;
	}

	@Override
	public String toString() {
		return super.toString() +"/n"+listaConsumibles;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Plato) {
			Plato other = (Plato) obj;
			if (this.getId() == other.getId())
				return true;
		}
		return false;
	}

	
	
	
	
	
	
}
