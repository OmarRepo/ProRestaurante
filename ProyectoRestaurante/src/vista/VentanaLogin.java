package vista;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InvalidNameException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTree;

import modelo.ConexionBBDD;
import modelo.Restaurante;
import net.miginfocom.swing.MigLayout;
/**
 * Ventana destinada al logeo de usuarios, dependiendo de su tipo se abrira una ventana u otra
 * 
 *
 */
public class VentanaLogin extends JFrame implements ActionListener,WindowListener{


	private JLabel user;
	private JTextField escribeUser;
	private JLabel password;
	private JPasswordField escribePassword;
	private JButton acceder;
	private JPanel panel;

	public VentanaLogin() {
		crearVentana();
	}

	public void crearVentana() {

		panel = new JPanel();
		panel.setLayout(new MigLayout("align center"));
		user = new JLabel("Usuario: ");
		escribeUser = new JTextField(10);
		password = new JLabel("Constase�a: ");
		escribePassword = new JPasswordField(10);
		acceder = new JButton("Acceder");
		acceder.addActionListener(this);

		panel.add(user);
		panel.add(escribeUser,"wrap");
		panel.add(password);
		panel.add(escribePassword,"wrap");
		panel.add(acceder,"skip, growx");
		panel.setAlignmentX(CENTER_ALIGNMENT);
		setLocationRelativeTo(null);
		this.add(panel);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 200);
		setResizable(false);
		setTitle("Inicio de sesi�n");
		setVisible(true);
		setLocationRelativeTo(null);
		this.addWindowListener(this);
	}
	public String obtenerTipoUsuario() throws ClassNotFoundException, SQLException, InvalidNameException {
		ConexionBBDD.setUsuario("resadmin");
		ConexionBBDD.setContrasena("resadmin123");
		Statement st=null;
		ResultSet rs=null;
		try {
			st = ConexionBBDD.getConnection().createStatement();
			rs=st.executeQuery("SELECT TIPO FROM EMPLEADOS WHERE USERNAME='"+escribeUser.getText()+"' AND CONTRASENA ='"+new String(escribePassword.getPassword())+"'");
			if(rs.next()) {
				return rs.getString(1);
			}
			else {
				throw new InvalidNameException();
			}
		}finally {
			if(st!=null) {
				st.close();
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		try {		
			if (e.getSource().equals(acceder)) {			
				switch (obtenerTipoUsuario()) {
				case "Jefe":
					new VentanaPrincipalAdmin();
					break;
				case "Camarero":
					new VentanaPrincipalCamarero();
					break;
				case "Cocinero":
					new VentanaPrincipalCocinero();
					break;
				}
				this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			}
			} catch (SQLException exception) {
				if(exception.getErrorCode()==1017)
					JOptionPane.showMessageDialog(this, "Usuario o contrase�a incorrecto\n"+"Codigo de error:"+exception.getErrorCode(),"Error",2);
				else
					JOptionPane.showMessageDialog(this, exception.getErrorCode());
			} catch (ClassNotFoundException e2) {
				JOptionPane.showMessageDialog(this, "No se puede iniciar la conexion.\nConsultelo con su administrador","Error",1);
			} catch (InvalidNameException e1) {
				JOptionPane.showMessageDialog(this, "Error:\nEmpleado sin usuario");
			}
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

