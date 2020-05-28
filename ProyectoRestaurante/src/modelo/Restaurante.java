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
	public Restaurante(Carta carta, int mesas, HashSet<Empleado> listaEmpleados, Almacen almacen) {
		this.carta = carta;
		this.mesas = mesas;
		this.listaEmpleados = listaEmpleados;
	}
	public Restaurante() throws ClassNotFoundException, SQLException {
		prepararRestaurante();
	}
	
	//Metodos
	public void prepararRestaurante() throws ClassNotFoundException, SQLException {
		this.carta = new Carta();
		this.listaEmpleados = actualizarEmpleados();
	}
	
	private HashSet<Empleado> actualizarEmpleados() throws ClassNotFoundException, SQLException {
		listaEmpleados = new HashSet<Empleado>();
		Statement consulta=ConexionBBDD.getConnection().createStatement();
		ResultSet resul=consulta.executeQuery("SELECT * FROM EMPLEADOS");
		while(resul.next()) {
			switch (resul.getString("TIPO")) {
				case "Camarero":
					listaEmpleados.add(new Camarero(resul.getString("ID_EMPLEADO"),resul.getString("DNI"),resul.getString("NOMBRE")+" "+resul.getString("APELLIDOS")));
					break;
				case "Cocinero":
					listaEmpleados.add(new Cocinero(resul.getString("ID_EMPLEADO"),resul.getString("DNI"),resul.getString("NOMBRE")+" "+resul.getString("APELLIDOS")));
					break;
				case "Jefe":
					listaEmpleados.add(new Jefe(resul.getString("ID_EMPLEADO"),resul.getString("DNI"),resul.getString("NOMBRE")+" "+resul.getString("APELLIDOS")));
			}
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







