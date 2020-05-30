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
import javax.swing.SwingConstants;

import modelo.Bebida;
import modelo.Consumible;
import modelo.Ingrediente;
import modelo.Menu;
import modelo.Pedido;
import modelo.Plato;
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
	private JButton anadirReceta;
	private JButton eliminarReceta;
	
	private JPanel panelNuevaReceta;
	private JLabel recetaId;
	private JTextField txtRecetaId;
	private JLabel recetaNombre;
	private JTextField txtRecetaNombre;
	private JLabel recetaPrecio;
	private JTextField txtRecetaPrecio;
	
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
		panelAlmacen.setLayout(new MigLayout("align 50%"));
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
		nuevo.setHorizontalTextPosition(SwingConstants.LEFT);
		nuevo.addActionListener(this);
		id = new JTextField(4);
		id.setEnabled(false);
		
		nombre = new JLabel("Nombre  ");
		txtNombre = new JTextField(6);
		cantidad = new JLabel("Cantidad");
		txtCantidad = new JTextField(6);
		actualizarIngrediente = new JButton("  Añadir al Almacén  ");
		actualizarIngrediente.addActionListener(this);
		eliminarIngrediente = new JButton("Eliminar del Almacén");
		eliminarIngrediente.addActionListener(this);
		
		
		formulario.add(nuevo);
		formulario.add(id,"wrap");
		formulario.add(nombre);
		formulario.add(txtNombre,"wrap");
		formulario.add(cantidad);
		formulario.add(txtCantidad,"wrap");
		formulario.add(actualizarIngrediente);
		formulario.add(eliminarIngrediente);
		panelAlmacen.add(formulario,"growy, pushy");
		panelAlmacen.add(scrollAlmacen,"grow,push");
		
		
		
		
		
		pestanas.addTab("Almacén", panelAlmacen);
		cargarAlmacen();
		
		panelRecetas = new JPanel();
		panelRecetas.setLayout(new MigLayout());
		//TABLA CONSUMIBLE----------------------------------------------------------------------------------------------------------
		consumible = new JTable();
		String[] cabeceraConsumible = {"ID","NOMBRE","PRECIO (€)"};
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
		anadirReceta = new JButton("Añadir Receta");
		anadirReceta.addActionListener(this);
		eliminarReceta = new JButton("Eliminar Receta");
		eliminarReceta.addActionListener(this);
		
		elegirTabla = new JComboBox<String>();
		elegirTabla.addItem("Menu");
		elegirTabla.addItem("Plato");
		elegirTabla.addActionListener(this);
		
		
		
		panelRecetas.add(scrollConsumible,"growx,pushx");
		panelRecetas.add(elegirTabla);
		panelRecetas.add(scrollComponente,"wrap,growx,pushx");
		panelRecetas.add(anadirReceta,"split2");
		panelRecetas.add(eliminarReceta);
		
		pestanas.addTab("Recetas", panelRecetas);
		cargarMenus();
		
		//FALTA CAMBIAR EL METODO PARA QUE SE ADAPTE SEGUN SEA PLATO O MENU
		//VentanaPrincipalCocinero.rellenarTabla(modeloConsumible, consumible, "CONSUMIBLES");
		//VentanaPrincipalCocinero.rellenarTabla(modeloComponente, componente, "INGREDIENTES");
		//--------------------------------------------------------------------------------
		
		this.add(pestanas);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setTitle("Cocinero");
		//Con el codigo comentado la ventana adapta su tamaño segun el tamaño de la pantalla
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
	
	public void cargarMenus() {
		Inicializar.vaciarTabla(consumible, modeloConsumible);
		Inicializar.vaciarTabla(componente, modeloComponente);
		for (Consumible i : res.getCarta().getListaConsumibles()) {
			if (i instanceof Menu) {
				String[] fila = {i.getId(),i.getNombre(),Double.toString(i.getPrecio())};
				modeloConsumible.addRow(fila);
			}
		}
		
		for (Consumible i : res.getCarta().getListaConsumibles()) {
			if (!(i instanceof Menu)) {
				String[] fila = {i.getId(),i.getNombre(),""};
				modeloComponente.addRow(fila);
			}
		}
	}
	
	public void cargarPlatos() {
		try {
			Inicializar.vaciarTabla(consumible, modeloConsumible);
			Inicializar.vaciarTabla(componente, modeloComponente);
			for (Consumible i : res.getCarta().getListaConsumibles()) {
				if (i instanceof Plato || i instanceof Bebida) {
					String[] fila = {i.getId(),i.getNombre(),Double.toString(i.getPrecio())};
					modeloConsumible.addRow(fila);
				}
			}
			for (Ingrediente i : Ingrediente.obtenerIngredientes()) {
				String[] fila = {i.getId(),i.getNombre(),""};
				modeloComponente.addRow(fila);
			}
			for (Bebida i : Bebida.obtenerBebidas()) {
				String[] fila = {i.getId(),i.getNombre(),""};
				modeloComponente.addRow(fila);
			}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void nuevaReceta() {
		panelNuevaReceta = new JPanel();
		panelNuevaReceta.setLayout(new MigLayout());

		recetaId = new JLabel("ID: ");
		txtRecetaId = new JTextField(6);
		
		recetaNombre = new JLabel("Nombre: ");
		txtRecetaNombre = new JTextField(6);
		
		recetaPrecio = new JLabel("Precio: ");
		txtRecetaPrecio = new JTextField(6);
		
		panelNuevaReceta.add(recetaId);
		panelNuevaReceta.add(txtRecetaId);
		panelNuevaReceta.add(recetaNombre);
		panelNuevaReceta.add(txtRecetaNombre);
		panelNuevaReceta.add(recetaPrecio);
		panelNuevaReceta.add(txtRecetaPrecio);
	}
	
	public void recargarRecetas() {
		if (elegirTabla.getSelectedItem().toString().equals("Menu"))
			cargarMenus();
		else 
			cargarPlatos();
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
					try {
						Bebida bebida = new Bebida(id.getText(),txtNombre.getText(),Integer.parseInt(txtCantidad.getText()));
						System.out.format("%s \n",bebida.existe());
						if (bebida.existe()) {
							bebida.modificarBebida();
							bebida.asignarCantidadBebida();
							JOptionPane.showMessageDialog(this, "Bebida cargada correctamente.");
						}
						else {
							bebida.insertarBebida();
							bebida.asignarCantidadBebida();
							JOptionPane.showMessageDialog(this, "Bebida cargada correctamente.");
						}
						
					} catch (ClassNotFoundException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(this, "Error de conexión, contacte con el administrador.");
					} catch (SQLException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(this, ex.getMessage());
					} catch (NumberFormatException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(this, "Número no válido.");
					}
				}
				else
					JOptionPane.showMessageDialog(this, "Formato de ID erróneo");
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
			if (e.getSource().equals(elegirTabla)) {
				recargarRecetas();
			}
			if (e.getSource().equals(anadirReceta)) {
				nuevaReceta();
				int resultado = JOptionPane.showConfirmDialog(this, panelNuevaReceta, "Introduce los parametros de la receta",JOptionPane.OK_CANCEL_OPTION);
				if (resultado == JOptionPane.OK_OPTION) {
					String id = txtRecetaId.getText();
					String nombre = txtRecetaNombre.getText();
					String precio = txtRecetaPrecio.getText();
					String[] fila = {id,nombre,precio};
					modeloConsumible.addRow(fila);
					if (id.startsWith("M"))
						new Menu(id,nombre,Double.parseDouble(precio)).insertarMenu();
					else if (id.startsWith("P"))
						new Plato(id,nombre,Double.parseDouble(precio)).insertarPlato();
					else if (id.startsWith("B")) {
						Bebida bebida = new Bebida(id,nombre,Double.parseDouble(precio));
						if (bebida.existe())
							bebida.modificarBebida(Double.parseDouble(precio));
						else
							JOptionPane.showMessageDialog(this, "Esa bebida no existe en el almacén.");
					}
					else
						JOptionPane.showMessageDialog(this, "ID Incorrecto.");
					recargarRecetas();
				}
			}
			if (e.getSource().equals(eliminarReceta)) {
				int filaSeleccionada = consumible.getSelectedRow();
				Consumible.borrarConsumible(consumible.getValueAt(filaSeleccionada, 0).toString());
				modeloConsumible.removeRow(filaSeleccionada);
			}
		} catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(this, "Error de conexión, contacte con el administrador.");
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Número no válido.");
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
	
