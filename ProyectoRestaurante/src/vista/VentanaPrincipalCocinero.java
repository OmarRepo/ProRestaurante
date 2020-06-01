package vista;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

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
import modelo.ConexionBBDD;
import modelo.Consumible;
import modelo.ESTADO_PEDIDO;
import modelo.Ingrediente;
import modelo.Menu;
import modelo.Pedido;
import modelo.Plato;
import modelo.Restaurante;
import net.miginfocom.swing.MigLayout;

public class VentanaPrincipalCocinero extends JFrame implements ActionListener,MouseListener{
	
	private Restaurante res;
	
	//PESTAÑA ALMACEN------------------------------------------------------------------------------------------------------------
	private JTable almacen;
	private ModeloTabla modeloAlmacen;
	
	private JTextField id;
	private JCheckBox nuevo;

	
		//PANEL CON EL FORMULARIO-------------------------------------------------------------------------------------------------
		private JPanel formulario;
		private JLabel nombre;
		private JTextField txtNombre;
		private JLabel cantidad;
		private JTextField txtCantidad;
		
		private JButton actualizarIngrediente;
		private JButton eliminarIngrediente;
		//------------------------------------------------------------------------------------------------------------------------
		
	
	//PESTAÑA RECETAS-------------------------------------------------------------------------------------------------------------
	private JTable consumible;
	private ModeloRecetas modeloConsumible;
	private JComboBox<String> elegirTabla;
	private JTable componente;
	private ModeloRecetas modeloComponente;
	private JButton anadirReceta;
	private JButton eliminarReceta;
	private JButton guardarComponentes;
	
	private JPanel panelNuevaReceta;
	private JLabel recetaId;
	private JTextField txtRecetaId;
	private JLabel recetaNombre;
	private JTextField txtRecetaNombre;
	private JLabel recetaPrecio;
	private JTextField txtRecetaPrecio;
	
	//PESTAÑA PEDIDOS-------------------------------------------------------------------------------------------------------------
	private JPanel panelPedidosCocinero;
	private ModeloTabla modeloPedidosCocinero;
	private JTable pedidosCocinero;
	private JScrollPane scrollPedidosCocinero;
	private JButton recargarPedidos;
	
	//PANELES---------------------------------------------------------------------------------------------------------------------
	private JPanel panelAlmacen;
	private JScrollPane scrollAlmacen;
	private JPanel panelRecetas;
	private JScrollPane scrollConsumible;
	private JScrollPane scrollComponente;
	private JTabbedPane pestanas;
	//-----------------------------------------------------------------------------------------------------------------------------
	public VentanaPrincipalCocinero() {
		try {
			res = new Restaurante(false);
			crearVentana();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Metodo que inicializa todos los objetos de la ventana
	 */
	public void crearVentana() {
		
		pestanas = new JTabbedPane();
		
		//PESTAÑA ALMACEN---------------------------------------------------------------------------------------------------------
		panelAlmacen = new JPanel();
		panelAlmacen.setLayout(new MigLayout("align 50%"));
		formulario = new JPanel();
		formulario.setLayout(new MigLayout());
		
		//TABLA ALMACEN-----------------------------------------------------------------------------------------------------------
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
		cargarAlmacen(); //se rellena la tabla almacen
		//------------------------------------------------------------------------------------------------------------------------
		//PESTAÑA RECETAS---------------------------------------------------------------------------------------------------------
		
		panelRecetas = new JPanel();
		panelRecetas.setLayout(new MigLayout());
		//TABLA CONSUMIBLE----------------------------------------------------------------------------------------------------------
		consumible = new JTable();
		String[] cabeceraConsumible = {"ID","NOMBRE","PRECIO (€)"};
		modeloConsumible = new ModeloRecetas(null, cabeceraConsumible);
		consumible.setModel(modeloConsumible);
		scrollConsumible = new JScrollPane(consumible);
		scrollConsumible.setSize((int) (panelRecetas.getSize().getWidth()/2) , (int) (panelRecetas.getSize().getHeight()/2));
		consumible.addMouseListener(this);
		//--------------------------------------------------------------------------------------------------------------------------
		//TABLA COMPONENTE----------------------------------------------------------------------------------------------------------
		componente = new JTable();
		String[] cabeceraComponente = {"ID","NOMBRE","CANTIDAD",""};
		modeloComponente = new ModeloRecetas(null, cabeceraComponente);
		componente.setModel(modeloComponente);
		componente.setCellEditor(componente.getDefaultEditor(Boolean.class));
		scrollComponente = new JScrollPane(componente);
		scrollComponente.setSize((int) (panelRecetas.getSize().getWidth()/2) , (int) (panelRecetas.getSize().getHeight()/2));
		//----------------------------------------------------------------------------------------------------------------------------
		anadirReceta = new JButton("Añadir Receta");
		anadirReceta.addActionListener(this);
		eliminarReceta = new JButton("Eliminar Receta");
		eliminarReceta.addActionListener(this);
		guardarComponentes = new JButton("Guardar Componentes");
		guardarComponentes.addActionListener(this);
		
		//Boton desplegable
		elegirTabla = new JComboBox<String>();
		elegirTabla.addItem("Menu");
		elegirTabla.addItem("Bebida");
		elegirTabla.addItem("Plato");
		elegirTabla.addActionListener(this);
		
		
		
		panelRecetas.add(scrollConsumible,"growx,pushx");
		panelRecetas.add(elegirTabla);
		panelRecetas.add(scrollComponente,"wrap,growx,pushx");
		panelRecetas.add(anadirReceta,"split2");
		panelRecetas.add(eliminarReceta);
		panelRecetas.add(guardarComponentes,"skip,split3,align center");
		pestanas.addTab("Recetas", panelRecetas);
		cargarMenus(); //se rellena las tablas consumibles y componentes
		//-------------------------------------------------------------------------------------------------------------------------
		//PESTAÑA PEDIDOS----------------------------------------------------------------------------------------------------------
		
		panelPedidosCocinero = new JPanel();
		panelPedidosCocinero.setLayout(new MigLayout());
		String[] cabeceraPedidosCocinero = {"ID_PEDIDO","MESA","ESTADO"};
		modeloPedidosCocinero = new ModeloTabla(null,cabeceraPedidosCocinero); 
		pedidosCocinero = new JTable();
		pedidosCocinero.addMouseListener(this);
		pedidosCocinero.setModel(modeloPedidosCocinero);
		scrollPedidosCocinero = new JScrollPane(pedidosCocinero);
		recargarPedidos = new JButton("Recargar");
		recargarPedidos.addActionListener(this);
		
		panelPedidosCocinero.add(scrollPedidosCocinero,"grow,push");
		panelPedidosCocinero.add(recargarPedidos,"dock east");
		
		
		pestanas.addTab("Pedidos", panelPedidosCocinero);
		recargarPedidos();
		//-------------------------------------------------------------------------------------------------------------------------
		//CONFIGURACION DE LA VENTANA----------------------------------------------------------------------------------------------
		this.add(pestanas);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(true);
		setTitle("Cocinero");
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int height = pantalla.height;
		int width = pantalla.width;
		setSize((int) (width/1.5), height/2);
	  
	    setLocationRelativeTo(null);
	    setVisible(true);
	}
	
	/**
	 * vacia la tabla almacen y recoge de la base de datos todos los ingredientes y bebidas para rellenar la tabla de nuevo
	 */
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
	
	/**
	 * vacia la tabla comestible y consumible y recoge de la base de datos todos los menus y comestibles para rellenar las tablas de nuevo
	 */
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
				Object[] fila = {i.getId(),i.getNombre(),0,false};
				modeloComponente.addRow(fila);
			}
		}
	}
	
	/**
	 * vacia la tabla comestible y consumible y recoge de la base de datos todos los platos e ingredientes para rellenar las tablas de nuevo
	 */
	public void cargarPlatos() {
		try {
			Inicializar.vaciarTabla(consumible, modeloConsumible);
			Inicializar.vaciarTabla(componente, modeloComponente);
			for (Consumible i : res.getCarta().getListaConsumibles()) {
				if (i instanceof Plato) {
					Object[] fila = {i.getId(),i.getNombre(),Double.toString(i.getPrecio())};
					modeloConsumible.addRow(fila);
				}
			}
			for (Ingrediente i : Ingrediente.obtenerIngredientes()) {
				Object[] fila = {i.getId(),i.getNombre(),0,false};
				modeloComponente.addRow(fila);
			}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * vacia la tabla comestible y consumible y recoge de la base de datos todas las bebidas para rellenar las tablas de nuevo
	 */
	public void cargarBebidas() {
		try {
			Inicializar.vaciarTabla(consumible, modeloConsumible);
			Inicializar.vaciarTabla(componente, modeloComponente);
			for (Consumible i : Bebida.obtenerBebidas()) {
				if (i instanceof Bebida) {
					Object[] fila = {i.getId(),i.getNombre(),Double.toString(i.getPrecio())};
					modeloConsumible.addRow(fila);
				}
			}
			for (Bebida i : Bebida.obtenerBebidas()) {
				Object[] fila = {i.getId(),i.getNombre(),0,false};
				modeloComponente.addRow(fila);
			}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * Dependiendo de la opcion que tenga el boton desplegable ejecuta uno de los tres metoods anteriores y prepara la interfaz
	 */
	public void recargarRecetas() {
		if (elegirTabla.getSelectedItem().toString().equalsIgnoreCase("Menu")) {
			anadirReceta.setText("Añadir Menu");
			guardarComponentes.setVisible(true);
			cargarMenus();
		} else if (elegirTabla.getSelectedItem().toString().equalsIgnoreCase("Plato")) {
			anadirReceta.setText("Añadir Plato");
			guardarComponentes.setVisible(true);
			cargarPlatos();
		} else {
			anadirReceta.setText("Añadir Precio");
			guardarComponentes.setVisible(false);
			cargarBebidas();
		}
	}
	
	/**
	 * Crea el panel nueva receta que se abre al pulsar el boton de añadir plato, menu, andir precio
	 */
	public void nuevaReceta() {
		panelNuevaReceta = new JPanel();
		panelNuevaReceta.setLayout(new MigLayout());

		recetaId = new JLabel("ID: ");
		txtRecetaId = new JTextField(6);
		
		recetaNombre = new JLabel("Nombre: ");
		txtRecetaNombre = new JTextField(6);
		
		recetaPrecio = new JLabel("Precio: ");
		txtRecetaPrecio = new JTextField(6);
		
		if(consumible.getSelectedRow()!=-1) {
			int filaSeleccionada = consumible.getSelectedRow();
			if (consumible.getValueAt(filaSeleccionada, 0).toString().startsWith("B")) {
				txtRecetaId.setText(consumible.getValueAt(filaSeleccionada, 0).toString());
				txtRecetaNombre.setText(consumible.getValueAt(filaSeleccionada, 1).toString());
				txtRecetaPrecio.setText(consumible.getValueAt(filaSeleccionada, 2).toString());	
			}
		}
		
		
		panelNuevaReceta.add(recetaId);
		panelNuevaReceta.add(txtRecetaId);
		panelNuevaReceta.add(recetaNombre);
		panelNuevaReceta.add(txtRecetaNombre);
		panelNuevaReceta.add(recetaPrecio);
		panelNuevaReceta.add(txtRecetaPrecio);
	}
	
	
	/**
	 * Actualiza los datos de la tabla pedidos recogiendolos de la base de datos, este metodo se ejecuta al crear la tabla y
	 * al pulsar el boton actualizar
	 */
	public void recargarPedidos() {
		Statement consulta;
		try {
			Inicializar.vaciarTabla(pedidosCocinero, modeloPedidosCocinero);
			consulta = ConexionBBDD.getConnection().createStatement();
			ResultSet resul=consulta.executeQuery("SELECT * FROM PEDIDOS ORDER BY ID_PEDIDO");
		while(resul.next()) {
			String[] fila = {resul.getString("ID_PEDIDO"),resul.getString("MESA"),resul.getString("ESTADO")};
			modeloPedidosCocinero.addRow(fila);
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
				botonActualizarIngrediente();
			}
			if (e.getSource().equals(eliminarIngrediente)) {
				botonEliminarIngrediente();
			}
			if (e.getSource().equals(elegirTabla)) {
				recargarRecetas();
			}
			if (e.getSource().equals(anadirReceta)) {
				botonAnadirReceta();
			}
			if (e.getSource().equals(eliminarReceta)) {
				int filaSeleccionada = consumible.getSelectedRow();
				Consumible.borrarConsumible(consumible.getValueAt(filaSeleccionada, 0).toString());
				modeloConsumible.removeRow(filaSeleccionada);
			}
			
			if (e.getSource().equals(guardarComponentes)) {
				botonGuardarComponentes();
				
			}
			if (e.getSource().equals(recargarPedidos)) {
				recargarPedidos();
			}
		} catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(this, "Error de conexión, contacte con el administrador.");
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error en sentencia SQL, contacte con el administrador.");
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Número no válido.");
		}
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		try {	
			if (e.getClickCount()==1) {
				if (e.getSource().equals(almacen)) {
					int filaSeleccionada = almacen.getSelectedRow();
					id.setText(almacen.getValueAt(filaSeleccionada, 0).toString());
					txtNombre.setText(almacen.getValueAt(filaSeleccionada, 1).toString());
					txtCantidad.setText(almacen.getValueAt(filaSeleccionada, 2).toString());
				}
				if (e.getSource().equals(consumible)) {
					HashMap<String, Integer> consumibles = new HashMap<String, Integer>();
					JTable tabla = (JTable)e.getSource();
					int filaSeleccionada = tabla.getSelectedRow();
					String idConsumible = tabla.getValueAt(filaSeleccionada, 0).toString();
					
					if (idConsumible.startsWith("M"))
						consumibles = Consumible.buscarComponentes(idConsumible,"menu");
					else if (idConsumible.startsWith("P"))
						consumibles = Consumible.buscarComponentes(idConsumible,"plato");
					else if (idConsumible.startsWith("B"))
						consumibles.put(idConsumible, 1);
					else
						JOptionPane.showMessageDialog(this, "ID Incorrecto.");
					
					for (int i=0; i<modeloComponente.getRowCount(); i++) {
						componente.setValueAt(false, i, 3);
						componente.setValueAt(0, i, 2);
						
						if (consumibles.keySet().contains(componente.getValueAt(i, 0).toString())) {
							//Pongo tick en el boton de check
							componente.setValueAt(true, i, 3);
							componente.setValueAt(consumibles.get(componente.getValueAt(i, 0).toString()), i, 2);
						}
					}
					
				}
				if (e.getSource().equals(pedidosCocinero)) {
					int filaSeleccionada = pedidosCocinero.getSelectedRow();
					String id = pedidosCocinero.getValueAt(filaSeleccionada, 0).toString();
					int resultado = JOptionPane.showConfirmDialog(this,Pedido.mostrarPedido(id) , "Pedido "+id,JOptionPane.OK_CANCEL_OPTION);
					
					if (resultado == JOptionPane.OK_OPTION) {
						if (pedidosCocinero.getValueAt(filaSeleccionada, 2).toString().equalsIgnoreCase("en_espera")) {
				
							Pedido.prepararPedido(id);
							recargarPedidos();
							
						}
					}
				}
			} 
				
		} catch (ClassNotFoundException | SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error de conexion con la base de datos, contacte con el administrador");
		}
	}
	
	
	/**
	 * Metodo ejecutado al pulsar el boton guardar componentes, dependiendo si el consumible seleccionado es un menu o un plato
	 * guarda en una tabla u otra los componentes marcados con un check que forman dicho consumible
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	private void botonGuardarComponentes() throws SQLException, ClassNotFoundException, NumberFormatException {
		int filaSeleccionada = consumible.getSelectedRow();
		System.out.format("%s \n",filaSeleccionada);
		String idConsumible = consumible.getValueAt(filaSeleccionada, 0).toString();
		
		if (idConsumible.startsWith("M")) {
			for (int i=0; i<modeloComponente.getRowCount(); i++) {
				if ((Boolean)componente.getValueAt(i, 3))
					Menu.insertarMenusConsumibles(idConsumible, componente.getValueAt(i, 0).toString(), Integer.parseInt(componente.getValueAt(i, 2).toString()));
			}
			JOptionPane.showMessageDialog(this, "Menu actualizado correctamente.");
		}
		else if (idConsumible.startsWith("P")) {
			for (int i=0; i<modeloComponente.getRowCount(); i++) {
				if ((Boolean)componente.getValueAt(i, 3))
					Plato.insertarPlatoIngredientes(idConsumible, componente.getValueAt(i, 0).toString(), Integer.parseInt(componente.getValueAt(i, 2).toString()));
			}
			JOptionPane.showMessageDialog(this, "Plato actualizado correctamente.");
		}
	}
	
	/**
	 * Metodo ejecutado al pulsar el boton añadir receta, segun si es un menu, plato o bebida, agrega este producto a su correspondiente tabla
	 * en la base de datos y actualiza las tanblas componente y consumible para que aparezca la nueva receta
	 * @throws HeadlessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws NumberFormatException
	 */
	private void botonAnadirReceta()
			throws HeadlessException, ClassNotFoundException, SQLException, NumberFormatException {
		nuevaReceta();
		int resultado = JOptionPane.showConfirmDialog(this, panelNuevaReceta, "Introduce los parametros de la receta",JOptionPane.OK_CANCEL_OPTION);
		if (resultado == JOptionPane.OK_OPTION) {
			String id = txtRecetaId.getText();
			String nombre = txtRecetaNombre.getText();
			String precio = txtRecetaPrecio.getText();
			//String[] fila = {id,nombre,precio};
			//modeloConsumible.addRow(fila);
			if (id.startsWith("M"))
				res.getCarta().anadirConsumible(new Menu(id,nombre,Double.parseDouble(precio)));
			else if (id.startsWith("P"))
				res.getCarta().anadirConsumible(new Plato(id,nombre,Double.parseDouble(precio)));
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
	
	/**
	 * Metodo ejecutado al pulsar el boton eliminar ingrediente, elimina de la tabla almacen y de la base de datos el ingrediente
	 * o bebida seleccionado
	 * @throws NumberFormatException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void botonEliminarIngrediente() throws NumberFormatException, ClassNotFoundException, SQLException {
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
	
	/**
	 * Metodo ejecutado al pulsar el boton actualizar ingrediente, cambia en la base de datos los campos del ingrediente con elm id
	 * puesto en el campo de arriba del fomuralario
	 * @throws NumberFormatException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws HeadlessException
	 */
	private void botonActualizarIngrediente()
			throws NumberFormatException, ClassNotFoundException, SQLException, HeadlessException {
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
		}
		else
			JOptionPane.showMessageDialog(this, "Formato de ID erróneo");
		cargarAlmacen(); //Vuelo a cargar el almacen para que aparezca el nuevo producto
	}

	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
	
