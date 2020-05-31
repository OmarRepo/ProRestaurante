package vista;

import javax.swing.table.DefaultTableModel;

public class ModeloRecetas extends DefaultTableModel {
	
	public ModeloRecetas(Object object, String[] titulosPedidos) {
		// TODO Auto-generated constructor stub
		super((Object[][]) object,titulosPedidos);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		if (this.getColumnName(3).equals(""))
			return true;
		return false;
	}
	
	public Class getColumnClass(int column) {
		switch (column) {
			case 0:
				return String.class;
			case 1:
				return String.class;
			case 2:
				return String.class;
			default:
				return Boolean.class;
		}
	}
	
	
}
