package modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Pedido {

	private String idPedido;
	private int idMesa;
	private HashMap<String, Integer> consumibles;// la clave es un id tipo String y la cantidad un integer
	private String idCamarero;
	private String idCocinero;
	private double precio;
	private ESTADO_PEDIDO estado;

	// Constructores
	public Pedido(String idPedido, int iDmesa, HashMap<String, Integer> consumibles,String idCamarero, String idCocinero) {
		this.idPedido = idPedido;
		this.idMesa = iDmesa;
		this.consumibles = consumibles;
		this.precio = 0;
		this.estado = ESTADO_PEDIDO.en_espera;
		this.idCamarero = idCamarero;
		this.idCocinero = idCocinero;
	}

	// Metodos
	
	public void insertarPedido() throws ClassNotFoundException, SQLException {
		
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "resadmin", "resadmin123");
		if (connection != null) {
			System.out.format("%s \n","Conexion realizada.");
			PreparedStatement ps = connection.prepareStatement("insert into PEDIDOS (ID_PEDIDO,MESA,)values(?,?,,?,?)");
			ps.setString(1, this.getIdPedido());
			ps.setInt(2, this.getIdMesa());
		}
	}
	
	public boolean cancelarPedido() {
		// BASE DE DATOS

		return true;

	}

	public boolean confirmarPedido() {
		// BASE DE DATOS
		return true;

	}

	/**
	 * comprueba si existen suficientes unidades de ingredientes para preparar
	 * platos y menús o suficientes unidades de bebida
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

			// si el consumible es un menú
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
	 * comprueba que queda en el almacén tanto la bebida del menú como los
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
			// para la bebida del menú
			if (idConsumible.startsWith("B")) {

				if ((almacenCutre.comprobarDisponibilidadBebida(idConsumible, consumibles.get(idConsumible))) != 0) {
					return false;// si hay bebidas suficientes
				}

			}

			// para los platos del menú
			if (idConsumible.startsWith("P")) {
				if (!almacenCutre.comprobarDisponibilidadPlato(consultarIngredientesPlato(carta, idConsumible))) {
					return false;
				}

			}

		}

		return true;// si tanto la bebida como los platos están disponibles (existen suficientes
					// ingredientes en el almacén para prepararlos)
	}

	/**
	 * devuelve un hashMap con los consumibles (platos y bebida) que forman el menú
	 * @param carta
	 * @param idMenu
	 * @return
	 */

	public HashMap<String, Integer> consultarConsumiblesMenu(Carta carta, String idMenu) {
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
	 * @param carta
	 * @param idPlato
	 * @return
	 */

	public HashMap<String, Integer> consultarIngredientesPlato(Carta carta, String idPlato) {
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
	
	public String getIdCamarero() {
		return idCamarero;
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
	
	public void setIdCamarero(String idCamarero) {
		this.idCamarero = idCamarero;
	}

	public void setIdCocinero(String idCocinero) {
		this.idCocinero = idCocinero;
	}

	@Override
	public String toString() {
		return "Pedido [numeroPedido=" + idPedido + ", iDmesa=" + idMesa + ", consumibles=" + consumibles + ", precio="
				+ precio + ", estado=" + estado + "]";
	}

	

	

}
