package geneticalgorithm.interfaces.functional;

public interface Crossover<T> {
    T cross(T solutionOne,T generatedSolution,int chance);
}
