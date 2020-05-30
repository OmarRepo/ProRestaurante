package vista;

import java.sql.Date;

import javax.swing.table.DefaultTableModel;

import modelo.Empleado;
import modelo.TIPO_EMPLEADO;

public class ModeloTablaUsuarios extends DefaultTableModel {
	
	public ModeloTablaUsuarios(Object object, String[] titulos) {
		super((Object[][]) object,titulos);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public Class getColumnClass(int column) {
		switch (column) {
			case 0:
				//id
				return String.class;
			case 1:
				//dni
				return String.class;
			case 2:
				//nombre
				return String.class;
			case 3:
				//apellido
				return String.class;
			case 4:
				//fecha contratacion
				return Date.class;
			case 5:
				//tipo
				return TIPO_EMPLEADO.class;
			default:
				return String.class;
		}
	}

	public void addRow(Empleado emp) {
		addRow(new Object[]{emp.getId(),emp.getDni(),emp.getNombre(),emp.getApellidos(),emp.getFechaContrato(),emp.getTipo()});
	}
	
	
}
