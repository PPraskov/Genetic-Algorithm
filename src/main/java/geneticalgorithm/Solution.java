package geneticalgorithm;

public class Solution<R,T> implements Cloneable{

    private T solution;
    private R fitness;

    public Solution(T solution) {
        this.solution = solution;
    }

    public R getFitness() {
        return fitness;
    }

    public void setFitness(R fitness) {
        this.fitness = fitness;
    }

    public T getSolution(){
        return this.solution;
    }

    public void setSolution(T solution) {
        this.solution = solution;
    }

    Class<?> getType() {
        return solution.getClass();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
