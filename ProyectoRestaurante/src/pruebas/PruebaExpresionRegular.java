package pruebas;

import modelo.AlmacenCutre;
import modelo.Bebida;
import modelo.Carta;
import modelo.Ingrediente;
import modelo.Menu;
import modelo.Plato;
import modelo.TIPO_PLATO;

/*
 * Prueba para comprobar si la expresión regular que se alica al atributo ID del constructor de las clases Ingrediente y Consumible 
 * (Plato, Menú y Bebida) funciona adecuadamente.
 * 
 * Además de ello, se prueba si al intentar añadir tanto un Ingrediente como una Bebida al almacén:
 * se añaden o no, ya que si el ID no cumple la expresión regular, toma valor null.
 * 
 * Para ello se ha creado el método boolean validarId(String id) en la clase Consumible y se han hecho algunas modificaciones en el 
 * constructor.
 * 
 * Se ha modificado ligeremente el método anadirProducto de la clase AlmacenCutre, añadiendo la comprobación de si el ID del producto 
 * (Ingrediente o Bebida) es null y en tal caso, no se añade el producto.
 */

public class PruebaExpresionRegular {

	public static void main(String[] args) {
		// cumple la expresión regular
		Ingrediente ig1 = new Ingrediente("I01", "Patatas", 3);
		System.out.format("%s\n", ig1.toString());

		// NO cumple la expresión regular
		Ingrediente ig2 = new Ingrediente("I02", "Ajos", 6);
		System.out.format("%s\n", ig2.toString());

		Ingrediente ig3 = new Ingrediente("I3", "Cebollas", 8);
		System.out.format("%s\n", ig3.toString());

		Ingrediente ig4 = new Ingrediente("I02", "Ajos", 6);
		System.out.format("%s\n", ig4.toString());

		System.out.format("\n");

		// cumple la expresión regular
		Bebida bebida1 = new Bebida("B01", "Coca-cola", 2, 3);
		System.out.format("%s\n", bebida1.toString());

		// NO cumple la expresión regular
		Bebida bebida2 = new Bebida("B02", "Energety", 2, 4);
		System.out.format("%s\n", bebida2.toString());

		Bebida bebida3 = new Bebida("B3", "Naranja", 2, 7);
		System.out.format("%s\n", bebida3.toString());

		System.out.format("\n\n");

		// ALMACEN
		AlmacenCutre alma = new AlmacenCutre();
		alma.anadirProducto(ig1);
		alma.anadirProducto(ig2);
		alma.anadirProducto(ig3);

		alma.anadirProducto(bebida1);
		alma.anadirProducto(bebida2);
		alma.anadirProducto(bebida3);

		System.out.format("%s\n", alma.toString());
		System.out.format("\n\n");
		//

		Plato plato1 = new Plato("P01", "Tortilla", 3, TIPO_PLATO.entrante);
		plato1.anadirIngrediente("I01", 1);
		plato1.anadirIngrediente("I02", 1);
		System.out.format("%s\n", plato1.toString());

		Plato plato2 = new Plato("P02", "Hamburguesa", 4, TIPO_PLATO.segundo);// REVISAR!!!!!! ESTÁ DEJANDO AÑADIR UN
																				// PLATO CON EL MISMO ID QUE EL ANTERIOR
																				// P01
																				// Y EL equals de Plato está bien
		plato2.anadirIngrediente("I03", 1);
		plato2.anadirIngrediente("I04", 2);
		System.out.format("%s\n", plato2.toString());

		Menu menu1 = new Menu("M01", "Especial", 7.30);
		menu1.anadirConsumible(bebida1);
		menu1.anadirConsumible(plato2);
		menu1.anadirConsumible(plato1);
		System.out.format("%s\n", menu1.toString());
		System.out.format("\n\n");

		Carta carta = new Carta();
		carta.anadirConsumible(menu1);

		carta.anadirConsumible(plato1);
		carta.anadirConsumible(plato2);

		carta.anadirConsumible(bebida1);
		carta.anadirConsumible(bebida2);

		System.out.format("%s\n", carta.toString());

	}

}
