package uno;

/**
 * La clase HiloBaraja representa un hilo encargado de gestionar la baraja en el juego UNO.
 * Este hilo se encarga de verificar y rellenar la baraja automáticamente en intervalos regulares.
 *
 * @version Diciembre 2023
 * 
 * Fue escrita por el equipo Tilingenieros
 */
public class HiloBaraja extends Thread {
    /**
     * La baraja que será gestionada por el hilo.
     */
    private volatile Baraja baraja;

    /**
     * Indica si el hilo está encendido o apagado.
     */
    private volatile boolean encendido = true;

    /**
     * Constructor de la clase HiloBaraja.
     *
     * @param baraja La baraja que será gestionada por el hilo(Baraja de juego).
     */
    public HiloBaraja(Baraja baraja) {
        this.baraja = baraja;
    }

    /**
     * Detiene el hilo, cambiando el estado de encendido a falso.
     */
    public void detener() {
        encendido = false;
    }

    /**
     * Método principal que se ejecuta cuando el hilo inicia.
     * Realiza una verificación del contenido de la baraja en intervalos regulares y la rellena si esta no tiene cartas.
     */
    @Override
    public void run(){
        while(encendido){
            synchronized(baraja){
                if(baraja.barajaVacia()){
                    Baraja copia = ObtenerBaraja.leerBaraja();
                    if (copia != null) {
                        baraja.getBaraja().addAll(copia.getBaraja());
                        System.out.println("Baraja rellenada");
                    }
                }

                baraja.mezclarBaraja();
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}