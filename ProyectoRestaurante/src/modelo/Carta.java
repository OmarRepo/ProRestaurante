package modelo;

import java.util.HashSet;
import java.util.Iterator;

public class Carta {

	private HashSet<Consumible> listaConsumibles;

	// Contructores
	public Carta() {
		listaConsumibles = new HashSet<Consumible>();
	}

	public Carta(HashSet<Consumible> listaConsumibles) {
		this.listaConsumibles = listaConsumibles;
	}

	// Metodos
	public void anadirConsumible(Consumible consumible) {
		listaConsumibles.add(consumible);
		

	}

	public boolean eliminarConsumible(Consumible consumible) {
		return listaConsumibles.removeIf((Consumible c) -> c.equals(consumible));

	}

	// antes en clase Empleado
	public String mostrarCarta() {
		Iterator<Consumible> it = listaConsumibles.iterator();
		String carta = "";
		while (it.hasNext()) {
			Consumible consumible = it.next();
			carta.concat(consumible.toString());
			carta.concat("\n");
		}

		return carta;
	}

	// Get
	public HashSet<Consumible> getListaConsumibles() {
		return listaConsumibles;
	}

	// Set
	public void setListaConsumibles(HashSet<Consumible> listaConsumibles) {
		this.listaConsumibles = listaConsumibles;
	}

	@Override
	public String toString() {
		return "Carta [listaMenus=" + listaConsumibles + "]";
	}

}
