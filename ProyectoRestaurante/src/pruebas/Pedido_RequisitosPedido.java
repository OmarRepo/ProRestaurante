package pruebas;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import modelo.Pedido;
import modelo.ConexionBBDD;
public class Pedido_RequisitosPedido {

	public static void main(String[] args) {
		String usuario = "resadmin";
		String contrasena = "resadmin123";
		String consulta = "";

		Pedido pedido = new Pedido("P1");//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< AQUI CAMBIAR EL ID DEL PEDIDO, ES LO ÚNICO
		HashMap<String, Integer> consumibles = new HashMap<String, Integer>();

		ConexionBBDD.setUsuario(usuario);
		ConexionBBDD.setContrasena(contrasena);
		try {
			Connection conexion = ConexionBBDD.getConnection();
			if (pedido.buscarPedido()) {
				System.out.println("PEDIDO ENCONTRADO");
				
				consumibles = Pedido.buscarConsumibles(pedido.getIdPedido());
				pedido.setConsumibles(consumibles);
				System.out.println(consumibles);
				
				pedido.calcularTodosRequisitosPedido();
				System.out.println("LOS REQUSITOS YA ESTÁN CALCULADOS");
				
				System.out.println();
				System.out.println("INGREDIENTES REQUERIDOS");
				HashMap<String, Integer>ingredientesRequeridos=new HashMap<String, Integer>();
				ingredientesRequeridos=pedido.getRequisitosPedido().getIngredientesRequeridos();
				
				for (String idIngrediente :ingredientesRequeridos.keySet() ) {
					
					System.out.println("\tClave: "+idIngrediente+ " Valor: "+ingredientesRequeridos.get(idIngrediente));
				}
				
				
				System.out.println();
				System.out.println("BEBIDAS REQUERIDAS");
				
				HashMap<String, Integer>bebidasRequeridas=new HashMap<String, Integer>();
				bebidasRequeridas=pedido.getRequisitosPedido().getBebidasRequeridas();
				
				for (String idBebida :bebidasRequeridas.keySet() ) {
					
					System.out.println("\tClave: "+idBebida+ " Valor: "+bebidasRequeridas.get(idBebida));
				}
				
				System.out.println();
				System.out.println();
				/*
				pedido.getRequisitosPedido().comprobarDisponibilidadBebidasBBDD();
				System.out.println("LAS BEBIDAS ESTÁN DISPONIBLES");
				
				
				pedido.getRequisitosPedido().comprobarDisponibilidadIngredientesBBDD();
				System.out.println("LOS INGREDIENTES ESTÁN DISPONIBLES");
				*/
				
				if(pedido.getRequisitosPedido().comprobarDisponibilidad()) {
					System.out.println();
					System.out.println();
					
					System.out.println("cantidadIngredientesRequeridos es lo que se va a restar de la BB.DD. para preparar el pedido");
					System.out.println("almacenadoActualizado es la cantidad final tras restar cantidadIngredientesRequeridos con la que se va a hacer el UPDATE en la BB.DD");
					pedido.getRequisitosPedido().confirmarPedido();
					System.out.println("Pedido confirmado");
					System.out.println("Cantidades actualizadas en la BB.DD.");
				}

	
			} else {
				System.out.println("PEDIDO NO ENCONTRADO");

			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}



	}
}



