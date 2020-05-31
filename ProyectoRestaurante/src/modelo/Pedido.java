package modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 
 * 
 *
 */

public class Pedido {

	private String idPedido;
	private int idMesa;
	private HashMap<String, Integer> consumibles;// la clave es un id tipo String y la cantidad un integer
	private String idCocinero;
	private double precio;
	private ESTADO_PEDIDO estado;

	// private HashMap<String, Integer> ingredientesYBebidas;// almacena las
	// cantidades de ingredientes y bebidas para el
	// pedido
	private RequisitosPedido requisitosPedido;

	// Constructores
	public Pedido(String idPedido, int iDmesa, HashMap<String, Integer> consumibles, ESTADO_PEDIDO estado) {
		this.idPedido = idPedido;
		this.idMesa = iDmesa;
		this.consumibles = consumibles;
		this.precio = 0;
		this.estado = estado;
		this.requisitosPedido = null;
	}

	public Pedido(String idPedido, int iDmesa, HashMap<String, Integer> consumibles, String idCocinero,
			ESTADO_PEDIDO estado) {
		this.idPedido = idPedido;
		this.idMesa = iDmesa;
		this.consumibles = consumibles;
		this.precio = 0;
		this.estado = estado;
		this.idCocinero = idCocinero;
		this.requisitosPedido = null;
	}

	public Pedido(String idPedido) {
		this.idPedido = idPedido;
		this.idMesa = 0;
		this.consumibles = null;
		this.precio = 0;
		this.idCocinero = "";
	}

	// Métodos

	public static String generarIdPedido() {
		String id = "P";
		int nPedidos = 1;
		try {
			Statement consulta = ConexionBBDD.getConnection().createStatement();
			ResultSet resultado = consulta.executeQuery("SELECT NVL(COUNT(*),0) FROM PEDIDOS");
			resultado.next();
			nPedidos += resultado.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.format("%s \n", nPedidos + "hola");
		return id + nPedidos;

	}

	public boolean buscarPedido() throws ClassNotFoundException, SQLException {
		Statement consulta = null;
		ResultSet resultado;
		consulta = ConexionBBDD.getConnection().createStatement();
		resultado = consulta
				.executeQuery("SELECT ID_PEDIDO FROM PEDIDOS WHERE ID_PEDIDO = " + "'" + this.idPedido + "'");
		if (resultado.next())
			return true;
		return false;
	}

	public static HashMap<String, Integer> recorrerPedidos(String idPedido)
			throws ClassNotFoundException, SQLException {

		HashMap<String, Integer> cons = null;

		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet resul = consulta.executeQuery("SELECT * FROM CONSUMIBLES");

		while (resul.next())
			cons = Pedido.buscarConsumibles(idPedido);

		resul.close();
		consulta.close();

		return cons;

	}

	public static HashMap<String, Integer> buscarConsumibles(String idPedido)
			throws ClassNotFoundException, SQLException {
		HashMap<String, Integer> consumibles = new HashMap<String, Integer>();
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet resul = consulta
				.executeQuery("SELECT * FROM PEDIDOS_CONSUMIBLES WHERE ID_PEDIDO = " + "'" + idPedido + "'");
		while (resul.next()) {
			consumibles.put(resul.getString("ID_CONSUMIBLE"), resul.getInt("CANTIDAD"));
		}
		return consumibles;
	}

	/**
	 * Inserta el pedido en la base de datos
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void insertarPedido() throws ClassNotFoundException, SQLException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("INSERT INTO PEDIDOS (ID_PEDIDO,MESA,ESTADO,PRECIO) VALUES (" + "'" + this.idPedido
				+ "'," + this.idMesa + ",'" + this.estado.toString() + "'," + this.precio + ")");
	}

	/**
	 * Modifica en la base de datos los campos que correspondan al pedido con el
	 * mismo id que el del objeto.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void modificarPedido() throws ClassNotFoundException, SQLException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("UPDATE PEDIDOS SET MESA =" + this.getIdMesa() + ", ESTADO = '" + this.getEstado().name()
				+ "', PRECIO = " + this.getPrecio() + " WHERE ID_PEDIDO = '" + this.getIdPedido() + "'");
	}

	public void borrarPedido() throws ClassNotFoundException, SQLException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("UPDATE PEDIDOS SET ESTADO = '" + ESTADO_PEDIDO.cancelado + "' WHERE ID_PEDIDO = '"
				+ this.getIdPedido() + "'");
	}

	public void insertarPedidosConsumibles(int cantidad, String idConsumible)
			throws SQLException, ClassNotFoundException {
		Statement consulta;
		consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("INSERT INTO PEDIDOS_CONSUMIBLES (ID_PEDIDO,ID_CONSUMIBLE,CANTIDAD) VALUES ('"
				+ this.getIdPedido() + "','" + idConsumible + "'," + cantidad + ")");
		consulta.close();
	}

	public void modificarPedidoConsumible(int cantidad, String idConsumible)
			throws SQLException, ClassNotFoundException {
		Statement consulta;
		consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("UPDATE PEDIDOS_CONSUMIBLES SET CANTIDAD = " + cantidad + " WHERE ID_PEDIDO = '"
				+ this.getIdPedido() + "' AND ID_CONSUMIBLE = '" + idConsumible + "'");
		consulta.close();
	}

	public void cancelarPedidoConsumible(String idConsumible) throws SQLException, ClassNotFoundException {
		Statement consulta;
		consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("DELETE FROM PEDIDOS_CONSUMIBLES WHERE ID_PEDIDO = '" + this.getIdPedido() + "'"
				+ " AND ID_CONSUMIBLE = '" + idConsumible + "'");
		consulta.close();
	}

	/**
	 * Devuelve un objeto de la clase RequisitosPedido que tiene los tres HashMap
	 * totalmente vacíos: ingredientesRequeridos, bebidasRequeridas e
	 * ingredientesInsuficientes.
	 * 
	 * @see RequisitosPedido
	 * @return
	 */

	public RequisitosPedido inicializarRequisitosPedido() {
		return new RequisitosPedido();
	}

	/**
	 * Calcula todos los ingredientes y/o bebidas requeridas para preparar los
	 * consumibles de un pedido (Bebida, Plato o Menú). Guarda la cantidad de todos
	 * ellos en los HashMap ingredientesRequeridos y bebidasRequeridas del atributo
	 * requisitosPedido, instancia de la clase RequisitosPedido.
	 * 
	 * @see Clase RequisitosPedido
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public void calcularTodosRequisitosPedido() throws ClassNotFoundException, SQLException {
		this.requisitosPedido = inicializarRequisitosPedido();
		requisitosPedido.cargarIdBebidasDesdeBBDD();
		requisitosPedido.cargarIdIngredientesDesdeBBDD();

		for (String idConsumible : consumibles.keySet()) {

			// si el consumible es una bebida
			if (idConsumible.startsWith("B")) {
				requisitosPedido.actualizarBebidasRequeridas(idConsumible, consumibles.get(idConsumible));

			}

			// si el consumible es un plato
			if (idConsumible.startsWith("P")) {

				if (consumibles.get(idConsumible) == 1) {// si la cantidad del mismo plato es 1
					requisitosPedido.actualizarIngredientesRequeridosPlato(consultarIngredientesPlato(idConsumible));
				} else {//si la cantidad del mismo plato es mayor
					HashMap<String, Integer> ingredientesPlato = consultarIngredientesPlato(idConsumible);
					for (int i = 0; i < consumibles.get(idConsumible); i++) {
						requisitosPedido.actualizarIngredientesRequeridosPlato(ingredientesPlato);
					}
				}

			}

			// si el consumible es un menú
			if (idConsumible.startsWith("M")) {
				calcularRequisitosMenu(consultarConsumiblesMenu(idConsumible));

			}
		}

	}

	/**
	 * Calcula todos los ingredientes y/o bebidas requeridas para preparar los
	 * consumibles de un Menu (Bebida y/o Plato). Guarda la cantidad de todos ellos
	 * en los HashMap ingredientesRequeridos y bebidasRequeridas del atributo
	 * requisitosPedido, instancia de la clase RequisitosPedido.
	 * 
	 * @see Clase RequisitosPedido
	 * @param consumibles
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public void calcularRequisitosMenu(HashMap<String, Integer> consumibles)
			throws ClassNotFoundException, SQLException {

		for (String idConsumible : consumibles.keySet()) {
			// para la bebida del menú
			if (idConsumible.startsWith("B")) {
				requisitosPedido.actualizarBebidasRequeridas(idConsumible, consumibles.get(idConsumible));

			}

			// para los platos del menú
			if (idConsumible.startsWith("P")) {
				requisitosPedido.actualizarIngredientesRequeridosPlato(consultarIngredientesPlato(idConsumible));

			}

		}

	}

	/**
	 * Devuelve un HashMap con los consumibles (Plato/s y/o Bebida/s) que forman un
	 * Menú consultando la tabla MENU_CONSUMIBLES de la BB.DD.
	 * 
	 * @param idMenu
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public HashMap<String, Integer> consultarConsumiblesMenu(String idMenu)
			throws ClassNotFoundException, SQLException {
		HashMap<String, Integer> consumiblesMenu = new HashMap<String, Integer>();

		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet resul = consulta.executeQuery("SELECT * FROM MENU_CONSUMIBLES WHERE ID_MENU =" + "'" + idMenu + "'");
		String idConsumible = "";
		Integer cantidad = 0;

		while (resul.next()) {
			idConsumible = resul.getString("ID_CONSUMIBLE");
			cantidad = resul.getInt("CANTIDAD");
			consumiblesMenu.put(idConsumible, cantidad);

		}

		return consumiblesMenu;

	}

	/**
	 * Devuelve un HashMap con los ingredientes que forman un Plato consultando la
	 * tabla PLATO_INGREDIENTES de la BB.DD.
	 * 
	 * @param idPlato
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public HashMap<String, Integer> consultarIngredientesPlato(String idPlato)
			throws ClassNotFoundException, SQLException {
		HashMap<String, Integer> ingredientesPlato = new HashMap<String, Integer>();

		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet resul = consulta
				.executeQuery("SELECT * FROM PLATO_INGREDIENTES WHERE ID_PLATO =" + "'" + idPlato + "'");
		String idIngrediente = "";
		Integer cantidad = 0;

		while (resul.next()) {
			idIngrediente = resul.getString("ID_INGREDIENTE");
			cantidad = resul.getInt("CANTIDAD");
			ingredientesPlato.put(idIngrediente, cantidad);

		}

		return ingredientesPlato;

	}

	/******************************************************************************************************************************************/

	/**
	 * Calcula el precio del pedido. Recorre el HashMap del atributo consumibles y
	 * va sumando el precio de los consumibles.
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
	 * Crea y almacena en un fichero de texto la factura con los datos del pedido.
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

	public String getIdCocinero() {
		return idCocinero;
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

	public void setIdCocinero(String idCocinero) {
		this.idCocinero = idCocinero;
	}

	public RequisitosPedido getRequisitosPedido() {
		return requisitosPedido;
	}

	public void setRequisitosPedido(RequisitosPedido requisitosPedido) {
		this.requisitosPedido = requisitosPedido;
	}

	@Override
	public String toString() {
		return "Pedido [numeroPedido=" + idPedido + ", iDmesa=" + idMesa + ", consumibles=" + consumibles + ", precio="
				+ precio + ", estado=" + estado + "]";
	}

	// ssustituir consumibles que da el id y la cantidad por el nombre y la
	// cantidad. Para ello, crear un hashMap auxiliar que almacene como clave el id
	// y como valor el nombre para permitir un acceso rápido, aleatroio eficiente y
	// cómodo desde el método toString

}
