package uno;

/**
 * La clase Modificadores representa los modificadores de juego en una partida de UNO.
 * Estos modificadores incluyen la capacidad de saltar el turno, invertir el orden de juego
 * y acumular cartas en jugadas especiales.
 * 
 * @version Diciembre 2023
 * 
 * Fue escrita por el equipo Tilingenieros
 */
public class Modificadores{
    private boolean salto;
    private boolean reversa;
    private int acumulador;

    /**
     * Construye una instancia de Modificadores con los valores iniciales dados.
     *
     * @param salto      Indica si se activa el modificador de salto.
     * @param reversa    Indica si se activa el modificador de reversa.
     * @param acumulador El valor inicial del acumulador de cartas.
     */
    public Modificadores(boolean salto, boolean reversa, int acumulador){

        this.salto = salto;
        this.reversa = reversa;
        this.acumulador = acumulador;
    }

    /**
     * Obtiene el valor actual del acumulador de cartas.
     *
     * @return El valor actual del acumulador.
     */
    public int getAcumulador() {
        return this.acumulador;
    }

    /**
     * Establece el valor del acumulador de cartas.
     *
     * @param acumulador El nuevo valor del acumulador.
     */
    public void setAcumulador(int acumulador) {
        this.acumulador = acumulador;
    }

    /**
     * Verifica si el modificador de salto está activado.
     *
     * @return true si el modificador de salto está activado, false de lo contrario.
     */
    public boolean getSalto() {
        return this.salto;
    }

    /**
     * Activa o desactiva el modificador de salto.
     *
     * @param salto true para activar el modificador de salto, false para desactivarlo.
     */
    public void setSalto(boolean salto) {
        this.salto = salto;
    }

    /**
     * Verifica si el modificador de reversa está activado.
     *
     * @return true si el modificador de reversa está activado, false de lo contrario.
     */
    public boolean getReversa() {
        return this.reversa;
    }

    /**
     * Activa o desactiva el modificador de reversa.
     *
     * @param reversa true para activar el modificador de reversa, false para desactivarlo.
     */
    public void setReversa(boolean reversa) {
        this.reversa = reversa;
    }
}