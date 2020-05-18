package modelo;

import java.util.HashSet;

public class Pedido {
	
	private int numeroPedido;
	private int iDmesa;
	private HashSet<Consumible> consumibles;
	private int numeroClientes;
	private double precio;
	private ESTADO_PEDIDO estado;
	
	//Constructores
	public Pedido(int numeroPedido, int iDmesa, HashSet<Consumible> consumibles, int numeroClientes, double precio) {
		this.numeroPedido = numeroPedido;
		this.iDmesa = iDmesa;
		this.consumibles = consumibles;
		this.numeroClientes = numeroClientes;
		this.precio = precio;
		this.estado = ESTADO_PEDIDO.en_espera;
	}
	
	//Metodos
	
	public void cancelarPedido() {
		
	}
	
	public void confirmarPedido() {
		
	}
	
	public void pagar() {
		
	}
	
	public void imprimirFactura() {
		
	}
	
	//Get
	public int getNumeroPedido() {
		return numeroPedido;
	}

	public int getiDmesa() {
		return iDmesa;
	}

	public HashSet<Consumible> getConsumibles() {
		return consumibles;
	}

	public int getNumeroClientes() {
		return numeroClientes;
	}

	public double getPrecio() {
		return precio;
	}

	public ESTADO_PEDIDO getEstado() {
		return estado;
	}
	
	
	//Set
	public void setNumeroPedido(int numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public void setiDmesa(int iDmesa) {
		this.iDmesa = iDmesa;
	}

	public void setConsumibles(HashSet<Consumible> consumibles) {
		this.consumibles = consumibles;
	}

	public void setNumeroClientes(int numeroClientes) {
		this.numeroClientes = numeroClientes;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public void setEstado(ESTADO_PEDIDO estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Pedido [numeroPedido=" + numeroPedido + ", iDmesa=" + iDmesa + ", consumibles=" + consumibles
				+ ", numeroClientes=" + numeroClientes + ", precio=" + precio + ", estado=" + estado + "]";
	}

	




	
	
	
	
}
