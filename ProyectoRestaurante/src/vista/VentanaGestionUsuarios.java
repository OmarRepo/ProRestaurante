package vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
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

public class VentanaGestionUsuarios extends JFrame implements ActionListener,MouseListener{
	
	private Restaurante res;
	private JPanel panelGestion;
	
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
	
	private void crearVentana() {
		//panel de ventana
		panelGestion = new JPanel();
		panelGestion.setLayout(new MigLayout("align 50% 50%"));
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
		ID=new JLabel("ID:");
		IDText=new JLabel("ninguno");
		DNI=new JLabel("DNI:");
		DNIText=new JTextField();
		nombre=new JLabel("Nombre:");
		nombreText=new JTextField();
		apellido=new JLabel("Apellidos:");
		apellidoText=new JTextField();
		constrasena=new JLabel("Contrase�a:");
		constrasenaText=new JPasswordField();
		fecha=new JLabel("Fecha Contratacion:");
		fechaText=new JLabel();
		tipo=new JLabel("Tipo:");
		tipoCombobox=new JComboBox<>();
		fecha=new JLabel("Fecha Contratacion:");
		fechaText=new JLabel();
		//insercion en el panel general
		panelGestion.add(scrolltablaUsuarios,"growy,pushy");
		
		panelGestion.add(ID);
		panelGestion.add(IDText,"wrap");
		panelGestion.add(DNI);
		panelGestion.add(DNIText,"wrap");
		panelGestion.add(nombre);
		panelGestion.add(nombreText,"wrap");
		panelGestion.add(apellido);
		panelGestion.add(apellidoText,"wrap");
		panelGestion.add(constrasena);
		panelGestion.add(constrasenaText,"wrap");
		panelGestion.add(fecha);
		panelGestion.add(fechaText,"wrap");
		panelGestion.add(tipo);
		panelGestion.add(tipoCombobox,"wrap");
		
		panelGestion.add(crearUsuario);
		panelGestion.add(modificarUsuario);
		panelGestion.add(eliminarUsuario);
		this.add(panelGestion);
		
		//ajustes a a esta ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Gestion de usuarios");
		//ajustar tama�o
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int height = pantalla.height;
		int width = pantalla.width;
		setSize((int) (width/1.5), height/2);
		//centrar la ventana
	    setLocationRelativeTo(null);
	    //lo hacemos visible
	    setVisible(true);
	}
	
	private void cargarValores() {
		cargarTablaUsuarios(res.getListaEmpleados());
	}
	
	private void cargarTablaUsuarios(HashSet<Empleado> empleados) {
		for (Empleado empleado : empleados) {
			modeloTablaUsuarios.addRow(empleado);
		}
	}
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

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getClickCount()==1) {
			if(e.getSource().equals(tablaUsuarios)) {
				Empleado emp=Restaurante.consultarEmpleado((String) tablaUsuarios.getValueAt(0, tablaUsuarios.getSelectedRow()));
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
			if(modificarUsuario.getText().equals("Modificar texto")) {
				iniciarModificacionUsuario();
			}
			else
				finalizarModificacionUsuario();
		}
		
	}

	private void finalizarModificacionUsuario() {
		
	}

	private void iniciarModificacionUsuario() {

	}

	private void finalizarCrearUsuario() {

	}

	private void iniciarCrearUsuario() {

	}

}
