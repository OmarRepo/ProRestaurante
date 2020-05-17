package modelo;

import java.util.HashSet;

public class Mesa {
	
	private int idMesa;
	private ESTADO estado;
	private HashSet<Pedido> pedidos;
	
	//Constructores
	public Mesa(int idMesa, ESTADO estado) {
		super();
		this.idMesa = idMesa;
		this.estado = estado;
		this.pedidos = new HashSet<Pedido>();
	}
	
	
	//Get
	public int getIdMesa() {
		return idMesa;
	}

	public ESTADO getEstado() {
		return estado;
	}

	public HashSet<Pedido> getPedidos() {
		return pedidos;
	}

	//Set
	public void setIdMesa(int idMesa) {
		this.idMesa = idMesa;
	}

	public void setEstado(ESTADO estado) {
		this.estado = estado;
	}

	public void setPedidos(HashSet<Pedido> pedidos) {
		this.pedidos = pedidos;
	}


	@Override
	public String toString() {
		return "Mesa [idMesa=" + idMesa + ", estado=" + estado + ", pedidos=" + pedidos + "]";
	}
	
	
}
