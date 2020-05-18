package modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class Pedido {

	private String idPedido;
	private int idMesa;
	private HashMap<String, Integer> consumibles;
	private double precio;
	private ESTADO_PEDIDO estado;

	// Constructores
	public Pedido(String idPedido, int iDmesa, HashMap<String, Integer> consumibles) {
		this.idPedido = idPedido;
		this.idMesa = iDmesa;
		this.consumibles = consumibles;
		this.precio = 0;
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
/*
	public boolean comprobarPedido(Carta carta, AlmacenCutre almacenCutre) {
		for (String idConsumible : consumibles.keySet()) {
			if (idConsumible.startsWith("B")) {
				if (almacenCutre.comprobarDisponibilidadBebida(bebida)) {
					return true;
				}
			}

			if (idConsumible.startsWith("P")) {

			}

			if (idConsumible.startsWith("M")) {

			}

		}

		return true;
	}
	
	
	public void consultarIngredientesPlato() {

	}
	
	
	public void consultarConsumiblesMenu() {
		
	}
	*/

	// antes public void pagar()

	/**
	 * recorre el HashSet consumibles y va sumando el precio de los consumibles
	 * 
	 */
	public void calcularPrecio(Carta carta) {
		Iterator<String> it = consumibles.keySet().iterator();
		Iterator<Consumible> itCarta = carta.getListaConsumibles().iterator();

		while (it.hasNext()) {
			String key = it.next();
			while (itCarta.hasNext()) {
				Consumible consumible = (Consumible) itCarta.next();
				if (key.equalsIgnoreCase(consumible.getId())) {
					precio += consumible.getPrecio();
				}
			}
		}
	}

	/**
	 * crea y almacena en un fichero de texto la factura con los datos del pedido
	 * 
	 * @throws IOException
	 */

	public void imprimirFactura() throws IOException {
		File f = new File("factura.txt");

		if (!f.exists()) {
			f.createNewFile();
		}
		FileWriter fw = new FileWriter(f, true);
		BufferedWriter bw = new BufferedWriter(fw);

		String texto = this.toString() + "\n";

		bw.write(texto);
		bw.close();

	}

	// Get
	public String getIdPedido() {
		return idPedido;
	}

	public int getIdMesa() {
		return idMesa;
	}

	public HashMap<String, Integer> getConsumibles() {
		return consumibles;
	}

	public double getPrecio() {
		return precio;
	}

	public ESTADO_PEDIDO getEstado() {
		return estado;
	}

	// Set
	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}

	public void setIdMesa(int iDmesa) {
		this.idMesa = iDmesa;
	}

	public void setConsumibles(HashMap<String, Integer> consumibles) {
		this.consumibles = consumibles;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public void setEstado(ESTADO_PEDIDO estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Pedido [numeroPedido=" + idPedido + ", iDmesa=" + idMesa + ", consumibles=" + consumibles + ", precio="
				+ precio + ", estado=" + estado + "]";
	}

}
