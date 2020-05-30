package vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import modelo.Bebida;
import modelo.Ingrediente;
import modelo.Restaurante;
import net.miginfocom.swing.MigLayout;

public class VentanaPrincipalCocinero extends JFrame implements ActionListener,MouseListener{
	
	private Restaurante res;
	
	//Almacen
	private JTable almacen;
	private ModeloTabla modeloAlmacen;
	
	private JTextField id;
	private JCheckBox nuevo;

	
	//Panel Ordenado
	private JPanel formulario;
	private JLabel nombre;
	private JTextField txtNombre;
	private JLabel cantidad;
	private JTextField txtCantidad;
	
	private JButton actualizarIngrediente;
	private JButton eliminarIngrediente;
	
	
	//RECETAS
	private JTable consumible;
	private ModeloTabla modeloConsumible;
	private JComboBox<String> elegirTabla;
	private JTable componente;
	private ModeloTabla modeloComponente;
	
	//PANELES
	private JPanel panelAlmacen;
	private JScrollPane scrollAlmacen;
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
		panelAlmacen.setLayout(new MigLayout("align center"));
		formulario = new JPanel();
		formulario.setLayout(new MigLayout());
		
		//TABLA ALMACEN------------------------------------------------------------------------------------------------
		almacen = new JTable();
		String[] cabeceraAlmacen = {"ID_INGREDIENTE", "NOMBRE", "CANTIDAD"};
		modeloAlmacen = new ModeloTabla(null, cabeceraAlmacen);
		almacen.setModel(modeloAlmacen);
		scrollAlmacen = new JScrollPane(almacen);
		scrollAlmacen.setSize((int) (panelAlmacen.getSize().getWidth()/2) , (int) (panelAlmacen.getSize().getHeight()/2));
		almacen.addMouseListener(this);
		//--------------------------------------------------------------------------------------------------------------------------
		nuevo = new JCheckBox("Nuevo");
		nuevo.addActionListener(this);
		id = new JTextField(4);
		id.setEnabled(false);
		
		nombre = new JLabel("Nombre  ");
		txtNombre = new JTextField(6);
		cantidad = new JLabel("Cantidad");
		txtCantidad = new JTextField(6);
		actualizarIngrediente = new JButton("  A�adir al Almac�n  ");
		actualizarIngrediente.addActionListener(this);
		eliminarIngrediente = new JButton("Eliminar del Almac�n");
		eliminarIngrediente.addActionListener(this);
		
		formulario.add(nuevo,"align center,split2");
		formulario.add(id,"wrap");
		formulario.add(nombre,"split2,align center");
		formulario.add(txtNombre,"wrap");
		formulario.add(cantidad,"split2, align center");
		formulario.add(txtCantidad,"wrap");
		formulario.add(actualizarIngrediente,"wrap");
		formulario.add(eliminarIngrediente);
		panelAlmacen.add(formulario);
		panelAlmacen.add(scrollAlmacen,"top");
		
		
		
		pestanas.addTab("Almac�n", panelAlmacen);
		cargarAlmacen();
		
		panelRecetas = new JPanel();
		panelRecetas.setLayout(new MigLayout());
		//TABLA CONSUMIBLE----------------------------------------------------------------------------------------------------------
		consumible = new JTable();
		String[] cabeceraConsumible = {"ID","NOMBRE","PRECIO (�)"};
		modeloConsumible = new ModeloTabla(null, cabeceraConsumible);
		consumible.setModel(modeloConsumible);
		scrollConsumible = new JScrollPane(consumible);
		scrollConsumible.setSize((int) (panelRecetas.getSize().getWidth()/2) , (int) (panelRecetas.getSize().getHeight()/2));
		consumible.addMouseListener(this);
		//--------------------------------------------------------------------------------------------------------------------------
		//TABLA COMPONENTE----------------------------------------------------------------------------------------------------------
		componente = new JTable();
		String[] cabeceraComponente = {"ID","NOMBRE","CANTIDAD",""};
		modeloComponente = new ModeloTabla(null, cabeceraComponente);
		componente.setModel(modeloComponente);
		scrollComponente = new JScrollPane(componente);
		scrollComponente.setSize((int) (panelRecetas.getSize().getWidth()/2) , (int) (panelRecetas.getSize().getHeight()/2));
		//----------------------------------------------------------------------------------------------------------------------------
		elegirTabla = new JComboBox<String>();
		elegirTabla.addItem("Menu");
		elegirTabla.addItem("Plato");
		elegirTabla.addActionListener(this);
		
		
		
		panelRecetas.add(scrollConsumible,"west");
		panelRecetas.add(elegirTabla);
		panelRecetas.add(scrollComponente,"east");
		
		pestanas.addTab("Recetas", panelRecetas);
		
		//FALTA CAMBIAR EL METODO PARA QUE SE ADAPTE SEGUN SEA PLATO O MENU
		//VentanaPrincipalCocinero.rellenarTabla(modeloConsumible, consumible, "CONSUMIBLES");
		//VentanaPrincipalCocinero.rellenarTabla(modeloComponente, componente, "INGREDIENTES");
		//--------------------------------------------------------------------------------
		
		this.add(pestanas);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setTitle("Cocinero");
		//Con el codigo comentado la ventana adapta su tama�o segun el tama�o de la pantalla
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int height = pantalla.height;
		int width = pantalla.width;
		setSize((int) (width/1.5), height/2);
	  
	    setLocationRelativeTo(null);
	    setVisible(true);
	}
	
	public void cargarAlmacen() {
		try {
			Inicializar.vaciarTabla(almacen, modeloAlmacen);
			for (Ingrediente i : Ingrediente.obtenerIngredientes()) {
				String[] fila = {i.getId(),i.getNombre(),Integer.toString(i.getCantidad())};
				modeloAlmacen.addRow(fila);
			}
			for (Bebida i : Bebida.obtenerBebidas()) {
				String[] fila = {i.getId(),i.getNombre(),Integer.toString(i.getCantidad())};
				modeloAlmacen.addRow(fila);
			}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource().equals(nuevo)) {
				if (nuevo.isSelected())
					id.setEnabled(true);
				else
					id.setEnabled(false);
			}
			if (e.getSource().equals(actualizarIngrediente)) {
				if (id.getText().startsWith("I")) {
					
					Ingrediente ingrediente = new Ingrediente(id.getText(),txtNombre.getText(),Integer.parseInt(txtCantidad.getText()));
					if (ingrediente.existe())
						ingrediente.modificarIngrediente();
					else
						ingrediente.insertarIngrediente();
					JOptionPane.showMessageDialog(this, "Ingrediente cargado correctamente.");
				}
				else if (id.getText().startsWith("B")) {
					
					Bebida bebida = new Bebida(id.getText(),txtNombre.getText(),Integer.parseInt(txtCantidad.getText()));
					if (bebida.existe())
						bebida.modificarBebida();
					else
						bebida.insertarBebida();
					JOptionPane.showMessageDialog(this, "Bebida cargada correctamente.");
				}
				else
					JOptionPane.showMessageDialog(this, "Formato de ID err�neo");
				cargarAlmacen(); //Vuelo a cargar el almacen para que aparezca el nuevo producto
			}
			if (e.getSource().equals(eliminarIngrediente)) {
				int filaSeleleccionada = almacen.getSelectedRow();
				if (id.getText().startsWith("I")) {
					Ingrediente ingrediente = new Ingrediente(id.getText(),txtNombre.getText(),Integer.parseInt(txtCantidad.getText()));
					ingrediente.eliminarIngrediente();
				}
				else if (id.getText().startsWith("B")) {
					Bebida bebida = new Bebida(id.getText(),txtNombre.getText(),Integer.parseInt(txtCantidad.getText()));
					bebida.eliminarBebida();
				}
				modeloAlmacen.removeRow(filaSeleleccionada);
			}
		} catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(this, "Error de conexi�n, contacte con el administrador.");
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "N�mero no v�lido.");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if (e.getClickCount()==1) {
			if (e.getSource().equals(almacen)) {
				int filaSeleccionada = almacen.getSelectedRow();
				id.setText(almacen.getValueAt(filaSeleccionada, 0).toString());
				txtNombre.setText(almacen.getValueAt(filaSeleccionada, 1).toString());
				txtCantidad.setText(almacen.getValueAt(filaSeleccionada, 2).toString());
			}
			/*if (e.getSource().equals(consumible)) {
			 * 	int cont=0;
				HashMap<String, Integer> consumibles = new HashMap<String, Integer>();
				JTable tabla = (JTable)e.getSource();
				int filaSeleccionada = tabla.getSelectedRow();
				String idPedido = tabla.getValueAt(filaSeleccionada, 0).toString();
				try {
					consumibles = Pedido.recorrerPedidos(idPedido);
					if (res.getListaMesas() > Integer.parseInt((tabla.getValueAt(filaSeleccionada, 1).toString()))) {
					
						for (Consumible j : res.getCarta().getListaConsumibles()) {
							tablaCarta.setValueAt(false, cont, 4);
							tablaCarta.setValueAt(0, cont, 2);
							
							if (consumibles.keySet().contains(tablaCarta.getValueAt(cont, 0).toString())) {
								//Pongo tick en el boton de check
								tablaCarta.setValueAt(true, cont, 4);
								tablaCarta.setValueAt(consumibles.get(tablaCarta.getValueAt(cont, 0).toString()), cont, 2);
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
			}*/
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
	
