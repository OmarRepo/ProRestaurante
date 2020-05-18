package modelo;

import java.util.HashSet;

public class Carta {
	
	private HashSet<Consumible> listaConsumibles;
	
	//Contructores
	public Carta() {
		listaConsumibles = new HashSet<Consumible>();
	}
	public Carta(HashSet<Consumible> listaMenus) {
		this.listaConsumibles = listaMenus;
	}
	
	//Metodos
	public void anadirMenu(Menu menu) {
		
	}
	public void eliminarMenu(int id) {
		
	}
	
	//Get
	public HashSet<Consumible> getListaConsumibles() {
		return listaConsumibles;
	}
	//Set
	public void setListaConsumibles(HashSet<Consumible> listaConsumibles) {
		this.listaConsumibles = listaConsumibles;
	}
	@Override
	public String toString() {
		return "Carta [listaMenus=" + listaConsumibles + "]";
	}
	
}
