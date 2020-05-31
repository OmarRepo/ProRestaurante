package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * Funciona de manera coordinada con la clase Pedido. Existe una relación única
 * de un objeto de tipo RequisitosPedido por cada instancia de la clase Pedido.
 * 
 * De hecho, Pedido tiene como atributo una instancia de esta clase.
 * 
 * Parte de la funcionalidad de Pedido calcula todos los ingredientes y bebidas
 * necesarias para la preparación del pedido.
 * 
 * RequisitosPedido se encarga de alamcenar esas cantidades necesarias
 * calculadas en Pedido, y almacenar: cada bebida (HashMap bebidasRequeridas) e
 * ingrediente (HashMap ingredientesRequeridos) en los HashMap, así como de los
 * ingredientes con cantidad insuficiente (HashMap ingredientesInsuficientes).
 * 
 * Finalmente, tras almacenar todas las cantidades, el método confirmarPedido
 * recorre los HashMap bebidasRequeridas e ingredientesRequeridos y realiza una
 * transacción con un UPDATE en la BB.DD con la cantidad de cada ingrediente o
 * bebida necesaria.
 *
 */

public class RequisitosPedido {
	// almacena el id y la cantidad necesaria de cada ingrediente para todos los
	// platos y menús del pedido asociado
	private HashMap<String, Integer> ingredientesRequeridos;

	// almacena el id y la cantidad necesaria de cada bebida para todas las bebidas
	// y menús del pedido asociado
	private HashMap<String, Integer> bebidasRequeridas;

	// almacena por orden de inserción los IDs de los ingredientes cuando no hay
	// suficientes para el pedido y se lanza InsuficentesExcepcion
	private LinkedHashSet<String> ingredientesInsuficientes;

	// Constructor

	public RequisitosPedido() {
		ingredientesRequeridos = new HashMap<String, Integer>();
		bebidasRequeridas = new HashMap<String, Integer>();
		ingredientesInsuficientes = new LinkedHashSet<String>();
	}

	// Métodos

	public void cargarIdIngredientesDesdeBBDD() throws SQLException, ClassNotFoundException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet resul = consulta.executeQuery("SELECT * FROM INGREDIENTES");
		String clave = "";
		while (resul.next()) {
			clave = resul.getString("ID_INGREDIENTE");
			ingredientesRequeridos.put(clave, 0);// añadimos a nuestro HashMap el ID_INGREDIENTE de la BB.DD como clave
													// e
			// inicializamos la cantidad a 0(contador de los ingredientes necesarios para el
			// pedido)
		}
	}

	/**
	 * Añade al HashMap ingredientesRequeridos el ID_INGREDIENTE de la BB.DD como
	 * clave e inicializamos la cantidad a 0 (contador de los ingredientes
	 * requeridos para el pedido).
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	public void cargarIdBebidasDesdeBBDD() throws SQLException, ClassNotFoundException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet resul = consulta.executeQuery("SELECT * FROM BEBIDAS");
		String clave = "";
		while (resul.next()) {
			clave = resul.getString("ID_BEBIDA");
			bebidasRequeridas.put(clave, 0);
		}
	}

	/**
	 * Añade al HashMap bebidasRequeridas el ID_BEBIDA de la BB.DD como clave e
	 * inicializamos la cantidad a 0 (contador de las bebidas requeridas para el
	 * pedido).
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	public void actualizarIngredientesRequeridosPlato(HashMap<String, Integer> Plato, int cantidadPlatos) {
		int cantidadIngredientesPlato;
		for (String idIngrediente : Plato.keySet()) {
			cantidadIngredientesPlato = Plato.get(idIngrediente);
			actualizarIngredientesRequeridos(idIngrediente, cantidadIngredientesPlato * cantidadPlatos);
		}
	}

	/**
	 * Añade la cantidad del ingrediente que corresponde al ID recibido como
	 * parámetro a la cantidad del mismo en el HashMap ingredientesRequeridos
	 * 
	 * @param idIngrediente
	 * @param cantidad
	 */
	public void actualizarIngredientesRequeridos(String idIngrediente, int cantidad) {
		int cantidadIngredientesRequeridos = 0;
		if (ingredientesRequeridos.containsKey(idIngrediente)) {// si lo contiene añadimos la nueva cantidad
			cantidadIngredientesRequeridos = ingredientesRequeridos.get(idIngrediente);
		}

		ingredientesRequeridos.replace(idIngrediente, cantidadIngredientesRequeridos + cantidad);
	}

	/**
	 * Añade la cantidad de la bebida que corresponde al ID recibido como parámetro
	 * la cantidad de la mismo en el HashMap bebidasRequeridas
	 * 
	 * @param idBebida
	 * @param cantidad
	 */
	public void actualizarBebidasRequeridas(String idBebida, int cantidad) {
		int cantidadBebidasRequeridas = 0;
		if (bebidasRequeridas.containsKey(idBebida)) {// si la contiene añadimos la nueva cantidad
			cantidadBebidasRequeridas += bebidasRequeridas.get(idBebida);
		}

		bebidasRequeridas.replace(idBebida, cantidadBebidasRequeridas + cantidad);
	}

	/**
	 * Procesa el pedido si no se produce ninguna excepción. Realiza los UPDATE
	 * sobre los campos ALMACENADO de la BB.DD. (de las tablas BEBIDAS e
	 * INGREDIENTES) con el cálculo de la diferencia de los ingredientes almacenados
	 * menos los ingredientes requeridos que se van a gastar en la preparación del
	 * pedido.
	 * 
	 * @see InsuficentesExcepcion
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public void confirmarPedido() throws ClassNotFoundException, SQLException {// COCINERO

		ConexionBBDD.getConnection().setAutoCommit(false);// deshabilitamos el auto-commit
		Statement consulta = ConexionBBDD.getConnection().createStatement();

		int almacenadoActualizado;
		// añadimos las consultas UPDATE de la cantidad almacenada en la BB.DDD

		// ingredientes
		System.out.println("INGREDIENTES");
		for (String idIngrediente : ingredientesRequeridos.keySet()) {
			if (ingredientesRequeridos.get(idIngrediente) > 0) {
				int cantidadIngredientesRequeridos = ingredientesRequeridos.get(idIngrediente);
				almacenadoActualizado = ingredientesAlmacenadoBBDD(idIngrediente) - cantidadIngredientesRequeridos;
				String SQL = "UPDATE INGREDIENTES SET ALMACENADO=" + almacenadoActualizado + "WHERE ID_INGREDIENTE="
						+ "'" + idIngrediente + "'";
				consulta.addBatch(SQL);
			}
		}

		// bebidas
		System.out.println("BEBIDAS");

		for (String idBebida : bebidasRequeridas.keySet()) {
			if (bebidasRequeridas.get(idBebida) > 0) {
				int cantidadBebidasRequeridas = bebidasRequeridas.get(idBebida);
				almacenadoActualizado = bebidasAlmacenadoBBDD(idBebida) - cantidadBebidasRequeridas;
				String SQL = "UPDATE BEBIDAS SET ALMACENADO=" + almacenadoActualizado + "WHERE ID_BEBIDA=" + "'"
						+ idBebida + "'";
				consulta.addBatch(SQL);

			}
		}
		consulta.executeBatch();

		ConexionBBDD.getConnection().commit();

		// ConexionBBDD.getConnection().rollback();// el rollback() se maneja desde el
		// paquete vista con un .showMessageDialog

	}

	/**
	 * Devuelve la cantidad de ingredientes almacenados en la tabla INGREDIENTES de
	 * la BB.DD. haciendo la consulta sobre el campo ALMACENADO
	 * 
	 * @param idIngrediente
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public int ingredientesAlmacenadoBBDD(String idIngrediente) throws ClassNotFoundException, SQLException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet resul = null;
		int almacenado = 0;

		resul = consulta
				.executeQuery("SELECT ALMACENADO FROM INGREDIENTES WHERE ID_INGREDIENTE =" + "'" + idIngrediente + "'");
		while (resul.next()) {
			almacenado = resul.getInt("ALMACENADO");
		}
		return almacenado;
	}

	/**
	 * Devuelve la cantidad de bebidas almacenadas en la tabla BEBIDAS de la BB.DD.
	 * haciendo la consulta sobre el campo ALMACENADO
	 * 
	 * @param idBebida
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public int bebidasAlmacenadoBBDD(String idBebida) throws ClassNotFoundException, SQLException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet resul = null;
		int almacenado = 0;

		resul = consulta.executeQuery("SELECT ALMACENADO FROM BEBIDAS WHERE ID_BEBIDA =" + "'" + idBebida + "'");
		while (resul.next()) {
			almacenado = resul.getInt("ALMACENADO");
		}
		return almacenado;

	}

	/**
	 * Devuelve true si hay suficientes ingredientes y bebidas para el pedido en la
	 * BB.DD, tabla INGREDIENTES y tabla BEBIDAS, campos ALMACENADO para ambas.
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InsuficentesExcepcion
	 * @throws SQLException
	 */

	public boolean comprobarDisponibilidad() throws ClassNotFoundException, InsuficentesExcepcion, SQLException {// CAMARERO

		try {
			comprobarDisponibilidadBebidasBBDD();
			comprobarDisponibilidadIngredientesBBDD();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Comprueba que hay suficientes ingredientes para el pedido en la BB.DD, tabla
	 * INGREDIENTES, campo ALMACENADO
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InsuficentesExcepcion
	 */
	public void comprobarDisponibilidadIngredientesBBDD()
			throws ClassNotFoundException, SQLException, InsuficentesExcepcion {

		for (String idIngrediente : ingredientesRequeridos.keySet()) {
			try {
				int cantidadIngredientesPedido = ingredientesRequeridos.get(idIngrediente);

				if (cantidadIngredientesPedido > ingredientesAlmacenadoBBDD(idIngrediente)) {// si no hay suficiente
																								// cantidad de
					// ingredientes
					// almacenados en la BB.DD necesarios para preparar el
					// pedido

					throw new InsuficentesExcepcion(idIngrediente);

				}

			} finally {
				ingredientesInsuficientes.add(idIngrediente);// se añade por orden de inserción
			}
		}

	}

	/**
	 * Comprueba que hay suficientes bebidas para el pedido en la BB.DD, tabla
	 * BEBIDAS, campo ALMACENADO
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InsuficentesExcepcion
	 */

	public void comprobarDisponibilidadBebidasBBDD()
			throws ClassNotFoundException, SQLException, InsuficentesExcepcion {

		for (String idBebida : bebidasRequeridas.keySet()) {
			try {
				int cantidadBebidasPedido = bebidasRequeridas.get(idBebida);

				if (cantidadBebidasPedido > bebidasAlmacenadoBBDD(idBebida)) {// si no hay suficiente cantidad de
																				// ingredientes
					// almacenados en la BB.DD necesarios para preparar el
					// pedido

					throw new InsuficentesExcepcion(idBebida);
				}

			} finally {
				ingredientesInsuficientes.add(idBebida);// se añade por orden de inserción
			}

		}

	}

	// getters y setters

	public HashMap<String, Integer> getIngredientesRequeridos() {
		return ingredientesRequeridos;
	}

	public void setIngredientesRequeridos(HashMap<String, Integer> ingredientesRequeridos) {
		this.ingredientesRequeridos = ingredientesRequeridos;
	}

	public HashMap<String, Integer> getBebidasRequeridas() {
		return bebidasRequeridas;
	}

	public void setBebidasRequeridas(HashMap<String, Integer> bebidasRequeridas) {
		this.bebidasRequeridas = bebidasRequeridas;
	}

	public LinkedHashSet<String> getIngredientesInsuficientes() {
		return ingredientesInsuficientes;
	}

	public void setIngredientesInsuficientes(LinkedHashSet<String> ingredientesInsuficientes) {
		this.ingredientesInsuficientes = ingredientesInsuficientes;
	}

	// toString

	@Override
	public String toString() {
		return "AlmacenCutre\nINGREDIENTES\n" + ingredientesRequeridos + "\n\n\nBEBIDAS\n" + bebidasRequeridas + "\n";
	}

}
