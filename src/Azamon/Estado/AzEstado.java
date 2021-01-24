package Azamon.Estado;
import IA.Azamon.Oferta;
import IA.Azamon.Paquete;
import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class AzEstado {
    static Paquetes packages;                   //Los paquetes que tenemos que enviar
    static Transporte offers;                   //Las diversas ofertas que nos ofrecen las diversas empresas
    private ArrayList<Integer> Correspond;      //Contiene por cada paqute la oferta que le toca
    private ArrayList<Double> Weights;          //Contiene el peso restante que puede transportar cada oferta
    private double Happiness;                   //La felicidad que obtendriamos con esta solucion
    private double cost;                        //El coste de la solucion actual
    private double almcost;

    //Creadora por defecto
    public AzEstado(AzEstado state) {
        Correspond = (ArrayList<Integer>)state.Correspond.clone();
        Weights = (ArrayList<Double>)state.Weights.clone();
        Happiness = state.Happiness;//vigilar que no sea un puntero
        cost = state.cost; //vigilar que no sea un puntero
        almcost = state.almcost;
    }

    //Otra creadora donde dada una informacion nos crea el estado con esos datos !!importante
    public AzEstado(int TransportSeed, int PaquetsSeed, double proportion, int npaquets) {
        packages = new Paquetes(npaquets, PaquetsSeed);
        offers = new Transporte(packages, proportion, TransportSeed);
        Correspond = new ArrayList<>();
        Weights = new ArrayList<Double>(offers.size());
        for (int i = 0; i < npaquets; ++i) Correspond.add(-1);
        for (int i = 0; i < offers.size(); ++i) Weights.add(offers.get(i).getPesomax());
        cost = Happiness = almcost = 0.0;
    }

    //ordena las ofertas
    public void sortOffers() {
        Collections.sort(offers, new Comparator<Oferta>() {
            @Override
            public int compare(Oferta p1, Oferta p2) { //we create a new comparator to sort the offers vector by the days it will take to deliver the packages
                return ((Integer) p1.getDias()).compareTo((Integer) p2.getDias());
            }
        });
    }

    //ordena los paquetes
    public void sortPackages() {
        Collections.sort(packages, new Comparator<Paquete>() {
            @Override
            public int compare(Paquete p1, Paquete p2) { //we create a new comparator to sort the packages vector by their priority
                return ((Integer) p1.getPrioridad()).compareTo((Integer) p2.getPrioridad());
            }
        });
    }

    //Comprueba que los dias y la priordad son validas
    public boolean matchPriority(int dias, int priority) {
        return dias == 1 && priority == 0 || dias <= 3 && priority == 1 || dias <= 5 && priority == 2;
    }

    //Nos retorna el numero de paquetes
    public int getPackagesSize() {
        return packages.size();
    }

    //Nos retorna el numero de ofertas
    public int getOffersSize() {
        return offers.size();
    }

    //Nos devuelve el coste del estado actual
    public double getCost() {
        return cost;
    }

    //Devuelve el coste de almacen del parametro implicito
    public double getAlmcost() {
        return almcost;
    }

    //Devuelve el coste total del parametro implicito
    public double getTotalcost() {
        return cost + almcost;
    }

    //Devuelve la felicidad que genera el parametro implicito
    public double getHappiness() {
        return Happiness;
    }

    //Suma la felicidad que genera p estando en la oferta of
    public void sum_Happiness(int p, int of) {
        int pri = (packages.get(p).getPrioridad());
        int diaenv = (offers.get(of)).getDias();
        if (pri == 1) Happiness += (3 - diaenv);
        else if (pri == 2) Happiness += (5 - diaenv);
    }

    //Extrae la felicidad que genera p estando en la oferta of
    public void substract_Happiness(int p, int of) {
        int pri = (packages.get(p).getPrioridad());
        int diaenv = (offers.get(of)).getDias();
        if (pri == 1) Happiness -= (3 - diaenv);
        else if (pri == 2) Happiness -= (5 - diaenv);
    }

    //Extrae el coste de transporte del paquete p estando en la oferta o
    public void extractCost(int p, int o) {
        cost -= packages.get(p).getPeso() * offers.get(o).getPrecio();
        if (cost < 0) cost = 0;
    }

    //Suma el coste de transporte del paquete p estando en la oferta o
    public void sumCost(int p, int o) {
        cost += packages.get(p).getPeso() * offers.get(o).getPrecio();
    }

    //Extrae el coste de almacen del paquete p estando en la oferta o
    public void extractAlmCost(int p, int o) {
        int days = offers.get(o).getDias();
        if (days == 5) almcost -= packages.get(p).getPeso() * 0.25 * 2;
        else if (days == 4 || days == 3) almcost -= packages.get(p).getPeso() * 0.25;
        if (almcost < 0) cost = 0;
    }

    //Suma el coste de almacen del paquete p estando en la oferta o
    public void sumAlmCost(int p, int o) {
        int days = offers.get(o).getDias();
        if (days == 5) almcost += packages.get(p).getPeso() * 0.25 * 2;
        else if (days == 4 || days == 3) almcost += packages.get(p).getPeso() * 0.25;
    }

    //Nos dice si el paquete p puede ser movido a la oferta o
    public boolean canBeMoved(int p, int o) {
        if (p < packages.size() && o < offers.size()) {
            int of_actual = Correspond.get(p);
            int dias = offers.get(o).getDias();
            int priority = packages.get(p).getPrioridad();
            return of_actual != o && matchPriority(dias, priority) && packages.get(p).getPeso() <= Weights.get(o);
        }
        return false;
    }

    //Comprueba si el paquete i es intercambiable con el paquete j
    public boolean canBeExchanged(int i, int j) {
        int o1 = Correspond.get(i);
        int o2 = Correspond.get(j);
        if (i != j && o1 != o2 && o1 != -1 && o2 != -1 && i < packages.size() && j < packages.size()) {
            double peso_cambio1 = (packages.get(i).getPeso() - packages.get(j).getPeso()) + Weights.get(o1);
            double peso_cambio2 = (packages.get(j).getPeso() - packages.get(i).getPeso()) + Weights.get(o2);
            boolean prioritymatch1 = matchPriority(offers.get(o1).getDias(), packages.get(j).getPrioridad());
            boolean prioritymatch2 = matchPriority(offers.get(o2).getDias(), packages.get(i).getPrioridad());
            return prioritymatch1 && prioritymatch2 && peso_cambio1 >= 0 && peso_cambio2 >= 0;
        }
        return false;
    }

    //Mueve un paquete a una oferta
    public void movePackage(int paquete, int oferta) {
        int of_actual = Correspond.get(paquete);
        if (of_actual != -1) {
            Weights.set(of_actual, Weights.get(of_actual) + packages.get(paquete).getPeso());
            extractCost(paquete, of_actual);
            extractAlmCost(paquete, of_actual);
            substract_Happiness(paquete, of_actual);
        }
        Correspond.set(paquete, oferta);
        Weights.set(oferta, Weights.get(oferta) - packages.get(paquete).getPeso());
        sumCost(paquete, oferta);
        sumAlmCost(paquete, oferta);
        sum_Happiness(paquete, oferta);
    }

    //Intercambia dos paquetes de dos ofertas distintas
    public void exchangePackages(int p1, int p2) {
        int oferta1 = Correspond.get(p1);
        int oferta2 = Correspond.get(p2);
        Correspond.set(p1,oferta2);
        Weights.set(oferta1, Weights.get(oferta1)+(packages.get(p1).getPeso()-packages.get(p2).getPeso()));
        extractAlmCost(p1, oferta1);
        extractAlmCost(p2, oferta2);
        sumAlmCost(p1, oferta2);
        sumAlmCost(p2, oferta1);
        extractCost(p1,oferta1);
        extractCost(p2,oferta2);
        sumCost(p1, oferta2);
        sumCost(p2, oferta1);
        substract_Happiness(p1, oferta1);
        substract_Happiness(p2, oferta2);
        sum_Happiness(p1, oferta2);
        sum_Happiness(p2, oferta1);
        Correspond.set(p2,oferta1);
        Weights.set(oferta2, Weights.get(oferta2)+(packages.get(p2).getPeso()-packages.get(p1).getPeso()));
    }

    //Nos dice si el paquete p se puede mover a la oferta o solo si tiene la misma prioridad
    public boolean Moveble(int p, int o) {
        int dias = offers.get(o).getDias();
        int priority = packages.get(p).getPrioridad();
        return (packages.get(p).getPeso() <= Weights.get(o)) && ((dias == 1 && priority == 0) ||  (dias > 1 && dias <= 3 && priority == 1) || (dias > 3  && dias <= 5 && priority == 2));
    }

    //Nos genera el estado inicial 1
    public void generateInitialState1() {
        int p = packages.size();
        int o = offers.size();
        boolean moved;
        for (int i = 0; i < p; ++i) {
            moved = false;
            for (int j = 0; j < o && !moved; ++j) {
                if (Moveble(i, j)) {
                    movePackage(i, j);
                    moved = true;
                }

            }
        }
    }

    //Nos genera el estado inicial 2
    public void generateInitialState2() {
        int p = packages.size();
        int o = offers.size();
        sortPackages();
        boolean moved;
        for (int i = 0; i < p; ++i) {
            moved = false;
            for (int j = 0; j < o && !moved; ++j) {
                if (canBeMoved(i, j)) {
                    movePackage(i, j);
                    moved = true;
                }
            }
        }
    }

    //Nos dice las asignaciones del estado actual
    public void printCorresponds() {
        int npaquets = packages.size();
        for (int i = 0; i < npaquets; ++i) {
            System.out.printf("Paquete: %s, Oferta: %s \n", i, Correspond.get(i));
        }
    }

    /*Funcion que nos dice si estamos en un estado solucion. En este caso siempre devolvemos
      falso ya que queremos seguir buscando soluciones mejores*/
    public boolean isGoalState() {
        return false;
    }

}
