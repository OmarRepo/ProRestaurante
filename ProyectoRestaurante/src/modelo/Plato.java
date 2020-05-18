package modelo;

import java.util.HashSet;

public class Plato extends Consumible{
	
	private HashSet<Ingrediente> ingredientes;
	private TIPO_PLATO tipo;
	
	//Constructores
	public Plato(String id, String nombre, double precio, HashSet<Ingrediente> ingredientes, TIPO_PLATO tipo) {
		super(id, nombre, precio);
		this.ingredientes = ingredientes;
		this.tipo = tipo;
	}
	public Plato(String id, String nombre, double precio, TIPO_PLATO tipo) {
		super(id, nombre, precio);
		this.tipo = tipo;
	}


	//Metodos
	public void anadirIngrediente(Ingrediente ingrediente) {
		
	}
	
	public void eliminarIngrediente(int id) {
		
	}
	
	//get
	public HashSet<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public TIPO_PLATO getTipo() {
		return tipo;
	}
	
	//set
	public void setIngredientes(HashSet<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public void setTipo(TIPO_PLATO tipo) {
		this.tipo = tipo;
	}

	
	
	
	
	
}
