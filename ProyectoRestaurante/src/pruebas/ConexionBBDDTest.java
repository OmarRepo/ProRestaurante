package pruebas;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import modelo.ConexionBBDD;
@RunWith(Parameterized.class)
public class ConexionBBDDTest {
	private String usuario;
	private String contrasena;
	private Class error;
	private Connection conex;
	public ConexionBBDDTest(String usuario, String contrasena, Class error) {
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.error = error;
	}
	@Parameters
	public static Collection<Object[]>usuarios(){
		return Arrays.asList(new Object[][]{{"RESADMIN","resadmin123",null},{"pepe","123",SQLException.class}});
	}
	/**
	 * Se prueba la primera conexion con el objeto
	 */
	@Test
	public void testGetConnection() {
		ConexionBBDD.setContrasena(contrasena);
		ConexionBBDD.setUsuario(usuario);
		try {
			ConexionBBDD.cerrarConexion();
			conex=ConexionBBDD.getConnection();
			assertNotEquals(null,conex);
		} catch (Exception e) {
			assertTrue(error.isInstance(e));
			
		}
	}
	/**
	 * Se prueba la segunda conexion con el objeto
	 */
	@Test
	public void testReconnection() {
		try {
			ConexionBBDD.cerrarConexion();
			conex=ConexionBBDD.getConnection();
			assertNotEquals(null,conex);
		} catch (Exception e) {
			assertTrue(error.isInstance(e));
		}
	}
}
