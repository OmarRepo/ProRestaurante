package vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
import javax.swing.table.DefaultTableModel;

import modelo.Restaurante;
import net.miginfocom.swing.MigLayout;

public class VentanaPrincipalCamarero extends JFrame implements ActionListener{
	
	private Restaurante res;
	private	HashMap<String, Integer> consumibles;
	
	//Carta
	private JButton mostrarCarta;
	private JLabel idPedido;
	private JTextField txtIdPedido;
	private JLabel mesa;
	private JTextField txtMesa;
	private JLabel consumible;
	private JTextField txtConsumible;
	private JLabel cantidad;
	private JTextField txtCantidad;
	private JButton anadirIngrediente;
	private JButton hacerPedido;
	private JButton pagar;
	
	//Pedidos
	private DefaultTableModel model;
	private JTable tabla;
	//Paneles
	private JTabbedPane pestañas;
	private JPanel panelCarta;
	private JScrollPane panelPedidos;
	
	public VentanaPrincipalCamarero() {
		crearVentana();
		prepararTabla();
		res = Pruebas.prepararRestaurante();
		consumibles = new HashMap<String, Integer>();
	}
	
	public void crearVentana() {
		
		pestañas = new JTabbedPane();
		
		//Panel Carta
		panelCarta = new JPanel();
		panelCarta.setLayout(new MigLayout());
		
		mostrarCarta = new JButton("Mostrar Carta");
		mostrarCarta.addActionListener(this);
		
		idPedido = new JLabel("ID Pedido: ");
		txtIdPedido = new JTextField(4);
		mesa = new JLabel("Mesa: ");
		txtMesa = new JTextField(4);
		consumible = new JLabel("Consumible: ");
		txtConsumible = new JTextField(4);
		cantidad = new JLabel("Cantidad: ");
		txtCantidad = new JTextField(4);
		
		anadirIngrediente = new JButton("Añadir Consumible");
		anadirIngrediente.addActionListener(this);
		hacerPedido = new JButton("Hacer Pedido");
		hacerPedido.addActionListener(this);
		pagar = new JButton("Pagar");
		pagar.addActionListener(this);
		
		panelCarta.add(mostrarCarta,"wrap");
		panelCarta.add(idPedido,"align 50% 50%");
		panelCarta.add(txtIdPedido,"wrap");
		panelCarta.add(mesa);
		panelCarta.add(txtMesa,"wrap");
		panelCarta.add(consumible);
		panelCarta.add(txtConsumible,"wrap");
		panelCarta.add(cantidad);
		panelCarta.add(txtCantidad,"wrap");
		panelCarta.add(anadirIngrediente);
		panelCarta.add(hacerPedido,"split2");
		panelCarta.add(pagar);
		
		pestañas.addTab("Carta", panelCarta);
		
		//Panel Pedidos
		tabla = new JTable();
		panelPedidos = new JScrollPane(tabla);
		
		
		
		//panelPedidos.add(tabla);
		pestañas.addTab("Pedidos", panelPedidos);
		
		
		this.add(pestañas);
		
		
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
	
	public void prepararTabla() {
		
		String titulos[] = {"ID Pedido","Mesa","Consumibles","Estado"};
		model = new DefaultTableModel(null,titulos);
		tabla.setModel(model);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(mostrarCarta))
			JOptionPane.showMessageDialog(this, res.getCarta().mostrarCarta());
		else if (e.getSource().equals(anadirIngrediente)) {
			Pruebas.AnadirConsumible(consumibles, txtConsumible.getText(), Integer.parseInt(txtCantidad.getText()));
		}	
		else if (e.getSource().equals(hacerPedido)) {
			JOptionPane.showMessageDialog(this, Pruebas.hacerPedido(res, consumibles, txtIdPedido.getText(), Integer.parseInt(txtMesa.getText())));
		}
		else if (e.getSource().equals(pagar)) {
			try {
				Pruebas.pagarPedido(res);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
}
