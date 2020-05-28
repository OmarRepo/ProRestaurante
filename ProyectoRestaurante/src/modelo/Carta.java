package modelo;

import java.util.TreeSet;
import java.util.Iterator;

public class Carta {

	private TreeSet<Consumible> listaConsumibles;

	// Contructores
	public Carta() {
		listaConsumibles = new TreeSet<Consumible>();
	}

	public Carta(TreeSet<Consumible> listaConsumibles) {
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
		String carta = "<html><body>Carta<br>";
		while (it.hasNext()) {
			Consumible consumible = it.next();
			carta += consumible.getId() + " " + consumible.getNombre() + " " + consumible.getPrecio() + "€" + "<br>";
		}

		return carta+"</body></html>";
	}
	

	// Get
	public TreeSet<Consumible> getListaConsumibles() {
		return listaConsumibles;
	}

	// Set
	public void setListaConsumibles(TreeSet<Consumible> listaConsumibles) {
		this.listaConsumibles = listaConsumibles;
	}

	@Override
	public String toString() {
		return "CARTA\n" + listaConsumibles;
	}

}
