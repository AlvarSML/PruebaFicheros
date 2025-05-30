package GestionEventos;

import GestionEventos.Evento;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.BlockingQueue;

/**
 * Consume lineas de la cola y las inserta en una lista ordenada
 *
 * @author alvar
 */
public class FuentesReader implements Runnable {

    private BlockingQueue<String> colaLineas = null;
    private ArrayList<Fuente> listaFuentes;
    // Indicador de fin de lecturas
    private final String terminator = "EOF";

    public FuentesReader(BlockingQueue<String> colaLineas, ArrayList<Fuente> listaDestino) {
        this.colaLineas = colaLineas;
        this.listaFuentes = listaDestino;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String evento = colaLineas.take();
                Fuente fuenteConstruida = Fuente.fromCsv(evento);
                if (fuenteConstruida != null) {
                    // System.out.println("> "+fuenteConstruida.toString());
                    this.listaFuentes.add(fuenteConstruida);
                }

                if (evento == this.terminator) {
                    break;
                }
            } catch (InterruptedException ex) {
                System.out.println("Error tomando mensaje de la pila");
            }

        }

    }

}
