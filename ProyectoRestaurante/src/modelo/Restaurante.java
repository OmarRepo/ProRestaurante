package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

public class Restaurante {
	
	private Carta carta;
	private int mesas;
	private HashSet<Empleado> listaEmpleados;
	
	//Constructores
	public Restaurante(boolean admin) throws ClassNotFoundException, SQLException {
		if(admin)
			prepararRestauranteAdmin();
		else
			prepararRestaurante();
	}
	
	//Metodos
	private void prepararRestaurante() throws ClassNotFoundException, SQLException {
		this.carta = new Carta();
		this.listaEmpleados = null;
		this.mesas = cargarMesas();
	}
	private void prepararRestauranteAdmin() throws ClassNotFoundException, SQLException {
		this.carta = new Carta();
		this.listaEmpleados = actualizarEmpleados();
		this.mesas = cargarMesas();
	}
	private int cargarMesas() throws ClassNotFoundException, SQLException {
		Statement consulta=ConexionBBDD.getConnection().createStatement();
		ResultSet resul=consulta.executeQuery("SELECT VALUE FROM CONFIGURACION WHERE KEY='n_mesas'");
		if(resul.next())
			return Integer.parseInt(resul.getString("VALUE"));
		else
			return 999;
	}

	
	private HashSet<Empleado> actualizarEmpleados() throws ClassNotFoundException, SQLException {
		Statement consulta=null;
		ResultSet resul=null;
		try {
			listaEmpleados = new HashSet<Empleado>();
			consulta=ConexionBBDD.getConnection().createStatement();
			resul=consulta.executeQuery("SELECT * FROM EMPLEADOS");
			while(resul.next()) {
				listaEmpleados.add(new Empleado(resul.getString(1),resul.getString(2),resul.getString(3),resul.getString(4),resul.getString(5),resul.getDate(6),TIPO_EMPLEADO.valueOf(resul.getString(7))));
			}
		}finally {
			consulta.close();
		}
		return listaEmpleados;
	}
	
	@Override
	public String toString() {
		return "Restaurante [carta=" + carta + ", Mesas=" + mesas + "]";
	}
	
	public Carta getCarta() {
		return carta;
	}


	public int getListaMesas() {
		return mesas;
	}


	public HashSet<Empleado> getListaEmpleados() {
		return listaEmpleados;
	}

	public void setCarta(Carta carta) {
		this.carta = carta;
	}


	public void setListaMesas(int mesas) {
		this.mesas = mesas;
	}


	public void setListaEmpleados(HashSet<Empleado> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}
	
}







