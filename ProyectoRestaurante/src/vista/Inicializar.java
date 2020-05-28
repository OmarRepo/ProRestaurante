package vista;

import java.io.IOException;
import java.util.HashMap;

import modelo.AlmacenCutre;
import modelo.Bebida;
import modelo.Carta;
import modelo.ConexionBBDD;
import modelo.ESTADO_PEDIDO;
import modelo.Ingrediente;
import modelo.Menu;
import modelo.Mesa;
import modelo.Pedido;
import modelo.Plato;
import modelo.Restaurante;
import modelo.TIPO_PLATO;

public class Inicializar {
	
	
	public static void AnadirConsumible(HashMap<String, Integer> consumibles, String idConsumible, Integer cantidad) {
		consumibles.put(idConsumible, cantidad);
	}
	
	/*public static void pagarPedido(Restaurante res) throws IOException {
		System.out.format("%s\n", "Generando factura...");
		for (Mesa m : res.getListaMesas()) {
			for (Pedido p : m.getPedidos()) {
				p.imprimirFactura();
			}
		}
	}*/
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConexionBBDD.setUsuario("resadmin");
		ConexionBBDD.setContrasena("resadmin123");
		new VentanaLogin();
	}

}
