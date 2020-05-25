package vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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

public class VentanaPrincipalCamarero extends JFrame implements ActionListener{
	
	private Restaurante res;
	private	HashMap<String, Integer> consumibles;
	
	private JButton mostrarCarta;
	private JLabel idPedido;
	private JTextField txtIdPedido;
	private JLabel mesa;
	private JTextField txtMesa;
	private JLabel consumible;
	private JTextField txtConsumible;
	private JLabel cantidad;
	private JTextField txtCantidad;
	private JButton anadirIngrediente;
	private JButton hacerPedido;
	private JButton pagar;
	
	private JPanel panel;
	
	public VentanaPrincipalCamarero() {
		crearVentana();
		res = Pruebas.prepararRestaurante();
		consumibles = new HashMap<String, Integer>();
	}
	
	public void crearVentana() {
		panel = new JPanel();
		panel.setLayout(new MigLayout());
		
		mostrarCarta = new JButton("Mostrar Carta");
		mostrarCarta.addActionListener(this);
		
		idPedido = new JLabel("ID Pedido: ");
		txtIdPedido = new JTextField(4);
		mesa = new JLabel("Mesa: ");
		txtMesa = new JTextField(4);
		consumible = new JLabel("Consumible: ");
		txtConsumible = new JTextField(4);
		cantidad = new JLabel("Cantidad: ");
		txtCantidad = new JTextField(4);
		
		anadirIngrediente = new JButton("Añadir Consumible");
		anadirIngrediente.addActionListener(this);
		hacerPedido = new JButton("Hacer Pedido");
		hacerPedido.addActionListener(this);
		pagar = new JButton("Pagar");
		pagar.addActionListener(this);
		
		panel.add(mostrarCarta,"wrap");
		panel.add(idPedido);
		panel.add(txtIdPedido,"wrap");
		panel.add(mesa);
		panel.add(txtMesa,"wrap");
		panel.add(consumible);
		panel.add(txtConsumible,"wrap");
		panel.add(cantidad);
		panel.add(txtCantidad,"wrap");
		panel.add(anadirIngrediente);
		panel.add(hacerPedido,"split2");
		panel.add(pagar);
		
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
		if (e.getSource().equals(mostrarCarta))
			JOptionPane.showMessageDialog(this, res.getCarta().mostrarCarta());
		else if (e.getSource().equals(anadirIngrediente)) {
			Pruebas.AnadirConsumible(consumibles, txtConsumible.getText(), Integer.parseInt(txtCantidad.getText()));
		}	
		else if (e.getSource().equals(hacerPedido)) {
			JOptionPane.showMessageDialog(this, Pruebas.hacerPedido(res, consumibles, txtIdPedido.getText(), Integer.parseInt(txtMesa.getText())));
		}
		else if (e.getSource().equals(pagar)) {
			try {
				Pruebas.pagarPedido(res);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
}
