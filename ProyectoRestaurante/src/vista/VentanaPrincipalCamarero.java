package vista;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import modelo.ConexionBBDD;
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
	private ModeloTabla modeloMenu;
	private JScrollPane sCartaPlatos;
	private JTable cartaPlatos;
	private ModeloTabla modeloPlato;
	private JScrollPane sCartaBebidas;
	private JTable cartaBebidas;
	private ModeloTabla modeloBebida;
	
	//Pedidos
	private ModeloTabla modelPedidos;
	private ModeloTabla modelCarta;
	private JTable tablaPedidos;
	private JTable tablaCarta;
	private JButton anadirPedido;
	//Panel Introducir Pedidos
			private JLabel mesa;
			private JTextField txtMesa;
			private JPanel panelIntroducirPedido;
	private JButton eliminarPedido;
	private JButton pagarPedido;
	private JButton guardarPedido;
	private JButton cargarPedidos;
		
	//Paneles
	private JTabbedPane pestanas;
	private JPanel panelCarta;
	private JPanel panelPedidos;
	private JScrollPane panelTablaPedidos;
	private JScrollPane panelTablaCarta;
	
	public VentanaPrincipalCamarero() {
		try {
			res = new Restaurante();
			crearVentana();
			prepararTablas();
			prepararCarta();
			prepararMuestraDeCarta();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos.");
		}
	}
	
	
	public void prepararCarta() {
		for (Consumible i : this.res.getCarta().getListaConsumibles()) {
			Object[] fila = {i.getId(),i.getNombre(),0,Double.toString(i.getPrecio()),false};
			modelCarta.addRow(fila);
		}
	}
	
	public void prepararMuestraDeCarta() {
		for (Consumible i : this.res.getCarta().getListaConsumibles()) {
			Object[] fila = {i.getNombre(),Double.toString(i.getPrecio())};
			switch(i.getId().charAt(0)) {
			case 'M':
				modeloMenu.addRow(fila);
				break;
			case 'P':
				modeloPlato.addRow(fila);
				break;
			case 'B':
				modeloBebida.addRow(fila);
				break;
			}
		}
	}
	
	public void crearVentana() {
		
		pestanas = new JTabbedPane();
		
		//Panel Carta
		panelCarta = new JPanel();
		panelCarta.setLayout(new MigLayout());
		
		String[] titulosM = {"Menus","Precio"};
		modeloMenu = new ModeloTabla(null, titulosM);
		String[] titulosP = {"Platos","Precio"};
		modeloPlato = new ModeloTabla(null, titulosP);
		String[] titulosB = {"Bebidas","Precio"};
		modeloBebida = new ModeloTabla(null, titulosB);
		
		//Alinear hacia la derecha las columnas
		DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
		Alinear.setHorizontalAlignment(SwingConstants.RIGHT);
		//Editar cartaMenu
		cartaMenu = new JTable();
		cartaMenu.setModel(modeloMenu);
		cartaMenu.setShowGrid(false);
		
		cartaMenu.getTableHeader().setFont(new Font("Courier New",1,18));
		cartaMenu.setFont(new Font("Courier New",0,15));
		cartaMenu.setFillsViewportHeight(true);
		
		cartaMenu.getColumnModel().getColumn(1).setCellRenderer(Alinear); //Coges el modelo de las columnas, el numeor de columna que queires editar y seleccionas el renderizado
		//Editar cartaPlatos
		cartaPlatos = new JTable();
		cartaPlatos.setModel(modeloPlato);
		cartaPlatos.setShowGrid(false);
		cartaPlatos.getTableHeader().setFont(new Font("Cooper Black",1,14));
		cartaPlatos.getColumnModel().getColumn(1).setCellRenderer(Alinear);
		cartaPlatos.setFillsViewportHeight(true);
		
		//Editar cartaBebidas
		cartaBebidas = new JTable();
		cartaBebidas.setModel(modeloBebida);
		cartaBebidas.setShowGrid(false);
		cartaBebidas.getTableHeader().setFont(new Font("Cooper Black",1,14));
		cartaBebidas.getColumnModel().getColumn(1).setCellRenderer(Alinear);
		cartaBebidas.setFillsViewportHeight(true);
		
		sCartaMenu = new JScrollPane(cartaMenu);
		sCartaPlatos = new JScrollPane(cartaPlatos);
		sCartaBebidas = new JScrollPane(cartaBebidas);
		
		panelCarta.add(sCartaMenu);
		panelCarta.add(sCartaPlatos);
		panelCarta.add(sCartaBebidas);
		
		/*panelCarta.add(cartaMenus,"dock north,wrap");
		panelCarta.add(cartaBebidas,"dock west");
		panelCarta.add(cartaPlatos,"dock east");
		*/
		pestanas.addTab("Carta", panelCarta);
		
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
		guardarPedido = new JButton("Guardar Pedido");
		guardarPedido.addActionListener(this);
		cargarPedidos = new JButton("Cargar Pedidos");
		cargarPedidos.addActionListener(this);
		
		panelPedidos = new JPanel();
		panelPedidos.setLayout(new MigLayout("align 50% 50%"));
		
		panelTablaCarta = new JScrollPane(tablaCarta);
		panelTablaCarta.setSize((int) (panelPedidos.getSize().getWidth()/2) , (int) (panelPedidos.getSize().getHeight()/2));
		
		panelTablaPedidos = new JScrollPane(tablaPedidos);
		panelTablaPedidos.setSize((int) (panelPedidos.getSize().getWidth()/2) , (int) (panelPedidos.getSize().getHeight()/2));
		
		panelPedidos.add(panelTablaPedidos,"growx, pushx");
		panelPedidos.add(panelTablaCarta,"wrap, growx, pushx");
		
		panelPedidos.add(cargarPedidos,"split3,align 50% 50%");
		panelPedidos.add(anadirPedido);
		panelPedidos.add(eliminarPedido);
		panelPedidos.add(guardarPedido,"split2,align 50% 50%");
		panelPedidos.add(pagarPedido);
		
		
		pestanas.addTab("Pedidos", panelPedidos);
		
		
		this.add(pestanas);
		
		
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

		mesa = new JLabel("Mesa: ");
		txtMesa = new JTextField(4);
		
		panelIntroducirPedido.add(mesa);
		panelIntroducirPedido.add(txtMesa,"wrap");

	}
	
	public Pedido seleccionarPedido() {
		int filaSeleccionada = tablaPedidos.getSelectedRow();
		
		String id = null;
		int mesa = 0;
		HashMap<String, Integer> conPedidos = null;
		ESTADO_PEDIDO estado = null;
		
		if (filaSeleccionada == -1)
			JOptionPane.showMessageDialog(null, "No hay ninguna fila seleccionada.");
		else {
			id = tablaPedidos.getValueAt(filaSeleccionada, 0).toString();
			mesa = Integer.parseInt(tablaPedidos.getValueAt(filaSeleccionada, 1).toString());
			conPedidos = new HashMap<String,Integer>();
			estado = ESTADO_PEDIDO.valueOf(tablaPedidos.getValueAt(filaSeleccionada, 2).toString());
			
			for (int i=0; i<=tablaPedidos.getRowCount(); i++) {
				if ((Boolean) tablaCarta.getValueAt(i, 4))
					conPedidos.put(tablaCarta.getValueAt(i, 0).toString(), Integer.parseInt(tablaCarta.getValueAt(i, 2).toString()));
			}
		}
			return new Pedido(id,mesa,conPedidos,estado);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		//Botones Pedidos
		if (e.getSource().equals(cargarPedidos)) {
			Statement consulta;
			try {
				consulta = ConexionBBDD.getConnection().createStatement();
				ResultSet resul=consulta.executeQuery("SELECT * FROM PEDIDOS");
			while(resul.next()) {
				String[] fila = {resul.getString("ID_PEDIDO"),resul.getString("MESA"),resul.getString("ESTADO")};
				modelPedidos.addRow(fila);
			}
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource().equals(anadirPedido)) {
			try {
				nuevoPedido();
				int resultado = JOptionPane.showConfirmDialog(this, panelIntroducirPedido, "Introduce los parametros del pedido",JOptionPane.OK_CANCEL_OPTION);
				if (resultado == JOptionPane.OK_OPTION) {
					String[] fila = {Pedido.generarIdPedido(),txtMesa.getText(),modelo.ESTADO_PEDIDO.en_espera.name()};
					
					modelPedidos.addRow(fila);
					
				}
			} catch (Exception e1) {
				// TODO: handle exception
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
			Pedido pedido;
			if (filaSeleccionada == -1)
				JOptionPane.showMessageDialog(null, "No hay ninguna fila seleccionada.");
			else if (modelPedidos.getValueAt(filaSeleccionada, 2).equals(modelo.ESTADO_PEDIDO.en_espera.name())) {
				try {
					pedido = seleccionarPedido();
					pedido.calcularPrecio(res.getCarta());
					pedido.imprimirFactura();
					modelPedidos.setValueAt(modelo.ESTADO_PEDIDO.pagado.name(), filaSeleccionada, 2);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else
				JOptionPane.showMessageDialog(this, "El pedido aun no ha sido preparado.");
		}
		if (e.getSource().equals(guardarPedido)) {
				Pedido pedido = seleccionarPedido();
				HashMap<String, Integer> consumibles = null;
				try {
					consumibles = Pedido.buscarConsumibles(pedido.getIdPedido());
				} catch (ClassNotFoundException | SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				//Seleccionar Consumibles
				for (int i=0; i<tablaCarta.getRowCount();i++) {
					Statement consulta = null;
					try {
						
						if (consumibles.containsKey(tablaCarta.getValueAt(i, 0))) {
						
								if ((Boolean)tablaCarta.getValueAt(i, 4)) {
									//Modificar cantidad pedido
									consulta = ConexionBBDD.getConnection().createStatement();
									consulta.executeUpdate("UPDATE PEDIDOS_CONSUMIBLES SET CANTIDAD = "+tablaCarta.getValueAt(i, 2));
									consulta.close();
								} else {
									//borrar pedido
									consulta = ConexionBBDD.getConnection().createStatement();
									consulta.executeUpdate("DELETE FROM PEDIDOS_CONSUMIBLES WHERE ID_PEDIDO = '"+pedido.getIdPedido()+"'"+" AND ID_CONSUMIBLE = '"+tablaCarta.getValueAt(i, 0)+"'");
									consulta.close();
								}
						} else if ((Boolean)tablaCarta.getValueAt(i, 4)) {
							//Insertar pedido consumible
							consulta = ConexionBBDD.getConnection().createStatement();
							consulta.executeUpdate("INSERT INTO PEDIDOS_CONSUMIBLES (ID_PEDIDO,ID_CONSUMIBLE,CANTIDAD) VALUES ('"+pedido.getIdPedido()+"','"+tablaCarta.getValueAt(i, 0)+"',"+tablaCarta.getValueAt(i, 2)+")");							
							consulta.close();
						}
						JOptionPane.showMessageDialog(this, "Pedido guardado correctamente.");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				//--------------------------------------------------
				pedido.calcularPrecio(res.getCarta());
				try {
					if (!pedido.buscarPedido()) 
						pedido.insertarPedido();
					else {
						pedido.modificarPedido();
					}
				} catch(ClassNotFoundException | SQLException ce) {
					JOptionPane.showMessageDialog(this, "Error al actualizar el pedido.");
				}	
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		int cont=0;
		if (e.getClickCount()==1) {
			
			HashMap<String, Integer> cons = new HashMap<String, Integer>();
			JTable tabla = (JTable)e.getSource();
			int filaSeleccionada = tabla.getSelectedRow();
			String idPedido = tabla.getValueAt(filaSeleccionada, 0).toString();
			try {
			cons = Pedido.recorrerPedidos(idPedido);
				if (res.getListaMesas() > Integer.parseInt((tabla.getValueAt(filaSeleccionada, 1).toString()))) {
					
						for (Consumible j : res.getCarta().getListaConsumibles()) {
							tablaCarta.setValueAt(false, cont, 4);
							tablaCarta.setValueAt(0, cont, 2);
							
							if (cons.keySet().contains(tablaCarta.getValueAt(cont, 0).toString())) {
								//Pongo tick en el boton de check
								tablaCarta.setValueAt(true, cont, 4);
								tablaCarta.setValueAt(cons.get(tablaCarta.getValueAt(cont, 0).toString()).toString(), cont, 2);
							}
							cont++;
						}
						
				}
				else
					JOptionPane.showMessageDialog(this, "Esa mesa no existe.");
			
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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

