package modelo;

import java.util.HashSet;

public class Mesa {
	
	private int idMesa;
	private boolean disponible;
	private HashSet<Pedido> pedidos;
	
	//Constructores
	public Mesa(int idMesa) {
		this.idMesa = idMesa;
		this.disponible = true;
		this.pedidos = new HashSet<Pedido>();
	}
	
	
	//Get
	public int getIdMesa() {
		return idMesa;
	}
	public boolean isDisponible() {
		return disponible;
	}
	public HashSet<Pedido> getPedidos() {
		return pedidos;
	}

	//Set
	public void setIdMesa(int idMesa) {
		this.idMesa = idMesa;
	}
	public void setPedidos(HashSet<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	@Override
	public String toString() {
		return "Mesa [idMesa=" + idMesa + ", disponible=" + disponible + ", pedidos=" + pedidos + "]";
	}



	
}
