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
	
	//Pedidos
	private DefaultTableModel model;
	private JTable tabla;
	private JButton anadirPedido;
	//Panel Introducir Pedidos
			private JLabel pedido;
			private JTextField txtPedido;
			private JLabel mesa;
			private JTextField txtMesa;
			private JLabel consumible;
			private JTextField txtConsumible;
			private JLabel cantidad;
			private JTextField txtCantidad;
			private JPanel panelIntroducirPedido;
	private JButton eliminarPedido;
	private JButton pagarPedido;
		
	//Paneles
	private JTabbedPane pestañas;
	private JPanel panelCarta;
	private JPanel panelPedidos;
	private JScrollPane panelTabla;
	
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
		panelCarta.setLayout(new MigLayout("align 50% 50%"));
		
		mostrarCarta = new JButton("Mostrar Carta");
		mostrarCarta.addActionListener(this);
		
		panelCarta.add(mostrarCarta,"wrap");
		
		pestañas.addTab("Carta", panelCarta);
		
		//Panel Pedidos
		tabla = new JTable();
		
		anadirPedido = new JButton("Añadir Pedido");
		anadirPedido.addActionListener(this);
		eliminarPedido = new JButton("Eliminar Pedido");
		eliminarPedido.addActionListener(this);
		pagarPedido = new JButton("Pagar Pedido");
		pagarPedido.addActionListener(this);
		
		panelPedidos = new JPanel();
		panelPedidos.setLayout(new MigLayout("align 50% 50%"));
		
		panelTabla = new JScrollPane(tabla);
		panelTabla.setSize((int) (panelPedidos.getSize().getWidth()/6) , (int) (panelPedidos.getSize().getHeight()/6));
		
		panelPedidos.add(panelTabla,"wrap");
		panelPedidos.add(anadirPedido,"split3,align 50% 50%");
		panelPedidos.add(eliminarPedido);
		panelPedidos.add(pagarPedido);
		
		pestañas.addTab("Pedidos", panelPedidos);
		
		
		this.add(pestañas);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setResizable(false);
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
		
		String titulos[] = {"ID Pedido","Mesa","Consumible","Cantidad","Estado"};
		model = new DefaultTableModel(null,titulos);
		tabla.setModel(model);
	}
	
	public void introducirPedido() {
		panelIntroducirPedido = new JPanel();
		panelIntroducirPedido.setLayout(new MigLayout());
		pedido = new JLabel("ID Pedido: ");
		txtPedido = new JTextField(4);
		mesa = new JLabel("Mesa: ");
		txtMesa = new JTextField(4);
		consumible = new JLabel("Consumible: ");
		txtConsumible = new JTextField(4);
		cantidad = new JLabel("Cantidad: ");
		txtCantidad = new JTextField(4);
		
		panelIntroducirPedido.add(pedido);
		panelIntroducirPedido.add(txtPedido,"wrap");
		panelIntroducirPedido.add(mesa);
		panelIntroducirPedido.add(txtMesa,"wrap");
		panelIntroducirPedido.add(consumible);
		panelIntroducirPedido.add(txtConsumible,"wrap");
		panelIntroducirPedido.add(cantidad);
		panelIntroducirPedido.add(txtCantidad,"wrap");

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Botones carta
		if (e.getSource().equals(mostrarCarta))
			JOptionPane.showMessageDialog(this, res.getCarta().mostrarCarta());
	
		//Botones Pedidos
		if (e.getSource().equals(anadirPedido)) {
			introducirPedido();
			int resultado = JOptionPane.showConfirmDialog(this, panelIntroducirPedido, "Introduce los parametros del pedido",JOptionPane.OK_CANCEL_OPTION);
			if (resultado == JOptionPane.OK_OPTION) {
				model = (DefaultTableModel) tabla.getModel();
				String[] fila = {txtPedido.getText(),txtMesa.getText(),txtConsumible.getText(),txtCantidad.getText(),modelo.ESTADO_PEDIDO.en_espera.name()};
				model.addRow(fila);
			}
		}
		if (e.getSource().equals(eliminarPedido)) {
			int filaSeleccionada = tabla.getSelectedRow();
			if (filaSeleccionada == -1)
				JOptionPane.showMessageDialog(null, "No hay ninguna fila seleccionada.");
			else {
				model = (DefaultTableModel) tabla.getModel();
				model.removeRow(filaSeleccionada);
			}
		}
		if (e.getSource().equals(pagarPedido)) {
			int filaSeleccionada = tabla.getSelectedRow();
			if (filaSeleccionada == -1)
				JOptionPane.showMessageDialog(null, "No hay ninguna fila seleccionada.");
			else if (model.getValueAt(filaSeleccionada, 4).equals(modelo.ESTADO_PEDIDO.en_espera.name())) {
				try {
					Pruebas.pagarPedido(res);
					model.setValueAt(modelo.ESTADO_PEDIDO.finalizado.name(), filaSeleccionada, 4);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
