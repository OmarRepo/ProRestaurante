package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import vista.Inicializar;

public class Ingrediente {
	private String id;
	private String nombre;
	private int cantidad;

	// constructor
	public Ingrediente(String id, String nombre, int cantidad) {
		if (validarId(id)) {
			this.id = id;
		}

		if (this.id != null) {// solo guardamos el resto de datos si el id es correcto
			this.nombre = nombre;
			this.cantidad = cantidad;
		}
	}
	
	
	

	// M�todos
	public boolean validarId(String id) {
		return id.matches("^([I][0-9]{2})$");
	}
	/**
	 * Guarda el ingrediente en la base de datos
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void insertarIngrediente() throws ClassNotFoundException, SQLException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("INSERT INTO INGREDIENTES (ID_INGREDIENTE,NOMBRE,ALMACENADO) VALUES ('" + this.id
				+ "','" + this.nombre + "'," + this.cantidad + ")");
	}
	/**
	 * Modifica el nombre y la cantidad del ingrediente con el mismo id en la base de datos
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void modificarIngrediente() throws SQLException, ClassNotFoundException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("UPDATE INGREDIENTES SET NOMBRE = '"+this.getNombre()+"', ALMACENADO = "+this.getCantidad()+" WHERE ID_INGREDIENTE = '"+this.getId()+"'");
	}
	/**
	 * 
	 * Elimina el ingrediente con el mismo id de la base de datos
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void eliminarIngrediente() throws ClassNotFoundException, SQLException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("DELETE FROM INGREDIENTES WHERE ID_INGREDIENTE ='" + this.id + "'");
	}
	/**
	 * 
	 * comprueba si existe un ingrediente en la base de datos con el mimso id
	 * 
	 * @return true si el ingrediente existe
	 * @return false si no existe
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean existe() throws ClassNotFoundException, SQLException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet resultado = consulta.executeQuery("SELECT * FROM INGREDIENTES WHERE ID_INGREDIENTE ='" + this.id + "'");
		if (resultado.next())
			return true;
		return false;
	}
	/**
	 * 
	 * Busca en la base de datos todos los ingredientes ordenados por id y los devuelve en una lista ordenada por el orden de
	 * inserci�n
	 * 
	 * @return LinkedList lista con los ingredientes ya ordenados por id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static LinkedList<Ingrediente> obtenerIngredientes() throws ClassNotFoundException, SQLException {
		LinkedList<Ingrediente> lista = new LinkedList<Ingrediente>();
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet resul=consulta.executeQuery("SELECT * FROM INGREDIENTES ORDER BY ID_INGREDIENTE");
		while(resul.next()) {
			lista.add(new Ingrediente(resul.getString(1),resul.getString(2),resul.getInt(3)));
		}
		return lista;
	}
	
	// get
	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public int getCantidad() {
		return cantidad;
	}

	// set
	public void setId(String id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "\tIngrediente [idIngrediente=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + "]\n";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Ingrediente) {
			Ingrediente other = (Ingrediente) obj;
			if (this.getId().equals(other.getId()))
				return true;
		}
		return false;
	}

}
