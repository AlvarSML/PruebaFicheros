/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import GestionEventos.MultipleFileReader;
import GestionEventos.Evento;
import GestionEventos.Fuente;
import GestionEventos.GestionEventos;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.*;

/**
 *
 * @author alvar
 */
public class MainTest {

    GestionEventos ge;
    Fuente[] fs;

    public MainTest() throws InterruptedException {
        this.fs = new Fuente[]{
            new Fuente(0, "F0"),
            new Fuente(1, "F1"),
            new Fuente(2, "F2"),
            new Fuente(3, "F3"),
            new Fuente(4, "F4"),};

        this.ge = new GestionEventos("./", ".ev", ".fu").leerArchivosEventos();
        //this.mfr = new MultipleFileReader();
        //this.arr = mfr.readFilesFromPath("./", ".txt");

        for (Evento e : this.ge.datosEventos) {
            System.out.println(e);
        }

        for (Fuente f : this.ge.datosFuentes) {
            System.out.println(f);
        }
    }

    /**
     * Test base de lectura correcta de ficheros
     */
    @Test
    public void testMain() throws Exception {

        System.out.println("Tamaño" + this.ge.datosEventos.size());
        // 40 lineas - 3 con errores
        assert (this.ge.datosEventos.size() == 37);

        // Comprueba que los datos esten ordenados
        for (int i = 1; i < this.ge.datosEventos.size(); i++) {
            assertFalse(this.ge.datosEventos.get(i - 1).compareTo(this.ge.datosEventos.get(i)) > 0);
        }
    }

    /**
     * Test base de lectura correcta de ficheros
     */
    @Test
    public void testBSearch() {

        var targetExacto = 1717009916;

        var targetNoExiste = 1717009917;
        var targetSiguiente = 1717009918;
        var targetAnterior = 1717009916;

        var resExacto = this.ge.bSearchClosestEvento(targetExacto, 0);
        var resMenor = this.ge.bSearchClosestEvento(targetNoExiste, -1);
        var resMayor = this.ge.bSearchClosestEvento(targetNoExiste, 0);

        assertEquals(targetExacto, this.ge.datosEventos.get(resExacto).timestamp);
        assertEquals(targetAnterior, this.ge.datosEventos.get(resMenor).timestamp);
        assertEquals(targetSiguiente, this.ge.datosEventos.get(resMayor).timestamp);

    }

    /**
     * Test base de lectura correcta de ficheros
     */
    @Test
    public void eventosPorNombreFuente() {
        Evento[] res = this.ge.buscarEventosPorFuente("F1");

        assertTrue(res != null);
        assertEquals(4, res.length);

    }

    /**
     * Test base de busqueda de periodos
     * me faltan muchos casos vacios
     */
    @Test
    public void eventosPorPeriodos() {
        Date inicio = new Date(1717009904);
        Date fin = new Date(1717009911);
        
        Evento[] res = this.ge.buscarEntreFechas(inicio,fin);

        assertTrue(res != null);
        assertEquals(12, res.length);

    }

    /**
     * Test base de busqueda de valores
     */
    @Test
    public void eventosPorValores() {
        Evento[] res = this.ge.buscarEntreValores(68,80);

        assertTrue(res != null);
        assertEquals(6, res.length);

    }
}
