package Azamon.Successors;
import Azamon.Estado.AzEstado;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;

//Generador que solo usa mover
public class AzSuccessorFunction3 implements SuccessorFunction {

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
                if (son.canBeMoved(i, j))  {
                    son.movePackage(i, j);
                    moviment = "Moved Package " + i + " to offer " + j + "\n";
                    sons.add(new Successor(moviment, son));
                }

            }
        }
        return sons;
    }

}
