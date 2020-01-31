package geneticalgorithm;

import geneticalgorithm.interfaces.functional.*;

import java.util.Random;
import java.util.concurrent.Callable;

class GeneticAlgorithmImplementation<R, T> implements Callable<R> {

    private final BestSolutionHolder bestSolutionHolder;
    private final CalculateFitness<R, T> calculateFitness;
    private final SolutionGenerator<T> solutionGenerator;
    private final CompareSolutions<R> compareSolutions;
    private final Crossover<T> crossover;
    private final Mutation<T> mutation;
    private final int crossoverChance;
    private final int mutationChance;
    private Solution<R, T> solution;

    GeneticAlgorithmImplementation(BestSolutionHolder bestSolutionHolder,
                                   CalculateFitness<R, T> calculateFitness,
                                   SolutionGenerator<T> solutionGenerator,
                                   CompareSolutions<R> compareSolutions,
                                   Crossover<T> crossover,
                                   Mutation<T> mutation,
                                   int crossoverChance,
                                   int mutationChance) {
        this.bestSolutionHolder = bestSolutionHolder;
        this.calculateFitness = calculateFitness;
        this.solutionGenerator = solutionGenerator;
        this.compareSolutions = compareSolutions;
        this.crossover = crossover;
        this.mutation = mutation;
        this.crossoverChance = crossoverChance;
        this.mutationChance = mutationChance;
    }

    public R call() throws Exception {
        execute();
        return this.solution.getFitness();
    }

    private void execute() throws CloneNotSupportedException {
        selection();
        crossover();
        mutation();
        compareWithTheBestSolution(this.solution);
    }

    private void compareWithTheBestSolution(Solution<R, T> solution) {
        Solution bestSolution = this.bestSolutionHolder.getBestSolution();
        if (this.compareSolutions.compare(solution.getFitness(), (R) bestSolution.getFitness()) > 0) {
            this.bestSolutionHolder.setBestSolution(solution);
        }
    }

    private void mutation() {
        Random random = new Random();
        int roll = random.nextInt(100);
        if (roll < mutationChance) {
            this.mutation.mutate(this.solution.getSolution());
        }
        this.solution.setFitness(this.calculateFitness.calculate(this.solution.getSolution()));
    }

    private void crossover() {
        Solution<R, T> generatedSolution = new Solution<>(this.solutionGenerator.generateSolution());
        Solution<R, T> afterCrossOver = new Solution<>(this.crossover
                .cross(this.solution.getSolution(), generatedSolution.getSolution(), crossoverChance));
        afterCrossOver.setFitness(this.calculateFitness.calculate(afterCrossOver.getSolution()));
        this.compareSolutions(afterCrossOver);
    }

    private void selection() throws CloneNotSupportedException {
        this.solution = (Solution<R,T>) this.bestSolutionHolder.getCopy();
    }

    private void compareSolutions(Solution<R, T> solution) {
        if (this.compareSolutions.compare(solution.getFitness(), this.solution.getFitness()) > 0) {
            this.solution.setSolution(solution.getSolution());
            this.solution.setFitness(solution.getFitness());
        }
    }

}
