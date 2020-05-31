package modelo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
/**
 * Clase dedicada a manipular los datos del empleado
 * 
 *
 */
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
	/**
	 * Metodo que crea el empleado en la base de datos
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void crearEmpleado() throws ClassNotFoundException, SQLException {
		PreparedStatement prst=null;
		try {
			prst = ConexionBBDD.getConnection().prepareStatement("INSERT INTO EMPLEADOS(ID_EMPLEADO, DNI, NOMBRE, APELLIDOS, FECHA_CONTRATACION ,USERNAME, TIPO) VALUES(?,?,?,?,?,?,?)");
			prst.setString(1, this.getId());
			prst.setString(2, this.getDni());
			prst.setString(3, this.getNombre());
			prst.setString(4, this.getApellidos());
			prst.setDate(5, this.getFechaContrato());
			prst.setString(6, this.getUsername());
			prst.setString(7, this.getTipo().name());
			prst.execute();
		}finally {
			if(prst!=null)
				prst.close();
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
			st= ConexionBBDD.getConnection().createStatement();
			st.executeUpdate("UPDATE EMPLEADOS SET USERNAME='"+this.getUsername()+"', CONTRASENA='"+password+"' WHERE ID_EMPLEADO='"+this.getId()+"'");
		}finally {
			st.close();
		}
	}
	public void borrarUsuario() throws ClassNotFoundException, SQLException {
		Statement st=null;
		try {
			st= ConexionBBDD.getConnection().createStatement();
			st.executeUpdate("UPDATE EMPLEADOS SET USERNAME='',CONTRASENA='' WHERE ID_EMPLEADO='"+this.getId()+"'");
		}finally {
			st.close();
		}
	}
	
	/**
	 * Metodo que modifica ciertos campos de empleado
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void modificarEmpleado() throws ClassNotFoundException, SQLException {
		PreparedStatement prst=null;
		try {
			prst= ConexionBBDD.getConnection().prepareStatement("UPDATE EMPLEADOS SET DNI=?, NOMBRE=?, APELLIDOS=?, TIPO=? WHERE ID_EMPLEADO=?");
			prst.setString(1, this.getDni());
			prst.setString(2, this.getNombre());
			prst.setString(3, this.getApellidos());
			prst.setString(4, this.getTipo().name());
			prst.setString(5, this.getId());
			prst.execute();
		}finally {
			if(prst!=null)
				prst.close();
		}
	}
	/**
	 * Metodo que modifica la fecha contratacion con la actual
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void actualizarFechaContrato() throws ClassNotFoundException, SQLException {
		PreparedStatement prst=null;
		try {
			prst= ConexionBBDD.getConnection().prepareStatement("UPDATE EMPLEADOS SET FECHA_CONTRATO=? WHERE ID_EMPLEADO=?");
			prst.setDate(1,new Date(System.currentTimeMillis()));
			prst.setString(2, this.getId());
			prst.execute();
		}finally {
			if(prst!=null)
				prst.close();
		}
	}
	/**
	 * Metodo que genera el username a partir del id y su nombre
	 */
	public void generarUsername() {
		this.setUsername(id+nombre.substring(0, 3));
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
