package modelo;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class Empleado {

	private String id;
	private String dni;
	private String nombre;
	private String username;
	private String apellidos;
	private Date fechaContrato;
	private TIPO_EMPLEADO tipo;
	/*Aqui he decidido probar con el Date de la libreria SQL para ver si no da problemas 
	en lugar de la de util*/

	//Constructores
	public Empleado(String id, String dni, String nombre, String apellidos ,String username,Date fechaContrato,
			TIPO_EMPLEADO tipo) {
		this.id = id;
		this.dni = dni;
		this.nombre = nombre;
		this.username = username;
		this.apellidos = apellidos;
		this.fechaContrato = fechaContrato;
		this.tipo = tipo;
	}
	public Empleado(Empleado that) {
		this.id = that.id;
		this.dni = that.dni;
		this.nombre = that.nombre;
		this.username = that.username;
		this.apellidos = that.apellidos;
		this.fechaContrato = that.fechaContrato;
		this.tipo=that.tipo;
	}

	//Metodos
	public void borrarEmpleado() throws ClassNotFoundException, SQLException {
		Statement st=null;
		try {
			ConexionBBDD.getConnection().setAutoCommit(false);
			st= ConexionBBDD.getConnection().createStatement();
			st.executeUpdate("DROP USER "+this.getUsername());
			st.executeUpdate("UPDATE EMPLEADOS SET USERNAME=null WHERE ID_EMPLEADO='"+this.getId()+"'");
			ConexionBBDD.getConnection().commit();
		}finally {
			if(st!=null)
				st.close();
			ConexionBBDD.getConnection().setAutoCommit(true);
		}

	}
	/**
	 * Metodo que crea el empleado en la base de datos
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void crearEmpleado() throws ClassNotFoundException, SQLException {
		Statement st=null;
		try {
			ConexionBBDD.getConnection().setAutoCommit(false);
			st= ConexionBBDD.getConnection().createStatement();
			st.executeUpdate("INSERT INTO EMPLEADOS(ID_EMPLEADO, DNI, NOMBRE, APELLIDO, USERNAME, TIPO)"+
			"VALUES('"+this.getId()+"','"+this.getDni()+"','"+this.getNombre()+"','"+this.getApellidos()+"','"+this.getUsername()+"','"+this.getTipo()+"')");
			ConexionBBDD.getConnection().commit();
		}finally {
			if(st!=null)
				st.close();
			ConexionBBDD.getConnection().setAutoCommit(true);
		}
	}
	/**
	 * Metodo que crea el usuario del empleado con diferentes permisos segun su tipo
	 * @param password
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void crearUsuario(String password) throws ClassNotFoundException, SQLException {
		Statement st=null;
		try {
			ConexionBBDD.getConnection().setAutoCommit(false);
			st= ConexionBBDD.getConnection().createStatement();
			st.executeUpdate("CREATE USER "+this.getUsername()+" IDENTIFIED BY " +password+ 
					"DEFAULT TABLESPACE \"Restaurante\"" + 
					"TEMPORARY TABLESPACE temp" + 
					"ACCOUNT UNLOCK");
			if(this.tipo.equals(TIPO_EMPLEADO.Jefe)) {
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.PLATO_INGREDIENTES TO "+this.getUsername());
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.MENUS_CONSUMIBLES TO "+this.getUsername());
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.PEDIDOS_CONSUMIBLES TO "+this.getUsername());
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.INGREDIENTES TO "+this.getUsername());
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.BEBIDAS TO "+this.getUsername());
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.PLATOS TO "+this.getUsername());
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.MENUS TO "+this.getUsername());
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.CONSUMIBLES TO "+this.getUsername());
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.PEDIDOS TO "+this.getUsername());
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.EMPLEADOS TO "+this.getUsername());
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.CONFIGURACION TO "+this.getUsername());
				st.addBatch("GRANT CREATE USER TO "+this.getUsername());
				st.addBatch("GRANT DROP USER TO "+this.getUsername());
			}
			else if(this.tipo.equals(TIPO_EMPLEADO.Camarero)) {
				st.addBatch("GRANT SELECT, ON restaurante.PLATO_INGREDIENTES;");
				st.addBatch("GRANT SELECT, DELETE ON restaurante.MENUS_CONSUMIBLES;");
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.PEDIDOS_CONSUMIBLES;");
				st.addBatch("GRANT SELECT, UPDATE ON restaurante.INGREDIENTES;");
				st.addBatch("GRANT SELECT, UPDATE ON restaurante.BEBIDAS;");
				st.addBatch("GRANT SELECT ON restaurante.CONSUMIBLES;");
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.PEDIDOS;");
				st.addBatch("GRANT SELECT ON restaurante.EMPLEADOS;");
				st.addBatch("GRANT SELECT ON restaurante.CONFIGURACION;");
			}
			else if(this.tipo.equals(TIPO_EMPLEADO.Cocinero)) {
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.PLATO_INGREDIENTES;");
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.MENUS_CONSUMIBLES;");
				st.addBatch("GRANT SELECT ON restaurante.PEDIDOS_CONSUMIBLES;");
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.INGREDIENTES;");
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.BEBIDAS;");
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.PLATOS;");
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.MENUS;");
				st.addBatch("GRANT SELECT, INSERT, UPDATE, DELETE ON restaurante.CONSUMIBLES;");
				st.addBatch("GRANT SELECT, UPDATE, ON restaurante.PEDIDOS;");
				st.addBatch("GRANT SELECT ON restaurante.EMPLEADOS;");
				st.addBatch("GRANT SELECT ON restaurante.CONFIGURACION;");
			}
			st.addBatch("UPDATE EMPLEADO SET USERNAME='"+this.getUsername()+"'WHERE ID_EMPLEADO='"+this.getId()+"'");
			st.executeBatch();
			ConexionBBDD.getConnection().commit();
		}finally {
			if(st!=null)
				st.close();
			ConexionBBDD.getConnection().setAutoCommit(true);
		}
	}
	public void modificarEmpleado() throws ClassNotFoundException, SQLException {
		Statement st=null;
		try {
			ConexionBBDD.getConnection().setAutoCommit(false);
			st= ConexionBBDD.getConnection().createStatement();
			st.executeUpdate("UPDATE EMPLEADOS SET DNI='"+this.getDni()+"', SET NOMBRE='"+this.getNombre()+"', SET APELLIDOS='"+this.getApellidos()+"', SET TIPO='"+this.getTipo().name()+"')");
			ConexionBBDD.getConnection().commit();
		}finally {
			if(st!=null)
				st.close();
			ConexionBBDD.getConnection().setAutoCommit(true);
		}
	}
	public String getId() {
		return id;
	}
	public String getDni() {
		return dni;
	}
	public String getNombre() {
		return nombre;
	}
	public String getUsername() {
		return username;
	}
	public String getApellidos() {
		return apellidos;
	}
	public Date getFechaContrato() {
		return fechaContrato;
	}
	public TIPO_EMPLEADO getTipo() {
		return tipo;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public void setFechaContrato(Date fechaContrato) {
		this.fechaContrato = fechaContrato;
	}
	public void setTipo(TIPO_EMPLEADO tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj instanceof Empleado)) {
			Empleado that=(Empleado)obj;
			if(this.id.equals(that.id))
				return true;
		}
		return false;

	}
	@Override
	public String toString() {
		return "Empleado [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", username=" + username + ", apellidos="
				+ apellidos + ", fechaContrato=" + fechaContrato + ", tipo=" + tipo + "]";
	}










}
