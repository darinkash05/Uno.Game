package uno;

import java.util.ArrayList;
import java.util.Random;
/** 
 * La clase jugador representa a los participantes en el juego. Cada jugador cuenta con un nombre, mano de cartas 
 * y puede ser jugador humano o máquina (bot)
 * 
 * @version Noviembre 2023 
 * 
 * Fue escrita por el equipo Tilingenieros
 */
public class Jugador{
  
    private boolean esHumano;
    private String nombre;
    private ArrayList<Carta> mano;
  
    /**
     * En el constructor de la clase se crea un nuevo jugador con un nombre y establece si es un humano o máquina
     * @param nombre, nombre del jugador 
     * @param humano, verdadero si lo es, falso en caso contrario
     */
    
    public Jugador(String nombre, boolean humano){
        this.esHumano = humano;
        this.nombre = nombre;
        this.mano = new ArrayList<>();
    }

    //Getters y Setters
    /**
     * Obtiene un valor que indica si el jugador es humano o una máquina.
     *
     * @return Verdadero si el jugador es humano, falso si es una máquina.
     */
    public boolean getEsHumano(){
      return this.esHumano;
    }
    /**
     * Establece si el jugador es humano o una máquina.
     *
     * @param humano, Verdadero si el jugador es humano, falso si es una máquina.
     */
    public void setEsHumano(boolean humano){
      this.esHumano = humano;
    }
    /**
     * Obtiene el nombre del jugador.
     *
     * @return El nombre del jugador.
     */
    public String getNombre() {
      return this.nombre;
    }
    /**
     * Establece el nombre del jugador.
     *
     * @param nombre, nombre del jugador.
     */
    public void setNombre(String nombre){
      this.nombre = nombre;
    }
    /**
     * Obtiene la mano de cartas del jugador.
     *
     * @return La lista de cartas en la mano del jugador.
     */
    public ArrayList<Carta> getMano() {
      return this.mano;
    }
    /**
     * Establece la mano del jugador con una lista de cartas.
     *
     * @param mano, lista de cartas que conforman la mano del jugador.
     */
    public void setMano(ArrayList<Carta> mano){
      for(Carta carta: mano){
        this.mano.add(carta);
      }
    }
    /**
     * Obtiene una carta de la mano del jugador en función de su índice.
     *
     * @param indice, índice de la carta en la mano del jugador.
     * @return La carta en la posición indicada.
     */
    public Carta getCarta(int indice){
      return this.mano.get(indice);
    }
    /**
     * Agrega una carta a la mano del jugador.
     *
     * @param carta, carta que se agregará a la mano del jugador.
     */
    public void setCarta(Carta carta){
      this.mano.add(carta);
    }

    //Metodos del jugador
    /**
     * El jugador toma un número específico de cartas del mazo y las agrega a su mano.
     *
     * @param baraja, mazo del que se toman cartas.
     * @param numero, número de cartas que el jugador debe tomar.
     */
    public void comerCartas(Baraja baraja, int numero){

      bucle : for(int i = 0; i < numero; i++){
        if(baraja.barajaVacia()){
            System.out.println("No hay mas cartas disponibles");
            break bucle;
        } else {
            this.mano.add(baraja.getCarta());
        }
      }
    }
    /**
     * El jugador juega una carta específica de su mano.
     *
     * @param carta La carta que se desea jugar.
     * @return La carta jugada.
     */
    public Carta usarCarta(Carta carta){ //Metodo para maquina
      this.mano.remove(carta);
      return carta;
    }
    /**
     * El jugador humano juega una carta específica de su manocon respecto a su índice.
     *
     * @param indice, índice de la carta en la mano del jugador.
     * @return La carta jugada.
     */
    public Carta usarCarta(int indice){ //Metodo para humanos
      Carta carta = this.mano.get(indice);
      this.mano.remove(indice);
      return carta;
    }
}