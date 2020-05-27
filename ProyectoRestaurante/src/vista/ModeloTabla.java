package vista;

import javax.swing.table.DefaultTableModel;

public class ModeloTabla extends DefaultTableModel {
	
	public ModeloTabla(Object object, String[] titulosPedidos) {
		// TODO Auto-generated constructor stub
		super((Object[][]) object,titulosPedidos);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
