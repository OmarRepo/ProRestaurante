package modelo;

import java.util.HashMap;

public class Cocinero extends Empleado {

	// Constructores
	public Cocinero(String id,String dni, String nombre) {
		super(id, dni, nombre);
	}

	// Metodos
	/**
	 * prepara el pedido una vez que se ha comprobado que se tiene todo lo
	 * necesario. actualiza el stock del almacén restando las cantidades de bebidas
	 * e ingredientes empleadas en los platos y menús del pedido
	 * 
	 * @param pedido
	 * @param carta
	 * @param almacenCutre
	 * @return
	 */
	//antes prepararPedido
	public boolean isPreparado(Pedido pedido, Carta carta, AlmacenCutre almacenCutre) { // Iterator<Consumible> it =
		if (pedido.isComprobado(carta, almacenCutre)) {

			HashMap<String, Integer> ingredientesPlato = new HashMap<String, Integer>();

			HashMap<String, Integer> consumiblesPedido = pedido.getConsumibles();

			HashMap<String, Integer> consumiblesMenu = new HashMap<String, Integer>();

			for (String idConsumible : consumiblesPedido.keySet()) {

				// si el consumible es una bebida
				if (idConsumible.startsWith("B")) {
					almacenCutre.actualizarCantidadBebidas(idConsumible);

				}

				// si el consumible es un plato
				if (idConsumible.startsWith("P")) {
					ingredientesPlato = pedido.consultarIngredientesPlato(carta, idConsumible);
					for (String idIngrediente : ingredientesPlato.keySet()) {
						almacenCutre.actualizarCantidadIngrediente(idIngrediente);
					}
				}

				// si el consumible es un menú
				if (idConsumible.startsWith("M")) {
					consumiblesMenu = pedido.consultarConsumiblesMenu(carta, idConsumible);

					for (String idConsumibleMenu : consumiblesMenu.keySet()) {
						// si el consumible es una bebida
						if (idConsumibleMenu.startsWith("B")) {
							almacenCutre.actualizarCantidadBebidas(idConsumible);

						}

						// si el consumible es un plato
						if (idConsumibleMenu.startsWith("P")) {
							ingredientesPlato = pedido.consultarIngredientesPlato(carta, idConsumible);
							for (String idIngrediente : ingredientesPlato.keySet()) {
								almacenCutre.actualizarCantidadIngrediente(idIngrediente);
							}
						}
					}

				}

			}

			return true;

		}
		return false;

	}

	@Override
	public String toString() {
		return "Cocinero [" + super.toString() + "]";
	}

}
