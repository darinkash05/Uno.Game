package uno;
/** 
 * Excepcion personalizada para manejar los errores que pueden presentarse de entrada no válida
 * Hereda de la clase base Exception
 * 
 * @version Noviembre 2023
 * 
 * Fue escrita por el equipo Tilingenieros
 */

public class EntradaInvalidaException extends Exception {
    /**
     * Crea una nueva instancia de la excepcion con un mensaje especifico.
     * @param mensaje, mensaje de error que describe a la excepcion, en este caso, la entrada no válida.
     */
    public EntradaInvalidaException(String mensaje) {
        super(mensaje);
    }
}