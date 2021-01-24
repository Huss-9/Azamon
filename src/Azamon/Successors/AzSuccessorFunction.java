package Azamon.Successors;

import java.util.ArrayList;

import Azamon.Estado.AzEstado;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;


//Función que nos permite generar sucesores mediante HillClimbing, intercambiar y mover
public class AzSuccessorFunction implements SuccessorFunction {

    public ArrayList<Successor> getSuccessors(Object state) {
        AzEstado Fstate = (AzEstado) state;
        ArrayList<Successor> sons = new ArrayList<>();
        int p = Fstate.getPackagesSize();
        int o = Fstate.getOffersSize();
        String moviment;
        for (int i = 0; i < p; ++i) {
            //Movemos paquetes entre distintas ofertas para generar sucesores
            for (int j = o - 1; j >= 0; --j) {
                AzEstado son = new AzEstado(Fstate);
                if (son.canBeMoved(i, j)) {
                    son.movePackage(i, j);
                    moviment = "Moved Package " + i + " to offer " + j + "\n";
                    sons.add(new Successor(moviment, son));
                }
            }
            //Intecambiamos paquete i con todos los que tienen una prioridad menor
            for (int j = i + 1; j < p; ++j) {
                AzEstado son = new AzEstado(Fstate);
                if (son.canBeExchanged(i, j)) {
                    son.exchangePackages(i, j);
                    moviment = "Exchanging Package " + i + " with " + j + "\n";
                    sons.add(new Successor(moviment, son));
                }
            }
        }
        return sons;
    }

}
