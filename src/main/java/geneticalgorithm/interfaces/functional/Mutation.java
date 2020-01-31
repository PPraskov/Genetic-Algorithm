package geneticalgorithm.interfaces.functional;

public interface Mutation<T> {
    T mutate(T solutionOne);
}
