package modelo;

import java.util.Arrays;

public class Restaurante {
	
	private Carta carta;
	private Mesa[] Mesa;
	private Empleado[] listaEmpleados;
	private Almacen almacen;
	
	//Constructores
	public Restaurante(Carta carta, modelo.Mesa[] mesa, Empleado[] listaEmpleados, Almacen almacen) {
		super();
		this.carta = carta;
		Mesa = mesa;
		this.listaEmpleados = listaEmpleados;
		this.almacen = almacen;
	}
	
	//Get
	public Carta getCarta() {
		return carta;
	}

	public Mesa[] getMesa() {
		return Mesa;
	}

	public Empleado[] getListaEmpleados() {
		return listaEmpleados;
	}

	public Almacen getAlmacen() {
		return almacen;
	}
	
	//Set
	public void setCarta(Carta carta) {
		this.carta = carta;
	}

	public void setMesa(Mesa[] mesa) {
		Mesa = mesa;
	}

	public void setListaEmpleados(Empleado[] listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}

	@Override
	public String toString() {
		return "Restaurante [carta=" + carta + ", Mesa=" + Arrays.toString(Mesa) + ", almacen=" + almacen + "]";
	}
	
	
	
}
