package Azamon.Heuristics;
import Azamon.Estado.AzEstado;
import aima.search.framework.HeuristicFunction;

//Funcion heuristica de coste
public class AzHeuristicFunction implements HeuristicFunction {
        public double getHeuristicValue(Object state){
                AzEstado estado = (AzEstado) state;
                return estado.getCost();
        }
}