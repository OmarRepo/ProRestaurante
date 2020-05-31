package pruebas;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import modelo.ConexionBBDD;
import modelo.Ingrediente;
@RunWith(Parameterized.class)
public class IngredienteTest {
	private int cantidad2;
	private String nombre2;
	private Ingrediente ingre;
	/*
	 * JUNIT 4 no permite mantener un orden constante en las pruebas, por ello cada prueba ha de tener todos los pasos para que funcione
	 * He marcado con un comentario a la derecha de la linea que realmente pruebo
	 * */
	public IngredienteTest(String ID,String nombre,int cantidad,int cantidad2,String nombre2) {
		ingre=new Ingrediente(ID, nombre, cantidad);
		this.cantidad2=cantidad2;
		this.nombre2=nombre2;
		//Ingredientes utiliza la conexion que utiliza
		ConexionBBDD.setUsuario("RESADMIN");
		ConexionBBDD.setContrasena("resadmin123");
	}
	@Parameters
	public static Collection<Object[]>usuarios(){
		return Arrays.asList(new Object[][]{{"I01","spagetis",0,4,"macarrones"},{"AAA","mayonesa",4,0,"kepchup"}});
	}
	@Test
	public void testInsertarIngrediente() throws ClassNotFoundException, SQLException {
		ingre.eliminarIngrediente();
		ingre.insertarIngrediente();//
		Statement st=ConexionBBDD.getConnection().createStatement();
		assertEquals(true, st.executeQuery("SELECT 1 FROM INGREDIENTES WHERE ID_INGREDIENTE='"+ingre.getId()+"'").next()); 
		ingre.eliminarIngrediente();
	}
	@Test(expected=SQLException.class)
	public void testInsertarIngredienteFallo() throws ClassNotFoundException, SQLException {
		ingre.eliminarIngrediente();
		ingre.insertarIngrediente();
		ingre.insertarIngrediente();//
	}
	@Test
	public void testModificarCantidad() throws ClassNotFoundException, SQLException {
		ingre.eliminarIngrediente();
		ingre.insertarIngrediente();
		ingre.setCantidad(cantidad2);
		ingre.modificarIngrediente();//
		Statement st=ConexionBBDD.getConnection().createStatement();
		ResultSet rs=st.executeQuery("SELECT ALMACENADO FROM INGREDIENTES WHERE ID_INGREDIENTE='"+ingre.getId()+"'");
		rs.next();
		assertEquals(cantidad2, rs.getInt(1));
		ingre.eliminarIngrediente();
	}
	@Test
	public void testModificarNombre() throws ClassNotFoundException, SQLException {
		ingre.eliminarIngrediente();
		ingre.insertarIngrediente();
		ingre.setNombre(nombre2);
		ingre.modificarIngrediente();//
		Statement st=ConexionBBDD.getConnection().createStatement();
		ResultSet rs=st.executeQuery("SELECT NOMBRE FROM INGREDIENTES WHERE ID_INGREDIENTE='"+ingre.getId()+"'");
		rs.next();
		assertEquals(nombre2, rs.getString(1));
		ingre.eliminarIngrediente();
	}
	@Test
	public void testEliminarIngrediente() throws ClassNotFoundException, SQLException {
		ingre.eliminarIngrediente();
		ingre.insertarIngrediente();
		ingre.eliminarIngrediente();//
		Statement st=ConexionBBDD.getConnection().createStatement();
		assertEquals(false, st.executeQuery("SELECT 1 FROM INGREDIENTES WHERE ID_INGREDIENTE='"+ingre.getId()+"'").next());
		ingre.eliminarIngrediente();
	}

}
