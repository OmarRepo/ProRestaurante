package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Proporciona un acceso global a la instancia que permite la conexión a la BB.DD desde todo el programa.
 * Esta clase hace uso del "patrón Singleton", o "patrón de instancia única", que limita la existencia de objetos de una misma 
 * clase a un sólo objeto.
 * Para instanciar la clase hacemos uso del método getConnection(): 
 * 		Connection conexion = ConexionBBDD.getConnection();
 * @see patrón Singleton
 *
 */


public class ConexionBBDD {

	private static Connection conexion = null;
	private static String usuario;
	private static String contrasena;

	/**
	 * Constructor con modificador de acceso private para garantizar una sola instancia
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private ConexionBBDD() throws ClassNotFoundException, SQLException {
		String bd = "XE";
		String url = "jdbc:oracle:thin:@localhost:1521:" + bd;
		String driver = "oracle.jdbc.driver.OracleDriver"; 

		Class.forName(driver);
		conexion = DriverManager.getConnection(url, usuario, contrasena);

	}
	
	

	// métodos

	public static String getUsuario() {
		return usuario;
	}



	public static void setUsuario(String usuario) {
		ConexionBBDD.usuario = usuario;
	}



	public static String getContrasena() {
		return contrasena;
	}



	public static void setContrasena(String contrasena) {
		ConexionBBDD.contrasena = contrasena;
	}



	/**
	 * Instancia si no existe y retorna una conexión abierta con el usuario y password almacenados en los campos static de esta clase.
	 * @return Connection
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * 
	 */
	
	

	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		if (conexion == null) {
			new ConexionBBDD();
		}

		return conexion;
	}

	/**
	 * @throws SQLException
	 * 
	 */

	public static void cerrarConexion() throws SQLException {
		if (conexion != null) {
			conexion.close();
		}

	}

}
