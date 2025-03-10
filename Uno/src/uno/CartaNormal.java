package uno;
/**
 * Esta clase 'Carta Normal' es una implementacion concreta de la clase 'Carta' y
 * del patron de diseño constructor 'BuilderCarta'
 * 
 * @version Diciembre 2023 
 * 
 * Fue escrita por el equipo Tilingenieros
 */

public class CartaNormal extends Carta{

/** Constructor que crea una carta normal con color y contenido especificos
 * 
 * @param color que representa el color de la carta normal 
 * @param contenido que representa el contenido que tendrá la carta normal
 */
  public CartaNormal(Color color, Contenido contenido){
    super(color, contenido);
  }

  @Override
  public CartaNormal seleccionarColor(Color color){
    this.color = color;
    return this;
  }

  @Override
  public CartaNormal seleccionarContenido(Contenido contenido){
    this.contenido = contenido;
    return this;
  }

  @Override
  public CartaNormal construir() {
    return new CartaNormal(this.color, this.contenido);
  }
}