package GestionEventos;


import GestionEventos.Evento;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.BlockingQueue;

/**
 * Consume lineas de la cola y las inserta en una lista ordenada
 * @author alvar
 */
public class EventosReader implements Runnable{
    private BlockingQueue<String> colaLineas = null;
    private ArrayList<Evento> listaOrdenada;
    // Indicador de fin de lecturas
    private final String terminator = "EOF";
    
    public EventosReader(BlockingQueue<String> colaLineas, ArrayList<Evento> listaDestino) {
        this.colaLineas = colaLineas;
        this.listaOrdenada = listaDestino;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                String evento = colaLineas.take();
                Evento evConstruido = Evento.fromCsv(evento);
                if (evConstruido != null){
                    //System.out.println("> "+evConstruido.toString());
                    this.listaOrdenada.add(evConstruido);
                }
                
                if (evento == this.terminator) break;
            } catch (InterruptedException ex) {
                System.out.println("Error tomando mensaje de la pila");
            }
            
            // Tiene implementado comparable
            this.listaOrdenada.sort(Comparator.naturalOrder());
        }
        
        
    }
    
}
