package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
/**
 * Clase que permite obtener y actualizar la carta o la lista de empleados
 * 
 *
 */
public class Restaurante {
	
	private Carta carta;
	private int mesas;
	private HashSet<Empleado> listaEmpleados;
	
	/**
	 * 
	 * @param booleano que indica el modo de inicializacion
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Restaurante(boolean admin) throws ClassNotFoundException, SQLException {
		if(admin)
			prepararRestauranteAdmin();
		else
			prepararRestaurante();
	}
	
	/**
	 * Metodo que prepara el restaurante para los usuarios cocinero o camarero
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void prepararRestaurante() throws ClassNotFoundException, SQLException {
		this.carta = new Carta();
		this.listaEmpleados = null;
		this.mesas = cargarMesas();
	}
	/**
	 * Metodo que prepara el restaurante para los usuarios jefes
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void prepararRestauranteAdmin() throws ClassNotFoundException, SQLException {
		this.carta = null;
		actualizarListaEmpleados();
		this.mesas = cargarMesas();
	}
	/**
	 * Metodo que carga la configuracion del numero de mesas desde la bbdd
	 * @return numero de mesas de la configuracion, si no esta configurada devuelve 999
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private int cargarMesas() throws ClassNotFoundException, SQLException {
		Statement consulta=ConexionBBDD.getConnection().createStatement();
		ResultSet resul=consulta.executeQuery("SELECT VALUE FROM CONFIGURACION WHERE KEY='n_mesas'");
		if(resul.next())
			return Integer.parseInt(resul.getString("VALUE"));
		else
			return 999;
	}

	/**
	 * Metodo que actualiza la lista de empleados del restaurante 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void actualizarListaEmpleados() throws ClassNotFoundException, SQLException {
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
	}
	/**
	 * Metodo que permite obtener al empleado con la id correspondiente
	 * @param id del empleado a obtener
	 * @return empleado a obtener
	 */
	public Empleado consultarEmpleado(String id) {
		for (Empleado empleado: listaEmpleados) {
			if(empleado.getId().equals(id)) {
				return empleado;
			}
		}
		return null;
	}
	/**
	 * Metodo que elimina usuario del empleado y elimina fecha de contrato
	 * @param emp
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void despedirEmpleado(Empleado emp) throws ClassNotFoundException, SQLException {
		for (Iterator iterator = listaEmpleados.iterator(); iterator.hasNext();) {
			Empleado empleado = (Empleado) iterator.next();
			if(empleado.equals(emp)) {
				empleado.borrarUsuario();
				empleado.setFechaContrato(null);
				break;
			}
		}
		
	}
	/**
	 * Metodo que devuelve el siguiente ID de empleado para asignar
	 * @return El siguiente id
	 */
	public String generarIDEmpleado() {
		String id="E";
		int numeroID=this.getListaEmpleados().size()+1;
		for (int i = 0; i < 3-(String.valueOf(numeroID)).length(); i++) {
			id+="0";
		}
		id+=numeroID;
		return id;
	}
	public void contratarEmpleado(Empleado emp,String password) throws ClassNotFoundException, SQLException {
		try {
			ConexionBBDD.getConnection().setAutoCommit(false);
			if(this.listaEmpleados.contains(emp)) {
				emp.crearUsuario(password);
				emp.actualizarFechaContrato();
				this.listaEmpleados.add(emp);
			}
			else {
				emp.crearEmpleado();
				emp.crearUsuario(password);
				this.listaEmpleados.add(emp);
			}
			ConexionBBDD.getConnection().commit();
		}finally {
			ConexionBBDD.getConnection().setAutoCommit(true);
		}
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







