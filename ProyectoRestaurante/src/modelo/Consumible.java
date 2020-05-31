package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Objects;

public abstract class Consumible implements Comparable<Consumible> {
	private String id;
	private String nombre;
	private double precio;

	// constructor
	public Consumible(String id, String nombre, double precio) {
		if (validarId(id)) {
			this.id = id;
		}
		// this.id = id;
		if (this.id != null) {//solo guardamos el resto de datos si el id es correcto
		this.nombre = nombre;
		this.precio = precio;
		}
	}

	public Consumible(Consumible c) {
		this.id = c.getId();
		this.nombre = c.getNombre();
		this.precio = c.getPrecio();
	}
	
	//Metodos
	public boolean validarId(String id) {
		switch (id.charAt(0)) {
		//si es una bebida
		case 'B':
			if (id.matches("^([B][0-9]{2})$")) {
				return true;
			}
			break;
		//si es un plato
		case 'P':
			if (id.matches("^([P][0-9]{2})$")) {
				return true;
			}
			break;
		//si es un men�
		case 'M':
			if (id.matches("^([M][0-9]{2})$")) {
				return true;
			}
			break;

		default:
			System.out.format("%s\n","ID incorrecto");
			break;
		}

		return false;

	}
	
	public static void borrarConsumible(String id) throws ClassNotFoundException, SQLException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("DELETE FROM CONSUMIBLES WHERE ID_CONSUMIBLE ='" + id + "'");
	}
	
	public static String buscarConsumible(String id) throws ClassNotFoundException, SQLException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet result = consulta.executeQuery("SELECT * FROM CONSUMIBLES WHERE ID_CONSUMIBLE ='" + id + "'");
		if (result.next())
			return result.getString("NOMBRE")+"	 "+result.getDouble("PRECIO")+"� ";
		return id;
	}
	
	public static double obtenerPrecioConsumible(String id) throws ClassNotFoundException, SQLException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet result = consulta.executeQuery("SELECT * FROM CONSUMIBLES WHERE ID_CONSUMIBLE ='" + id + "'");
		if (result.next())
			return result.getDouble("PRECIO");
		return 0;
	}
	
	public static HashMap<String, Integer> buscarComponentes(String idConsumible, String tipo)
			throws ClassNotFoundException, SQLException {
		HashMap<String, Integer> componente = new HashMap<String, Integer>();
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet resul = null;
		if (tipo.equalsIgnoreCase("menu")) {
			resul = consulta.executeQuery("SELECT * FROM MENUS_CONSUMIBLES WHERE ID_MENU = " + "'" + idConsumible + "'");
		} else if (tipo.equalsIgnoreCase("plato")) {
			resul = consulta.executeQuery("SELECT * FROM PLATO_INGREDIENTES WHERE ID_PLATO = " + "'" + idConsumible + "'");
		}
			
		while (resul.next()) {
			componente.put(resul.getString(2), resul.getInt(3));
		}
		return componente;
	}

	// get
	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public double getPrecio() {
		return precio;
	}

	// set
	public void setId(String id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "id=" + id + ", nombre=" + nombre + ", precio=" + precio;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getId(), this.getNombre(), this.getPrecio());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Consumible) {
			Consumible other = (Consumible) obj;
			if (this.getId().equals(other.getId()))
				return true;
		}
		return false;
	}

	@Override
	public int compareTo(Consumible o) {
		
		return this.getId().compareTo(o.getId());
	
	}

}
