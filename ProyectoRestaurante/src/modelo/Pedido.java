package modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

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
	private RequisitosPedido realizarPedido;

	// Constructores
	public Pedido(String idPedido, int iDmesa, HashMap<String, Integer> consumibles, ESTADO_PEDIDO estado) {
		this.idPedido = idPedido;
		this.idMesa = iDmesa;
		this.consumibles = consumibles;
		this.precio = 0;
		this.estado = estado;
		this.realizarPedido = new RequisitosPedido();
	}

	public Pedido(String idPedido, int iDmesa, HashMap<String, Integer> consumibles, String idCocinero,
			ESTADO_PEDIDO estado) {
		this.idPedido = idPedido;
		this.idMesa = iDmesa;
		this.consumibles = consumibles;
		this.precio = 0;
		this.estado = estado;
		this.idCocinero = idCocinero;
		this.realizarPedido = new RequisitosPedido();
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
		String id="P";
		int nPedidos=1;
		try {
			Statement consulta=ConexionBBDD.getConnection().createStatement();
			ResultSet resultado = consulta.executeQuery("SELECT NVL(COUNT(*),0) FROM PEDIDOS");
			resultado.next();
			nPedidos+=resultado.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.format("%s \n",nPedidos+"hola");
		return id+nPedidos;

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
		Statement consulta=ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("UPDATE PEDIDOS SET MESA ="+this.getIdMesa()+", ESTADO = '"+this.getEstado().name()+"', PRECIO = "
		+ this.getPrecio()+" WHERE ID_PEDIDO = '"+this.getIdPedido()+"'");
	}
	
	public void borrarPedido() throws ClassNotFoundException, SQLException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("UPDATE PEDIDOS SET ESTADO = '"+ESTADO_PEDIDO.cancelado+"' WHERE ID_PEDIDO = '"+this.getIdPedido()+"'");
	}
	
	public void insertarPedidosConsumibles(int cantidad, String idConsumible)
			throws SQLException, ClassNotFoundException {
		Statement consulta;
		consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("INSERT INTO PEDIDOS_CONSUMIBLES (ID_PEDIDO,ID_CONSUMIBLE,CANTIDAD) VALUES ('"+this.getIdPedido()+"','"+idConsumible+"',"+cantidad+")");							
		consulta.close();
	}
	
	public void modificarPedidoConsumible(int cantidad, String idConsumible)
			throws SQLException, ClassNotFoundException {
		Statement consulta;
		consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("UPDATE PEDIDOS_CONSUMIBLES SET CANTIDAD = "+cantidad+" WHERE ID_PEDIDO = '"+this.getIdPedido()+"' AND ID_CONSUMIBLE = '"+idConsumible+"'");
		consulta.close();
	}
	
	public void cancelarPedidoConsumible(String idConsumible) throws SQLException, ClassNotFoundException {
		Statement consulta;
		consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("DELETE FROM PEDIDOS_CONSUMIBLES WHERE ID_PEDIDO = '"+this.getIdPedido()+"'"+" AND ID_CONSUMIBLE = '"+idConsumible+"'");
		consulta.close();
	}


	public boolean confirmarPedido() {
		// BASE DE DATOS
		return true;

	}
	
	/*
	//version
	public void procesarPedido() throws ClassNotFoundException, SQLException {
		if(realizarPedido.comprobarDisponibilidadBebidas()) { //si no hay bebidas suficientes en la BB.DD se sale (+eficiente)
			if(realizarPedido.comprobarDisponibilidadIngredientes()) {//si hay existencias suficientes de ingredientes en stock
				
			}
		}
	}
	*/
	

	
	
	/**
	 * comprueba si existen suficientes unidades de ingredientes para preparar
	 * platos y menús o suficientes unidades de bebida
	 * 
	 * @param carta
	 * @param realizarPedido
	 * @return
	 */

	public void actualizarTodosEnRealizarPedido(Carta carta) {

		for (String idConsumible : consumibles.keySet()) {

			// si el consumible es una bebida
			if (idConsumible.startsWith("B")) {
				realizarPedido.actualizarCantidadBebidas(idConsumible);

			}

			// si el consumible es un plato
			if (idConsumible.startsWith("P")) {
				realizarPedido.sumarIngredientesPlato(consultarIngredientesPlato(carta, idConsumible));

			}

			// si el consumible es un menú
			if (idConsumible.startsWith("M")) {
				actualizarPrepararPedidoMenu(consultarConsumiblesMenu(carta, idConsumible), carta);

			}
		}

	}

	/**
	 * comprueba que queda en el almacén tanto la bebida del menú como los
	 * ingredientes necesarios para preparar los platos
	 * 
	 * @param consumibles
	 * @param almacenCutre
	 * @param carta
	 * @return
	 */

	public void actualizarPrepararPedidoMenu(HashMap<String, Integer> consumibles, Carta carta) {

		for (String idConsumible : consumibles.keySet()) {
			// para la bebida del menú
			if (idConsumible.startsWith("B")) {
				realizarPedido.actualizarCantidadBebidas(idConsumible);

			}

			// para los platos del menú
			if (idConsumible.startsWith("P")) {
				realizarPedido.sumarIngredientesPlato(consultarIngredientesPlato(carta, idConsumible));

			}

		}

	}

	/**
	 * devuelve un hashMap con los consumibles (platos y bebida) que forman el menú
	 * 
	 * @param carta
	 * @param idMenu
	 * @return
	 */

	public HashMap<String, Integer> consultarConsumiblesMenu(Carta carta, String idMenu) {// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		Iterator<Consumible> itCarta = carta.getListaConsumibles().iterator();

		while (itCarta.hasNext()) {
			Consumible consumible = itCarta.next();
			if (consumible.getId().startsWith("M")) {// primero combrobamos que el consumible de la carta es un menú
														// (más eficiente)
				if (consumible.getId().equalsIgnoreCase(idMenu)) {// después comprobamos si es el menú buscado
					Menu menu = (Menu) consumible; // casteamos el objeto consumible para poder acceder al atributo
													// private HashMap<String,Integer> ingredientes;

					// convertimos el atributo de la clase Menu "listaConsumibles" ya que es un
					// hashSet y necesitamos un hashMap ya que es lo que acepta como parámetro
					// nuestro método
					// boolean comprobarDisponibilidadMenu(HashMap<String, Integer> consumibles,
					// AlmacenCutre almacenCutre, Carta carta)
					HashSet<Consumible> setListaConsumibles = menu.getListaConsumibles();
					Iterator<Consumible> itSet = setListaConsumibles.iterator();

					HashMap<String, Integer> mapListaConsumibles = new HashMap<String, Integer>();

					while (itSet.hasNext()) {
						Consumible consumibleSet = itSet.next();
						mapListaConsumibles.put(consumibleSet.getId(), 1);// los parametros son id del consumible (plato
																			// o bebida) y la cantidad que vamos a
																			// fijarla en 1
					}

					return mapListaConsumibles;
				}
			}
		}
		return null;

	}

	/**
	 * devuelve un hasMap con los ingredientes que forman un plato
	 * 
	 * @param carta
	 * @param idPlato
	 * @return
	 */

	public HashMap<String, Integer> consultarIngredientesPlato(Carta carta, String idPlato) {// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		Iterator<Consumible> itCarta = carta.getListaConsumibles().iterator();

		while (itCarta.hasNext()) {
			Consumible consumible = itCarta.next();
			if (consumible.getId().startsWith("P")) {// primero combrobamos que el consumible de la carta es un plato
														// (más eficiente)
				if (consumible.getId().equalsIgnoreCase(idPlato)) {// después comprobamos si es el plato buscado
					Plato plato = (Plato) consumible; // casteamos el objeto consumible para poder acceder al atributo
														// private HashMap<String,Integer> ingredientes;
					HashMap<String, Integer> ingredientes = plato.getIngredientes();
					return ingredientes;
				}
			}
		}
		return null;

	}

	/******************************************************************************************************************************************/

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
