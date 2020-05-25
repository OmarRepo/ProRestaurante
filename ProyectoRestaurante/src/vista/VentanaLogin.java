package vista;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTree;

import modelo.Restaurante;
import net.miginfocom.swing.MigLayout;

public class VentanaLogin extends JFrame implements ActionListener{
	
	
	private JLabel user;
	private JTextField escribeUser;
	private JLabel password;
	private JPasswordField escribePassword;
	private JButton acceder;
	private JButton crearUsuario;
	private JPanel panel;
	Restaurante res;
	
	
	public VentanaLogin() {
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
		acceder.addActionListener(this);
		crearUsuario = new JButton("Crear Usuario");
		crearUsuario.addActionListener(this);
		crearUsuario.setToolTipText("Necesitas ser administrador.");
		
		panel.add(user);
		panel.add(escribeUser,"wrap");
		panel.add(password);
		panel.add(escribePassword,"wrap");
		panel.add(acceder,"skip, split2");
		panel.add(crearUsuario);
		panel.setAlignmentX(CENTER_ALIGNMENT);
		setLocationRelativeTo(null);
		this.add(panel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Inicio de sesión");
		//Con el codigo comentado la ventana adapta su tamaño segun el tamaño de la pantalla
		/*Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int height = pantalla.height;
		int width = pantalla.width;
		setSize(width/6, height/6);	*/
		pack();
	    setVisible(true);
	    setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(acceder)) {
			
			new VentanaPrincipalCamarero();
		}
		if (e.getSource().equals(crearUsuario)) {
			
			
			
		}
		
	}
}
