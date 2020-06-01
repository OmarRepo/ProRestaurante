package modelo;

import java.sql.SQLException;
import java.sql.Statement;
//import java.util.HashMap;
import java.util.HashSet;

public class Menu extends Consumible {

	private HashSet<Consumible> listaConsumibles;
	//private HashMap<String, Integer> listaConsumibles;

	// Constructores
	public Menu(String id, String nombre, double precio) {
		super(id, nombre, precio);
		this.listaConsumibles = new HashSet<Consumible>();
	}

	public Menu(Menu m) {
		super(m);
		this.listaConsumibles = m.getListaConsumibles();
	}

	// Metodos
	/*
	@Override
	public boolean validarId(String id) {
		return id.matches("^([M][0-9]{2})$");
	}
	*/

	/**
	 * Añade un consumible al menu
	 * 
	 * @param consumible
	 * 
	 */
	public void anadirConsumible(Consumible consumible) {
		listaConsumibles.add(consumible);
	}

	/**
	 * Busca en la lista de consumibles el id pasado como parametro y lo elimina
	 * 
	 * @param id
	 * @return true si encuentra y elimina el consumible, false si no encuentra el
	 *         id de consumible
	 */
	public boolean eliminarConsumible(String id) {

		return listaConsumibles.removeIf((Consumible c) -> c.getId().equalsIgnoreCase(id));

	}
	
	/**
	 * Guarda el menu en la base de datos
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void insertarMenu() throws ClassNotFoundException, SQLException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("INSERT INTO CONSUMIBLES (ID_CONSUMIBLE,NOMBRE,PRECIO,TIPO) VALUES ('" + this.getId()
				+ "','" + this.getNombre() + "', "+this.getPrecio()+", 'Menu')");
	}
	
	/**
	 * 
	 * Guarda en la base de datos un consumible del menu y su cantidad
	 * 
	 * @param idConsumible
	 * @param idComponente
	 * @param cantidad
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void insertarMenusConsumibles(String idConsumible,String idComponente,int cantidad)
			throws SQLException, ClassNotFoundException {
		Statement consulta;
		consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("INSERT INTO MENUS_CONSUMIBLES (ID_MENU,ID_CONSUMIBLE,CANTIDAD) VALUES ('"+idConsumible+"','"+idComponente+"',"+cantidad+")");							
		consulta.close();
	}

	public HashSet<Consumible> getListaConsumibles() {
		return listaConsumibles;
	}

	public void setListaConsumibles(HashSet<Consumible> listaConsumibles) {
		this.listaConsumibles = listaConsumibles;
	}

	@Override
	public String toString() {
		return "\n\tMENU (" + super.toString() + "\n" + listaConsumibles + ")\n";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Plato) {
			Plato other = (Plato) obj;
			if (this.getId().equals(other.getId()))
				return true;
		}
		return false;
	}

}
