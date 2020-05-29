package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class RealizarPedido {

	private HashMap<String, Integer> ingredientes; // almacena el id y la cantidad necesaria de cada ingrediente para
													// todos los platos y menús del pedido asociado
	private HashMap<String, Integer> bebidas; // almacena el id y la cantidad necesaria de cada bebida para todas las
												// bebidas y menús del pedido asociado

	// Constructor

	public RealizarPedido() {
		ingredientes = new HashMap<String, Integer>();
		bebidas = new HashMap<String, Integer>();
	}

	// Métodos

	public void cargarIdIngredientesDesdeBBDD() throws SQLException, ClassNotFoundException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet resul = consulta.executeQuery("SELECT * FROM INGREDIENTES");
		String clave = "";
		while (resul.next()) {
			clave = resul.getString("ID_INGREDIENTE");
			ingredientes.put(clave, 0);// añadimos a nuestro HashMap el ID_INGREDIENTE de la BB.DD como clave e
										// inicializamos la cantidad a 0(contador de los ingredientes necesarios para el
										// pedido)
		}
	}

	public void cargarIdBebidasDesdeBBDD() throws SQLException, ClassNotFoundException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet resul = consulta.executeQuery("SELECT * FROM BEBIDAS");
		String clave = "";
		while (resul.next()) {
			clave = resul.getString("ID_BEBIDA");
			ingredientes.put(clave, 0);// añadimos a nuestro HashMap el ID_INGREDIENTE de la BB.DD como clave e
										// inicializamos la cantidad a 0(contador de los ingredientes necesarios para el
										// pedido)
		}
	}

	public void sumarIngredientesPlato(HashMap<String, Integer> Plato) {
		int cantidadIngredientesPlato;
		for (String idIngrediente : Plato.keySet()) {
			cantidadIngredientesPlato = Plato.get(idIngrediente);
			actualizarCantidadIngrediente(idIngrediente, cantidadIngredientesPlato);// ahora permite añadir toda la cantidad
																				// al mismo tiempo si un plato tiene
																				// varias unidades del mismo ingrediente
		}
	}

	/**
	 * 
	 * @param idIngrediente
	 */
	public void actualizarCantidadIngrediente(String idIngrediente, int cantidad) {

		int cantidadIngredientesPedido = ingredientes.get(idIngrediente);
		ingredientes.replace(idIngrediente, cantidadIngredientesPedido + cantidad);
	}

	/**
	 * 
	 * @param idBebida
	 */
	public void actualizarCantidadBebidas(String idBebida) {
		int cantidadBebidasPedido = bebidas.get(idBebida);
		bebidas.replace(idBebida, cantidadBebidasPedido + 1);
	}
	
	
	// OPCIÓN 1: HACIENDO USO DE TRANSACCIONES commit() y rollback
	// SÓLO SE EJECUTAN LAS CONSULTAS SI PUEDEN EJECUTARSE TODAS (HAY CANTIDAD
	// SUFICIENTE DE TODAS LAS BEBIDAS E INGREDIENTES) O NINUNGA SI FALTA ALGO
	public void procesarPedido() {
		try {
			ConexionBBDD.getConnection().setAutoCommit(false);//deshabilitamos el auto-commit poniéndolo a false
			Statement consulta = ConexionBBDD.getConnection().createStatement();
			
			//añadimos las consultas UPDATE de actualización de la cantidad almacenada en la BB.DDD
			
			//ingredientes
			for (String idIngrediente : ingredientes.keySet()) {
				int cantidadIngredientesPedido = ingredientes.get(idIngrediente);
				String SQL="UPDATE INGREDIENTES SET ALMACENADO=" +cantidadIngredientesPedido+"WHERE" + "'" + idIngrediente + "'";
				consulta.addBatch(SQL);
			}
			//bebidas
			for (String idBebida : bebidas.keySet()) {
				int cantidadBebidasPedido = bebidas.get(idBebida);
				String SQL="UPDATE BEBIDAS SET ALMACENADO=" +cantidadBebidasPedido+"WHERE" + "'" + idBebida + "'";
				consulta.addBatch(SQL);
				
			}
			
			int count[]=consulta.executeBatch();
			ConexionBBDD.getConnection().commit();//si no hay ningun error y no se mete al catch
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				ConexionBBDD.getConnection().rollback();//si algo ha ido mal se deshacen los cambios
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		//NOTA: PENDIENTE mirar alguna forma de recuperar el ID del ingrediente o bebida (el primero del que no hay suficiente) ó 
		//a poder ser de todos para de alguna forma consultar su cantidad ALMACENADO en la BB.DD y si es muy baja informar al usuario
		//posible utilidad para reponer, informar al cliente
		
		//EN NUESTRO CASO: no mostrar en la carta si no hay suficientes para preparar ningún plato ó informar al usuario para añadir más 
		//mediante la interfaz de nuestra aplicación
		
		
		//EN LA OPCIÓN 2: EJECUTANDO CONSULTAS SOBRE ALMACENADO Y COMPARANDO CON LA CANTIDAD NECESARIA PARA EL PEDIDO, SI HAY POSIBILIDAD DE
		//RECUPERAR EL ID DEL INGREDIENTE O BEBDIA DE LOS QUE NO HAY SUFICIENTE
		
		//PERO IMPORTANTE!!!!: SÓLO DEL PRIMERO (ANTES DE SALIR DEL MÉTODO Y RETORNAR FALSE)

	}
	
	
/*
	//OPCIÓN 2: HACIENDO USO DE BOOLEAN, RETORNAMOS FALSE SI NO HAY CANTIDAD SUFICIENTE DE ALGO
	public boolean comprobarDisponibilidadIngredientes() throws ClassNotFoundException, SQLException {

		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet resul = null;

		for (String idIngrediente : ingredientes.keySet()) {
			int cantidadIngredientesPedido = ingredientes.get(idIngrediente);
			resul = consulta
					.executeQuery("SELECT ALMACENADO FROM INGREDIENTES WHERE ID_PEDIDO =" + "'" + idIngrediente + "'");
			if (cantidadIngredientesPedido > resul.getInt("ALMACENADO")) {// si no hay suficiente cantidad de ingredientes
																// almacenados en la BB.DD necesarios para preparar el
																// pedido
				System.out.format("%s", informarIngredienteInsuficiente(idIngrediente));//PRUEBAS
				return false;
			}
		}

		return true;

	}
	
	//OPCIÓN 2: HACIENDO USO DE BOOLEAN, RETORNAMOS FALSE SI NO HAY CANTIDAD SUFICIENTE DE ALGO
	public boolean comprobarDisponibilidadBebidas() throws ClassNotFoundException, SQLException {

		Statement consulta = ConexionBBDD.getConnection().createStatement();
		ResultSet resul = null;

		for (String idBebida : bebidas.keySet()) {
			int cantidadBebidasPedido = bebidas.get(idBebida);
			resul = consulta
					.executeQuery("SELECT ALMACENADO FROM BEBIDAS WHERE ID_BEBIDA =" + "'" + idBebida + "'");
			if (cantidadBebidasPedido > resul.getInt("ALMACENADO")) {// si no hay suficiente cantidad de ingredientes
																// almacenados en la BB.DD necesarios para preparar el
																// pedido
				System.out.format("%s", informarBebidaInsuficiente(idBebida)); //PRUEBAS
				return false;
			}
		}

		return true;

	}
	
	
	
	*/
	
	/*PRUEBAS*/public String informarIngredienteInsuficiente(String idIngrediente) {
		return idIngrediente;
		
	}
	
	
	
	/*PRUEBAS*/public String informarBebidaInsuficiente(String idBebida) {
		return idBebida;
		
	}
	
	
	

	// getters y setters

	public HashMap<String, Integer> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(HashMap<String, Integer> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public HashMap<String, Integer> getBebidas() {
		return bebidas;
	}

	public void setBebidas(HashMap<String, Integer> bebidas) {
		this.bebidas = bebidas;
	}

	@Override
	public String toString() {
		return "AlmacenCutre\nINGREDIENTES\n" + ingredientes + "\n\n\nBEBIDAS\n" + bebidas + "\n";
	}

}
