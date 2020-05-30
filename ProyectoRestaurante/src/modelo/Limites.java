package modelo;

import java.util.regex.Pattern;

public class Limites {
	public static boolean comprobarL(String cadena,int longitud) {
		if(cadena.length()>=longitud)
			return true;
		return false;
	}
	public static boolean comprobarInt(String cadena) {
		return Pattern.matches("\\d+", cadena);
	}
	public static boolean comprobarInt(String cadena,int longitud) {
		if(comprobarL(cadena,longitud))
			return comprobarInt(cadena);
		return false;
	}
	public static boolean comprobarDouble(String cadena) {
		return Pattern.matches("-?\\d+(.\\d+)?", cadena);
	}
	public static boolean comprobarDNI(String cadena){
		return Pattern.matches("\\d{8}[A-HJ-NP-TV-Z]", cadena);
	}
}
