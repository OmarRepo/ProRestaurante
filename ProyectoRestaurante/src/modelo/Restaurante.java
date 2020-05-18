package modelo;

import java.util.Arrays;

public class Restaurante {
	
	private Carta carta;
	private Mesa[] listaMesas;
	private Empleado[] listaEmpleados;
	private Almacen almacen;
	
	//Constructores
	public Restaurante(Carta carta, Mesa[] listalistaMesass, Empleado[] listaEmpleados, Almacen almacen) {
		this.carta = carta;
		this.listaMesas = listalistaMesass;
		this.listaEmpleados = listaEmpleados;
		this.almacen = almacen;
	}
	
	
	@Override
	public String toString() {
		return "Restaurante [carta=" + carta + ", listaMesas=" + Arrays.toString(listaMesas) + ", almacen=" + almacen + "]";
	}
	
	public Carta getCarta() {
		return carta;
	}


	public Mesa[] getListaMesas() {
		return listaMesas;
	}


	public Empleado[] getListaEmpleados() {
		return listaEmpleados;
	}


	public Almacen getAlmacen() {
		return almacen;
	}


	public void setCarta(Carta carta) {
		this.carta = carta;
	}


	public void setListaMesas(Mesa[] listaMesas) {
		this.listaMesas = listaMesas;
	}


	public void setListaEmpleados(Empleado[] listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}


	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}


	/**
	 * busca y devuelve un objeto listaMesas en el array de mesas del restaurante
	 * @param idlistaMesas
	 * @return listaMesas si existe una mesa con el idlistaMesas pasado como paráemtro dentro del array
	 * @return null si no existe una mesa con el idlistaMesas pasado como parámetro
	 */
	
	public Mesa buscarMesa(int idMesa) {
		for (int i = 0; i < listaMesas.length; i++) {
			if(listaMesas[i].getIdMesa()==idMesa) {
				return listaMesas[i];
			}
		}
		
		return null;
	}
	
	
	
}







