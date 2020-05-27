package vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import modelo.Consumible;
import modelo.Restaurante;
import net.miginfocom.swing.MigLayout;

public class VentanaPrincipalCamarero extends JFrame implements ActionListener,MouseListener{
	
	private Restaurante res;
	
	//Carta
	private JLabel carta;
	private JButton mostrarCarta;
	
	//Pedidos
	private ModeloTabla modelPedidos;
	private ModeloTabla modelCarta;
	private JTable tablaPedidos;
	private JTable tablaCarta;
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
	private JScrollPane panelTablaPedidos;
	private JScrollPane panelTablaCarta;
	
	public VentanaPrincipalCamarero() {
		res = Pruebas.prepararRestaurante();
		crearVentana();
		prepararTablas();
		prepararCarta();
	}
	
	
	public void prepararCarta() {
		for (Consumible i : this.res.getCarta().getListaConsumibles()) {
			Object[] fila = {i.getId(),i.getNombre(),Double.toString(i.getPrecio()),new JCheckBox()};
			modelCarta.addRow(fila);
		}
	}
	
	public void crearVentana() {
		
		pestañas = new JTabbedPane();
		
		//Panel Carta
		panelCarta = new JPanel();
		panelCarta.setLayout(new MigLayout("align 50% 50%"));
		
		
		carta = new JLabel(res.getCarta().mostrarCarta());
		
		mostrarCarta = new JButton("Mostrar Carta");
		mostrarCarta.addActionListener(this);
		
		panelCarta.add(carta);
		panelCarta.add(mostrarCarta,"wrap");
		
		pestañas.addTab("Carta", panelCarta);
		
		//Panel Pedidos
		tablaPedidos = new JTable();
		
		tablaPedidos.addMouseListener(this);
		tablaCarta = new JTable();
		
		anadirPedido = new JButton("Añadir Pedido");
		anadirPedido.addActionListener(this);
		eliminarPedido = new JButton("Eliminar Pedido");
		eliminarPedido.addActionListener(this);
		pagarPedido = new JButton("Pagar Pedido");
		pagarPedido.addActionListener(this);
		
		panelPedidos = new JPanel();
		panelPedidos.setLayout(new MigLayout("align 50% 50%"));
		
		panelTablaCarta = new JScrollPane(tablaCarta);
		panelTablaCarta.setSize((int) (panelPedidos.getSize().getWidth()/2) , (int) (panelPedidos.getSize().getHeight()/2));
		
		panelTablaPedidos = new JScrollPane(tablaPedidos);
		panelTablaPedidos.setSize((int) (panelPedidos.getSize().getWidth()/2) , (int) (panelPedidos.getSize().getHeight()/2));
		
		panelPedidos.add(panelTablaPedidos);
		panelPedidos.add(panelTablaCarta,"wrap");
		
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
	
	public void prepararTablas() {
		
		String titulosPedidos[] = {"ID Pedido","Mesa","Estado"};
		modelPedidos = new ModeloTabla(null,titulosPedidos);
		tablaPedidos.setModel(modelPedidos);
	
		String titulosCarta[] = {"ID Consumible","Nombre","Cantidad",""};
		modelCarta = new ModeloTabla(null,titulosCarta);
		tablaCarta.setModel(modelCarta);
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
				modelPedidos = (ModeloTabla) tablaPedidos.getModel();
				String[] fila = {txtPedido.getText(),txtMesa.getText(),txtConsumible.getText(),txtCantidad.getText(),modelo.ESTADO_PEDIDO.en_espera.name()};
				modelPedidos.addRow(fila);
				
			}
		}
		if (e.getSource().equals(eliminarPedido)) {
			int filaSeleccionada = tablaPedidos.getSelectedRow();
			if (filaSeleccionada == -1)
				JOptionPane.showMessageDialog(null, "No hay ninguna fila seleccionada.");
			else {
				modelPedidos = (ModeloTabla) tablaPedidos.getModel();
				modelPedidos.removeRow(filaSeleccionada);
			}
		}
		if (e.getSource().equals(pagarPedido)) {
			int filaSeleccionada = tablaPedidos.getSelectedRow();
			if (filaSeleccionada == -1)
				JOptionPane.showMessageDialog(null, "No hay ninguna fila seleccionada.");
			else if (modelPedidos.getValueAt(filaSeleccionada, 4).equals(modelo.ESTADO_PEDIDO.en_espera.name())) {
				try {
					Pruebas.pagarPedido(res);
					modelPedidos.setValueAt(modelo.ESTADO_PEDIDO.finalizado.name(), filaSeleccionada, 4);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	

	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getClickCount()==2) {
			JTable tabla = (JTable)e.getSource();
			int filaSeleccionada = tabla.getSelectedRow();
			tabla.getValueAt(filaSeleccionada, 0);
			System.out.format("%s \n","hola");
			
			//res.getCarta().
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

