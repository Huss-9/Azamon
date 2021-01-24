package Azamon.Heuristics;
import Azamon.Estado.AzEstado;
import aima.search.framework.HeuristicFunction;

//Funcion heuristica de coste y felicidad
public class AzHeuristicFunction3 implements HeuristicFunction{
    public double getHeuristicValue(Object state) {
        AzEstado estat = (AzEstado) state;
        double np = estat.getPackagesSize();
        return estat.getTotalcost() - np/2.0 * estat.getHappiness();
    }
}
