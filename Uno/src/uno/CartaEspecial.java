package uno;
/**
 * Esta clase 'Carta Especial' es una implementacion concreta de la clase 'Carta' y
 * del patron de diseño constructor 'BuilderCarta'
 * 
 * @version Diciembre 2023 
 * 
 * Fue escrita por el equipo Tilingenieros
 */

public class CartaEspecial extends Carta{

  /** Constructor que crea una carta especial con color y contenido especificos
 * 
 * @param color que representa el color de la carta especial 
 * @param contenido que representa el contenido que tendrá la carta especial
 */
  public CartaEspecial(Color color, Contenido contenido){
    super(color, contenido);
  }

  @Override
  public CartaEspecial seleccionarColor(Color color){
    this.color = color;
    return this;
  }

  @Override
  public CartaEspecial seleccionarContenido(Contenido contenido){
    this.contenido = contenido;
    return this;
  }

  @Override
  public CartaEspecial construir() {
    return new CartaEspecial(color, contenido);
  }
}