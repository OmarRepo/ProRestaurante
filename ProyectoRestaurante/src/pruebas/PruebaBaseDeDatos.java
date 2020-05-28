package pruebas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.ConexionBBDD;

public class PruebaBaseDeDatos {

	public static void main(String[] args) {
		String usuario="resadmin";
		String contrasena="resadmin123";
		String consulta = "";

		try {
			ConexionBBDD.setUsuario(usuario);
			ConexionBBDD.setContrasena(contrasena);
			Connection conexion = ConexionBBDD.getConnection();

			System.out.println("U"+ConexionBBDD.getUsuario());
			System.out.println("P"+ConexionBBDD.getContrasena());
			consulta = "INSERT INTO INGREDIENTES" + "(ID_INGREDIENTE, NOMBRE, ALMACENADO)" + "VALUES" + "('I03','limon', 2)";
			PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
			ResultSet resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}




