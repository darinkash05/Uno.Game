package uno;

import java.util.ArrayList;
import java.util.Collections;

import java.io.Serializable;

/**
 * Esta clase representa una baraja de cartas correspondiente al juego UNO
 * En esta se implemeto una lista de cartas que se utilizan para repartir cartas a los jugadores 
 * y para hacer el pozo de cartas, esta esta compuesto por cartas normales y especiales
 * 
 * @version Diciembre 2023 
 * 
 * Fue escrita por el equipo Tilingenieros
 */
public class Baraja implements Serializable{

  /**
   * Identificador de versión para la serialización
   */
  private static final long serialVersionUID = 1L;

  /**
   * Lista que conntiene las cartas de la baraja
   */
  private ArrayList<Carta> baraja;
/**
 * Constructor de la clase, incializa una nueva braja, crea las cartas, las serializa y las mezcla
 */
  public Baraja(){
    this.baraja = new ArrayList<>();
    crearCartas();
    ObtenerBaraja.guardarBaraja(this);
    mezclarBaraja();
  }

/**
 * Este método se encarga de generar las cartas del juego UNO y añadirlas a la baraja.
 * Se utiliza un patrón de diseño de constructor (Builder) para facilitar la creación de diferentes tipos de cartas.
 * Se crean 112 cartas; 80 normales, 24 especiales, 4 comodines y 4 comodines +4.
 */
  private void crearCartas(){

    BuilderCarta.Color[] colores = {BuilderCarta.Color.ROJO, BuilderCarta.Color.VERDE, BuilderCarta.Color.AZUL, BuilderCarta.Color.AMARILLO};

    BuilderCarta.Contenido[] contenidosNormales = {BuilderCarta.Contenido.CERO, BuilderCarta.Contenido.UNO, BuilderCarta.Contenido.DOS, BuilderCarta.Contenido.TRES, 
                                                      BuilderCarta.Contenido.CUATRO, BuilderCarta.Contenido.CINCO, BuilderCarta.Contenido.SEIS, BuilderCarta.Contenido.SIETE, 
                                                      BuilderCarta.Contenido.OCHO, BuilderCarta.Contenido.NUEVE};
    BuilderCarta.Contenido[] contenidosEspeciales = {BuilderCarta.Contenido.TOMA_2, BuilderCarta.Contenido.REVERSA, BuilderCarta.Contenido.SALTO};

    for(BuilderCarta.Color color : colores){
      for(BuilderCarta.Contenido contenido : contenidosNormales){
        for(int i = 0; i < 2; i++){
          this.baraja.add(new CartaNormal(null, null).seleccionarColor(color).seleccionarContenido(contenido).construir());
        }
      }
    }

    for(BuilderCarta.Color color : colores){
      for(BuilderCarta.Contenido contenido : contenidosEspeciales){
        for(int i = 0; i < 2; i++){
          this.baraja.add(new CartaEspecial(null, null).seleccionarColor(color).seleccionarContenido(contenido).construir());
        }
      }
    }

    for(int i = 0; i < 4; i++){
      this.baraja.add(new CartaEspecial(null, null).seleccionarContenido(BuilderCarta.Contenido.COMODIN).construir());
    }

    for(int i = 0; i < 4; i++){
      this.baraja.add(new CartaEspecial(null, null).seleccionarContenido(BuilderCarta.Contenido.COMODIN_4).construir());
    }
  }
/**
 * Se reparte un conjunto de cartas a cada jugador
 * 
 * @param jugador, El jugados al que se le reparten las cartas 
 */
  public synchronized void repartirCartasAJugador(Jugador jugador){
    ArrayList<Carta> cartasJugador = new ArrayList<>();
    
    for(int i = 0; i < 7; i++){
        cartasJugador.add(this.baraja.get(0));
        this.baraja.remove(0);
    }
    
    jugador.setMano(cartasJugador);
  }
/** 
   * Obtiene una carta de la baraja y la elimina de esta misma
   * 
   * @return la carta obtenida 
   */
  public synchronized Carta getCarta(){
    Carta carta = this.baraja.get(0);
    this.baraja.remove(0);
    return carta;
  }

/**
 * Obtiene las cartas de la baraja
 * 
 * @return las cartas de la baraja
 * 
 */
  public synchronized ArrayList<Carta> getBaraja(){
    return this.baraja;
  }
/**
 * Obtiene el tamaño actual de la baraja
 * 
 * @return el número de cartas que quedan en la baraja
 * 
 */
  public int getTamañoBaraja(){
    return this.baraja.size();
  }

 /**
   * Verifica si la baraja esta vacía 
   * 
   * @return 'true' si la baraja esta vacía  y 'false' si aún contiene cartas. 
   */
  public boolean barajaVacia(){
    if(this.baraja.isEmpty()){
        return true;
    } else{
        return false;
    }
  }
/**
 * Mezcla las cartas de la baraja de forma aleatoria.
 * 
 */
  public synchronized void mezclarBaraja(){
    Collections.shuffle(this.baraja);
  }

}