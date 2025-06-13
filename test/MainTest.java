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
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;

import org.junit.Test;

/**
 *
 * @author alvar
 */
public class MainTest {

    static GestionEventos ge;

    static GestionEventos geGrande;
  
  
    @BeforeClass
    public static void setup() throws InterruptedException{
      ge = new GestionEventos("./", ".ev", ".fu").leerArchivosEventos();
      geGrande = new GestionEventos("./archivos-test", ".ev", ".fu").leerArchivosEventos();
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

    @Test
    public void testBuscarEventosPorFuente_NombreInexistente() {
      Evento[] res = this.ge.buscarEventosPorFuente("FuenteInexistente");
      assertNotNull(res);
      assertEquals(0, res.length);
    }

    @Test
    public void testBSearchClosestEvento_ListaVacia() {
      GestionEventos vacio = new GestionEventos("./", ".ev", ".fu");
      int idx = vacio.bSearchClosestEvento(12345L, 0);
      assertEquals(-1, idx);
    }

    @Test
    public void testBuscarEntreFechas_FechasFueraDeRango() {
      Date inicio = new Date(0); // Muy anterior a cualquier evento
      Date fin = new Date(10);   // Muy anterior a cualquier evento
      Evento[] res = this.ge.buscarEntreFechas(inicio, fin);
      assertNotNull(res);
    }
  
    @Test
    public void testBuscarEntreValores_MinMayorQueMax() {
      Evento[] res = this.ge.buscarEntreValores(100, 50); // min > max
      assertNotNull(res);
    }
  
    @Test
    public void testBuscarEntreValores_SinCoincidencias() {
      Evento[] res = this.ge.buscarEntreValores(-1000, -900); // Rango fuera de los valores
      assertNotNull(res);
      assertEquals(0, res.length);
    }
  
    @Test
    public void testBuscarEntreFechas_UnSoloEvento() {
      if (!this.ge.datosEventos.isEmpty()) {
          Date fechaEvento = new Date(this.ge.datosEventos.get(0).timestamp);
          Evento[] res = this.ge.buscarEntreFechas(fechaEvento, fechaEvento);
          assertNotNull(res);
    }
  }
}
