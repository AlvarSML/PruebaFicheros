package GestionEventos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Clase principal de la api que se encarga de coordinar la lectura y busqueda
 * de datos.
 *
 * @author alvar
 */
public class GestionEventos {

    private String dirOrigen = "./";
    private String extensionEventos = ".ev";
    private String extensionFuentes = ".fu";

    private final HashMap<Integer, Fuente> fuentesId = new HashMap<>();
    private final HashMap<String, Fuente> fuentesNombre = new HashMap<>();

    public ArrayList<Evento> datosEventos = new ArrayList<>();
    public ArrayList<Fuente> datosFuentes = new ArrayList<>();

    public GestionEventos(
            String dirOrigen,
            String extensionArchivos,
            String extensionFuentes
    ) {
        if (dirOrigen != null) {
            this.dirOrigen = dirOrigen;
        }
        if (extensionArchivos != null) {
            this.extensionEventos = extensionArchivos;
        }
        if (extensionFuentes != null) {
            this.extensionFuentes = extensionFuentes;
        }
    }

    public GestionEventos leerArchivosEventos() throws InterruptedException {
        MultipleFileReader reader = new MultipleFileReader();
        reader.readFilesFromPath(
                this.dirOrigen,
                this.extensionEventos,
                this.extensionFuentes,
                this.datosEventos,
                this.datosFuentes
        );
        this.updateMapsFuentes();
        return this;
    }
    
    private void updateMapsFuentes(){
        for (Fuente f:this.datosFuentes) {
            this.fuentesId.put(f.id, f);
            this.fuentesNombre.put(f.nombre, f);
        }
    }
    
    /**
     * Busqueda de Eventos segun la fuente comp: O(n)
     * TODO: no he caido que el nombre puede no ser unico
     * @param nombreFuente nombre de la fuente
     * @return lista con referencias a los eventos de la fuente
     */
    public Evento[] buscarEventosPorFuente(String nombreFuente) {
        Fuente fuenteObjetivo = this.fuentesNombre.get(nombreFuente);
        ArrayList<Evento> eventosFuente = new ArrayList();
        for (Evento e : this.datosEventos) {
            if (e.fuente == fuenteObjetivo.id) {
                eventosFuente.add(e);
            }
        }
        return eventosFuente.toArray(Evento[]::new);
    }

    /**
     * Busqueda binaria del valor mas cercano comp: O(log n) (No realmente si
     * todos estuvienen repetidos pero seria complicado en el caso de milis)
     *
     * @param target
     * @param lado < 0 si el mas cercano menor >= 0 si el mas cercano mayor
     * @return *indice* del valor mas cercano
     */
    public int bSearchClosestEvento(long target, int lado) {
        if (datosEventos == null || datosEventos.isEmpty()) {
            return -1;
        }

        int low = 0;
        int high = datosEventos.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            long midTimestamp = datosEventos.get(mid).timestamp;

            if (midTimestamp == target) {
                return mid; // Exact match found
            } else if (midTimestamp < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (low == 0) {
            return 0;
        }
        //target es mayor que todos los elementos
        if (low == datosEventos.size()) {
            return datosEventos.size() - 1;
        }

        long valHigh = datosEventos.get(high).timestamp;
        long valLow = datosEventos.get(low).timestamp;

        long diffHigh = Math.abs(target - valHigh);
        long diffLow = Math.abs(target - valLow);

        if (diffHigh < diffLow) {
            return high;
        } else if (diffLow < diffHigh) {
            return low;
        } else { // Las diferencias son iguales
            if (lado < 0) { // Prefiere el menor o igual
                return high;
            } else { // Prefiere el mayor o igual
                return low;
            }
        }
    }

    /**
     * Busca el intervalo entre las fechas [inicio, fin]. Si no existiesen se
     * buscarian las mas cercanas
     *
     * @param inicio
     * @param fin
     * @return Array de Evento que cumple la condicion
     */
    public Evento[] buscarEntreFechas(Date inicio, Date fin) {
        ArrayList<Evento> eventosPeriodo = new ArrayList();
        long timestampInicio = inicio.getTime();
        long timestampFin = fin.getTime();
        int posInicio = this.bSearchClosestEvento(timestampInicio, 1);
        int posFin = this.bSearchClosestEvento(timestampFin, -1);

        var periodo = this.datosEventos.subList(posInicio, posFin);

        return periodo.toArray(Evento[]::new);
    }

    /**
     * Busca los eventos ente los valores [min,max] inclusive.
     *
     * @param min
     * @param max
     * @return Array de Evento que cumple la condicion
     */
    public Evento[] buscarEntreValores(double min, double max) {
        ArrayList<Evento> eventosValor = new ArrayList();
        if (min > max) {
            double t = min;
            min = max;
            max = t;
        }

        for (int i = 0; i < this.datosEventos.size(); i++) {
            Evento e = this.datosEventos.get(i);
            if (e == null) {
                continue;
            }
            if (e.valor >= min && e.valor <= max) {
                eventosValor.add(e);
            }
        }

        return eventosValor.toArray(Evento[]::new);
    }
}
