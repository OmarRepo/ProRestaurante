package modelo;

import java.util.HashSet;

public class Carta {
	
	private HashSet<Consumibles> listaMenus;
	
	//Contructores
	public Carta() {
		listaMenus = new HashSet<Consumibles>();
	}
	public Carta(HashSet<Consumibles> listaMenus) {
		this.listaMenus = listaMenus;
	}
	
	//Metodos
	public void anadirMenu(Menu menu) {
		
	}
	public void eliminarMenu(int id) {
		
	}
	
	//Get
	public HashSet<Consumibles> getListaMenus() {
		return listaMenus;
	}
	//Set
	public void setListaMenus(HashSet<Consumibles> listaMenus) {
		this.listaMenus = listaMenus;
	}
	@Override
	public String toString() {
		return "Carta [listaMenus=" + listaMenus + "]";
	}
	
}
