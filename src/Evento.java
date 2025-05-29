
public class Evento implements Comparable<Evento> {
  public int timestamp;
  public int fuente;
  public double valor;

  public Evento(int timestamp, int fuente, double valor) {
    this.timestamp = timestamp;
    this.fuente = fuente;
    this.valor = valor;
  }
  
  @Override
  public int equals(Evento ev) {
    return (this.timestamp == ev.timestamp) 
    && (this.fuente == ev.fuente) 
    && (this.valor == ev.valor);
  }

  @Override
  public int compareTo(Evento ev) {
    return Integer.compare(this.timestamp, ev.timestamp);
  }
}
