package modelo;

import java.util.TreeSet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

public class Carta {

	private TreeSet<Consumible> listaConsumibles;

	// Contructores
	public Carta() throws ClassNotFoundException, SQLException {
		actualizarCarta();
	}

	public Carta(TreeSet<Consumible> listaConsumibles) {
		this.listaConsumibles = listaConsumibles;
	}

	// Metodos
	public void anadirConsumible(Consumible consumible) throws ClassNotFoundException, SQLException {
		if(!listaConsumibles.contains(consumible)) {
			Statement consulta=ConexionBBDD.getConnection().createStatement();
			consulta.executeUpdate("INSERT INTO CONSUMIBLES VALUES('"+consumible.getId()+"','"+consumible.getNombre()+"','"+consumible.getPrecio()+"','"+consumible.getClass().getSimpleName()+"')");
			listaConsumibles.add(consumible);
		}

	}

	public boolean eliminarConsumible(Consumible consumible) throws ClassNotFoundException, SQLException {
		if(listaConsumibles.removeIf((Consumible c) -> c.equals(consumible))) {
			Statement consulta=ConexionBBDD.getConnection().createStatement();
			consulta.executeUpdate("DELETE FROM CONSUMIBLES WHERE ID_CONSUMIBLE='"+consumible.getId()+"'");
			return true;
		}
		else 
			return false;

	}
	private void actualizarCarta() throws ClassNotFoundException, SQLException {
		listaConsumibles = new TreeSet<Consumible>();
		Statement consulta=ConexionBBDD.getConnection().createStatement();
		ResultSet resul=consulta.executeQuery("SELECT * FROM CONSUMIBLES");
		while(resul.next()) {
			switch (resul.getString("Tipo")) {
			case "Menu":
				listaConsumibles.add(new Menu(resul.getString("ID_Consumible"),resul.getString("Nombre"),resul.getDouble("Precio")));
				break;
			case "Plato":
				listaConsumibles.add(new Plato(resul.getString("ID_Consumible"),resul.getString("Nombre"),resul.getDouble("Precio")));
				break;
			case "Bebida":
				listaConsumibles.add(new Bebida(resul.getString("ID_Consumible"),resul.getString("Nombre"),resul.getDouble("Precio")));
				break;
			}
		}
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
