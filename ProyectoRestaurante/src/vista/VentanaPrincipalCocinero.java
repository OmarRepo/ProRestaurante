package vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import modelo.Restaurante;
import net.miginfocom.swing.MigLayout;

public class VentanaPrincipalCocinero extends JFrame implements ActionListener{
	
	private Restaurante res;
	
	private JTable ingredientes;
	private JButton anadirIngrediente;
	private JButton eliminarIngrediente;
	private JButton nuevoPlatoMenu;
	
	
	
	
	private JPanel panelAlmacen;
	private JScrollPane almacen;
	private JTabbedPane pestanas;
	
	public VentanaPrincipalCocinero() {
		try {
			res = new Restaurante();
			crearVentana();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void crearVentana() {
		
		pestanas = new JTabbedPane();
		panelAlmacen = new JPanel();
		panelAlmacen.setLayout(new MigLayout());
		
		ingredientes = new JTable();
		almacen = new JScrollPane(ingredientes);
		
		anadirIngrediente = new JButton("Añadir al Almacén");
		eliminarIngrediente = new JButton("Eliminar del Almacén");
		
		panelAlmacen.add(anadirIngrediente);
		panelAlmacen.add(almacen);
		pestanas.addTab("Almacén", panelAlmacen);
		this.add(pestanas);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Camarero");
		//Con el codigo comentado la ventana adapta su tamaño segun el tamaño de la pantalla
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int height = pantalla.height;
		int width = pantalla.width;
		setSize(width/2, height/2);
	  
	    setLocationRelativeTo(null);
	    setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
	
