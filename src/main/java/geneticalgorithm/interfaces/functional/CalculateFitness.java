package geneticalgorithm.interfaces.functional;

public interface CalculateFitness<R,T> {
    R calculate(T solution);
}
