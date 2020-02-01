package geneticalgorithm;

import geneticalgorithm.interfaces.functional.*;

public class GeneticAlgorithmConfiguration<R, T> {

    private final Solution<R, T> initialSolution;
    private final Target<R> target;
    private final SolutionGenerator<T> solutionGenerator;
    private final CalculateFitness calculateFitness;
    private final CompareSolutions<R> compareSolutions;
    private final Crossover<T> crossover;
    private final Mutation<T> mutation;
    private final CompareResults<R> compareResults;
    private final int crossoverChance;
    private final int mutationChance;


    public GeneticAlgorithmConfiguration(Solution<R, T> initialSolution,
                                         Target<R> target,
                                         SolutionGenerator<T> solutionGenerator,
                                         CalculateFitness<R, T> calculateFitness,
                                         CompareSolutions<R> compareSolutions,
                                         Crossover<T> crossover,
                                         Mutation<T> mutation,
                                         CompareResults<R> compareResults,
                                         int crossoverChance,
                                         int mutationChance) {
        this.initialSolution = initialSolution;
        this.target = target;
        this.solutionGenerator = solutionGenerator;
        this.calculateFitness = calculateFitness;
        this.compareSolutions = compareSolutions;
        this.crossover = crossover;
        this.mutation = mutation;
        this.compareResults = compareResults;
        this.crossoverChance = crossoverChance;
        this.mutationChance = mutationChance;
        setFitness();
    }

    private void setFitness(){
        this.initialSolution.setFitness((R) this.calculateFitness.calculate(this.initialSolution.getSolution()));
    }

    Solution<R, T> getInitialSolution() {
        return initialSolution;
    }

    Target<R> getTarget() {
        return target;
    }

    SolutionGenerator<T> getSolutionGenerator() {
        return solutionGenerator;
    }

    CalculateFitness getCalculateFitness() {
        return calculateFitness;
    }

    CompareSolutions<R> getCompareSolutions() {
        return compareSolutions;
    }

    Crossover<T> getCrossover() {
        return crossover;
    }

    Mutation<T> getMutation() {
        return mutation;
    }

    int getMutationChance() {
        return mutationChance;
    }

    int getCrossoverChance() {
        return crossoverChance;
    }

    CompareResults<R> getCompareResults() {
        return compareResults;
    }
}
