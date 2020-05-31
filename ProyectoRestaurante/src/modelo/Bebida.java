package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class Bebida extends Consumible {

	private int cantidad;

	// Constructores
	public Bebida(String id, String nombre, double precio, int cantidad) {
		super(id, nombre, precio);
		if (this.getId() != null) {// solo guardamos el resto de datos si el id es correcto
			this.cantidad = cantidad;
		}
	}
	public Bebida(String id, String nombre, int cantidad) {
		super(id,nombre,0);
		this.setCantidad(cantidad);
	}
	public Bebida(String id, String nombre, double precio) {
		super(id, nombre, precio);
	}
	public Bebida(Bebida b) {
		super(b);
		this.cantidad = b.getCantidad();

	}
	
	// Métodos
	
	/**
	 * Guarda el id y el nombre de una bebida en la base de datos
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void insertarBebida() throws ClassNotFoundException, SQLException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("INSERT INTO CONSUMIBLES (ID_CONSUMIBLE,NOMBRE,TIPO) VALUES ('" + this.getId()
				+ "','" + this.getNombre() + "', 'Bebida')");
		
	}
	/**
	 * Cambia el nombre de la bebida con el mimso id en la base de datos
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void modificarBebida() throws SQLException, ClassNotFoundException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		System.out.format("%s \n",consulta.executeUpdate("UPDATE CONSUMIBLES SET NOMBRE = '"+this.getNombre()+"' WHERE ID_CONSUMIBLE = '"+this.getId()+"'"));
		consulta.close();
	}
	
	/**
	 * 
	 * Modifica el nombre y le asigna un precio a la bebida con el mismo id en la base de datos
	 * 
	 * @param precio
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void modificarBebida(double precio) throws SQLException, ClassNotFoundException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		System.out.format("%s \n",consulta.executeUpdate("UPDATE CONSUMIBLES SET NOMBRE = '"+this.getNombre()+"', PRECIO = "+precio+" WHERE ID_CONSUMIBLE = '"+this.getId()+"'"));
		consulta.close();
	}
	/**
	 * 
	 * Le asgina a una bebida las unidades que tiene en el almacen (esto se guarda en la tabla bebidas en vez de en consumible
	 * que es donde se indica el precio)
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void asignarCantidadBebida() throws SQLException, ClassNotFoundException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("UPDATE BEBIDAS SET ALMACENADO = "+this.getCantidad()+" WHERE ID_BEBIDA = '"+this.getId()+"'");
		consulta.close();
	}
	/**
	 * 
	 * Elimina la bebida con el mismo id en la base de datos
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void eliminarBebida() throws ClassNotFoundException, SQLException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("DELETE FROM CONSUMIBLES WHERE ID_CONSUMIBLE ='" + this.getId() + "'");
	}
	/**
	 * 
	 * Comprueba si la bebida existe en la base de datos
	 * 
	 * @return true si existe
	 * @return false si no existe
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean existe() throws ClassNotFoundException, SQLException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet resultado = consulta.executeQuery("SELECT * FROM CONSUMIBLES WHERE ID_CONSUMIBLE ='" + this.getId() + "'");
		if (resultado.next())
			return true;
		return false;
	}
	/**
	 * 
	 * Devuelve en una lista las bebidas ordenadas por el id
	 * 
	 * @return lista de tipo LinkedList para que se ordenen por el orden de insercion con las bebidas
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static LinkedList<Bebida> obtenerBebidas() throws ClassNotFoundException, SQLException {
		LinkedList<Bebida> lista = new LinkedList<Bebida>();
		Statement consulta = ConexionBBDD.getConnection().createStatement();	
		ResultSet resul=consulta.executeQuery("SELECT * FROM CONSUMIBLES WHERE TIPO = 'Bebida' ORDER BY ID_CONSUMIBLE");
		Statement consultaCantidad = ConexionBBDD.getConnection().createStatement();
		ResultSet resultCantidad = consultaCantidad.executeQuery("SELECT ALMACENADO FROM BEBIDAS ORDER BY ID_BEBIDA");
		while(resul.next()) {
			resultCantidad.next();
			lista.add(new Bebida(resul.getString(1),resul.getString(2),resul.getDouble(3),resultCantidad.getInt(1)));
		}
		return lista;
	}
	
	// get
	public int getCantidad() {
		return cantidad;
	}

	// set
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "\tBebida [" + super.toString() + "cantidad=" + cantidad + "]\n";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Bebida) {
			Bebida other = (Bebida) obj;
			if (this.getId().equals(other.getId()))
				return true;
		}
		return false;
	}

}
