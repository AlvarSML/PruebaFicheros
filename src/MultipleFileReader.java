
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

    public ArrayList<String> readFilesFromPath(String path, String extension) throws InterruptedException {
        ArrayList<String> data = new ArrayList<String>();
        
        File dir = new File(path);

        // Evitar paths incorrectos
        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("Error obteniendo el directorio" + path);
            return data;
        }

        File[] files = dir.listFiles();

        if (files == null) {
            System.err.println("El directorio de entrada esta vacio");
            return data;
        }
        
        BlockingQueue<String> queue = new ArrayBlockingQueue(1024);
        
        // Pool de hilos por eficiencia
        // de momento 4, lo parametrizaré
        ExecutorService tPool = Executors.newFixedThreadPool(4);
        ExecutorService consumerPool = Executors.newSingleThreadExecutor();
        
        FileReaderThread[] results = new FileReaderThread[files.length];
        
        LinesConsumer consumer = new LinesConsumer(queue,"EOF");
        consumerPool.execute(consumer);
        
        int i = 0;
        for (File file : files) {
            // si hay otros tipos de archivos se ignoran
            if (!file.isFile()) {
                continue;
            }
            // filtro por si especifico una extension para los test
            if (extension != null && !file.getName().endsWith(extension)) {
                continue;
            }
            FileReaderThread reader = new FileReaderThread(file, queue);
            tPool.execute(reader);
            results[i] = reader;
            i++;
        }
        
        tPool.shutdown();
        while (!tPool.awaitTermination(10, TimeUnit.SECONDS)) {
             System.out.println("Esperando finalizacion de hilos");
        }
        System.out.println("Archivos leidos");
        queue.put("EOF");
        
        consumerPool.shutdown();
        while (!tPool.awaitTermination(10, TimeUnit.SECONDS)) {
             System.out.println("Esperando finalizacion de conumidores");
        }
        System.out.println("ConsumidorFin");

        return data;
    }
}
