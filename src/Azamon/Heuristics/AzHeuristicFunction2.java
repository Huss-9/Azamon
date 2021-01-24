package Azamon.Heuristics;
import Azamon.Estado.AzEstado;
import aima.search.framework.HeuristicFunction;

//Funcion heuristica de felicidad
public class AzHeuristicFunction2 implements HeuristicFunction {
    public double getHeuristicValue(Object state){
        AzEstado estado = (AzEstado) state;
        return (-1)*estado.getHappiness();
    }
}