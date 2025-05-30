package GestionEventos;

import GestionEventos.Evento;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author alvar
 */
public class MultipleFileReader {

    public void readFilesFromPath(
            String path, 
            String extensionEvento, 
            String extensionFuente, 
            ArrayList<Evento> eventosOut, 
            ArrayList<Fuente> fuentesOut) throws InterruptedException {

        File dir = new File(path);

        // Evitar paths incorrectos
        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("Error obteniendo el directorio" + path);
            return;
        }

        File[] files = dir.listFiles();

        if (files == null) {
            System.err.println("El directorio de entrada esta vacio");
            return;
        }

        // En teoria la lectura de archivo siempre sera mas lenta que el consumo
        // de la cola
        BlockingQueue<String> queueEventos = new ArrayBlockingQueue(1048576);
        BlockingQueue<String> queueFuentes = new ArrayBlockingQueue(1024);

        // Pool de hilos por eficiencia
        // de momento 4, lo parametrizaré
        ExecutorService eventReaderPool = Executors.newFixedThreadPool(4);
        ExecutorService fuentesReaderPool = Executors.newFixedThreadPool(4);

        ExecutorService consumerPool = Executors.newSingleThreadExecutor();

        EventosReader eventosConsumer = new EventosReader(queueEventos, eventosOut);
        FuentesReader fuentesConsumer = new FuentesReader(queueFuentes, fuentesOut);
        consumerPool.execute(eventosConsumer);
        consumerPool.execute(fuentesConsumer);

        int archivosEventos = 0;
        int archivosFuentes = 0;
        for (File file : files) {
            // si hay otros tipos de archivos se ignoran
            if (!file.isFile()) {
                continue;
            }

            if (file.getName().endsWith(extensionEvento)) {
                FileReaderThread reader = new FileReaderThread(file, queueEventos);
                eventReaderPool.execute(reader);
                archivosEventos++;
            }
            if (file.getName().endsWith(extensionFuente)) {
                FileReaderThread reader = new FileReaderThread(file, queueFuentes);
                fuentesReaderPool.execute(reader);
                archivosFuentes++;
            }

        }
        
        fuentesReaderPool.shutdown();        
        eventReaderPool.shutdown();
        
        while (!fuentesReaderPool.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("Esperando finalizacion de hilos de fuentes");
        }
        System.out.println("Fuentes leidas");
        queueFuentes.put("EOF");
        
        while (!eventReaderPool.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("Esperando finalizacion de hilos de eventos");
        }
        System.out.println("Archivos leidos");
        queueEventos.put("EOF");

        consumerPool.shutdown();
        while (!consumerPool.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("Esperando finalizacion de conumidores");
        }
        System.out.println("ConsumidorFin");
    }
}
