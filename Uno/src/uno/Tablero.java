package uno;

import java.util.ArrayList;
/**
 * Aqui se encuentra la clase 'Tablero' en la que se manejan las instrucciones para mandar a imprimir con un formato especial a pantalla
 * 
 * @version Diciembre 2023
 * 
 * Fue escrita por el equipo Tilingenieros
 */
public class Tablero{
    /**
     * Constructor de la clase 'Tablero'
     * 
     * @param jugadores, Lista de los jugadores que participan en el juego
     * @param pila, Lista de cartas en la pila correspondiente al juego 
     * @param pozo, Baraja que representa el pozo de cartas del jeugo 
     */

    public Tablero(ArrayList<Jugador> jugadores, ArrayList<Carta> pila, Baraja pozo){
        imprimirTablero(jugadores, pila, pozo);
    }
/**
 * Se realiza la impresión del estado del tablero correspondiente al juego, se muestran las manos de los jugadores,
 * es decir, sus cartas, la pila del juego y el tamaño del pozo
 * 
 * @param jugadores, Lista de jugadores que participan 
 * @param pila, Lista de cartas en la pila 
 * @param pozo, Bara que representa el pozo de cartas 
 */
    public void imprimirTablero(ArrayList<Jugador> jugadores, ArrayList<Carta> pila, Baraja pozo) {
        int i = 0;
        boolean mayorAI = true;

        // Imprimir los nombres de los jugadores con formato
        for (Jugador jugador : jugadores) {
            System.out.print(String.format("|  %-20s  ", jugador.getNombre()));
        }

        System.out.print("|\n\n");

        while (mayorAI) {

            mayorAI = false;
            //Ciclo for que itera por la lista de los Jugadores para que así se puedan imprimir sus nombres con formato
            for (Jugador jugador : jugadores) {

                if (i < jugador.getMano().size()) {
                    String formatoCarta = String.format("%d. %s", (i + 1), jugador.getCarta(i).toString());
                    System.out.print(String.format("|  %-20s  ", formatoCarta));
                    mayorAI = true;
                } else {
                    System.out.print(String.format("|  %-20s  ", "")); //No hay carta
                }
            }

            System.out.print("|\n");
            i++;
        }
        //Se imprime la pila del juego
        System.out.println("\nPila del Juego: ");
        for(int j = 0; j < pila.size(); j++){
            if(pila.get(j).getContenido() == BuilderCarta.Contenido.COMODIN || pila.get(j).getContenido() == BuilderCarta.Contenido.COMODIN_4){
                System.out.print(pila.get(j) + " " + pila.get(j).getColor() + " ==> ");
            } else{
                System.out.print(pila.get(j) + " ==> ");
            }
        }
        //Imprimir el tamaño del pozo 
        System.out.println("\nEl pozo tiene " + pozo.getTamañoBaraja() + " cartas disponibles\n" );
    }

}