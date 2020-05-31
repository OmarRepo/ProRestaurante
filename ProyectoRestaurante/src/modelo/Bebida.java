package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class Bebida extends Consumible {

	private int cantidad;

	// Constructor
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
	/*
	 * @Override public boolean validarId(String id) { return
	 * id.matches("^([B][0-9]{2})$"); }
	 */
	
	/**
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
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void modificarBebida() throws SQLException, ClassNotFoundException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		System.out.format("%s \n",consulta.executeUpdate("UPDATE CONSUMIBLES SET NOMBRE = '"+this.getNombre()+"' WHERE ID_CONSUMIBLE = '"+this.getId()+"'"));
		consulta.close();
	}
	
	public void modificarBebida(double precio) throws SQLException, ClassNotFoundException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		System.out.format("%s \n",consulta.executeUpdate("UPDATE CONSUMIBLES SET NOMBRE = '"+this.getNombre()+"', PRECIO = "+precio+" WHERE ID_CONSUMIBLE = '"+this.getId()+"'"));
		consulta.close();
	}
	/**
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void asignarCantidadBebida() throws SQLException, ClassNotFoundException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		System.out.format("%s \n","UPDATE BEBIDAS SET ALMACENADO = "+this.getCantidad()+" WHERE ID_BEBIDA = '"+this.getId()+"'");
		consulta.executeUpdate("UPDATE BEBIDAS SET ALMACENADO = "+this.getCantidad()+" WHERE ID_BEBIDA = '"+this.getId()+"'");
		consulta.close();
	}
	/**
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
	 * @return
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
	 * @return
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
