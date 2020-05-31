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
	// platos y men�s del pedido asociado
	private HashMap<String, Integer> ingredientesRequeridos;

	// almacena el id y la cantidad necesaria de cada bebida para todas las bebidas
	// y men�s del pedido asociado
	private HashMap<String, Integer> bebidasRequeridas;

	// almacena por orden de inserci�n los IDs de los ingredientes cuando no hay
	// suficientes para el pedido y se lanza InsuficentesExcepcion
	private LinkedHashSet<String> ingredientesInsuficientes;

	// Constructor

	public RequisitosPedido() {
		ingredientesRequeridos = new HashMap<String, Integer>();
		bebidasRequeridas = new HashMap<String, Integer>();
		ingredientesInsuficientes = new LinkedHashSet<String>();
	}

	// M�todos

	public void cargarIdIngredientesDesdeBBDD() throws SQLException, ClassNotFoundException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet resul = consulta.executeQuery("SELECT * FROM INGREDIENTES");
		String clave = "";
		while (resul.next()) {
			clave = resul.getString("ID_INGREDIENTE");
			ingredientesRequeridos.put(clave, 0);// a�adimos a nuestro HashMap el ID_INGREDIENTE de la BB.DD como clave
													// e
			// inicializamos la cantidad a 0(contador de los ingredientes necesarios para el
			// pedido)
		}
	}

	/**
	 * A�ade al HashMap ingredientesRequeridos el ID_INGREDIENTE de la BB.DD como
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
			ingredientesRequeridos.put(clave, 0);
		}
	}

	/**
	 * A�ade al HashMap bebidasRequeridas el ID_BEBIDA de la BB.DD como clave e
	 * inicializamos la cantidad a 0 (contador de las bebidas requeridas para el
	 * pedido).
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	public void actualizarIngredientesRequeridosPlato(HashMap<String, Integer> Plato) {
		int cantidadIngredientesPlato;
		for (String idIngrediente : Plato.keySet()) {
			cantidadIngredientesPlato = Plato.get(idIngrediente);
			actualizarIngredientesRequeridos(idIngrediente, cantidadIngredientesPlato);
		}
	}

	/**
	 * A�ade la cantidad del ingrediente que corresponde al ID recibido como
	 * par�metro a la cantidad del mismo en el HashMap ingredientesRequeridos
	 * 
	 * @param idIngrediente
	 * @param cantidad
	 */
	public void actualizarIngredientesRequeridos(String idIngrediente, int cantidad) {

		int cantidadIngredientesPedido = ingredientesRequeridos.get(idIngrediente);
		ingredientesRequeridos.replace(idIngrediente, cantidadIngredientesPedido + cantidad);
	}

	/**
	 * A�ade la cantidad de la bebida que corresponde al ID recibido como par�metro
	 * a la cantidad de la mismo en el HashMap bebidasRequeridas
	 * 
	 * @param idBebida
	 * @param cantidad
	 */
	public void actualizarBebidasRequeridas(String idBebida, int cantidad) {
		int cantidadBebidasPedido = bebidasRequeridas.get(idBebida);
		bebidasRequeridas.replace(idBebida, cantidadBebidasPedido + cantidad);
	}

	/**
	 * HACIENDO USO DE TRANSACCIONES commit() y rollback S�LO SE EJECUTAN LAS
	 * CONSULTAS SI PUEDEN EJECUTARSE TODAS (HAY CANTIDAD SUFICIENTE DE TODAS LAS
	 * BEBIDAS E INGREDIENTES) O NINUNGA SI FALTA ALGO
	 * 
	 * Formaliza el pedido si no se produce ninguna excepci�n.
	 * Hace uso de 
	 * 
	 * @see InsuficentesExcepcion
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void confirmarPedido() throws ClassNotFoundException, SQLException {// COCINERO

		ConexionBBDD.getConnection().setAutoCommit(false);// deshabilitamos el auto-commit
		Statement consulta = ConexionBBDD.getConnection().createStatement();

		// a�adimos las consultas UPDATE de la cantidad almacenada en la BB.DDD

		// ingredientes
		for (String idIngrediente : ingredientesRequeridos.keySet()) {
			int cantidadIngredientesPedido = ingredientesRequeridos.get(idIngrediente);
			String SQL = "UPDATE INGREDIENTES SET ALMACENADO=" + cantidadIngredientesPedido + "WHERE" + "'"
					+ idIngrediente + "'";
			consulta.addBatch(SQL);
		}
		// bebidas
		for (String idBebida : bebidasRequeridas.keySet()) {
			int cantidadBebidasPedido = bebidasRequeridas.get(idBebida);
			String SQL = "UPDATE BEBIDAS SET ALMACENADO=" + cantidadBebidasPedido + "WHERE" + "'" + idBebida + "'";
			consulta.addBatch(SQL);

		}

		int count[] = consulta.executeBatch();
		ConexionBBDD.getConnection().commit();

		// ConexionBBDD.getConnection().rollback();// el rollback() se maneja desde el
		// paquete vista con un .showMessageDialog

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
	// ning�n plato � informar al usuario para a�adir m�s
	// mediante la interfaz de nuestra aplicaci�n

	// EN LA OPCI�N 2: EJECUTANDO CONSULTAS SOBRE ALMACENADO Y COMPARANDO CON LA
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
						"SELECT ALMACENADO FROM INGREDIENTES WHERE ID_PEDIDO =" + "'" + idIngrediente + "'");

				if (cantidadIngredientesPedido > resul.getInt("ALMACENADO")) {// si no hay suficiente cantidad de
																				// ingredientes
					// almacenados en la BB.DD necesarios para preparar el
					// pedido

					throw new InsuficentesExcepcion(idIngrediente);

				}
			} finally {
				ingredientesInsuficientes.add(idIngrediente);// se a�ade por orden de inserci�n
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
				if (cantidadBebidasPedido > resul.getInt("ALMACENADO")) {// si no hay suficiente cantidad de
																			// ingredientes
					// almacenados en la BB.DD necesarios para preparar el
					// pedido

					throw new InsuficentesExcepcion(idBebida);
				}

			} finally {
				ingredientesInsuficientes.add(idBebida);// se a�ade por orden de inserci�n
			}

		}

	}

	// getters y setters

	public HashMap<String, Integer> getIngredientes() {
		return ingredientesRequeridos;
	}

	public void setIngredientes(HashMap<String, Integer> ingredientes) {
		this.ingredientesRequeridos = ingredientes;
	}

	public HashMap<String, Integer> getBebidas() {
		return bebidasRequeridas;
	}

	public void setBebidas(HashMap<String, Integer> bebidas) {
		this.bebidasRequeridas = bebidas;
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