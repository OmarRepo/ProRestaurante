package modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class Pedido {

	private String idPedido;
	private int idMesa;
	private HashMap<String, Integer> consumibles;// la clave es un id tipo String y la cantidad un integer
	private String idCocinero;
	private double precio;
	private ESTADO_PEDIDO estado;

	// Constructores
	public Pedido(String idPedido, int iDmesa, HashMap<String, Integer> consumibles, ESTADO_PEDIDO estado) {
		this.idPedido = idPedido;
		this.idMesa = iDmesa;
		this.consumibles = consumibles;
		this.precio = 0;
		this.estado = estado;
	}
	
	public Pedido(String idPedido, int iDmesa, HashMap<String, Integer> consumibles,String idCocinero, ESTADO_PEDIDO estado) {
		this.idPedido = idPedido;
		this.idMesa = iDmesa;
		this.consumibles = consumibles;
		this.precio = 0;
		this.estado = estado;
		this.idCocinero = idCocinero;
	}
	
	public Pedido(int iDmesa, HashMap<String, Integer> consumibles,String idCocinero, ESTADO_PEDIDO estado) throws ClassNotFoundException, SQLException {
		this.idPedido = generarIdPedido();
		this.idMesa = iDmesa;
		this.consumibles = consumibles;
		this.precio = 0;
		this.estado = estado;
		this.idCocinero = idCocinero;
	}

	// Metodos
	
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
		try {
			consulta=ConexionBBDD.getConnection().createStatement();
			resultado = consulta.executeQuery("SELECT ID_PEDIDO FROM PEDIDOS WHERE ID_PEDIDO = "+"'"+this.idPedido+"'");
			if (resultado.getFetchSize()==0)
				return false;
			return true;
		} finally {
			consulta.close();
		}

	}
	
	public static HashMap<String, Integer> recorrerPedidos(String idPedido) throws ClassNotFoundException, SQLException {
		
		HashMap<String, Integer> cons = null;
			
		Statement consulta=ConexionBBDD.getConnection().createStatement(); 
		ResultSet resul=consulta.executeQuery("SELECT * FROM CONSUMIBLES");
			
		while(resul.next())
			cons = Pedido.buscarConsumibles(idPedido);
		
		resul.close();
		consulta.close();
		
		return cons;
		
	}
	
	public static HashMap<String, Integer> buscarConsumibles(String idPedido) throws ClassNotFoundException, SQLException {
		HashMap<String, Integer> consumibles = new HashMap<String,Integer>();
		Statement consulta=ConexionBBDD.getConnection().createStatement();
		ResultSet resul=consulta.executeQuery("SELECT * FROM PEDIDOS_CONSUMIBLES WHERE ID_PEDIDO = "+"'"+idPedido+"'");
		while(resul.next()) {
			consumibles.put(resul.getString("ID_CONSUMIBLE"), resul.getInt("CANTIDAD"));
		}
		return consumibles;
	}
	
	/**
	 * Inserta el pedido en la base de datos
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void insertarPedido() throws ClassNotFoundException, SQLException {
			Statement consulta=ConexionBBDD.getConnection().createStatement();
			consulta.executeUpdate("INSERT INTO PEDIDOS (ID_PEDIDO,MESA,ESTADO,PRECIO) VALUES ("+"'"+this.idPedido+"',"+this.idMesa+",'"+this.estado.toString()+"',"+this.precio+")");
	}
	
	/**
	 * Modifica en la base de datos los campos que correspondan al pedido con el mismo id que el del objeto.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void modificarPedido() throws ClassNotFoundException, SQLException {
		Statement consulta=ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("UPDATE PEDIDOS (ID_PEDIDO,MESA,ESTADO,PRECIO) SET MESA ="+this.idMesa+", ESTADO = '"+this.estado.name()+"', PRECIO = "+this.precio+")");
	}
	
	

	public boolean confirmarPedido() {
		// BASE DE DATOS
		return true;

	}

	/**
	 * comprueba si existen suficientes unidades de ingredientes para preparar
	 * platos y men�s o suficientes unidades de bebida
	 * 
	 * @param carta
	 * @param almacenCutre
	 * @return
	 */
	//antes comprobarPedido
	public boolean isComprobado(Carta carta, AlmacenCutre almacenCutre) {
		int noDisponibles;//prueba
		for (String idConsumible : consumibles.keySet()) {

			// si el consumible es una bebida
			if (idConsumible.startsWith("B")) {

				if ((noDisponibles=almacenCutre.comprobarDisponibilidadBebida(idConsumible, consumibles.get(idConsumible))) != 0) {
					return true;// si hay bebidas suficientes
				}
				//inicio prueba
				else {
					System.out.format("%s%d\n","Bebidas no disponibles ",noDisponibles);
				}
				
				//fin prueba

			}

			// si el consumible es un plato
			if (idConsumible.startsWith("P")) {
				if (!almacenCutre.comprobarDisponibilidadPlato(consultarIngredientesPlato(carta, idConsumible))) {
					return true;
				}

			}

			// si el consumible es un men�
			if (idConsumible.startsWith("M")) {
				// Menu menu=(Menu)consumibles.get(idConsumible);
				if (comprobarDisponibilidadMenu(consultarConsumiblesMenu(carta, idConsumible), almacenCutre, carta)) {
					return true;
				}

			}

		}

		return false;
	}

	/**
	 * comprueba que queda en el almac�n tanto la bebida del men� como los
	 * ingredientes necesarios para preparar los platos
	 * 
	 * @param consumibles
	 * @param almacenCutre
	 * @param carta
	 * @return
	 */

	public boolean comprobarDisponibilidadMenu(HashMap<String, Integer> consumibles, AlmacenCutre almacenCutre,
			Carta carta) {
		for (String idConsumible : consumibles.keySet()) {
			// para la bebida del men�
			if (idConsumible.startsWith("B")) {

				if ((almacenCutre.comprobarDisponibilidadBebida(idConsumible, consumibles.get(idConsumible))) != 0) {
					return false;// si hay bebidas suficientes
				}

			}

			// para los platos del men�
			if (idConsumible.startsWith("P")) {
				if (!almacenCutre.comprobarDisponibilidadPlato(consultarIngredientesPlato(carta, idConsumible))) {
					return false;
				}

			}

		}

		return true;// si tanto la bebida como los platos est�n disponibles (existen suficientes
					// ingredientes en el almac�n para prepararlos)
	}

	/**
	 * devuelve un hashMap con los consumibles (platos y bebida) que forman el men�
	 * @param carta
	 * @param idMenu
	 * @return
	 */

	public HashMap<String, Integer> consultarConsumiblesMenu(Carta carta, String idMenu) {
		Iterator<Consumible> itCarta = carta.getListaConsumibles().iterator();

		while (itCarta.hasNext()) {
			Consumible consumible = itCarta.next();
			if (consumible.getId().startsWith("M")) {// primero combrobamos que el consumible de la carta es un men�
														// (m�s eficiente)
				if (consumible.getId().equalsIgnoreCase(idMenu)) {// despu�s comprobamos si es el men� buscado
					Menu menu = (Menu) consumible; // casteamos el objeto consumible para poder acceder al atributo
													// private HashMap<String,Integer> ingredientes;

					// convertimos el atributo de la clase Menu "listaConsumibles" ya que es un
					// hashSet y necesitamos un hashMap ya que es lo que acepta como par�metro
					// nuestro m�todo
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
	 * @param carta
	 * @param idPlato
	 * @return
	 */

	public HashMap<String, Integer> consultarIngredientesPlato(Carta carta, String idPlato) {
		Iterator<Consumible> itCarta = carta.getListaConsumibles().iterator();

		while (itCarta.hasNext()) {
			Consumible consumible = itCarta.next();
			if (consumible.getId().startsWith("P")) {// primero combrobamos que el consumible de la carta es un plato
														// (m�s eficiente)
				if (consumible.getId().equalsIgnoreCase(idPlato)) {// despu�s comprobamos si es el plato buscado
					Plato plato = (Plato) consumible; // casteamos el objeto consumible para poder acceder al atributo
														// private HashMap<String,Integer> ingredientes;
					HashMap<String, Integer> ingredientes = plato.getIngredientes();
					return ingredientes;
				}
			}
		}
		return null;

	}

	// antes public void pagar()

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
		File f = new File("facturas/factura"+this.getIdPedido()+".txt");
		
		if (!f.getParentFile().exists())
			f.getParentFile().mkdir();
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
		return "Pedido \n N� Pedido	" + idPedido + "\n"
				+ " Mesa	" + idMesa + "\n "
						+ "Consumibles	" + consumibles + "\n "
								+ "Total	" + precio + "\n";
	}

	

	

}
