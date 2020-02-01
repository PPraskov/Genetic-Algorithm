package geneticalgorithm;

import executorservice.ExecutorManager;

import java.util.concurrent.Callable;

public class GeneticAlgorithmManager<R,T> {

    private final GeneticAlgorithmConfiguration<R,T> configuration;
    private volatile BestSolutionHolder bestSolutionHolder;

    public GeneticAlgorithmManager(GeneticAlgorithmConfiguration<R,T> configuration) {
        this.configuration = configuration;
    }

    GeneticAlgorithmConfiguration getConfiguration() {
        return configuration;
    }

    public void start(){
        this.bestSolutionHolder = new BestSolutionHolder(this.configuration.getInitialSolution());
        Callable<R> callable = generateCallable();
        ExecutorManager<R> executorManager = ExecutorManager.getManager(
                callable,
                this.configuration.getTarget(),
                this.configuration.getCompareResults());
        executorManager.startComputation();
        executorManager.killManager();
    }

    private Callable<R> generateCallable(){
        GeneticAlgorithmConfiguration configuration = getConfiguration();
        return new GeneticAlgorithmImplementation<R,T>(this.bestSolutionHolder,
                configuration.getCalculateFitness(),
                configuration.getSolutionGenerator(),
                configuration.getCompareSolutions(),
                configuration.getCrossover(),
                configuration.getMutation(),
                configuration.getCrossoverChance(),
                configuration.getMutationChance());
    }

    public T getBestSolution(){
        return (T)this.bestSolutionHolder.getBestSolution().getSolution();
    }
    public R getBestSolutionFitness(){
        return (R)this.bestSolutionHolder.getBestSolution().getFitness();
    }
}
