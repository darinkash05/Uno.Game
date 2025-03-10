import uno.Logica;
import uno.EntradaInvalidaException;
import uno.LeerInstrucciones;

import java.util.Scanner;
/**
 * Aqui se encuentra la clase principal con la que se inicia la implementacion del juego UNO
 * 
 * @version Diciembre 2023 
 * 
 * Fue escrita por el equipo Tilingenieros
 */
class Uno {

  /**
 * Este es el método principal que inicia la ejecución del juego UNO.
 * 
 * @param args Los argumentos de la línea de comandos (no se utilizan en este juego).
 */
  public static void main(String[] args) {
    // Se crea una instancia de la lógica del juego UNO 
    Logica uno = new Logica();
    Scanner scan = new Scanner(System.in);
    //Inicializacion de las variables 'continuar' y 'numJugadores'
    boolean continuar = false;//Indica si el juego debe continuar si (true) o no (false)
    int numJugadores = 0;//Almacena el numero de jugadores que participan 
    int opcion = 0;

    System.out.println("Juguemos UNO");
    do{

      try{
        //Se solicita al usuario el número de jugadores de la partida a comenzar 
        System.out.println("¿Cuantos jugadores habra en la partida? (Minimo 2, Maximo 8)");
        numJugadores = scan.nextInt();
        if(numJugadores < 2 || numJugadores > 8){

          throw new EntradaInvalidaException("El número de jugadores debe ser de entre 2 y 8.");

        } else{

          System.out.println("La partida sera de " + numJugadores + " jugadores");
          continuar = true;

        }
      } catch(EntradaInvalidaException e){

        System.err.println("Error: " + e.getMessage());
        continuar = false;

      } catch (java.util.InputMismatchException e) {
        System.out.println("Entrada no válida. Debe ingresar un número.");
        scan.next();
        continuar = false;
      }
      
    }while(!continuar);

    continuar = false;
    
    do{
//Dentro de esta implementacion se manejan dos formas de juego, aquí se solicita que el usuario eliga cuál prefiere 
      try{

        System.out.println("\n\nIngresa el modo de juego que deseas");
        System.out.println("1. Jugador VS Maquina");
        System.out.println("2. Maquina VS Máquina");
        System.out.println("3. ¿Como se juega? (Instrucciones y reglas del juego)");
        System.out.println("4. Salir");
        opcion = scan.nextInt();

        if(opcion < 1 || opcion > 4){

          throw new EntradaInvalidaException(opcion + " no es una opcion valida");

        } else if(opcion == 3) {

          System.out.println(LeerInstrucciones.obtenerInstrucciones());
          continuar = false;

        } else {
          continuar = true;
        }

      } catch(EntradaInvalidaException e){

        System.err.println("Error: " + e.getMessage());
        continuar = false;

      } catch (java.util.InputMismatchException e) {
        System.out.println("Entrada no válida. Debe ingresar un número.");
        scan.next();
        continuar = false;
      }

    }while(!continuar);

    switch(opcion){
      case 1: //Jugador VS Maquina 
        System.out.println("Iniciando juego en modo Jugador vs Maquina...");
        uno.jugadorVSMaquina(numJugadores);
        continuar = true;
        break;
          
      case 2: //Maquina VS Maquina
        System.out.println("Iniciando juego en modo Maquina vs Maquina...");
        uno.maquinaVSMaquina(numJugadores);
        continuar = true;
        break;

      case 3:
        System.out.println("Ingrese una opcion valida");
        continuar = false;
        break;
          
      case 4: 
        System.out.println("Gracias por jugar");
        continuar = true;
        break;
          
      default: 
        System.out.println("Ingrese una opcion valida");
        continuar = false;
        break;
          
      }

  }
}