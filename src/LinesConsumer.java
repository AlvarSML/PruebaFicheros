
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author alvar
 */
public class LinesConsumer implements Runnable{
    private BlockingQueue<String> colaLineas = null;
    private String termiator;
    
    public LinesConsumer(BlockingQueue<String> colaLineas, String terminator) {
        this.colaLineas = colaLineas;
        this.termiator = terminator;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                String linea = colaLineas.take();
                System.out.println("> "+linea);
                if (linea.equals(this.termiator)) break;
            } catch (InterruptedException ex) {
                System.out.println("Error tomando mensaje de la pila");
            }
        }
        
    }
    
}
