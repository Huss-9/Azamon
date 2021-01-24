import aima.search.framework.*;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.Random;
import java.util.Scanner;
import Azamon.Estado.*;
import Azamon.Heuristics.*;
import Azamon.Successors.*;


public class Menu {
    private static int npaquetes;
    private static int seed;
    private static int tseed;
    private static double proprotion;
    private static int heuristic;
    private static int inicial;
    private static int operadores;
    private static String opcion;

    public static void main(String[] args) {
        System.out.println("Practica 1 IA: Azamon");
        System.out.println();
        Scanner entrada = new Scanner(System.in);

        opcion = "";
        while (!opcion.equals("salir")) {
            System.out.println("Seleccione que desea hacer:");
            System.out.println("--Si desea hacer una prueba con Hill Climbing, escriba Hill");
            System.out.println("--Si desea hacer una prueba con Simulated Annealing, escriba Simulated");
            System.out.println("--Si deasea Salir escriba Salir");
            opcion = (entrada.nextLine()).toLowerCase();
            if (!opcion.equals("hill") && !opcion.equals("simulated") && !opcion.equals("salir")) {
                System.out.println("Introduzca alguna de las opciones previamente mencionadas");
                opcion = (entrada.nextLine()).toLowerCase();
                while(!opcion.equals("hill") && !opcion.equals("simulated") && !opcion.equals("salir")){
                    System.out.println("Introduzca alguna de las opciones previamente mencionadas");
                    opcion = (entrada.nextLine()).toLowerCase();
                }
            }
            lectura_datos();
            if (opcion.equals("hill")) {
                AzHillClimbing();
            }
            else if (opcion.equals("simulated")) {
                AzSimulatedSearch();
            }
            if (!opcion.equals("salir")) {
                System.out.println("----------------------------------------------------------------------");
                System.out.println("Seleccione que desea hacer:");
                System.out.println("--Si desea seguir haciendo mas pruebas, pulse cualquier tecla");
                System.out.println("--Si desea salir, escriba Salir");
                opcion = (entrada.nextLine()).toLowerCase();
                System.out.println("----------------------------------------------------------------------");
            }
        }

    }
    public static void lectura_datos() {
        Scanner entrada = new Scanner(System.in);
        Random seed_selector = new Random();
        System.out.println("Introduzca una seed para los paquetes o el valor '-1' si desea usar una seed random:");
        seed = entrada.nextInt();
        if (seed < 0) seed = seed_selector.nextInt(20000);
        System.out.println("Introduzca una seed para el transporte o el valor '-1' si desea usar una seed random:");
        tseed = entrada.nextInt();
        if (tseed < 0) tseed = seed_selector.nextInt(20000);
        System.out.println("Introduzca el número de paquetes:");
        npaquetes = entrada.nextInt();
        System.out.println("Introduzca la proporción paquetes/ofertas:");
        proprotion = entrada.nextDouble();
        System.out.println("Introduzca el numero del estado inicial que desea escoger:");
        System.out.println("1: Estado inicial 1");
        System.out.println("2: Estado inicial 2");
        inicial = entrada.nextInt();
        if (inicial != 1 && inicial != 2) {
            while(inicial != 1 && inicial != 2) {
                System.out.println("Introduzca el numero del estado inicial que desea escoger:");
                System.out.println("1: Estado inicial 1");
                System.out.println("2: Estado inicial 2");
                inicial = entrada.nextInt();
            }
        }
        System.out.println("Introduzca el numero relacionado a la heuristica que desea escoger:");
        System.out.println("1: Costes");
        System.out.println("2: Felicidad");
        System.out.println("3: Costes y felicidad");
        heuristic = entrada.nextInt();
        if (heuristic < 1 || heuristic > 3) {
            while (heuristic < 1 || heuristic > 3) {
                System.out.println("Introduzca el numero relacionado a la heuristica que desea escoger:");
                System.out.println("1: Costes");
                System.out.println("2: Felicidad");
                System.out.println("3: Costes y felicidad");
                heuristic = entrada.nextInt();
            }
        }
        if (!opcion.equals("simulated")) {
            System.out.println("Introduzca el numero relacionado con los operadores que desea escoger:");
            System.out.println("1: Mover y Intercambiar");
            System.out.println("2: Intercambiar");
            System.out.println("3: Mover");
            operadores = entrada.nextInt();
            if (operadores < 1 || operadores > 3) {
                while (operadores < 1 || operadores > 3) {
                    System.out.println("Introduzca el numero relacionado con los operadores que desea escoger:");
                    System.out.println("1: Mover y Intercambiar");
                    System.out.println("2: Intercambiar");
                    System.out.println("3: Mover");
                    operadores = entrada.nextInt();
                }
            }
        }


    }
    public static void probleminfo() {
        System.out.println("-------------------------PROBLEMA-----------------------");
        System.out.println();
        if (opcion.equals("hill")) System.out.println("    Prueba con Hill Climbing");
        else System.out.println("    Prueba con Simulated Annealing");
        System.out.println("    Seed Paquetes:               "+seed);
        System.out.println("    Seed Transporte:             "+tseed);
        System.out.println("    Numero de paquetes:          "+npaquetes);
        System.out.println("    Proporción paquetes/ofertas: "+proprotion);

        if (inicial == 1) System.out.println("    Estado inicial:              "+1);
        else System.out.println("    Estado inicial:              "+2);
        if (heuristic == 1) System.out.println("    Heuristico:                  Costes");
        else if(heuristic == 2) System.out.println("    Heuristico:                  Felicidad");
        else System.out.println("    Heuristico:                  Costes y Felicidad");
        if (!opcion.equals("simulated")) {
            if (operadores == 1) System.out.println("    Operadores:                  Mover y Intercambiar");
            else if (operadores == 2) System.out.println("    Operador:                  Intercambiar");
            else System.out.println("    Operador:                  Mover");
        }
        System.out.println();
    }
    public static void AzHillClimbing() {
        probleminfo();
        AzEstado estado = new AzEstado(tseed, seed, proprotion, npaquetes);
        if (inicial == 1) estado.generateInitialState1();
        else estado.generateInitialState2();
        System.out.println("------------------Información Estado Inicial-------------");
        System.out.println();
        System.out.printf("    Costes de transporte: %.2f\n", estado.getCost());
        System.out.printf("    Costes de almacen:    %.2f\n", estado.getAlmcost());
        System.out.printf("    Costes totales:       %.2f\n", estado.getTotalcost());
        System.out.printf("    Feliciadad:           %s\n", estado.getHappiness());
        System.out.println();
        Problem prueba;
        SuccessorFunction op;
        if (operadores == 1) op = new AzSuccessorFunction();
        else if (operadores == 2) op = new AzSuccessorFunction2();
        else op = new AzSuccessorFunction3();
        HeuristicFunction heu;
        if (heuristic == 1) heu = new AzHeuristicFunction();
        else if (heuristic == 2) heu = new AzHeuristicFunction2();
        else heu = new AzHeuristicFunction3();
        prueba = new Problem(estado, op, new AzGoalTest(), heu);
        try {
            HillClimbingSearch search = new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(prueba, search);
            AzEstado f = (AzEstado) search.getGoalState();
            System.out.println("------------------Información Estado Final----------------");
            System.out.println();
            System.out.printf("    Costes de transporte: %.2f\n", f.getCost());
            System.out.printf("    Costes de almacen:    %.2f\n", f.getAlmcost());
            System.out.printf("    Costes totales:       %.2f\n", f.getTotalcost());
            System.out.printf("    Feliciadad:           %s\n", f.getHappiness());
            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void AzSimulatedSearch() {
        probleminfo();
        AzEstado estado = new AzEstado(tseed, seed, proprotion, npaquetes);
        if (inicial == 1) estado.generateInitialState1();
        else estado.generateInitialState2();
        System.out.println("------------------Información Estado Inicial-------------");
        System.out.println();
        System.out.printf("    Costes de transporte: %.2f\n", estado.getCost());
        System.out.printf("    Costes de almacen:    %.2f\n", estado.getAlmcost());
        System.out.printf("    Costes totales:       %.2f\n", estado.getTotalcost());
        System.out.printf("    Feliciadad:           %s\n", estado.getHappiness());
        System.out.println();
        HeuristicFunction heu;
        if (heuristic == 1) heu = new AzHeuristicFunction();
        else if (heuristic == 2) heu = new AzHeuristicFunction2();
        else heu = new AzHeuristicFunction3();
        Problem prueba = new Problem(estado, new AzSuccessorFunction4(), new AzGoalTest(), heu);
        try {
            SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(100000, 100, 1, 0.1d);
            SearchAgent agent = new SearchAgent(prueba, search);
            AzEstado f = (AzEstado) search.getGoalState();
            System.out.println("------------------Información Estado Final----------------");
            System.out.println();
            System.out.printf("    Costes de transporte: %.2f\n", f.getCost());
            System.out.printf("    Costes de almacen:    %.2f\n", f.getAlmcost());
            System.out.printf("    Costes totales:       %.2f\n", f.getTotalcost());
            System.out.printf("    Feliciadad:           %s\n", f.getHappiness());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
