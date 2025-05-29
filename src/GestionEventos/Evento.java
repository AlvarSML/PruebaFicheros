package GestionEventos;


/**
 *
 * @author alvar
 */
public class Evento implements Comparable<Evento> {

    public int timestamp;
    public int fuente;
    public double valor;

    public Evento(int timestamp, int fuente, double valor) {
        this.timestamp = timestamp;
        this.fuente = fuente;
        this.valor = valor;
    }

    @Override
    public boolean equals(Object otro) {
        if (otro == null || otro.getClass() != this.getClass()) {
            return false;
        }

        Evento ev = (Evento) otro;

        return (this.timestamp == ev.timestamp)
                && (this.fuente == ev.fuente)
                && (this.valor == ev.valor);
    }

    // generado por nb
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.timestamp;
        hash = 41 * hash + this.fuente;
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.valor) ^ (Double.doubleToLongBits(this.valor) >>> 32));
        return hash;
    }

    // Para que puedan ordenarse
    @Override
    public int compareTo(Evento ev) {
        return Integer.compare(this.timestamp, ev.timestamp);
    }

    @Override
    public String toString() {
        return String.format("Evento{%d - %d - %f}", this.timestamp, this.fuente, this.valor);
    }

    public static Evento fromCsv(String linea) {
        String[] partes = linea.split(";");
        if (partes.length < 3) {
            return null;
        }
        if ("".equals(partes[0]) | "".equals(partes[1]) | "".equals(partes[2])) {
            return null;
        }
        try {
            int timestamp = Integer.parseInt(partes[0]);
            int fuenteId = Integer.parseInt(partes[1]);
            double valor = Double.parseDouble(partes[2]);
            Evento compuesto = new Evento(timestamp, fuenteId, valor);
            return compuesto;
        } catch (NumberFormatException e) {
            return null;
        }

    }
}
