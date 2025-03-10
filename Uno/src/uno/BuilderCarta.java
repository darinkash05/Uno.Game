package uno;

/**
 * La interfaz BuilderCarta define las caracterisicas que deben de tener las cartas en comun dentro del juego
 * como el color, contenido y las operaciones disponibles para poder acceder a estos valores, tambien se 
 * establece como 
 * 
 * @version Diciembre 2023 
 * 
 * Fue escrita por el equipo Tilingenieros
 */
public interface BuilderCarta{
  //Constantes que representan los colores y contenidos posibles de las cartas pertenecientes al juego UNO 
  
  /**
   * Enumeracion que representa los valores constantes "Color" que pueden adquirir las cartas 
   */
  public enum Color {
    /**
     * Representa el color Rojo en una carta
     */
    ROJO, 
    
    /**
     * Representa el color Amarillo en una carta
     */
    AMARILLO, 
    
    /**
     * Representa el color Azul en una carta
     */
    AZUL, 

    /**
     * Representa el color Verde en una carta
     */
    VERDE
  }
  
  /**
   * Enumeracion que representa los valores constantes "Contenido" que pueden adquirir las cartas 
   */
  public enum Contenido {
    /**
     * Representa el numero Cero en una carta
     */
    CERO, 

    /**
     * Representa el numero Uno en una carta
     */
    UNO, 
    
    /**
     * Representa el numero Dos en una carta
     */
    DOS, 
    
    /**
     * Representa el numero Tres en una carta
     */
    TRES,
    
    /**
     * Representa el numero Cuatro en una carta
     */
    CUATRO, 
    
    /**
     * Representa el numero Cinco en una carta
     */
    CINCO, 
    
    /**
     * Representa el numero Seis en una carta
     */
    SEIS, 
    
    /**
     * Representa el numero Siete en una carta
     */
    SIETE, 
    
    /**
     * Representa el numero Ocho en una carta
     */
    OCHO, 
    
    /**
     * Representa el numero Nueve en una carta
     */
    NUEVE, 
    
    /**
     * Representa una carta con la caracteristica de un Comodin (Eleccion del color de carta)
     */
    COMODIN, 
    
    /**
     * Representa una carta con la caracteristica de forzar al siguiente jugador a comer 2 cartas
     */
    TOMA_2, 
    
    /**
     * Representa una carta con la caracteristica de un Comodin (Eleccion del color de carta) y la de forzar al proximo jugador a comer 4 cartas
     */
    COMODIN_4, 
    
    /**
     * Representa una carta con la caracteristica de provocar que el turno del siguiente jugador sea omitido
     */
    SALTO,

    /**
     * Representa una carta con la caracteristica de invertir el orden de los turnos de los jugadores
     */
    REVERSA
  }
  
  /** 
   * Establece el color de la carta 
   * 
   * @param color, color de la carta
   */
  void setColor(Color color);
  /**
   * Obtiene el color de la carta 
   * @return el color de la carta
   */ 
  Color getColor();
  /**
   * Establece el contenido de la carta
   * 
   * @param contenido, contenido de la carta
   */
  void setContenido(Contenido contenido);
  /**
   * 
   * Obtiene el contenido de la carta 
   * 
   * @return el contenido de la carta
   */
  Contenido getContenido();
  /**
   * Obtiene una representacion de la carta en forma de cadena 
   * @return cadena que representa la carta, puede incluir color y contenido 
   */
  String toString();

  /**
  * Selecciona el color de la carta.
  *
  * @param color El color de la carta.
  * @return Esta instancia de BuilderCarta para permitir llamadas encadenadas.
  */
  BuilderCarta seleccionarColor(Color color);

  /**
  * Selecciona el contenido de la carta.
  *
  * @param contenido El contenido de la carta.
  * @return Esta instancia de BuilderCarta para permitir llamadas encadenadas.
  */
  BuilderCarta seleccionarContenido(Contenido contenido);

  /**
  * Construye la carta con las opciones seleccionadas.
  *
  * @return La carta construida.
  */
  Carta construir();

}