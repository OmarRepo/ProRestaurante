package modelo;

import java.util.HashSet;

public class Pedido {
	
	private int numeroPedido;
	private Mesa mesa;
	private HashSet<Menu> menus;
	private int numeroClientes;
	private double precio;
	private boolean estado;
	
	//Constructores
	public Pedido(int numeroPedido, Mesa mesa, HashSet<Menu> menus, int numeroClientes, double precio, boolean estado) {
		super();
		this.numeroPedido = numeroPedido;
		this.mesa = mesa;
		this.menus = menus;
		this.numeroClientes = numeroClientes;
		this.precio = precio;
		this.estado = estado;
	}
	
	//Metodos
	
	public void cancelarPedido() {
		
	}
	
	public void confirmarPedido() {
		
	}
	
	public void pagar() {
		
	}
	
	//Get
	public int getNumeroPedido() {
		return numeroPedido;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public HashSet<Menu> getMenus() {
		return menus;
	}

	public int getNumeroClientes() {
		return numeroClientes;
	}

	public double getPrecio() {
		return precio;
	}

	public boolean isEstado() {
		return estado;
	}
	
	//Set
	public void setNumeroPedido(int numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public void setMenus(HashSet<Menu> menus) {
		this.menus = menus;
	}

	public void setNumeroClientes(int numeroClientes) {
		this.numeroClientes = numeroClientes;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Pedido [numeroPedido=" + numeroPedido + ", mesa=" + mesa + ", menus=" + menus + ", numeroClientes="
				+ numeroClientes + ", precio=" + precio + ", estado=" + estado + "]";
	}
	
	
	
	
}
