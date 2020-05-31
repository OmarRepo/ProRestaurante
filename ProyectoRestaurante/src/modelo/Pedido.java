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
		this.requisitosPedido = null;

	}

	// Métodos

	/**
	 * Genera automaticamente el id de un nuevo pedido
	 * 
	 * @return id
	 */
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
		return id + nPedidos;

	}

	/**
	 * Busca un pedido con el mismo id en la base de datos y devuelve true si existe
	 * y false si no existe
	 * 
	 * @return true si existe el pedido
	 * @return false si no existe el pedido
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
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

	/**
	 * Busca los consumibles del pedido cuyo id sea igual al pasado como parametro
	 * 
	 * @param idPedido
	 * @return hashMap con los consumibles del pedido
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
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

	/**
	 * 
	 * Marca en la base de datos el pedido como cancelado
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void borrarPedido() throws ClassNotFoundException, SQLException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("UPDATE PEDIDOS SET ESTADO = '" + ESTADO_PEDIDO.cancelado + "' WHERE ID_PEDIDO = '"
				+ this.getIdPedido() + "'");
	}

	/**
	 * Asigna en la base de datos un consumible y si cantidad al pedido
	 * 
	 * @param cantidad
	 * @param idConsumible
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void insertarPedidosConsumibles(int cantidad, String idConsumible)
			throws SQLException, ClassNotFoundException {
		Statement consulta;
		consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("INSERT INTO PEDIDOS_CONSUMIBLES (ID_PEDIDO,ID_CONSUMIBLE,CANTIDAD) VALUES ('"
				+ this.getIdPedido() + "','" + idConsumible + "'," + cantidad + ")");
		consulta.close();
	}

	/**
	 * Modifica en la base de datos la cantidad del consumible que se ha pedido en
	 * el pedido
	 * 
	 * @param cantidad
	 * @param idConsumible
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void modificarPedidoConsumible(int cantidad, String idConsumible)
			throws SQLException, ClassNotFoundException {
		Statement consulta;
		consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("UPDATE PEDIDOS_CONSUMIBLES SET CANTIDAD = " + cantidad + " WHERE ID_PEDIDO = '"
				+ this.getIdPedido() + "' AND ID_CONSUMIBLE = '" + idConsumible + "'");
		consulta.close();
	}

	/**
	 * Borra de la base de datos la asignacion del consumible al pedido
	 * 
	 * @param idConsumible
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void cancelarPedidoConsumible(String idConsumible) throws SQLException, ClassNotFoundException {
		Statement consulta;
		consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("DELETE FROM PEDIDOS_CONSUMIBLES WHERE ID_PEDIDO = '" + this.getIdPedido() + "'"
				+ " AND ID_CONSUMIBLE = '" + idConsumible + "'");
		consulta.close();
	}

	/**
	 * 
	 * Modifica en la base de datos el estado del pedido a preparado
	 * 
	 */
	public static void prepararPedido(String idPedido) throws ClassNotFoundException, SQLException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("UPDATE PEDIDOS SET ESTADO = '" + ESTADO_PEDIDO.preparado.name()
				+ "' WHERE ID_PEDIDO = '" + idPedido + "'");
		consulta.close();
	}

	/**
	 * Muestra los datos del pedido
	 * 
	 * @param idPedido
	 * @return cadena Con los datos del pedido a mostrar
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static String mostrarPedido(String idPedido) throws ClassNotFoundException, SQLException {
		HashMap<String, Integer> consumibles = null;
		String cadena = "";
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet result = consulta.executeQuery("SELECT * FROM PEDIDOS WHERE ID_PEDIDO = '" + idPedido + "'");

		if (result.next()) {
			cadena = "Pedido " + result.getString("ID_PEDIDO") + "\n Fecha " + result.getDate("FECHA_CREACION")
					+ "\n Mesa " + result.getInt("MESA") + "\n Camarero " + result.getString("ID_CAMARERO")
					+ "\n  Productos:\n";
			consumibles = buscarConsumibles(idPedido);
			for (String i : consumibles.keySet()) {
				cadena += i + "	" + Consumible.mostrarConsumible(i) + " * " + consumibles.get(i) + "\n";
			}
			cadena += "";

		}
		consulta.close();
		return cadena;

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

				requisitosPedido.actualizarIngredientesRequeridosPlato((consultarIngredientesPlato(idConsumible)),
						consumibles.get(idConsumible));

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
				requisitosPedido.actualizarIngredientesRequeridosPlato((consultarIngredientesPlato(idConsumible)),
						consumibles.get(idConsumible));

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
		ResultSet resul = consulta.executeQuery("SELECT * FROM MENUS_CONSUMIBLES WHERE ID_MENU =" + "'" + idMenu + "'");
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
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * 
	 */
	public void calcularPrecio() throws ClassNotFoundException, SQLException {
		HashMap<String, Integer> consumibles = buscarConsumibles(this.getIdPedido());

		for (String i : consumibles.keySet()) {
			this.setPrecio(
					this.getPrecio() + (Consumible.obtenerPrecioConsumible(this.getIdPedido()) * consumibles.get(i)));
		}
	}

	/**
	 * Crea y almacena en un fichero de texto la factura con los datos del pedido.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void imprimirFactura() throws IOException, ClassNotFoundException, SQLException {
		File f = new File("facturas/factura" + this.getIdPedido() + ".txt");

		if (!f.getParentFile().exists())
			f.getParentFile().mkdir();
		if (!f.exists()) {
			f.createNewFile();
		}
		FileWriter fw = new FileWriter(f, true);
		BufferedWriter bw = new BufferedWriter(fw);

		String texto = mostrarPedido(this.getIdPedido()) + "\n";

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

	public String getIdCocinero() {
		return idCocinero;
	}

	public double getPrecio() {
		return precio;
	}

	public ESTADO_PEDIDO getEstado() {
		return estado;
	}

	public RequisitosPedido getRequisitosPedido() {
		return requisitosPedido;
	}

	// Set

	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}

	public void setIdMesa(int idMesa) {
		this.idMesa = idMesa;
	}

	public void setConsumibles(HashMap<String, Integer> consumibles) {
		this.consumibles = consumibles;
	}

	public void setIdCocinero(String idCocinero) {
		this.idCocinero = idCocinero;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public void setEstado(ESTADO_PEDIDO estado) {
		this.estado = estado;
	}

	public void setRequisitosPedido(RequisitosPedido requisitosPedido) {
		this.requisitosPedido = requisitosPedido;
	}

	// ssustituir consumibles que da el id y la cantidad por el nombre y la
	// cantidad. Para ello, crear un hashMap auxiliar que almacene como clave el id
	// y como valor el nombre para permitir un acceso rápido, aleatroio eficiente y
	// cómodo desde el método toString

	@Override
	public String toString() {
		return "Pedido [numeroPedido=" + idPedido + ", iDmesa=" + idMesa + ", consumibles=" + consumibles + ", precio="
				+ precio + ", estado=" + estado + "]";
	}

}
