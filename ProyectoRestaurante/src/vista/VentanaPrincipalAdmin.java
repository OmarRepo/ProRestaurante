package vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import modelo.Restaurante;
import net.miginfocom.swing.MigLayout;

public class VentanaPrincipalAdmin extends JFrame implements ActionListener{
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
		setTitle("Inicio de sesión");
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int height = pantalla.height;
		int width = pantalla.width;
		setSize(width/4, height/3);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);
	    setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(GestionarUsuarios)) {
			try {
				new VentanaGestionUsuarios();
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if (e.getSource().equals(abrirCamarero)) {
			new VentanaPrincipalCamarero();
		}
		
		else if (e.getSource().equals(abrirCocinero)) {
			new VentanaPrincipalCocinero();
		}
		
	}
	
}
