package pruebas;

import modelo.Bebida;
import modelo.Carta;
import modelo.Consumible;
import modelo.Ingrediente;
import modelo.Menu;
import modelo.Mesa;
import modelo.Pedido;
import modelo.Plato;
import modelo.Restaurante;
import modelo.TIPO_PLATO;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import modelo.AlmacenCutre;

public class PruebaPedido {

	public static Scanner sc = new Scanner(System.in);

	public static Restaurante prepararRestaurante() {
		// van para almacen
		Ingrediente ig1 = new Ingrediente("I01", "Patatas", 3);
		Ingrediente ig2 = new Ingrediente("I02", "Huevos", 6);
		Ingrediente ig3 = new Ingrediente("I03", "Carne", 7);
		Ingrediente ig4 = new Ingrediente("I04", "Pan", 8);
		Bebida bebida1 = new Bebida("B01", "Coca-cola", 2, 3);
		Bebida bebida2 = new Bebida("B02", "Energety", 2, 4);
		//
		AlmacenCutre alma = new AlmacenCutre();
		alma.anadirProducto(ig1);
		alma.anadirProducto(ig2);
		alma.anadirProducto(ig3);
		alma.anadirProducto(ig4);
		alma.anadirProducto(bebida1);
		alma.anadirProducto(bebida2);
		// van para carta
		Plato plato1 = new Plato("P01", "Tortilla", 3, TIPO_PLATO.entrante);
		plato1.anadirIngrediente("I01", 1);
		plato1.anadirIngrediente("I02", 1);
		Plato plato2 = new Plato("P01", "Hamburguesa", 4, TIPO_PLATO.segundo);
		plato2.anadirIngrediente("I03", 1);
		plato2.anadirIngrediente("I04", 2);
		Plato plato3 = new Plato("P01", "Patatas fritas", 4, TIPO_PLATO.primero);
		plato3.anadirIngrediente("I01", 2);
		Menu menu1 = new Menu("M01", "Especial", 7.30);
		menu1.anadirConsumible(bebida1);
		menu1.anadirConsumible(plato2);
		menu1.anadirConsumible(plato1);
		Menu menu2 = new Menu("M02", "Extraordinario", 9.30);
		menu2.anadirConsumible(bebida2);
		menu2.anadirConsumible(plato2);
		menu2.anadirConsumible(plato3);
		Carta carta = new Carta();
		carta.anadirConsumible(menu1);
		carta.anadirConsumible(menu2);
		carta.anadirConsumible(plato1);
		carta.anadirConsumible(plato2);
		carta.anadirConsumible(plato3);
		carta.anadirConsumible(bebida1);
		carta.anadirConsumible(bebida2);
		Mesa[] mesas = { new Mesa(1), new Mesa(2) };
		return new Restaurante(carta, mesas, null, alma);
	}

	public static void mostrarCarta(Restaurante res) {
		System.out.format("%s\n", "Mostrando carta");
		System.out.format("%s\n", res.getCarta().mostrarCarta());
		System.out.format("%s\n", "----------");
	}

	public static void mostrarAlmacen(Restaurante res) {
		System.out.format("%s\n", "Mostrando almacen");
		System.out.format("%s\n", "Ingredientes");
		System.out.format("%s\n", ((AlmacenCutre) res.getAlmacen()).mostrarIngredientes());
		System.out.format("%s\n", "Bebidas");
		System.out.format("%s\n", ((AlmacenCutre) res.getAlmacen()).mostrarBebidas());
		System.out.format("%s\n", "----------");
	}

	public static void hacerPedido(Restaurante res) {
		String continuar = "";
		Integer cantidad;
		String idConsumible;
		int idMesa;
		System.out.format("%s\n", "Introduce numero de mesa");
		idMesa = Integer.parseInt(sc.nextLine());
		HashMap<String, Integer> consumibles = new HashMap<String, Integer>();
		System.out.format("%s\n", "Introduce numero de pedido");
		String numeroPedido = sc.nextLine();
		do {
			System.out.format("%s\n", "Introduce id de consumible");
			idConsumible = sc.nextLine();
			System.out.format("%s\n", "Introduce cantidad");
			cantidad = Integer.parseInt(sc.nextLine());
			consumibles.put(idConsumible, cantidad);
			System.out.format("%s\n", "¿Quieres continuar pidiendo?(Si/No)");
			continuar = sc.nextLine();
		} while (continuar.equalsIgnoreCase("si"));

		Pedido ped = new Pedido(numeroPedido, idMesa, consumibles);
		if (res.buscarMesa(idMesa) != null)
			;
		{
			res.buscarMesa(idMesa).getPedidos().add(ped);
			System.out.format("%s\n", "Pedido creado y añadido");
			System.out.format("%s\n", "Pedido creado: ");
			System.out.format("%s\n", ped.toString());
		}
	}

	public static void verPedidos(Restaurante res) {
		System.out.format("%s\n", "Viendo pedidos");
		for (Mesa m : res.getListaMesas()) {
			System.out.format("%s\n", "Mesa:" + m.getIdMesa());
			for (Pedido p : m.getPedidos()) {
				System.out.format("%s\n", p.toString());
			}
		}
	}

	public static void pagarPedido(Restaurante res) throws IOException {
		System.out.format("%s\n", "Generando factura...");
		for (Mesa m : res.getListaMesas()) {
			for (Pedido p : m.getPedidos()) {
				p.imprimirFactura();
			}
		}
	}

	public static void main(String[] args) {
		String opcion;
		Restaurante res = prepararRestaurante();
		System.out.format("%s\n", "mostrar carta\nmostrar almacen\nhacer pedido\nver pedidos\npagar\nsalir");
		System.out.format("%s", "Opcion: ");
		opcion = sc.nextLine();
		while (!opcion.equals("salir")) {
			switch (opcion) {
			case "mostrar carta":
				mostrarCarta(res);
				break;
			case "mostrar almacen":
				mostrarAlmacen(res);
				break;
			case "hacer pedido":
				hacerPedido(res);
				break;
			case "ver pedidos":
				verPedidos(res);
				break;
			case "pagar":
				try {
					pagarPedido(res);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
			System.out.format("%s\n", "mostrar carta\nmostrar almacen\nhacer pedido\nver pedidos\npagar\nsalir");
			opcion = sc.nextLine();
		}
	}
}
