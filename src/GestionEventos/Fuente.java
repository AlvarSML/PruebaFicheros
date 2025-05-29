package GestionEventos;

/**
 *
 * @author alvar
 */
public class Fuente {

    public int id;
    public String nombre;

    public Fuente(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return String.format("Fuente{%d - %s }", this.id, this.nombre);
    }
    
    public static Fuente fromCsv(String linea) {
        String[] partes = linea.split(";");
        if (partes.length < 2) {
            return null;
        }
        if ("".equals(partes[0]) | "".equals(partes[1])) {
            return null;
        }
        try {
            int id = Integer.parseInt(partes[0]);
            String nombre = partes[1];
            Fuente compuesta = new Fuente(id,nombre);
            return compuesta;
        } catch (NumberFormatException e) {
            return null;
        }

    }
}
