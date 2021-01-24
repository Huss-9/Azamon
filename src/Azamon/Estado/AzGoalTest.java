package Azamon.Estado;
import aima.search.framework.GoalTest;

//Redifinimos la funcion isGoalTest del framework del aima para que funcione con nuestro problema
public class AzGoalTest implements GoalTest {
    public boolean isGoalState(Object o) {
        AzEstado estado = (AzEstado) o;
        return estado.isGoalState();
    }

}
