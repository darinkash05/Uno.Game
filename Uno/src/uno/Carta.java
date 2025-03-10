package uno;

import java.io.Serializable;

/** 
 * Clase Abstracta que representa una carta en el juego Uno 
 * 
 * @version Diciembre 2023 
 * 
 * Fue escrita por el equipo Tilingenieros
 */

public abstract class Carta implements BuilderCarta, Serializable{
  //Atributos 

  /**
   * Variable protegida que representa el color de la carta.
   */
  protected Color color;

  /**
   * Variable protegida que representa el contenido de la carta.
   */
  protected Contenido contenido;

  //Constructores

  /**
   * Constructor que crea una carta con un color y contenido especificos
   * 
   * @param color, Color de la carta
   * @param contenido, Contenido de la carta
   */
  public Carta(Color color, Contenido contenido){ 
    this.color = color;
    this.contenido = contenido;
  }

  //Getters y Setters

  @Override
  public void setColor(Color color){
    this.color = color;
  }

  @Override
  public Color getColor(){
    return this.color;
  }

  @Override
  public void setContenido(Contenido contenido){
    this.contenido = contenido;
  }

  @Override
  public Contenido getContenido(){
    return this.contenido;
  }

  //Método para representar la carta como una cadena de texto
  @Override
  public String toString(){
    if(this.contenido == Contenido.COMODIN || this.contenido == Contenido.COMODIN_4){
      return contenido.toString();
    }
    else{
      return color + " " + contenido;
    }
  } 
}