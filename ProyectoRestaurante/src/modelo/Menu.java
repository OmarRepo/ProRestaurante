package modelo;

import java.util.HashSet;

public class Menu implements Consumibles{
	
	private int idMenu;
	private HashSet<Plato> listaPLatos;
	private String diaSemana;
	private double precio;
	
	//Constructores
	public Menu(int idMenu, HashSet<Plato> listaPLatos, String diaSemana, double precio) {
		this.idMenu = idMenu;
		this.listaPLatos = listaPLatos;
		this.diaSemana = diaSemana;
		this.precio = precio;
	}
	
	//Metodos
	
	public void anadirPlato(Plato plato) {
		
	}
	
	public void eliminarPlato(int id) {
		
	}
	
	//Get
	public int getIdMenu() {
		return idMenu;
	}

	public HashSet<Plato> getListaPLatos() {
		return listaPLatos;
	}

	public String getDiaSemana() {
		return diaSemana;
	}

	public double getPrecio() {
		return precio;
	}
	
	
	//Set
	public void setIdMenu(int idMenu) {
		this.idMenu = idMenu;
	}

	public void setListaPLatos(HashSet<Plato> listaPLatos) {
		this.listaPLatos = listaPLatos;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Menu [idMenu=" + idMenu + ", listaPLatos=" + listaPLatos + ", diaSemana=" + diaSemana
				+ ", precio=" + precio + "]";
	}
	
	
	
}
