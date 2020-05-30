package vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import modelo.Empleado;
import modelo.Restaurante;
import modelo.TIPO_EMPLEADO;
import net.miginfocom.swing.MigLayout;
/**
 * Clase que al ser instanciada crea una ventana destinada a la gestion de usuarios.
 * 
 *
 */
public class VentanaGestionUsuarios extends JFrame implements ActionListener,MouseListener{
	
	private Restaurante res;
	private JPanel panelGestion;
	private JPanel panelDatos;
	private JTable tablaUsuarios;
	private ModeloTablaUsuarios modeloTablaUsuarios;
	private JScrollPane scrolltablaUsuarios;
	
	private JButton crearUsuario;
	private JButton modificarUsuario;
	private JButton eliminarUsuario;
	
	private JLabel ID;
	private JLabel IDText;
	private JLabel DNI;
	private JTextField DNIText;
	private JLabel nombre;
	private JTextField nombreText;
	private JLabel apellido;
	private JTextField apellidoText;
	private JLabel constrasena;
	private JPasswordField constrasenaText;
	private JLabel fecha;
	private JLabel fechaText;
	private JLabel tipo;
	private JComboBox<TIPO_EMPLEADO> tipoCombobox;
	
	public VentanaGestionUsuarios() throws ClassNotFoundException, SQLException {
		res=new Restaurante(true);
		crearVentana();
		cargarValores();
	}
	/**
	 * Metodo que crea los componentes de la ventana y los añade a la misma.
	 */
	private void crearVentana() {
		//panel de ventana
		panelGestion = new JPanel();
		panelGestion.setLayout(new MigLayout("align 50%"));
		//tabla de usuarios
		tablaUsuarios = new JTable();
		tablaUsuarios.addMouseListener(this);
		String[] titulosTablaUsuarios={"ID","DNI","Nombre","Apellidos","Contrato","Tipo"};
		modeloTablaUsuarios=new ModeloTablaUsuarios(null, titulosTablaUsuarios);
		tablaUsuarios.setModel(modeloTablaUsuarios);
		scrolltablaUsuarios=new JScrollPane(tablaUsuarios);
		//botones
		crearUsuario = new JButton("Nuevo Usuario");
		crearUsuario.addActionListener(this);
		modificarUsuario = new JButton("Modificar Usuario");
		modificarUsuario.addActionListener(this);
		eliminarUsuario = new JButton("Eliminar Usuario");
		eliminarUsuario.addActionListener(this);
		//etiquetas y campos
		panelDatos=new JPanel();
		panelDatos.setLayout(new MigLayout());
		ID=new JLabel("ID:");
		IDText=new JLabel("ninguno");
		DNI=new JLabel("DNI:");
		DNIText=new JTextField();
		nombre=new JLabel("Nombre:");
		nombreText=new JTextField();
		apellido=new JLabel("Apellidos:");
		apellidoText=new JTextField();
		constrasena=new JLabel("Contraseña:");
		constrasenaText=new JPasswordField();
		fecha=new JLabel("Fecha Contratacion:");
		fechaText=new JLabel();
		tipo=new JLabel("Tipo:");
		tipoCombobox=new JComboBox<>();
		fecha=new JLabel("Fecha Contratacion:");
		fechaText=new JLabel();
		//insercion en el panel datos
		panelDatos.add(ID);
		panelDatos.add(IDText,"wrap");
		panelDatos.add(DNI);
		panelDatos.add(DNIText,"wrap");
		panelDatos.add(nombre);
		panelDatos.add(nombreText,"wrap");
		panelDatos.add(apellido);
		panelDatos.add(apellidoText,"wrap");
		panelDatos.add(constrasena);
		panelDatos.add(constrasenaText,"wrap");
		panelDatos.add(fecha);
		panelDatos.add(fechaText,"wrap");
		panelDatos.add(tipo);
		panelDatos.add(tipoCombobox,"wrap");
		panelDatos.add(crearUsuario);
		panelDatos.add(modificarUsuario);
		panelDatos.add(eliminarUsuario);
		//insDatosen el panel general
		panelGestion.add(panelDatos,"growy,pushy");
		panelGestion.add(scrolltablaUsuarios,"grow,push");
		
		
		
		
		this.add(panelGestion);
		
		//ajustes a a esta ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Gestion de usuarios");
		//ajustar tamaño
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int height = pantalla.height;
		int width = pantalla.width;
		setSize((int) (width/1.5), height/2);
		//centrar la ventana
	    setLocationRelativeTo(null);
	    //lo hacemos visible
	    setVisible(true);
	}
	/**
	 * Metodo dedicado a cargar los valores de la ventana tras iniciarla.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void cargarValores() throws ClassNotFoundException, SQLException {
		res.actualizarListaEmpleados();
		cargarTablaUsuarios(res.getListaEmpleados());
	}
	/**
	 * Metodo dedicada a cargar en la tabla de usuarios los empleados de la coleccion pasada
	 * @param empleados una coleccion de empleados
	 */
	private void cargarTablaUsuarios(Collection<Empleado> empleados) {
		for (Empleado empleado : empleados) {
			modeloTablaUsuarios.addRow(empleado);
		}
	}
	/**
	 * Metodo dedicado a mostrar los datos del empleado pasado por parametro en la parte derecha de la ventana
	 * @param empleado a ser mostrado
	 */
	private void mostrarDatosEmpleado(Empleado emp) {
		IDText.setText(emp.getId());
		DNIText.setText(emp.getDni());
		nombreText.setText(emp.getNombre());
		apellidoText.setText(emp.getApellidos());
		constrasenaText.setEditable(false);
		fechaText.setText(emp.getFechaContrato().toString());
		tipoCombobox.setEditable(false);
		tipoCombobox.setSelectedItem(emp.getTipo());
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Espera que clickques sobre un usuario de la tabla de usuarios para entonces cargarlo en la parte derecha
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getClickCount()==1) {
			if(e.getSource().equals(tablaUsuarios)&&modificarUsuario.getText().equals("Modificar usuario")) {
				Empleado emp=res.consultarEmpleado((String) tablaUsuarios.getValueAt(0, tablaUsuarios.getSelectedRow()));
				mostrarDatosEmpleado(emp);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(crearUsuario)) {
			if(crearUsuario.getText().equals("Crear usuario")) {
				iniciarCrearUsuario();
			}
			else
				finalizarCrearUsuario();
		}
		else if(e.getSource().equals(modificarUsuario)) {
			if(modificarUsuario.getText().equals("Modificar usuario")) {
				iniciarModificacionUsuario();
			}
			else
				finalizarModificacionUsuario();
		}
		if(e.getSource().equals(eliminarUsuario));
			borrarUsuario((String) tablaUsuarios.getValueAt(0, tablaUsuarios.getSelectedRow()));
	}
	/**
	 * Metodo que borra al usuario con el id pasado por parametro tras pedir confirmacion
	 * Borra al usuario de la BBDD y pone a nulo los campos con excepcion de la id
	 * @param id
	 */
	private void borrarUsuario(String id) {
		Empleado emp=res.consultarEmpleado((String) tablaUsuarios.getValueAt(0, tablaUsuarios.getSelectedRow()));
		
	}
	/**
	 * Metodo que desbloquea los campos del usuario mostrado para poder modificarlos
	 */
	private void iniciarModificacionUsuario() {

	}
	/**
	 * Metodo que efectua la modificacion con los nuevos datos
	 */
	private void finalizarModificacionUsuario() {
		
	}
	/**
	 * Metodo que vacio los campos y los desbloquea para poder rellenarlos
	 */
	private void iniciarCrearUsuario() {

	}
	/**
	 * Metodo que efectua la creacion con los nuevos datos
	 */
	private void finalizarCrearUsuario() {

	}
}
