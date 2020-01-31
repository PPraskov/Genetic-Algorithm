package geneticalgorithm;

import executorservice.SingleExecution;

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
        SingleExecution<R> execution = new SingleExecution<>(callable);
        execution.start();
        try {
            execution.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(execution.getResult());
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
}
