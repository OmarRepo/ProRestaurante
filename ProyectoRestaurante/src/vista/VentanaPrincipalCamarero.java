package vista;

import java.awt.Dimension;
import java.awt.GridLayout;
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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;


import modelo.Consumible;
import modelo.ESTADO_PEDIDO;
import modelo.Pedido;
import modelo.Restaurante;
import net.miginfocom.swing.MigLayout;

public class VentanaPrincipalCamarero extends JFrame implements ActionListener,MouseListener{
	
	private Restaurante res;
	
	//Carta
	private JScrollPane sCartaMenu;
	private JTable cartaMenu;
	private JScrollPane sCartaPlatos;
	private JTable cartaPlatos;
	private JScrollPane sCartaBebidas;
	private JTable cartaBebidas;
	
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
			private JPanel panelIntroducirPedido;
	private JButton eliminarPedido;
	private JButton pagarPedido;
	private JButton guardarPedido;
		
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
			Object[] fila = {i.getId(),i.getNombre(),0,Double.toString(i.getPrecio()),false};
			modelCarta.addRow(fila);
		}
	}
	
	public void crearVentana() {
		
		pestañas = new JTabbedPane();
		
		//Panel Carta
		panelCarta = new JPanel();
		panelCarta.setLayout(new MigLayout());
		
		
		/*panelCarta.add(cartaMenus,"dock north,wrap");
		panelCarta.add(cartaBebidas,"dock west");
		panelCarta.add(cartaPlatos,"dock east");
		*/
		pestañas.addTab("Carta", panelCarta);
		
		//Panel Pedidos
		tablaPedidos = new JTable();
		
		tablaPedidos.addMouseListener(this);
		tablaCarta = new JTable();
		
		anadirPedido = new JButton("Nuevo Pedido");
		anadirPedido.addActionListener(this);
		eliminarPedido = new JButton("Eliminar Pedido");
		eliminarPedido.addActionListener(this);
		pagarPedido = new JButton("Pagar Pedido");
		pagarPedido.addActionListener(this);
		guardarPedido = new JButton("Guardar Pedio");
		guardarPedido.addActionListener(this);
		
		panelPedidos = new JPanel();
		panelPedidos.setLayout(new MigLayout("align 50% 50%"));
		
		panelTablaCarta = new JScrollPane(tablaCarta);
		panelTablaCarta.setSize((int) (panelPedidos.getSize().getWidth()/2) , (int) (panelPedidos.getSize().getHeight()/2));
		
		panelTablaPedidos = new JScrollPane(tablaPedidos);
		panelTablaPedidos.setSize((int) (panelPedidos.getSize().getWidth()/2) , (int) (panelPedidos.getSize().getHeight()/2));
		
		panelPedidos.add(panelTablaPedidos,"growx, pushx");
		panelPedidos.add(panelTablaCarta,"wrap, growx, pushx");
		
		panelPedidos.add(anadirPedido,"split3,align 50% 50%");
		panelPedidos.add(eliminarPedido);
		panelPedidos.add(pagarPedido);
		panelPedidos.add(guardarPedido);
		
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
	
		String titulosCarta[] = {"ID Consumible","Nombre","Cantidad","Precio",""};
		modelCarta = new ModeloTabla(null,titulosCarta);
		tablaCarta.setModel(modelCarta);
		tablaCarta.setCellEditor(tablaCarta.getDefaultEditor(Boolean.class));
		
	}
	
	public void nuevoPedido() {
		panelIntroducirPedido = new JPanel();
		panelIntroducirPedido.setLayout(new MigLayout());
		pedido = new JLabel("ID Pedido: ");
		txtPedido = new JTextField(4);
		mesa = new JLabel("Mesa: ");
		txtMesa = new JTextField(4);
		
		panelIntroducirPedido.add(pedido);
		panelIntroducirPedido.add(txtPedido,"wrap");
		panelIntroducirPedido.add(mesa);
		panelIntroducirPedido.add(txtMesa,"wrap");

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Botones Pedidos
		if (e.getSource().equals(anadirPedido)) {
			nuevoPedido();
			int resultado = JOptionPane.showConfirmDialog(this, panelIntroducirPedido, "Introduce los parametros del pedido",JOptionPane.OK_CANCEL_OPTION);
			if (resultado == JOptionPane.OK_OPTION) {
				String[] fila = {txtPedido.getText(),txtMesa.getText(),modelo.ESTADO_PEDIDO.en_espera.name()};
				modelPedidos.addRow(fila);
				
			}
		}
		if (e.getSource().equals(eliminarPedido)) {
			int filaSeleccionada = tablaPedidos.getSelectedRow();
			if (filaSeleccionada == -1)
				JOptionPane.showMessageDialog(null, "No hay ninguna fila seleccionada.");
			else {
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
		if (e.getSource().equals(guardarPedido)) {
			int filaSeleccionada = tablaPedidos.getSelectedRow();
			String id = tablaPedidos.getValueAt(filaSeleccionada, 0).toString();
			int mesa = Integer.parseInt(tablaPedidos.getValueAt(filaSeleccionada, 1).toString());
			HashMap<String, Integer> conPedidos = new HashMap<String,Integer>();
			ESTADO_PEDIDO estado = ESTADO_PEDIDO.valueOf(tablaPedidos.getValueAt(filaSeleccionada, 2).toString());
			
			if (filaSeleccionada == -1)
				JOptionPane.showMessageDialog(null, "No hay ninguna fila seleccionada.");
			else {
				for (int i=0; i<=tablaPedidos.getRowCount(); i++) {
					if ((Boolean) tablaCarta.getValueAt(i, 4))
						conPedidos.put(tablaCarta.getValueAt(i, 0).toString(), Integer.parseInt(tablaCarta.getValueAt(i, 2).toString()));
				}
				res.getListaMesas()[Integer.parseInt(tablaPedidos.getValueAt(filaSeleccionada, 1).toString())].getPedidos().add(new Pedido(id,mesa,conPedidos,estado));
				
			}
		}
	}

	

	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		boolean pedidoExiste=false;
		int cont=0;
		if (e.getClickCount()==1) {
			JTable tabla = (JTable)e.getSource();
			int filaSeleccionada = tabla.getSelectedRow();
			System.out.format("%s \n",tabla.getValueAt(filaSeleccionada, 0).toString());
		
			if (res.getListaMesas().length > Integer.parseInt((tabla.getValueAt(filaSeleccionada, 1).toString()))) {
					for (Pedido i : res.getListaMesas()[Integer.parseInt((tabla.getValueAt(filaSeleccionada, 1).toString()))].getPedidos()) {
						
						for (Consumible j : res.getCarta().getListaConsumibles()) {
							tablaCarta.setValueAt(false, cont, 4);
							tablaCarta.setValueAt(0, cont, 2);
							if (i.getIdPedido().equalsIgnoreCase(tabla.getValueAt(filaSeleccionada, 0).toString())) {
									
									if (i.getConsumibles().keySet().contains(tablaCarta.getValueAt(cont, 0).toString())) {
										//Pongo tick en el boton de check
										tablaCarta.setValueAt(true, cont, 4);
										tablaCarta.setValueAt(i.getConsumibles().get(tablaCarta.getValueAt(cont, 0).toString()).toString(), cont, 2);
									}
							}
							cont++;
						}
							
					}
				}
			else
				JOptionPane.showMessageDialog(this, "Esa mesa no existe.");
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

