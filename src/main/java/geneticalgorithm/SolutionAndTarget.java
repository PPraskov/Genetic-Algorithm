package geneticalgorithm;

public class SolutionAndTarget<S,T> {
    private T type;
    private final Solution<S,T> solution;
    private final Target<T> target;

    public SolutionAndTarget(Solution<S,T> solution, Target<T> target) {
        this.solution = solution;
        this.target = target;
    }

    Solution<S,T> getSolution() {
        return solution;
    }

    Target<T> getTarget() {
        return target;
    }

    T getType() {
        return type;
    }
}
