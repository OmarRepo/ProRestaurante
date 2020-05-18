package modelo;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

public class Pedido {

	private int numeroPedido;
	private int iDmesa;
	private HashSet<Consumible> consumibles;
	private int numeroClientes;
	private double precio;
	private ESTADO_PEDIDO estado;

	// Constructores
	public Pedido(int numeroPedido, int iDmesa, HashSet<Consumible> consumibles, int numeroClientes, double precio) {
		this.numeroPedido = numeroPedido;
		this.iDmesa = iDmesa;
		this.consumibles = consumibles;
		this.numeroClientes = numeroClientes;
		this.precio = precio;
		this.estado = ESTADO_PEDIDO.en_espera;
	}

	// Metodos

	public boolean cancelarPedido() {
		// BASE DE DATOS

		return true;

	}

	public boolean confirmarPedido() {
		// BASE DE DATOS
		return true;

	}

	//antes public void pagar()
	
	/** recorre el HashSet consumibles y va sumando el precio de los consumibles
	 * 
	 */
	public void calcularPrecio() {
		Iterator<Consumible> it = consumibles.iterator();
		while (it.hasNext()) {
			Consumible consumible = it.next();
			precio += consumible.getPrecio();
		}
	}
	
	/**
	 * crea y almacena en un fichero de texto la factura con los datos del pedido
	 * @throws IOException
	 */

	public void imprimirFactura() throws IOException {
		File f = new File("factura.txt");

		if (!f.exists()) {
			f.createNewFile();
		}
		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);

		String texto = this.toString();

		bw.write(texto);
		bw.close();

	}

	// Get
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

	// Set
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
