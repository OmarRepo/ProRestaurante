package vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import modelo.ConexionBBDD;
import modelo.Restaurante;
import net.miginfocom.swing.MigLayout;

public class VentanaPrincipalCocinero extends JFrame implements ActionListener{
	
	private Restaurante res;
	//Almacen
	private JTable ingredientes;
	private ModeloTabla modeloIngredientes;
	
	private JLabel id;

	private JLabel nombre;
	private JTextField txtNombre;
	private JLabel cantidad;
	private JTextField txtCantidad;
	
	private JButton actualizarIngrediente;
	private JButton eliminarIngrediente;
	
	private JPanel panelAlmacen;
	private JScrollPane almacen;
	
	//Recetas
	private JTable consumible;
	private ModeloTabla modeloConsumible;
	private JTable componente;
	private ModeloTabla modeloComponente;
	
	private JPanel panelRecetas;
	private JScrollPane scrollConsumible;
	private JScrollPane scrollComponente;
	private JTabbedPane pestanas;
	
	public VentanaPrincipalCocinero() {
		try {
			res = new Restaurante(false);
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
		String[] cabeceraIngredientes = {"ID_INGREDIENTE", "NOMBRE", "CANTIDAD"};
		modeloIngredientes = new ModeloTabla(null, cabeceraIngredientes);
		ingredientes.setModel(modeloIngredientes);
		almacen = new JScrollPane(ingredientes);
		
		id = new JLabel("123123");
		
		nombre = new JLabel("Nombre");
		txtNombre = new JTextField(5);
		cantidad = new JLabel("Cantidad");
		txtCantidad = new JTextField(5);
		actualizarIngrediente = new JButton("Añadir al Almacén");
		eliminarIngrediente = new JButton("Eliminar del Almacén");
		
		panelAlmacen.add(almacen);
		panelAlmacen.add(id,"skip, wrap");
		panelAlmacen.add(nombre);
		panelAlmacen.add(txtNombre,"wrap");
		panelAlmacen.add(cantidad);
		panelAlmacen.add(txtCantidad,"wrap");
		panelAlmacen.add(actualizarIngrediente,"split2,wrap");
		panelAlmacen.add(eliminarIngrediente,"split2");
		
		pestanas.addTab("Almacén", panelAlmacen);
		VentanaPrincipalCocinero.rellenarTabla(modeloIngredientes, ingredientes, "INGREDIENTES");
		
		consumible = new JTable();
		String[] cabeceraConsumible = {"ID","NOMBRE","PRECIO"};
		modeloConsumible = new ModeloTabla(null, cabeceraConsumible);
		consumible.setModel(modeloConsumible);
		componente = new JTable();
		String[] cabeceraComponente = {"ID","NOMBRE","CANTIDAD",""};
		modeloComponente = new ModeloTabla(null, cabeceraComponente);
		consumible.setModel(modeloComponente);
		
		scrollConsumible = new JScrollPane(consumible);
		scrollComponente = new JScrollPane(componente);
		panelRecetas = new JPanel();
		
		panelRecetas.add(scrollConsumible);
		panelRecetas.add(scrollComponente);
		
		pestanas.addTab("Recetas", panelRecetas);
		
		//FALTA CAMBIAR EL METODO PARA QUE SE ADAPTE SEGUN SEA PLATO O MENU
		VentanaPrincipalCocinero.rellenarTabla(modeloConsumible, consumible, "CONSUMIBLES");
		VentanaPrincipalCocinero.rellenarTabla(modeloComponente, componente, "INGREDIENTES");
		//--------------------------------------------------------------------------------
		
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
	
	public static void rellenarTabla(ModeloTabla modelo,JTable tabla,String tablaBaseDatos) {
		Statement consulta;
		try {
			Inicializar.vaciarTabla(tabla, modelo);
			consulta = ConexionBBDD.getConnection().createStatement();
			ResultSet resul=consulta.executeQuery("SELECT * FROM "+tablaBaseDatos);
		while(resul.next()) {
			String[] fila = {resul.getString(1),resul.getString(2),resul.getString(3)};
			modelo.addRow(fila);
		}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
	
