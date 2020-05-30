package modelo;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Plato extends Consumible{
	
	private HashMap<String, Integer> ingredientes;
	
	//Constructores
	public Plato(String id, String nombre, double precio, HashMap<String,Integer> ingredientes) {
		super(id, nombre, precio);
		this.ingredientes = ingredientes;
	}
	public Plato(String id, String nombre, double precio) {
		super(id, nombre, precio);
		this.ingredientes = new HashMap<String,Integer>();
	}
	public Plato(Plato p) {
		super(p);
		this.ingredientes = p.getIngredientes();
	}


	//Metodos
	/*
	@Override
	public boolean validarId(String id) {
		return id.matches("^([P][0-9]{2})$");
	}
	*/
	/**
	 * Añade un ingrediente al hashMap con los ingredientes que forman el plato
	 * @param id identificador del ingrediente que vas a añadir
	 * @param cantidad cantidad del ingrediente que necesita el plato
	 * 
	 */
	public void anadirIngrediente(String id, int cantidad) {
		
		ingredientes.put(id, cantidad);
	
	}
	
	/**
	 * Busca en la lista de ingredientes el id pasado como parametro y lo elimina
	 * @param id
	 * @return true si encuentra y elimina el ingrediente, false si no encuentra el id de ingrediente
	 */
	public boolean eliminarIngrediente(String id) {
		
		if (ingredientes.remove(id) != null)
			return true;
		return false;
		
	}
	
	public void insertarPlato() throws ClassNotFoundException, SQLException {
		Statement consulta = ConexionBBDD.getConnection().createStatement();
		consulta.executeUpdate("INSERT INTO CONSUMIBLES (ID_CONSUMIBLE,NOMBRE,PRECIO,TIPO) VALUES ('" + this.getId()
				+ "','" + this.getNombre() + "', "+this.getPrecio()+", 'Plato')");
	}
	
	//get
	public HashMap<String,Integer> getIngredientes() {
		return ingredientes;
	}

	//set
	public void setIngredientes(HashMap<String,Integer> ingredientes) {
		this.ingredientes = ingredientes;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Plato) {
			Plato other = (Plato) obj;
			if (this.getId().equals(other.getId()))
				return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return "\tPlato ["+super.toString()+"]";
	}

}
