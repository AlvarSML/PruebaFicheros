/**
 *
 * @author alvar
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        MultipleFileReader reader = new MultipleFileReader();
        reader.readFilesFromPath("./", ".txt");
    }
} 

