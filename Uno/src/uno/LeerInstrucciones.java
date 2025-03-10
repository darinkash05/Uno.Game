package uno;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * La clase LeerInstrucciones proporciona un método estático para leer el contenido del archivo 'Instrucciones.txt'.
 * Este archivo contiene las instrucciones y reglas del juego UNO.
 * 
 * @version Diciembre 2023
 * 
 * Fue escrita por el equipo Tilingenieros
 */
public class LeerInstrucciones{

    /**
     * Obtiene el contenido del archivo 'Instrucciones.txt' como una cadena de texto.
     *
     * @return Las instrucciones del juego UNO como una cadena de texto.
     */
    public static String obtenerInstrucciones() {
        StringBuilder instrucciones = new StringBuilder();

        try(BufferedReader br = new BufferedReader(new FileReader("Instrucciones.txt"))){
            String linea;

            while((linea = br.readLine()) != null){
                instrucciones.append(linea).append("\n");
            }

        } catch (IOException e) {
            System.out.println("Lo siento, no se encontro el archivo 'Instrucciones.txt'");
        }

        return instrucciones.toString();
    }
}
