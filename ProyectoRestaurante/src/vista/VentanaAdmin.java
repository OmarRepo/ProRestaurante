package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import modelo.Restaurante;
import net.miginfocom.swing.MigLayout;

public class VentanaAdmin extends JFrame implements ActionListener{
	
	private JLabel skillsAdmin;
	private JButton crearUsuario;
	private JPanel panel;
	Restaurante res;
	
	public VentanaAdmin() {
		crearVentana();
	}
	
	public void crearVentana() {
		
		panel = new JPanel();
		panel.setLayout(new MigLayout());
		
		skillsAdmin = new JLabel("Opciones de administrador");
		crearUsuario = new JButton("Crear Usuario");
		crearUsuario.addActionListener(this);
		crearUsuario.setToolTipText("Necesitas ser administrador.");
		
		panel.add(skillsAdmin);
		panel.add(crearUsuario);
		
		panel.setAlignmentX(CENTER_ALIGNMENT);
		setLocationRelativeTo(null);
		this.add(panel);
		
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
		if (e.getSource().equals(crearUsuario)) {
			
		}
	
	}
	
}
