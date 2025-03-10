package uno;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * La clase {@code ReproductorDeMusica} proporciona métodos para reproducir música durante el juego UNO.
 *
 * @version Diciembre 2023
 * 
 * Fue escrita por el equipo Tilingenieros
 */
public class ReproductorDeMusica{

    private final String MENU = "uno/Musica/Menu.wav";
    private final String PARTIDA = "uno/Musica/Partida.wav";
    private final String UNO = "uno/Musica/Uno.wav";
    private final String VICTORIA = "uno/Musica/Victoria.wav";
    private final String ALERTA = "uno/Musica/Alerta.wav";
    private Clip clip;

    /**
     * Reproduce la música de fondo "Uno.wav" durante el juego cuando al menos uno de los jugadores tiene una carta.
     */
    public void reproducirUno() {

        String rutaMusica = UNO;

        try {
            File archivoMusica = new File(rutaMusica);

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivoMusica);

            clip = AudioSystem.getClip();

            clip.open(audioInputStream);

            clip.loop(Clip.LOOP_CONTINUOUSLY);

            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reproduce la música de fondo "Partida.wav" durante la partida.
     */
    public void reproducirPartida() {

        String rutaMusica = PARTIDA;
        try {
            File archivoMusica = new File(rutaMusica);

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivoMusica);

            clip = AudioSystem.getClip();

            clip.open(audioInputStream);

            clip.loop(Clip.LOOP_CONTINUOUSLY);

            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reproduce la música de fondo "Menu" durante el menu de selecciond e modo de juego y jugadores.
     */
    public void reproducirMenu() {

        String rutaMusica = MENU;

        try {
            File archivoMusica = new File(rutaMusica);

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivoMusica);

            clip = AudioSystem.getClip();

            clip.open(audioInputStream);

            clip.loop(Clip.LOOP_CONTINUOUSLY);

            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reproduce el efecto "Victoria.wav" cuando alguien gana la partida
     */
    public void reproducirVictoria() {

        String rutaMusica = VICTORIA;

        try {
            File archivoMusica = new File(rutaMusica);

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivoMusica);

            clip = AudioSystem.getClip();

            clip.open(audioInputStream);

            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reproduce el efecto "Alerta.wav" cuando un jugador se queda con solo una carta.
     */
    public void reproducirAlerta() {

        String rutaMusica = ALERTA;

        try {
            File archivoMusica = new File(rutaMusica);

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivoMusica);

            clip = AudioSystem.getClip();

            clip.open(audioInputStream);

            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Detiene la música de fondo que se este reproduciendo en el momento.
     */
    public void detenerMusica() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
