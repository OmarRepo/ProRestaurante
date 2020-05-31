package vista;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
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
	
	//PESTAÑA CARTA-----------------------------------
	private JScrollPane sCartaMenu;
	private JTable cartaMenu;
	private ModeloTabla modeloMenu;
	private JScrollPane sCartaPlatos;
	private JTable cartaPlatos;
	private ModeloTabla modeloPlato;
	private JScrollPane sCartaBebidas;
	private JTable cartaBebidas;
	private ModeloTabla modeloBebida;
	
	//PESTAÑA PEDIDOS---------------------------------
	private ModeloTabla modelPedidos;
	private ModeloTabla modelCarta;
	private JTable tablaPedidos;
	private JTable tablaCarta;
	private JButton anadirPedido;
	//PANEL PARA INTRODUCIR NUEVOS PEDIDOS------------
			private JLabel mesa;
			private JTextField txtMesa;
			private JPanel panelIntroducirPedido;
	//------------------------------------------------
	private JButton eliminarPedido;
	private JButton pagarPedido;
	private JButton guardarPedido;
	private JButton cargarPedidos;
		
	//PANELES-----------------------------------------
	private JTabbedPane pestanas;
	private JPanel panelCarta;
	private JPanel panelPedidos;
	private JScrollPane panelTablaPedidos;
	private JScrollPane panelTablaCarta;
	//-------------------------------------------------
	
	public VentanaPrincipalCamarero() {
		try {
			res = new Restaurante(false);
			crearVentana();
			prepararTablas();
			prepararCarta();
			prepararMuestraDeCarta();
			recargarPedidos();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos.");
		}
	}
	
	/**
	 * Recorre la carta del restaurante y rellena la tabla que muesta la carta de la pestaña pedidos
	 */
	public void prepararCarta() {
		for (Consumible i : this.res.getCarta().getListaConsumibles()) {
			Object[] fila = {i.getId(),i.getNombre(),1,Double.toString(i.getPrecio()),false};
			modelCarta.addRow(fila);
		}
	}
	/**
	 * Recorre la carta del restaurante y segun el tipo de producto rellena las 3 tablas que seran para la muestra del cliente de la pestaña carta
	 */
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
	
	/**
	 * Metodo que inicializa todos los objectos de la ventana
	 */
	public void crearVentana() {
		
		pestanas = new JTabbedPane();
		
		//PANEL CARTA-------------------------------------------------------------------------------------------------------------
		panelCarta = new JPanel();
		panelCarta.setLayout(new MigLayout());
			
		String[] titulosM = {"Menus","Precio"}; //Cabecera de la tabla con los menus
		modeloMenu = new ModeloTabla(null, titulosM);
		String[] titulosP = {"Platos","Precio"}; //Cabecera de la tabla con los platos
		modeloPlato = new ModeloTabla(null, titulosP);
		String[] titulosB = {"Bebidas","Precio"}; //Canecera de la tabla con las bebidas
		modeloBebida = new ModeloTabla(null, titulosB);
		
		//Alinear hacia la derecha las columnas
		DefaultTableCellRenderer alinearDerecha = new DefaultTableCellRenderer();
		alinearDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//CARTA MENUS-----------------------------------------------------------------------------------------------------------------
		cartaMenu = new JTable();
		cartaMenu.setModel(modeloMenu);
		cartaMenu.setShowGrid(false);
		
		cartaMenu.getTableHeader().setFont(new Font("Courier New",1,18)); //poner el tipo de letra a la cabecera de menus
		cartaMenu.setFont(new Font("Courier New",0,13)); //poner el tipo de letra a las filas de menus
		cartaMenu.setFillsViewportHeight(true); //rellena la tabla con filas vacias hasta el fondo SOLO ASPECTO VISUAL
		
		cartaMenu.getColumnModel().getColumn(1).setCellRenderer(alinearDerecha); //Coges el modelo de las columnas, el numero de columna que queires editar y seleccionas el renderizado
		//CARTA PLATOS------------------------------------------------------------------------------------------------------------------
		cartaPlatos = new JTable();
		cartaPlatos.setModel(modeloPlato);
		cartaPlatos.setShowGrid(false);
		cartaPlatos.getTableHeader().setFont(new Font("Courier New",1,18)); //poner el tipo de letra a la cabecera de platos
		cartaPlatos.setFont(new Font("Courier New",0,13)); //poner el tipo de letra a las filas de platos
		cartaPlatos.getColumnModel().getColumn(1).setCellRenderer(alinearDerecha);
		cartaPlatos.setFillsViewportHeight(true);
		
		//CARTA BEBIDAS------------------------------------------------------------------------------------------------------------------
		cartaBebidas = new JTable();
		cartaBebidas.setModel(modeloBebida);
		cartaBebidas.setShowGrid(false);
		cartaBebidas.getTableHeader().setFont(new Font("Courier New",1,18));
		cartaBebidas.setFont(new Font("Courier New",0,13));
		cartaBebidas.getColumnModel().getColumn(1).setCellRenderer(alinearDerecha);
		cartaBebidas.setFillsViewportHeight(true);
		//----------------------------------------------------------------------------------------------------------------------------
		//Paneles donde se guardan sus respectivas tablas
		sCartaMenu = new JScrollPane(cartaMenu);
		sCartaPlatos = new JScrollPane(cartaPlatos);
		sCartaBebidas = new JScrollPane(cartaBebidas);
		
		//se añaden los paneles al panel principal de la pestaña
		panelCarta.add(sCartaMenu);
		panelCarta.add(sCartaPlatos);
		panelCarta.add(sCartaBebidas);
		
		//se crea una nueva pestaña y se añade su respectivo panel
		pestanas.addTab("Carta", panelCarta);
		
		//PESTAÑA PEDIDOS--------------------------------------------------------------------------------------------------------------
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
		
		//se crea la pestaña y se le añade su panel
		pestanas.addTab("Pedidos", panelPedidos);
		
		//se añade las pestañas a la ventana
		this.add(pestanas);
		
		//CONFIGURACION DE LA VENTANA
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setTitle("Camarero");
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int height = pantalla.height;
		int width = pantalla.width;
		setSize((int) (width/1.5), height/2);
	  
	    setLocationRelativeTo(null);
	    setVisible(true);
	}
	/**
	 * Este metodo prepara la cabecera y añade el modelo a las ventanas de la pestaña pedidos
	 */
	public void prepararTablas() {
		
		String titulosPedidos[] = {"ID Pedido","Mesa","Estado"};
		modelPedidos = new ModeloTabla(null,titulosPedidos);
		tablaPedidos.setModel(modelPedidos);
	
		String titulosCarta[] = {"ID Consumible","Nombre","Cantidad","Precio",""};
		modelCarta = new ModeloTabla(null,titulosCarta);
		tablaCarta.setModel(modelCarta);
		tablaCarta.setCellEditor(tablaCarta.getDefaultEditor(Boolean.class));
		
	}
	/**
	 * Crea el panel y sus elementos que se usara al pulsar le boton de añadir pedido
	 */
	public void nuevoPedido() {
		panelIntroducirPedido = new JPanel();
		panelIntroducirPedido.setLayout(new MigLayout());

		mesa = new JLabel("Mesa: ");
		txtMesa = new JTextField(4);
		
		panelIntroducirPedido.add(mesa);
		panelIntroducirPedido.add(txtMesa,"wrap");

	}
	/**
	 * Crea un objeto pedido apartir de los elemetos de la tabla correspondientes a la fila que este seleccionada en el momento
	 * de usar el metodo
	 * 
	 * @return Pedido el objeto pedido creado
	 */
	public Pedido crearPedidoSeleccionado() {
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
		try {
			if (e.getSource().equals(cargarPedidos)) {
				recargarPedidos();
			}
			if (e.getSource().equals(anadirPedido)) {
					botonAnadirPedido();
			}
			if (e.getSource().equals(eliminarPedido)) {
				botonEliminarPedido();
			}
			if (e.getSource().equals(pagarPedido)) {
				botonPagarPedido();
			}
			if (e.getSource().equals(guardarPedido)) {
				botonGuardarPedido();
			}
		} catch (ClassNotFoundException e1) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(this, "Error al conectarse con la base de datos, contacte con el administrador");
		} catch (HeadlessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(this, "Error en sentencia SQL, contacte con el administrador y muestre el mensaje"
					+ "\n"+e1.getMessage());
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this, "Error al crear la factura, contacte con el administrador");
		}
	}
	


	@Override
	public void mousePressed(MouseEvent e) {
		
		try {
			cargarConsumiblesDelPedido(e);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, "Número introducido no válido.");
		} catch (HeadlessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos, contacte con el administrador.");
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(this, "Error en sentencia SQL, contacte con el administrador y muestre el mensaje"
					+ "\n"+e1.getMessage());
		}
	}
	
	
	/**
	 * Metodo ejecutado al hacer click sobre una fila de la tabla de pedidos, carga todos los consumibles de la base de datos 
	 * y los marca con un check segun si estan asignados al pedido o no, ademas tambien modifica la cantidad, segun la que este asignada
	 * al pedido 
	 * @param e
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws NumberFormatException
	 * @throws HeadlessException
	 */
	private void cargarConsumiblesDelPedido(MouseEvent e)
			throws ClassNotFoundException, SQLException, NumberFormatException, HeadlessException {
		int cont=0;
		if (e.getClickCount()==1) {
			
			HashMap<String, Integer> consumibles = new HashMap<String, Integer>();
			JTable tabla = (JTable)e.getSource();
			int filaSeleccionada = tabla.getSelectedRow();
			String idPedido = tabla.getValueAt(filaSeleccionada, 0).toString();
			consumibles = Pedido.buscarConsumibles(idPedido);
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
		}
	}
	
	
	
	/**
	 * Metodo ejecutado al pulsar el boton guardar pedido, guarda los consumibles del pedido o los elimina si estos han sido 
	 * retirados  y guarda el pedido en la base de datos. 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws NumberFormatException
	 * @throws HeadlessException
	 */
	private void botonGuardarPedido()
			throws ClassNotFoundException, SQLException, NumberFormatException, HeadlessException {
		Pedido pedido = crearPedidoSeleccionado();
		HashMap<String, Integer> consumibles = null;
		int cantidad;
		String idConsumible;
		pedido.calcularPrecio();
		if (!pedido.buscarPedido()) 
			pedido.insertarPedido();
		else
			pedido.modificarPedido();
		consumibles = Pedido.buscarConsumibles(pedido.getIdPedido());
		
		//Seleccionar Consumibles
		for (int i=0; i<tablaCarta.getRowCount();i++) {
		
			cantidad = Integer.parseInt(tablaCarta.getValueAt(i, 2).toString());
			idConsumible = tablaCarta.getValueAt(i, 0).toString();
			if (cantidad == 0) cantidad = 1;
			if (consumibles.containsKey(tablaCarta.getValueAt(i, 0))) {
			
					if ((Boolean)tablaCarta.getValueAt(i, 4)) {
						//Modificar cantidad pedido
						pedido.modificarPedidoConsumible(cantidad, idConsumible);
					} else {
						//borrar pedido
						pedido.cancelarPedidoConsumible(idConsumible);
					}
			} else if ((Boolean)tablaCarta.getValueAt(i, 4)) {
				//Insertar pedido consumible
				pedido.insertarPedidosConsumibles(cantidad, idConsumible);
			}
			
		}
		JOptionPane.showMessageDialog(this, "Pedido guardado correctamente");
	}
	
	/**
	 * Metodo ejecutado al pulsar el boton de pagar pedido, calcula el precio total del pedido, crea el txt con la factura en
	 * la carpeta facturas y cambia el estado del pedido ha pagado
	 * @throws HeadlessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	private void botonPagarPedido() throws HeadlessException, ClassNotFoundException, SQLException, IOException {
		int filaSeleccionada = tablaPedidos.getSelectedRow();
		Pedido pedido;
		if (filaSeleccionada == -1)
			JOptionPane.showMessageDialog(null, "No hay ninguna fila seleccionada.");
		else if (modelPedidos.getValueAt(filaSeleccionada, 2).equals(modelo.ESTADO_PEDIDO.preparado.name())) {
			pedido = crearPedidoSeleccionado();
			pedido.calcularPrecio();
			pedido.imprimirFactura();
			modelPedidos.setValueAt(modelo.ESTADO_PEDIDO.pagado.name(), filaSeleccionada, 2);
		} else
			JOptionPane.showMessageDialog(this, "El pedido aun no ha sido preparado.");
	}
	
	/**
	 * Metodo ejecutado al pulsar el boton eliminar pedido, elimina la fila seleccionada y a partir de los datos de esa fila
	 * cambia el estado del pedido en la base de datos a cancelado
	 * @throws HeadlessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void botonEliminarPedido() throws HeadlessException, ClassNotFoundException, SQLException {
		int filaSeleccionada = tablaPedidos.getSelectedRow();
		if (filaSeleccionada == -1)
			JOptionPane.showMessageDialog(null, "No hay ninguna fila seleccionada.");
		else {
				new Pedido(tablaPedidos.getValueAt(filaSeleccionada, 0).toString()).borrarPedido();
				modelPedidos.removeRow(filaSeleccionada);
		}
	}
	
	/**
	 * Metodo ejecutado al pulsa el boton añadir pedido, abre una ventana en la que se introduce el numeor de mesa del pedido, luego, 
	 * una vez dado a ok se crea una nueva fila en la tabla con el pedido, y s epuede proceder a elegir los
	 * consumibles
	 * @throws HeadlessException
	 */
	private void botonAnadirPedido() throws HeadlessException {
		nuevoPedido();
		int resultado = JOptionPane.showConfirmDialog(this, panelIntroducirPedido, "Introduce los parametros del pedido",JOptionPane.OK_CANCEL_OPTION);
		if (resultado == JOptionPane.OK_OPTION) {
			String[] fila = {Pedido.generarIdPedido(),txtMesa.getText(),modelo.ESTADO_PEDIDO.en_espera.name()};
			
			modelPedidos.addRow(fila);
		}
	}

	/**
	 * Metodo ejecutado al pulsar el boton de cargar pedidos, carga los pedidos de la base de datos y añade las filas a la tabla
	 */
	public void recargarPedidos() {
		Statement consulta;
		try {
			Inicializar.vaciarTabla(tablaPedidos, modelPedidos);
			consulta = ConexionBBDD.getConnection().createStatement();
			ResultSet resul=consulta.executeQuery("SELECT * FROM PEDIDOS ORDER BY ID_PEDIDO");
		while(resul.next()) {
			String[] fila = {resul.getString("ID_PEDIDO"),resul.getString("MESA"),resul.getString("ESTADO")};
			modelPedidos.addRow(fila);
		}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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

