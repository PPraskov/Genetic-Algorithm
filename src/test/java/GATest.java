import geneticalgorithm.GeneticAlgorithmConfiguration;
import geneticalgorithm.GeneticAlgorithmManager;
import geneticalgorithm.Solution;
import geneticalgorithm.Target;
import geneticalgorithm.interfaces.functional.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GATest {


    @Test
    public void start() throws InterruptedException {
        List<Integer> initialSolution = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            initialSolution.add(0);
        }
        Integer targetInt = 50;
        Solution<Integer, List<Integer>> solution = new Solution<>(initialSolution);
        Target<Integer> target = new Target<>(targetInt);
        CalculateFitness<Integer, List<Integer>> calculateFitness = (x) -> {
            int result = 0;
            for (int i = 0; i < x.size(); i++) {
                result += x.get(i);
            }
            return result;
        };
        SolutionGenerator<List<Integer>> solutionGenerator = () -> {
            Random random = new Random();
            List<Integer> integers = new ArrayList<>();
            for (int i = 0; i < initialSolution.size(); i++) {
                integers.add(random.nextInt(10));
            }
            return integers;
        };
        Crossover<List<Integer>> crossover = (x1, x2, chance) -> {
            Random random = new Random();
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                if (random.nextInt(100) < chance) {
                    result.add(x2.get(i));
                    continue;
                }
                result.add(x1.get(i));
            }
            return result;
        };

        Mutation<List<Integer>> mutation = (x1) -> {
            Random random = new Random();
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < x1.size(); i++) {
                if (random.nextBoolean()) {
                    if (random.nextBoolean()) {
                        result.add(x1.get(i) + 1);
                        continue;
                    }
                    result.add(x1.get(i) - 1);
                }
                result.add(x1.get(i));
            }
            return result;
        };
        CompareSolutions<Integer> compareSolutions = (x1, x2) -> {
            Integer first = x1;
            Integer second = x2;
            if (first > second) {
                return 1;
            }
            if (first < second) {
                return -1;
            }
            return 0;
        };
        GeneticAlgorithmConfiguration<Integer, List<Integer>> configuration =
                new GeneticAlgorithmConfiguration<>(
                        solution, target, solutionGenerator,
                        calculateFitness,compareSolutions,
                        crossover,mutation,50,20);
        GeneticAlgorithmManager<Integer,List<Integer>> manager = new GeneticAlgorithmManager<>(configuration);
        manager.start();
        Thread.sleep(100000);
    }
}
