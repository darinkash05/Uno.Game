package uno;

import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.EOFException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

/**
 * La clase {@code ObtenerBaraja} proporciona métodos para manejar la lectura y escritura
 * de un objeto Baraja predeterminado contenido en un archivo txt.
 * 
 * @version Diciembre 2023
 * 
 * Fue escrita por el equipo Tilingenieros
 */
public class ObtenerBaraja{

    /**
     * Nombre del archivo utilizado para almacenar la baraja.
     */
    private static final String NOMBRE_ARCHIVO = "Baraja.txt";

    /**
     * Deserializa la baraja desde el archivo especificado.
     * 
     * @return Una baraja leída desde el archivo o null en caso contrario.
     */
    public static Baraja leerBaraja(){
        Baraja baraja = null;

        Path path = Paths.get(NOMBRE_ARCHIVO);

        if(Files.exists(path)){
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(NOMBRE_ARCHIVO))) {
                Object obj = ois.readObject();
                if (obj instanceof Baraja){
                    baraja = (Baraja) obj;
                }
            } catch (EOFException e) {
                System.out.println("Se llego al final del archivo");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("IO o classnotfound exception");
                e.printStackTrace();
                return null;
            }
        }

        return baraja;
    }

    /**
     * Serializa la baraja en el archivo especificado.
     * 
     * @param baraja La baraja que se va a serializar en el archivo.
     */
    public static void guardarBaraja(Baraja baraja){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOMBRE_ARCHIVO))) {
            oos.writeObject(baraja);
        } catch (IOException e) {
        }
    }
}