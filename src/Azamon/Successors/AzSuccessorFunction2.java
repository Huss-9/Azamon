package Azamon.Successors;
import Azamon.Estado.AzEstado;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;

//Generador que solo usa intercambiar
public class AzSuccessorFunction2 implements SuccessorFunction {
    public ArrayList<Successor> getSuccessors(Object state) {
        AzEstado Fstate = (AzEstado) state;
        ArrayList<Successor> sons = new ArrayList<>();
        int p = Fstate.getPackagesSize();
        int o = Fstate.getOffersSize();
        String moviment;
        for (int i = 0; i < p; ++ i) {
            //Intecambiamos paquete i con todos los que tienen una prioridad menor
            for (int j = i + 1; j < p; ++j) {
                AzEstado son = new AzEstado(Fstate);
                if (son.canBeExchanged(i, j))  {
                    son.exchangePackages(i, j);
                    moviment = "Exchanging Package " + i + " with " + j + "\n";
                    sons.add(new Successor(moviment, son));
                }
            }
        }
        return sons;
    }
}
