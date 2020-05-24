package vista;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class VentanaPrincipal extends JFrame{
	
	private JLabel user;
	private JTextField escribeUser;
	private JLabel password;
	private JPasswordField escribePassword;
	private JButton acceder;
	private JPanel panel;
	
	public VentanaPrincipal() {
		crearVentana();
	}
	
	public void crearVentana() {
		panel = new JPanel();
		panel.setLayout(new MigLayout());
		user = new JLabel("Usuario: ");
		escribeUser = new JTextField(10);
		password = new JLabel("Constaseña: ");
		escribePassword = new JPasswordField(10);
		acceder = new JButton("Acceder");
		
		panel.add(user);
		panel.add(escribeUser,"wrap");
		panel.add(password);
		panel.add(escribePassword,"wrap");
		panel.add(acceder,"skip");
		panel.setAlignmentX(CENTER_ALIGNMENT);
		setLocationRelativeTo(null);
		this.add(panel);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Inicio de sesión");
		//Con el codigo comentado la ventana adapta su tamaño segun el tamaño de la pantalla
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int height = pantalla.height;
		int width = pantalla.width;
		setSize(width/3, height/3);
	  
	    setLocationRelativeTo(null);
	    setVisible(true);
	}
	
}
