package vista;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

public class VentanaLogin extends JFrame implements ActionListener{
	
	
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
		panel.setLayout(new MigLayout());
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
		panel.add(acceder,"skip, split2");
		panel.setAlignmentX(CENTER_ALIGNMENT);
		setLocationRelativeTo(null);
		this.add(panel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Inicio de sesi�n");
		pack();
	    setVisible(true);
	    setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(acceder)) {
			ConexionBBDD.setUsuario(escribeUser.getText());
			ConexionBBDD.setContrasena(new String(escribePassword.getPassword()));
			Statement st=null;
			ResultSet rs=null;
			try {
				st = ConexionBBDD.getConnection().createStatement();
				rs=st.executeQuery("SELECT TIPO FROM EMPLEADO WHERE USERNAME=USER");
				rs.next();
				String tipo=rs.getNString("TIPO");
				switch (tipo) {
				case "Jefe":
					new VentanaAdminPrincipal();
					break;
				case "Camarero":
					new VentanaPrincipalCamarero();
					break;
				case "Cocinero":
					new VentanaPrincipalCocinero();
					break;
				}
			} catch (SQLException exception) {
					if(exception.getErrorCode()==1017)
						JOptionPane.showMessageDialog(this, "Usuario o contrase�a incorrecto\n"+"Codigo de error:"+exception.getErrorCode(),"Error",2);
					else
						JOptionPane.showMessageDialog(this, "Error de acesso");
			} catch (ClassNotFoundException e2) {
					JOptionPane.showMessageDialog(this, "No se puede iniciar la conexion.\nConsultelo con su administrador","Error",1);
			}
			finally {
				if(st!=null) {
					try {
						st.close();
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(this,e1);
					}
				}
			}
			
		}
		
	}
}
