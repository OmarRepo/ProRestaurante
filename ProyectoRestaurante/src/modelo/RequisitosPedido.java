package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * 
 * 
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
	 * a la cantidad de la mismo en el HashMap bebidasRequeridas
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
	 * HACIENDO USO DE TRANSACCIONES commit() y rollback SÓLO SE EJECUTAN LAS
	 * CONSULTAS SI PUEDEN EJECUTARSE TODAS (HAY CANTIDAD SUFICIENTE DE TODAS LAS
	 * BEBIDAS E INGREDIENTES) O NINUNGA SI FALTA ALGO
	 * 
	 * Formaliza el pedido si no se produce ninguna excepción. Hace uso de
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

			int cantidadIngredientesRequeridos = ingredientesRequeridos.get(idIngrediente);
			System.out.println("\tcantidadIngredientesRequeridos: " + cantidadIngredientesRequeridos);// >>>>>>PRUEBA<<<<<<<
			almacenadoActualizado = ingredientesAlmacenadoBBDD(idIngrediente) - cantidadIngredientesRequeridos;
			System.out.println("\talmacenadoActualizado: " + almacenadoActualizado);// >>>>>>PRUEBA<<<<<<<
			System.out.println();// >>>>>>PRUEBA<<<<<<<
			String SQL = "UPDATE INGREDIENTES SET ALMACENADO=" + almacenadoActualizado + "WHERE ID_INGREDIENTE=" + "'"
					+ idIngrediente + "'";
			consulta.addBatch(SQL);
		}

		// bebidas
		System.out.println("BEBIDAS");
		for (String idBebida : bebidasRequeridas.keySet()) {
			int cantidadBebidasRequeridas = bebidasRequeridas.get(idBebida);
			System.out.println("\tcantidadBebidasRequeridas: " + cantidadBebidasRequeridas);// >>>>>>PRUEBA<<<<<<<
			almacenadoActualizado = bebidasAlmacenadoBBDD(idBebida) - cantidadBebidasRequeridas;
			System.out.println("\talmacenadoActualizado: " + almacenadoActualizado);// >>>>>>PRUEBA<<<<<<<
			System.out.println();// >>>>>>PRUEBA<<<<<<<
			String SQL = "UPDATE BEBIDAS SET ALMACENADO=" + almacenadoActualizado + "WHERE ID_BEBIDA=" + "'" + idBebida
					+ "'";
			consulta.addBatch(SQL);

		}
		consulta.executeBatch();
		// int count[] = consulta.executeBatch();
		ConexionBBDD.getConnection().commit();

		ConexionBBDD.getConnection().rollback();// el rollback() se maneja desde el
		// paquete vista con un .showMessageDialog

	}

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

	// EN NUESTRO CASO: no mostrar en la carta si no hay suficientes para preparar
	// ningún plato ó informar al usuario para añadir más
	// mediante la interfaz de nuestra aplicación

	// EN LA OPCIÓN 2: EJECUTANDO CONSULTAS SOBRE ALMACENADO Y COMPARANDO CON LA
	// CANTIDAD NECESARIA PARA EL PEDIDO, SI HAY POSIBILIDAD DE
	// RECUPERAR EL ID DEL INGREDIENTE O BEBDIA DE LOS QUE NO HAY SUFICIENTE

	/**
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InsuficentesExcepcion
	 */
	public void comprobarDisponibilidadIngredientesBBDD()
			throws ClassNotFoundException, SQLException, InsuficentesExcepcion {

		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet resul = null;

		for (String idIngrediente : ingredientesRequeridos.keySet()) {
			try {
				int cantidadIngredientesPedido = ingredientesRequeridos.get(idIngrediente);
				resul = consulta.executeQuery(
						"SELECT ALMACENADO FROM INGREDIENTES WHERE ID_INGREDIENTE =" + "'" + idIngrediente + "'");
				while (resul.next()) {
					if (cantidadIngredientesPedido > resul.getInt("ALMACENADO")) {// si no hay suficiente cantidad de
																					// ingredientes
						// almacenados en la BB.DD necesarios para preparar el
						// pedido

						throw new InsuficentesExcepcion(idIngrediente);

					}
				}
			} finally {
				ingredientesInsuficientes.add(idIngrediente);// se añade por orden de inserción
			}
		}

	}

	/**
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InsuficentesExcepcion
	 */

	public void comprobarDisponibilidadBebidasBBDD()
			throws ClassNotFoundException, SQLException, InsuficentesExcepcion {

		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet resul = null;

		for (String idBebida : bebidasRequeridas.keySet()) {
			try {
				int cantidadBebidasPedido = bebidasRequeridas.get(idBebida);
				resul = consulta
						.executeQuery("SELECT ALMACENADO FROM BEBIDAS WHERE ID_BEBIDA =" + "'" + idBebida + "'");
				while (resul.next()) {
					if (cantidadBebidasPedido > resul.getInt("ALMACENADO")) {// si no hay suficiente cantidad de
																				// ingredientes
						// almacenados en la BB.DD necesarios para preparar el
						// pedido

						throw new InsuficentesExcepcion(idBebida);
					}
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
