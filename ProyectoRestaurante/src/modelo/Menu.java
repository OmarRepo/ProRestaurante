package modelo;

import java.util.HashSet;

public class Menu extends Consumible{
	
	private HashSet<Consumible> listaConsumibles;
	
	//Constructores
	public Menu(String id, String nombre, double precio) {
		super(id, nombre, precio);
	}
	
	//Metodos
	
	public void anadirConsumible(Plato plato) {
		
	}
	
	public void eliminarConsumible(int id) {
		
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

	
	
	
	
	
	
}
