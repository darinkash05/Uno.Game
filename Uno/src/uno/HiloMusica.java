package uno;

import java.util.ArrayList;

/**
 * El hilo {@code HiloMusica} gestiona la reproducción de música de fondo durante el juego UNO.
 * Controla las transiciones entre las pistas de música según el estado del juego.
 *
 * @version Diciembre 2023
 * 
 * Fue escrita por el equipo Tilingenieros
 */
public class HiloMusica extends Thread {
    private boolean juegoEnProgreso;
    private final ReproductorDeMusica reproductor;
    private final ArrayList<Jugador> jugadores;

    /**
     * Crea una nueva instancia del hilo de música con la lista de jugadores.
     *
     * @param jugadores La lista de jugadores en el juego.
     */
    public HiloMusica(ArrayList<Jugador> jugadores) {
        this.reproductor = new ReproductorDeMusica();
        this.jugadores = jugadores;
        this.juegoEnProgreso = true;
    }

    @Override
    public void run() {
        boolean uno = false;
        boolean partida = false;
        while (juegoEnProgreso) {

            for(Jugador jugador : jugadores){
                if(jugador.getMano().size() <= 1){
                    uno = true;
                    break;
                } else {
                    uno = false;
                }
            }

            if(uno && partida){
                reproductor.detenerMusica();
                reproductor.reproducirUno();
                partida = false;
            } else if(!uno && !partida) {
                reproductor.detenerMusica();
                reproductor.reproducirPartida();
                partida = true;
            }


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }

        reproductor.detenerMusica();
    }

    /**
     * Detiene el hilo de música.
     */
    public void detenerHilo() {
        juegoEnProgreso = false;
    }
}