package modelo;

import java.sql.Date;
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
	public Empleado(String id, String dni, String nombre, String username, String apellidos, Date fechaContrato,
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
	public static Empleado consultarEmpleado(String valueAt) {
		
		return null;
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
	public String toString() {
		return "Empleado [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", username=" + username + ", apellidos="
				+ apellidos + ", fechaContrato=" + fechaContrato + ", tipo=" + tipo + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(id,dni,nombre);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Empleado))
			return false;
		Empleado other = (Empleado) obj;
		if (apellidos == null) {
			if (other.apellidos != null)
				return false;
		} else if (!apellidos.equals(other.apellidos))
			return false;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		if (fechaContrato == null) {
			if (other.fechaContrato != null)
				return false;
		} else if (!fechaContrato.equals(other.fechaContrato))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (tipo != other.tipo)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
	
	
	



	
	
	
}
