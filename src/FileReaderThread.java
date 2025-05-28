
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alvar
 */
public class FileReaderThread implements Runnable {
    
    private File file;
   
    protected BlockingQueue<String> colaLineas = null;
    
    public FileReaderThread(File file,BlockingQueue<String> colaLineas) {
        this.file = file;
        this.colaLineas = colaLineas;   
    } 
    
    @Override
    public void run() {
        System.out.println("Leyendo "+file.getName());
        String filePath = file.getPath();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                System.out.println("< "+linea);
                colaLineas.put(linea);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
        } finally {
            System.out.println("Terminado "+file.getName());
        }
    }
    
}
