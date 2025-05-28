/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

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

