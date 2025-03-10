package uno;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
/**
 * La clase Logica gestiona la lógica con la que se implemento el juego UNO, en esta se incluyen la inicializacion del juego
 * interaccion entre jugadores y la verificación de jugadas válidas. Controla el flujo de la partida,
 * como lo son los cambios de turno y la deteccion de un ganador.
 * 
 * @version Diciembre 2023 
 * 
 * Fue escrita por el equipo Tilingenieros
 */

public class Logica{

  Baraja pozo;//Baraja que se utiliza en el juego 
  ArrayList<Carta> pila;//Pila de cartas jugadas
  ArrayList<Jugador> jugadores;//Lista de los jugadores
  HiloBaraja hilo;//Hilo que evalua constantemente el estado de la baraja
  HiloMusica musica; //Hilo que evalua el estado del juego y cambia la musica en consecuencia
  ReproductorDeMusica reproductor;
/**
 * Constructor de la clase Logica, inicializa una nueva partida del juego UNO, creando una baraja
 * (o deserializando una), una pila y la lista de jugadores
 */ 
  public Logica(){
    if(ObtenerBaraja.leerBaraja() == null){
      this.pozo = new Baraja();
    } else {
      this.pozo = ObtenerBaraja.leerBaraja();
      this.pozo.mezclarBaraja();
    }
    this.pila = new ArrayList<Carta>();
    this.jugadores = new ArrayList<Jugador>();
    this.reproductor = new ReproductorDeMusica();
    this.reproductor.reproducirMenu();
  }
/**
 * Se incializa una partida, repartiendo cartas a todos los jugadores y colocando la primera carta en la pila.
 * Tambien se inicializan los hilos que estaran evaluando constantemete la partida.
 */
  public void inicializar(){

    this.reproductor.detenerMusica();
    this.hilo = new HiloBaraja(this.pozo);
    this.musica = new HiloMusica(this.jugadores);

    this.hilo.start();
    this.musica.start();

    for(Jugador jugador : this.jugadores){
      this.pozo.repartirCartasAJugador(jugador);
    }

    this.pila.add(this.pozo.getCarta());
    if( (this.pila.get(this.pila.size()-1).getColor() == null) && ( (this.pila.get(this.pila.size()-1).getContenido() == BuilderCarta.Contenido.COMODIN) || (this.pila.get(this.pila.size()-1).getContenido() == BuilderCarta.Contenido.COMODIN_4) ) ){

      Random rand = new Random();
      int eleccion = rand.nextInt(4) + 1;
      switch(eleccion){
            
        case(1):
          this.pila.get(this.pila.size()-1).setColor(BuilderCarta.Color.ROJO);
          break;

        case(2):
          this.pila.get(this.pila.size()-1).setColor(BuilderCarta.Color.AMARILLO);
          break;

        case(3):
          this.pila.get(this.pila.size()-1).setColor(BuilderCarta.Color.AZUL);
          break;

        case(4):
          this.pila.get(this.pila.size()-1).setColor(BuilderCarta.Color.VERDE);
          break;

        default:
          System.out.println("Ingrese una opcion valida");
          break;     
      }
    }
    
  }
/**
 * Inicia una partida entre jugadores humanos y máquinas 
 * 
 * @param numJugadores, el número de jugadores en la partida
 */
  public void jugadorVSMaquina(int numJugadores){

    for(int i = 0; i < numJugadores; i++){
      Scanner sc = new Scanner(System.in);
      boolean fin = true;
      Jugador jugador = null;
      int opcion = 0;
      
      System.out.println("Ingrese el nombre del jugador " + (i + 1) + ": ");
      String nombre = sc.nextLine();
      
      do{
        try{
          System.out.println("¿Desea que el jugador " + (i + 1) + " sea un humano o un robot? (1 = humano, 2 = robot)");
          opcion = sc.nextInt();

          if(opcion < 1 || opcion > 2){

            throw new EntradaInvalidaException(opcion + " no es una opcion valida, las opciones validas son 1 y 2");

          } else {
            fin = true;
          }

        } catch(EntradaInvalidaException e){
          System.err.println("Error: " + e.getMessage());
          fin = false;
        } catch (java.util.InputMismatchException e) {
          System.out.println("Entrada no válida. Debe ingresar un número.");
          sc.next();
          fin = false;
        }


      }while(!fin);

      switch(opcion){

        case(1):
          jugador = new Jugador(nombre, true);
          break;

        case(2):
          jugador = new Jugador(nombre, false);
          break;
            
        default:
          System.out.println("Ingrese una opcion valida");
          break;
      }
      
      this.jugadores.add(jugador);

    }

    inicializar();
    juego();
    
  }
/**
 * Inicia una partida entre máquinas (jugadores bot)
 * @param numJugadores, el número de jugadores
 */
  public void maquinaVSMaquina(int numJugadores){

    for(int i = 0; i < numJugadores; i++){
      Scanner sc = new Scanner(System.in);

      System.out.println("Ingrese el nombre del jugador " + (i + 1) + ": ");
      String nombre = sc.nextLine();
      Jugador jugador = new Jugador(nombre, false);
      this.jugadores.add(jugador);
    }

    inicializar();
    juego();
    
  }
/**
 * Verifica si un jugador tiene una carta compatible con la carta en la cima de la pila 
 * 
 * @param mano, mano del jugador (sus cartas) 
 * @param pila, pila de cartas jugadas 
 * @return 'true' si el jugador si cuenta con una carta compatible, 'false' en caso de que no la tenga 
 */
  public boolean verificarCartaCompatible(ArrayList<Carta> mano, ArrayList<Carta> pila){

    for(Carta carta : mano){
      if( (carta.getColor() == pila.get(pila.size() - 1).getColor()) || (carta.getContenido() == pila.get(pila.size() - 1).getContenido()) || (carta.getContenido() == BuilderCarta.Contenido.COMODIN) || (carta.getContenido() == BuilderCarta.Contenido.COMODIN_4) ){

        return true;
        
      }
    }
    return false; 
  }
/**
 * Gestiona el flujo del juego, en el que se incluyen los turnos de los jugadores, las jugadas llevadas a cabo 
 * y la detección de un ganador 
 */
  public void juego(){

    Scanner scanner = new Scanner(System.in);

    boolean hayGanador = false;
    boolean salto = false;
    boolean reversa = false;
    boolean cartaCompatible = false;

    int acumulador = 0;
    int acumuladorDeInjugables = 0;
    int i = 0;

    Modificadores modificadores = new Modificadores(salto, reversa, acumulador);

    while(!hayGanador){

      Jugador jugador = this.jugadores.get(i);

      Tablero tablero = new Tablero(this.jugadores, this.pila, this.pozo);

      cartaCompatible = verificarCartaCompatible(jugador.getMano(), this.pila);
        
      if(salto){
          
        saltoActivado(jugador);
        salto = false;
          
      } else if(acumulador != 0){

        acumuladorActivado(jugador, acumulador, this.pozo);
        acumulador = 0;
          
      } else if(!cartaCompatible){

        cartaIncompatibleActivada(cartaCompatible, jugador, this.pozo, this.pila);

        cartaCompatible = verificarCartaCompatible(jugador.getMano(), this.pila);

        tablero = new Tablero(this.jugadores, this.pila, this.pozo);

        if(jugador.getEsHumano() && cartaCompatible ){
          jugadaDeJugador(jugador, this.pila, scanner, acumulador, salto, reversa, modificadores);
        } else if( !jugador.getEsHumano() && cartaCompatible){

          Jugador siguienteJugador = null;

          if(reversa){
            if((i - 1) < 0){
              siguienteJugador = this.jugadores.get(this.jugadores.size() - 1);
            } else {
              siguienteJugador = this.jugadores.get(i - 1);
            }
          } else {
            if((i + 1) > this.jugadores.size() - 1){
              siguienteJugador = this.jugadores.get(0);
            } else {
              siguienteJugador = this.jugadores.get(i + 1);
            }
          }
          jugadaDeMaquina(jugador, siguienteJugador, this.pila, acumulador, salto, reversa, modificadores);
        }

        acumulador = modificadores.getAcumulador();
        modificadores.setAcumulador(0);

        salto = modificadores.getSalto();
        modificadores.setSalto(false);

        reversa = modificadores.getReversa();
        
      } else if(jugador.getEsHumano() && cartaCompatible ){

        jugadaDeJugador(jugador, this.pila, scanner, acumulador, salto, reversa, modificadores);
        acumulador = modificadores.getAcumulador();
        modificadores.setAcumulador(0);

        salto = modificadores.getSalto();
        modificadores.setSalto(false);

        reversa = modificadores.getReversa();
          
      } else if( !jugador.getEsHumano() && cartaCompatible){

        Jugador siguienteJugador = null;

        if(reversa){
          if((i - 1) < 0){
            siguienteJugador = this.jugadores.get(this.jugadores.size() - 1);
          } else {
            siguienteJugador = this.jugadores.get(i - 1);
          }
        } else {
          if((i + 1) > this.jugadores.size() - 1){
            siguienteJugador = this.jugadores.get(0);
          } else {
            siguienteJugador = this.jugadores.get(i + 1);
          }
        }

        jugadaDeMaquina(jugador, siguienteJugador, this.pila, acumulador, salto, reversa, modificadores);
        acumulador = modificadores.getAcumulador();
        modificadores.setAcumulador(0);

        salto = modificadores.getSalto();
        modificadores.setSalto(false);

        reversa = modificadores.getReversa();

      }

      if(reversa){
        i--;
        if(i < 0){
          i = this.jugadores.size() - 1;
        }
      } else {
        i++;
        if(i > this.jugadores.size() - 1){
          i = 0;
        }
      }

      if(jugador.getMano().isEmpty()){
        this.hilo.detener();
        this.musica.detenerHilo();
        System.out.println("¡El jugador " + jugador.getNombre() + " gano la partida!");
        this.reproductor.reproducirVictoria();
        hayGanador = true;

        try {
          TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
        }     
      }

      for(Jugador usuario : this.jugadores){

        cartaCompatible = verificarCartaCompatible(usuario.getMano(), this.pila);

        if(!cartaCompatible && this.pozo.barajaVacia()){
          acumuladorDeInjugables++;
        }

      }

      if(acumuladorDeInjugables == this.jugadores.size()){
        System.out.println("Se ha llegado a un punto muerto, nadie tiene cartas compatibles, el juego se cancela");
        hayGanador = true;
      }

      acumuladorDeInjugables = 0;
    }
  }

/**
 * Activa la función de salto, indicando que el turno del jugador actual ha sido saltado.
 *
 * @param jugador El jugador cuyo turno ha sido saltado.
 */
  public void saltoActivado(Jugador jugador){
    System.out.println("Se salta el turno del jugador " + jugador.getNombre());

    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

/**
 * Activa la función de acumulador, indicando que el jugador actual debe tomar cartas del pozo.
 *
 * @param jugador    El jugador que debe comer cartas.
 * @param acumulador El número de cartas que debe comer.
 * @param pozo       El pozo del cual el jugador toma cartas.
 */
  public void acumuladorActivado(Jugador jugador, int acumulador, Baraja pozo){

    System.out.println("El jugador " + jugador.getNombre() + " come " + acumulador + " cartas");
    if(pozo.barajaVacia()){
      System.out.println("El pozo esta vacio, no hay mas cartas disponibles");
    } else{
      jugador.comerCartas(pozo, acumulador);
    }

    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

/**
 * Activa la función de carta incompatible, obligando al jugador a tomar cartas del pozo hasta que tenga una carta jugable.
 *
 * @param cartaCompatible Indica si el jugador tiene una carta jugable.
 * @param jugador         El jugador que debe tomar cartas.
 * @param pozo            El pozo del cual el jugador toma cartas.
 * @param pila            La pila de cartas en juego.
 */
  public void cartaIncompatibleActivada(boolean cartaCompatible, Jugador jugador, Baraja pozo, ArrayList<Carta> pila){

    bucle: while(!cartaCompatible){
      System.out.println("El jugador " + jugador.getNombre() + " no tiene cartas jugables, come del pozo");
      
      if(pozo.barajaVacia()){
        System.out.println("El pozo esta vacio, no hay mas cartas disponibles");
        break bucle;

      } else{
        jugador.comerCartas(pozo, 1);
        cartaCompatible = verificarCartaCompatible(jugador.getMano(), pila);
        try {
          TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

/**
 * Realiza la jugada de un jugador humano, solicitando la carta que desea jugar y aplicando los efectos de juego
 * pertinentes a la carta seleccionada.
 *
 * @param jugador       El jugador que realiza la jugada.
 * @param pila          La pila de cartas en juego.
 * @param scanner       El objeto Scanner para la entrada del jugador.
 * @param acumulador    El acumulador de cartas a tomar, que puede ser alterado dependiendo de la carta jugada.
 * @param salto         Indica si la función de salto está activada, puede ser alterada dependiendo de la carta jugada.
 * @param reversa       Indica si la función de reversa está activada, puede ser alterada dependiendo de la carta jugada.
 * @param modificadores Objeto modificadores que almacena los cambios realizados por la carta jugada para reflejarlos en el juego.
 */
  public void jugadaDeJugador(Jugador jugador, ArrayList<Carta> pila, Scanner scanner, int acumulador, boolean salto, boolean reversa, Modificadores modificadores){

    boolean invalido = false;
    int indice = 0;
    
    do{
      try{
        System.out.println("Turno de " + jugador.getNombre() + ", elige que carta jugar");
        indice = scanner.nextInt();
        
        if(indice < 1 || indice > jugador.getMano().size()){
          
          throw new EntradaInvalidaException("La carta seleccionada no es valida, pues no es una opcion en tu mano");
          
        } else if( !(jugador.getCarta(indice - 1).getContenido() == BuilderCarta.Contenido.COMODIN) && !(jugador.getCarta(indice - 1).getContenido() == BuilderCarta.Contenido.COMODIN_4) && !(jugador.getCarta(indice - 1).getColor() == this.pila.get(this.pila.size() - 1).getColor()) && !(jugador.getCarta(indice - 1).getContenido() == this.pila.get(this.pila.size() - 1).getContenido()) ){
          
          throw new EntradaInvalidaException("La carta " + jugador.getCarta(indice - 1).toString() + " no es una carta compatible o jugable en este turno");

        } else{

          invalido = false;

        }
        
      } catch(EntradaInvalidaException e){
        
        System.err.println("Error: " + e.getMessage());
        invalido = true;

      } catch (java.util.InputMismatchException e) {
        System.out.println("Entrada no válida. Debe ingresar un número.");
        scanner.next();
        invalido = true;
      }
    
    }while(invalido);
    //
    if( (jugador.getCarta(indice - 1).getContenido() == BuilderCarta.Contenido.COMODIN) || (jugador.getCarta(indice - 1).getContenido() == BuilderCarta.Contenido.COMODIN_4) ){
      
      boolean valido = true;
      int color = 0;
      
      System.out.println("El jugador " + jugador.getNombre() + " jugo la carta: " + jugador.getCarta(indice - 1).toString());
      
      do{
        try{
          
          System.out.println("¿Que color desea selecionar? (1 = ROJO, 2 = AMARILLO, 3 = AZUL, 4 = VERDE)");
          color = scanner.nextInt();
          
          if(color < 1 || color > 4){
            
            throw new EntradaInvalidaException(color + " no es una opcion valida, las opciones validas son 1, 2, 3 y 4");
            
          } else{

            valido = true;

          }
          
        } catch(EntradaInvalidaException e){
          
          System.err.println("Error: " + e.getMessage());
          valido = false;
          
        } catch (java.util.InputMismatchException e) {

          System.out.println("Entrada no válida. Debe ingresar un número.");
          scanner.next();
          valido = false;
        }
      }while(!valido);
      
      //
      switch(color){
        case(1):
          jugador.getCarta(indice - 1).setColor(BuilderCarta.Color.ROJO);
          System.out.println("Se selecciono el color ROJO");
          break;
          
        case(2):
          jugador.getCarta(indice - 1).setColor(BuilderCarta.Color.AMARILLO);
          System.out.println("Se selecciono el color AMARILLO");
          break;
          
        case(3):
          jugador.getCarta(indice - 1).setColor(BuilderCarta.Color.AZUL);
          System.out.println("Se selecciono el color AZUL");
          break;
          
        case(4):
          jugador.getCarta(indice - 1).setColor(BuilderCarta.Color.VERDE);
          System.out.println("Se selecciono el color VERDE");
          break;

        default:
          System.out.println("Ingrese una opcion valida");
          break;
      }
      
      //
      if(jugador.getCarta(indice - 1).getContenido() == BuilderCarta.Contenido.COMODIN_4){
        acumulador += 4;
        modificadores.setAcumulador(acumulador);
      }
      
      pila.add(jugador.usarCarta(indice - 1));
    } else if( ( (jugador.getCarta(indice - 1).getColor() == pila.get(this.pila.size() - 1).getColor()) && (jugador.getCarta(indice - 1).getContenido() == BuilderCarta.Contenido.SALTO) ) || ( (jugador.getCarta(indice - 1).getContenido() == pila.get(pila.size() - 1).getContenido()) && (jugador.getCarta(indice - 1).getContenido() == BuilderCarta.Contenido.SALTO) ) ){
      
      System.out.println("El jugador " + jugador.getNombre() + " jugo la carta: " + jugador.getCarta(indice - 1).toString());
      pila.add(jugador.usarCarta(indice - 1));
      salto = true;
      modificadores.setSalto(salto);
    
    } else if( ( (jugador.getCarta(indice - 1).getColor() == pila.get(this.pila.size() - 1).getColor()) && (jugador.getCarta(indice - 1).getContenido() == BuilderCarta.Contenido.REVERSA) ) || ( (jugador.getCarta(indice - 1).getContenido() == pila.get(pila.size() - 1).getContenido()) && (jugador.getCarta(indice - 1).getContenido() == BuilderCarta.Contenido.REVERSA) ) ){

      System.out.println("El jugador " + jugador.getNombre() + " jugo la carta: " + jugador.getCarta(indice - 1).toString());
      pila.add(jugador.usarCarta(indice - 1));
      if(reversa){
        reversa = false;
      } else if(!reversa){
        reversa = true;
      }
      modificadores.setReversa(reversa);

    } else if( ( (jugador.getCarta(indice - 1).getColor() == pila.get(pila.size() - 1).getColor()) && (jugador.getCarta(indice - 1).getContenido() == BuilderCarta.Contenido.TOMA_2) ) || ( (jugador.getCarta(indice - 1).getContenido() == pila.get(pila.size() - 1).getContenido()) && (jugador.getCarta(indice - 1).getContenido() == BuilderCarta.Contenido.TOMA_2) ) ){
      
      System.out.println("El jugador " + jugador.getNombre() + " jugo la carta: " + jugador.getCarta(indice - 1).toString());
      pila.add(jugador.usarCarta(indice - 1));
      acumulador += 2;
      modificadores.setAcumulador(acumulador);
    
    } else if( (jugador.getCarta(indice - 1).getColor() == pila.get(pila.size() - 1).getColor()) || (jugador.getCarta(indice - 1).getContenido() == pila.get(pila.size() - 1).getContenido()) ){
      
      System.out.println("El jugador " + jugador.getNombre() + " jugo la carta: " + jugador.getCarta(indice - 1).toString());
      pila.add(jugador.usarCarta(indice - 1));
      
    } 

    //
    if(this.pila.size() > 3){
      this.pila.remove(0);
    }
    
    if(jugador.getMano().size() == 1){
      System.out.println("ALERTA: El jugador " + jugador.getNombre() + " activo UNO!");
      this.reproductor.reproducirAlerta();
    }
  }

/**
 * Realiza la jugada de una máquina, seleccionando la primer carta disponible para jugar y usandola.
 *
 * @param jugador           El jugador (máquina) que realiza la jugada.
 * @param siguienteJugador  El jugador del turno siguiente.
 * @param pila              La pila de cartas en juego.
 * @param acumulador        El acumulador de cartas a tomar, que puede ser alterado dependiendo de la carta jugada.
 * @param salto             Indica si la función de salto está activada, puede ser alterada dependiendo de la carta jugada.
 * @param reversa           Indica si la función de reversa está activada, puede ser alterada dependiendo de la carta jugada.
 * @param modificadores     Objeto modificadores que almacena los cambios realizados por la carta jugada para reflejarlos en el juego.
 */
  public void jugadaDeMaquina(Jugador jugador, Jugador siguienteJugador, ArrayList<Carta> pila, int acumulador, boolean salto, boolean reversa, Modificadores modificadores){
    
    System.out.println("Turno de " + jugador.getNombre());

    Carta carta = evaluarOpciones(jugador, siguienteJugador, pila);
      
    if( (carta.getContenido() == BuilderCarta.Contenido.COMODIN) || (carta.getContenido() == BuilderCarta.Contenido.COMODIN_4) ){
        
      boolean valido = true;
      Random rand = new Random();
        
      System.out.println("El jugador " + jugador.getNombre() + " jugo la carta: " + carta.toString());
      do{
          
        int eleccion = rand.nextInt(4) + 1;
        switch(eleccion){
          
          case(1):
            carta.setColor(BuilderCarta.Color.ROJO);
            System.out.println("Se selecciono el color ROJO");
            valido = true;
            break;

          case(2):
            carta.setColor(BuilderCarta.Color.AMARILLO);
            System.out.println("Se selecciono el color AMARILLO");
            valido = true;
            break;

          case(3):
            carta.setColor(BuilderCarta.Color.AZUL);
            System.out.println("Se selecciono el color AZUL");
            valido = true;
            break;

          case(4):
            carta.setColor(BuilderCarta.Color.VERDE);
            System.out.println("Se selecciono el color VERDE");
            valido = true;
            break;

          default:
            System.out.println("Ingrese una opcion valida");
            valido = false;
            break;
            
        }

      }while(!valido);
        
      if(carta.getContenido() == BuilderCarta.Contenido.COMODIN_4){
        acumulador += 4;
        modificadores.setAcumulador(acumulador);
      }
              
      pila.add(jugador.usarCarta(carta));
              
    } else if( ( (carta.getColor() == pila.get(pila.size() - 1).getColor()) && (carta.getContenido() == BuilderCarta.Contenido.SALTO) ) || ( (carta.getContenido() == pila.get(pila.size() - 1).getContenido()) && (carta.getContenido() == BuilderCarta.Contenido.SALTO) ) ){
        
      System.out.println("El jugador " + jugador.getNombre() + " jugo la carta: " + carta.toString());
      pila.add(jugador.usarCarta(carta));
      salto = true;
      modificadores.setSalto(salto);
        
    } else if( ( (carta.getColor() == pila.get(pila.size() - 1).getColor()) && (carta.getContenido() == BuilderCarta.Contenido.REVERSA) ) || ( (carta.getContenido() == pila.get(pila.size() - 1).getContenido()) && (carta.getContenido() == BuilderCarta.Contenido.REVERSA) ) ){

      System.out.println("El jugador " + jugador.getNombre() + " jugo la carta: " + carta.toString());
      pila.add(jugador.usarCarta(carta));
      if(reversa){
        reversa = false;
      } else if (!reversa) {
        reversa = true;
      }
      modificadores.setReversa(reversa);

    } else if( ( (carta.getColor() == pila.get(pila.size() - 1).getColor()) && (carta.getContenido() == BuilderCarta.Contenido.TOMA_2) ) || ( (carta.getContenido() == pila.get(pila.size() - 1).getContenido()) && (carta.getContenido() == BuilderCarta.Contenido.TOMA_2) ) ){

      System.out.println("El jugador " + jugador.getNombre() + " jugo la carta: " + carta.toString());
      pila.add(jugador.usarCarta(carta));
      acumulador += 2;
      modificadores.setAcumulador(acumulador);
      
    } else if( (carta.getColor() == pila.get(pila.size() - 1).getColor()) || (carta.getContenido() == pila.get(pila.size() - 1).getContenido()) ){
        
      System.out.println("El jugador " + jugador.getNombre() + " jugo la carta: " + carta.toString());
      pila.add(jugador.usarCarta(carta));
        
    }
    
    if(pila.size() > 3){
      pila.remove(0);
    }
    
    if(jugador.getMano().size() == 1){
      System.out.println("ALERTA: El jugador " + jugador.getNombre() + " activo UNO!");
      this.reproductor.reproducirAlerta();
    }
    
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private Carta evaluarOpciones(Jugador jugador, Jugador siguienteJugador, ArrayList<Carta> pila){
    Carta mejorCarta = null;
    int mejorPuntuacion = 0; 

    for(Carta carta : jugador.getMano()){
      int puntuacion = calcularPuntuacion(carta, pila.get(pila.size() - 1), siguienteJugador);

      if(puntuacion > mejorPuntuacion){
        mejorPuntuacion = puntuacion;
        mejorCarta = carta;
      }
    }

    return mejorCarta;
  }

  private int calcularPuntuacion(Carta carta, Carta cartaEnPila, Jugador siguienteJugador){
    int puntuacion = 0;

    if(siguienteJugador.getMano().size() == 1){
      if(carta.getColor() == cartaEnPila.getColor()){
        puntuacion += 1;
      }

      if(carta.getContenido() == cartaEnPila.getContenido()){
        puntuacion += 3;
      }

      if( (carta.getContenido() == BuilderCarta.Contenido.TOMA_2) && (carta.getColor() == cartaEnPila.getColor() || carta.getContenido() == cartaEnPila.getContenido()) ){
        puntuacion += 4;
      }

      if( (carta.getContenido() == BuilderCarta.Contenido.REVERSA) && (carta.getColor() == cartaEnPila.getColor() || carta.getContenido() == cartaEnPila.getContenido()) ){
        puntuacion += 3;
      }

      if( (carta.getContenido() == BuilderCarta.Contenido.SALTO) && (carta.getColor() == cartaEnPila.getColor() || carta.getContenido() == cartaEnPila.getContenido()) ){
        puntuacion += 5;
      }

      if(carta.getContenido() == BuilderCarta.Contenido.COMODIN_4){
        puntuacion += 7;
      }

      if(carta.getContenido() == BuilderCarta.Contenido.COMODIN){
        puntuacion += 1;
      }

    } else if(siguienteJugador.getMano().size() < 4){

      if(carta.getColor() == cartaEnPila.getColor()){
        puntuacion += 2;
      }

      if(carta.getContenido() == cartaEnPila.getContenido()){
        puntuacion += 2;
      }

      if( (carta.getContenido() == BuilderCarta.Contenido.TOMA_2) && (carta.getColor() == cartaEnPila.getColor() || carta.getContenido() == cartaEnPila.getContenido()) ){
        puntuacion += 5;
      }

      if( (carta.getContenido() == BuilderCarta.Contenido.REVERSA) && (carta.getColor() == cartaEnPila.getColor() || carta.getContenido() == cartaEnPila.getContenido()) ){
        puntuacion += 3;
      }

      if( (carta.getContenido() == BuilderCarta.Contenido.SALTO) && (carta.getColor() == cartaEnPila.getColor() || carta.getContenido() == cartaEnPila.getContenido()) ){
        puntuacion += 4;
      }

      if(carta.getContenido() == BuilderCarta.Contenido.COMODIN_4){
        puntuacion += 2;
      }

      if(carta.getContenido() == BuilderCarta.Contenido.COMODIN){
        puntuacion += 1;
      }
    } else {
      if(carta.getColor() == cartaEnPila.getColor()){
        puntuacion += 2;
      }

      if(carta.getContenido() == cartaEnPila.getContenido()){
        puntuacion += 2;
      }

      if( (carta.getContenido() == BuilderCarta.Contenido.TOMA_2) && (carta.getColor() == cartaEnPila.getColor() || carta.getContenido() == cartaEnPila.getContenido()) ){
        puntuacion += 3;
      }

      if( (carta.getContenido() == BuilderCarta.Contenido.REVERSA) && (carta.getColor() == cartaEnPila.getColor() || carta.getContenido() == cartaEnPila.getContenido()) ){
        puntuacion += 2;
      }

      if( (carta.getContenido() == BuilderCarta.Contenido.SALTO) && (carta.getColor() == cartaEnPila.getColor() || carta.getContenido() == cartaEnPila.getContenido()) ){
        puntuacion += 1;
      }

      if(carta.getContenido() == BuilderCarta.Contenido.COMODIN || carta.getContenido() == BuilderCarta.Contenido.COMODIN_4){
        puntuacion += 1;
      }
    }
    
    return puntuacion;
  }


}