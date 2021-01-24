package Azamon.Successors;
import Azamon.Estado.AzEstado;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.Random;

//Generador de simulated Annealing
public class AzSuccessorFunction4 implements SuccessorFunction {


    public ArrayList<Successor> getSuccessors(Object state) {
        AzEstado estado = (AzEstado) state;
        AzEstado Fstate = new AzEstado(estado);
        ArrayList<Successor> sons = new ArrayList<>();
        boolean sucessorvalid = false;
        int operacio;
        String moviment;
        int paqs = Fstate.getPackagesSize();
        int ofs = Fstate.getOffersSize();
        Random numeros = new Random();
        while(!sucessorvalid) {
            operacio = numeros.nextInt(2);
            if (operacio == 0) {
                int p = numeros.nextInt(paqs);
                int of = numeros.nextInt(ofs);
                if (Fstate.canBeMoved(p, of)) {
                    AzEstado son = new AzEstado(Fstate);
                    son.movePackage(p, of);
                    moviment = "Moved Package " + p + " to offer " + of + "\n";
                    sons.add(new Successor(moviment, son));
                    sucessorvalid = true;
                }
            }
            else {
                int p1 = numeros.nextInt(paqs);
                int p2 = numeros.nextInt(paqs);
                if (Fstate.canBeExchanged(p1, p2)) {
                    AzEstado son = new AzEstado(Fstate);
                    son.exchangePackages(p1, p2);
                    moviment = "Exchanging Package " + p1 + " with " + p2 + "\n";
                    sons.add(new Successor(moviment, son));
                    sucessorvalid = true;
                }
            }
        }
        return sons;
    }

}
