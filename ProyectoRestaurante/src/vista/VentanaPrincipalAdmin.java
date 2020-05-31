package vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.naming.InvalidNameException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import modelo.ConexionBBDD;
import modelo.Restaurante;
import net.miginfocom.swing.MigLayout;
/**
 * Ventana que contiene las opciones disponibles para un usuario jefe o administrador
 */
public class VentanaPrincipalAdmin extends JFrame implements ActionListener,WindowListener{
	//panel principal
	private JPanel panel;
	//componentes panel principal	
	private JLabel title;
	private JButton GestionarUsuarios;
	private JButton abrirCamarero;
	private JButton abrirCocinero;
	
	public VentanaPrincipalAdmin() {
		crearVentana();
	}
	
	public void crearVentana() {
		
		panel = new JPanel();
		panel.setLayout(new MigLayout("align center"));
		
		
		title = new JLabel("Opciones de administrador");
		
		GestionarUsuarios = new JButton("Gestionar usuarios");
		GestionarUsuarios.addActionListener(this);
		
		abrirCamarero = new JButton("Probar camarero");
		abrirCamarero.addActionListener(this);
		
		abrirCocinero = new JButton("Probar cocinero");
		abrirCocinero.addActionListener(this);
		
		panel.add(title,"growx,wrap");
		panel.add(GestionarUsuarios,"growx,wrap");
		panel.add(abrirCamarero,"growx,wrap");
		panel.add(abrirCocinero,"growx,wrap");
		
		
		setLocationRelativeTo(null);
		this.add(panel);
		
		setResizable(false);
		setTitle("Opciones de administrador");
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int height = pantalla.height;
		int width = pantalla.width;
		setSize(width/4, height/3);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);
	    setLocationRelativeTo(null);
	    this.addWindowListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(GestionarUsuarios)) {
			try {
				new VentanaGestionUsuarios();
			} catch (SQLException exception) {
				if(exception.getErrorCode()==1017)
					JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrecto\n"+"Codigo de error:"+exception.getErrorCode(),"Error",2);
				else
					JOptionPane.showMessageDialog(this, exception.getErrorCode());
			} catch (ClassNotFoundException e2) {
				JOptionPane.showMessageDialog(this, "No se puede iniciar la conexion.\nConsultelo con su administrador","Error",1);
			}
		}
		else if (e.getSource().equals(abrirCamarero)) {
			new VentanaPrincipalCamarero();
		}
		
		else if (e.getSource().equals(abrirCocinero)) {
			new VentanaPrincipalCocinero();
		}
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		try {
			ConexionBBDD.cerrarConexion();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, e1.getErrorCode());
		}
		
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
