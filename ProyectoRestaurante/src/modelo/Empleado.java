package modelo;

import java.sql.Date;

public class Empleado {
	
	private String id;
	private String dni;
	private String nombre;
	private Date fechaContrato;
	/*Aqui he decidido probar con el Date de la libreria SQL para ver si no da problemas 
	en lugar de la de util*/
	
	//Constructores
	public Empleado(String id, String dni, String nombre) {
		this.id = id;
		this.dni = dni;
		this.nombre = nombre;
		this.fechaContrato =null;
	}
	public Empleado(Empleado that) {
		this.id = that.id;
		this.dni = that.dni;
		this.nombre = that.nombre;
		this.fechaContrato = that.fechaContrato;
	}
	
	//Get
	public String getId() {
		return id;
	}
	public String getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}
	
	//Set
	public void setId(String id) {
		this.id = id;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Date getFechaContrato() {
		return fechaContrato;
	}


	public void setFechaContrato(Date fechaContrato) {
		this.fechaContrato = fechaContrato;
	}


	@Override
	public String toString() {
		return "Empleado [dni=" + dni + ", nombre=" + nombre + ", fechaContrato=" + fechaContrato + "]";
	}
	
	



	
	
	
}
