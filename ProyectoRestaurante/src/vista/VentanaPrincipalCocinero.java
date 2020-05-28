package vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import modelo.Restaurante;
import net.miginfocom.swing.MigLayout;

public class VentanaPrincipalCocinero extends JFrame implements ActionListener{
	
	private Restaurante res;
	
	
	private JPanel panel;
	
	public VentanaPrincipalCocinero() {
		crearVentana();
		try {
			res = new Restaurante();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void crearVentana() {
		panel = new JPanel();
		panel.setLayout(new MigLayout());
		

		
		setLocationRelativeTo(null);
		this.add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Camarero");
		//Con el codigo comentado la ventana adapta su tamaño segun el tamaño de la pantalla
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int height = pantalla.height;
		int width = pantalla.width;
		setSize(width/2, height/2);
	  
	    setLocationRelativeTo(null);
	    setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
	
