package vista;

import java.io.IOException;
import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelo.Bebida;
import modelo.Carta;
import modelo.ConexionBBDD;
import modelo.ESTADO_PEDIDO;
import modelo.Ingrediente;
import modelo.Menu;
import modelo.Mesa;
import modelo.Pedido;
import modelo.Plato;
import modelo.Restaurante;
import modelo.TIPO_PLATO;

public class Inicializar {
	
	
	public static void vaciarTabla(JTable tabla,DefaultTableModel model) {
		int filas = tabla.getRowCount()-1;
		for (int i = filas; i >= 0; i--)
			model.removeRow(i);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConexionBBDD.setUsuario("resadmin");
		ConexionBBDD.setContrasena("resadmin123");
		new VentanaPrincipalCamarero();
	}

}
