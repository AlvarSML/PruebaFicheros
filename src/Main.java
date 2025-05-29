
import GestionEventos.GestionEventos;
import GestionEventos.MultipleFileReader;

/**
 *
 * @author alvar
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        GestionEventos ge = new GestionEventos("./", ".ev",".fu").leerArchivosEventos();
    }
}
