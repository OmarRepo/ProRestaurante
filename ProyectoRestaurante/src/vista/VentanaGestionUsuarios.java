package vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
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
import modelo.ConexionBBDD;
import modelo.Empleado;
import modelo.FieldFormatException;
import modelo.Limites;
import modelo.Restaurante;
import modelo.TIPO_EMPLEADO;
import net.miginfocom.swing.MigLayout;
/**
 * Clase que al ser instanciada crea una ventana destinada a la gestion de usuarios y empleados.
 * 
 *
 */
public class VentanaGestionUsuarios extends JFrame implements ActionListener,MouseListener,WindowListener{

	private static final String ID_DEFAULT;
	private static final String NUEVO_USUARIO1;
	private static final String NUEVO_USUARIO2;
	private static final String DESPEDIR_EMPLEADO;

	private Restaurante restaurante;
	private JPanel panelGestion;
	private JPanel panelDatos;
	private JTable tablaUsuarios;
	private ModeloTablaUsuarios modeloTablaUsuarios;
	private JScrollPane scrolltablaUsuarios;

	private JButton crearUsuario;
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

	static {
		NUEVO_USUARIO1 = "Nuevo usuario";
		NUEVO_USUARIO2 = "Guardar usuario";
		DESPEDIR_EMPLEADO ="Eliminar usuario";
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
		eliminarUsuario = new JButton("Eliminar Usuario");
		eliminarUsuario.addActionListener(this);
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
		panelDatos.add(crearUsuario,"growx");;
		panelDatos.add(eliminarUsuario,"wrap");
		//insDatosen el panel general
		panelGestion.add(panelDatos,"growy,pushy");
		panelGestion.add(scrolltablaUsuarios,"grow,push,shrink 0");




		this.add(panelGestion);

		//ajustes a a esta ventana
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
		this.addWindowListener(this);
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
	 * Metodo que verifica la valided de los datos introducidos
	 * @throws FieldFormatException 
	 */
	private void verificarCampos() throws FieldFormatException {
		String cause="DNI";
		if(Limites.comprobarDNI(DNIText.getText())) {
			cause="Nombre, maximo 15 caracteres";
			if(Limites.comprobarL(nombreText.getText(), 15)) {
				cause="Apellidos, maximo 30 caracteres";
				if(Limites.comprobarL(apellidoText.getText(), 30)) {
					cause="Contraseña, maximo 15 caracteres";
					if(Limites.comprobarL(new String(contrasenaText.getPassword()), 15)) {
						return;
					}
				}
			}
		}
		throw new FieldFormatException(cause);
	}
	/**
	 * Metodo que genera un empleado desde los datos del panel datos usuarios
	 * @throws FieldFormatException 
	 */
	private Empleado generarEmpleado() throws FieldFormatException {
		verificarCampos();
		Empleado generado=new Empleado(IDText.getText(),DNIText.getText(),nombreText.getText(),apellidoText.getText(),
				"",new Date(System.currentTimeMillis()),(TIPO_EMPLEADO)tipoCombobox.getSelectedItem());
		generado.generarUsername();
		return generado;

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
			if(e.getSource().equals(tablaUsuarios)) {
				Empleado emp=restaurante.consultarEmpleado((String) tablaUsuarios.getValueAt(tablaUsuarios.getSelectedRow(),0));
				if(crearUsuario.getText().equals(NUEVO_USUARIO1)) {
					mostrarDatosEmpleado(emp);
				}
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
					IDText.setText(restaurante.generarIDEmpleado());
					contrasenaText.setText("");
					contrasenaText.setEditable(true);
				}
				else {
					finalizarCrearUsuario();
				}
			}
			else if(evento.getSource().equals(eliminarUsuario)) {
				if(eliminarUsuario.getText().equals(DESPEDIR_EMPLEADO))
					despedirEmpleado();
			}
		}catch (SQLException exception) {
			if(exception.getErrorCode()==1017)
				JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrecto\n"+"Codigo de error:"+exception.getErrorCode(),"Error",2);
			else {
				try {
					if(!ConexionBBDD.getConnection().getAutoCommit())
						ConexionBBDD.getConnection().rollback();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				exception.printStackTrace();
			}//JOptionPane.showMessageDialog(this, "Error de base de datos\n Codigo error:"+exception.getErrorCode());
		} catch (ClassNotFoundException e2) {
			JOptionPane.showMessageDialog(this, "No se puede iniciar la conexion.\nConsultelo con su administrador","Error",1);
		} catch (FieldFormatException e) {
			JOptionPane.showMessageDialog(this, "Error en el campo "+e.getMessage());
			;
		}
	}

	/**
	 * Metodo que borra al usuario seleccionado tras pedir confirmacion
	 * Borra al empleado de la tabla de usuarios y eliminar su usuario  
	 * @throws SQLException 
	 * @throws ClassNotFoundException
	 */
	private void despedirEmpleado() throws ClassNotFoundException, SQLException {
		if(tablaUsuarios.getSelectedRow()!=-1) {
			String id=(String) tablaUsuarios.getValueAt(tablaUsuarios.getSelectedRow(),0);
			Empleado emp=restaurante.consultarEmpleado(id);
			String advertencia="Se despedira al empleado y se eliminara su usario. ¿Esta seguro?";
			if(JOptionPane.showConfirmDialog(this,advertencia,"Borrar usuario",2,3)==JOptionPane.OK_OPTION) {
				restaurante.despedirEmpleado(emp);
				modeloTablaUsuarios.removeRow(tablaUsuarios.getSelectedRow());
				modeloTablaUsuarios.addRow(emp);
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "Ningun empleado seleccionado por favor seleccione uno de la tabla");
		}
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
	 * @throws FieldFormatException 
	 */
	private void finalizarCrearUsuario() throws ClassNotFoundException, SQLException, FieldFormatException {
		restaurante.contratarEmpleado(generarEmpleado(), new String(contrasenaText.getPassword()));
		modeloTablaUsuarios.addRow(generarEmpleado());
		edicionPanelDatos(false);
		contrasenaText.setEditable(false);
		IDText.setText(ID_DEFAULT);
		crearUsuario.setText(NUEVO_USUARIO1);
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		try {
			ConexionBBDD.cerrarConexion();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
