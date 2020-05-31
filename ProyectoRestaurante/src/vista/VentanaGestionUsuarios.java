package vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;
import java.util.HashSet;

import javax.naming.InvalidNameException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sun.tools.javac.util.Name.Table;

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

	private static final String ID_DEFAULT;
	private static final String NUEVO_USUARIO1;
	private static final String NUEVO_USUARIO2;
	private static final String MODIFICAR_USUARIO1;
	private static final String MODIFICAR_USUARIO2;
	private Restaurante restaurante;
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
	private JLabel contrasena;
	private JPasswordField contrasenaText;
	private JLabel fecha;
	private JLabel fechaText;
	private JLabel tipo;
	private JComboBox<TIPO_EMPLEADO> tipoCombobox;
	private JButton limpiarSeleccion;

	static {
		MODIFICAR_USUARIO1 = "Modificar usuario";
		MODIFICAR_USUARIO2 = "Guardadr modificacion";
		NUEVO_USUARIO1 = "Nuevo Usuario";
		NUEVO_USUARIO2 = "Guardar usuario";
		ID_DEFAULT = "ninguno";
	}
	public VentanaGestionUsuarios() throws ClassNotFoundException, SQLException {
		restaurante=new Restaurante(true);
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
		String[] titulosTablaUsuarios={"ID","DNI","Nombre","Apellidos","Usuario","Fecha Contrato","Tipo"};
		modeloTablaUsuarios=new ModeloTablaUsuarios(null, titulosTablaUsuarios);
		tablaUsuarios.setModel(modeloTablaUsuarios);
		scrolltablaUsuarios=new JScrollPane(tablaUsuarios);
		//botones
		crearUsuario = new JButton(NUEVO_USUARIO1);
		crearUsuario.addActionListener(this);
		modificarUsuario = new JButton(MODIFICAR_USUARIO1);
		modificarUsuario.addActionListener(this);
		eliminarUsuario = new JButton("Eliminar Usuario");
		eliminarUsuario.addActionListener(this);
		limpiarSeleccion = new JButton("Limpiar Seleccion");
		limpiarSeleccion.addActionListener(this);
		//etiquetas y campos
		panelDatos=new JPanel();
		panelDatos.setLayout(new MigLayout());
		ID=new JLabel("ID:");
		IDText=new JLabel(ID_DEFAULT);
		DNI=new JLabel("DNI:");
		DNIText=new JTextField(9);
		nombre=new JLabel("Nombre:");
		nombreText=new JTextField(15);
		apellido=new JLabel("Apellidos:");
		apellidoText=new JTextField(15);
		contrasena=new JLabel("Contraseña:");
		contrasenaText=new JPasswordField(15);
		contrasenaText.setEditable(false);
		fecha=new JLabel("Fecha Contratacion:");
		fechaText=new JLabel();
		tipo=new JLabel("Tipo:");
		tipoCombobox=new JComboBox<TIPO_EMPLEADO>(TIPO_EMPLEADO.values());
		fecha=new JLabel("Fecha Contratacion:");
		fechaText=new JLabel();
		edicionPanelDatos(false);
		//insercion en el panel datos
		panelDatos.add(ID);
		panelDatos.add(IDText,"wrap");
		panelDatos.add(DNI);
		panelDatos.add(DNIText,"wrap");
		panelDatos.add(nombre);
		panelDatos.add(nombreText,"wrap");
		panelDatos.add(apellido);
		panelDatos.add(apellidoText,"wrap");
		panelDatos.add(contrasena);
		panelDatos.add(contrasenaText,"wrap");
		panelDatos.add(fecha);
		panelDatos.add(fechaText,"wrap");
		panelDatos.add(tipo);
		panelDatos.add(tipoCombobox,"wrap");
		panelDatos.add(crearUsuario,"growx");
		panelDatos.add(modificarUsuario,"split2");
		panelDatos.add(eliminarUsuario,"wrap");
		panelDatos.add(limpiarSeleccion,"wrap");
		//insDatosen el panel general
		panelGestion.add(panelDatos,"growy,pushy");
		panelGestion.add(scrolltablaUsuarios,"grow,push,shrink 0");




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
		setMinimumSize(new Dimension(1000, 300));
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
		restaurante.actualizarListaEmpleados();
		cargarTablaUsuarios(restaurante.getListaEmpleados());
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
		if(emp.getFechaContrato()!=null)
			fechaText.setText(emp.getFechaContrato().toString());
		tipoCombobox.setSelectedItem(emp.getTipo());
		edicionPanelDatos(false);
	}
	/**
	 * Metodo que genera un empleado desde los datos del panel datos usuarios
	 */
	private Empleado generarEmpleado() {
		return null;
		
	}
	/**
	 * Metodo que altera la capacidad de editar en el panel de datos
	 * @param modo
	 */
	private void edicionPanelDatos(boolean modo) {
		DNIText.setEditable(modo);
		nombreText.setEditable(modo);
		apellidoText.setEditable(modo);
		contrasenaText.setEditable(modo);
		tipoCombobox.setEnabled(modo);
	}
	/**
	 * 
	 */
	private void vaciarPanelDatos() {
		IDText.setText("");
		DNIText.setText("");
		nombreText.setText("");
		apellidoText.setText("");
		fechaText.setText("");
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
			if(e.getSource().equals(tablaUsuarios)&&modificarUsuario.getText().equals(MODIFICAR_USUARIO1)&&crearUsuario.getText().equals(NUEVO_USUARIO1)) {
				Empleado emp=restaurante.consultarEmpleado((String) tablaUsuarios.getValueAt(0, tablaUsuarios.getSelectedRow()));
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
	public void actionPerformed(ActionEvent evento) {
		try {
			if(evento.getSource().equals(crearUsuario)) {
				if(crearUsuario.getText().equals(NUEVO_USUARIO1)) {
					iniciarCrearUsuario();
				}
				else {
					finalizarCrearUsuario();
				}
			}
			else if(evento.getSource().equals(modificarUsuario)) {
				if(modificarUsuario.getText().equals(MODIFICAR_USUARIO1)) {
					iniciarModificacionUsuario();
				}
				else {
					finalizarModificacionUsuario();
				}
			}
			else if(evento.getSource().equals(eliminarUsuario)) {
				borrarUsuario();
			}
			else if(evento.getSource().equals(limpiarSeleccion)) {
				deseleccionarEmpleado();
			}
		}catch (SQLException exception) {
			if(exception.getErrorCode()==1017)
				JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrecto\n"+"Codigo de error:"+exception.getErrorCode(),"Error",2);
			else
				JOptionPane.showMessageDialog(this, exception.getErrorCode());
		} catch (ClassNotFoundException e2) {
			JOptionPane.showMessageDialog(this, "No se puede iniciar la conexion.\nConsultelo con su administrador","Error",1);
		}
	}
	/**
	 * Metodo que borra al usuario seleccionado tras pedir confirmacion
	 * Borra al empleado de la tabla de usuarios y eliminar su usuario  
	 * @throws SQLException 
	 * @throws ClassNotFoundException
	 */
	private void borrarUsuario() throws ClassNotFoundException, SQLException {
		if(tablaUsuarios.getSelectedRow()!=-1) {
			String id=(String) tablaUsuarios.getValueAt(0, tablaUsuarios.getSelectedRow());
			Empleado emp=restaurante.consultarEmpleado(id);
			String advertencia="Se borrara al empleado y se eliminara su usario.\nEsta decision no se puede revertir. ¿Esta seguro?";
			if(JOptionPane.showConfirmDialog(this,advertencia,"Borrar usuario",2,3)==0) {
				restaurante.borrarEmpleado(emp);
				modeloTablaUsuarios.removeRow(tablaUsuarios.getSelectedRow());
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "Ningun empleado seleccionado por favor seleccione uno de la tabla");
		}
	}
	
	/**
	 * Metodo que desbloquea los campos del usuario mostrado para poder modificarlos
	 */
	private void iniciarModificacionUsuario() {
		modificarUsuario.setText(MODIFICAR_USUARIO2);
		edicionPanelDatos(true);
	}
	/**
	 * Metodo que efectua la modificacion con los nuevos datos
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private void finalizarModificacionUsuario() throws ClassNotFoundException, SQLException {
		modificarUsuario.setText(MODIFICAR_USUARIO1);
		edicionPanelDatos(false);
		generarEmpleado().modificarEmpleado();
	}
	/**
	 * Metodo que vacio los campos y los desbloquea para poder rellenarlos
	 */
	private void iniciarCrearUsuario() {
		crearUsuario.setText(NUEVO_USUARIO2);
		edicionPanelDatos(true);
		vaciarPanelDatos();
		fechaText.setText((new Date(System.currentTimeMillis()).toString()));
	}
	/**
	 * Metodo que efectua la creacion con los nuevos datos y vuelve a bloquear los campos
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private void finalizarCrearUsuario() throws ClassNotFoundException, SQLException {
		crearUsuario.setText(NUEVO_USUARIO1);
		edicionPanelDatos(false);
		restaurante.contratarEmpleado(generarEmpleado(), new String(contrasenaText.getPassword()));
	}
	/**
	 * Metodo que deselecciona el usuario elejido y borra sus datos para poder crear un usario nuevo
	 */
	private void deseleccionarEmpleado() {
		edicionPanelDatos(false);
		tablaUsuarios.clearSelection();
		vaciarPanelDatos();
		IDText.setText(ID_DEFAULT);
	}
}
