package executorservice;

import geneticalgorithm.Target;
import geneticalgorithm.interfaces.functional.CompareResults;

import java.util.concurrent.Callable;

public class ExecutorManager<R> {
    private static ExecutorManager manager = null;
    private final Result<R> result;
    private final Callable<R> callable;
    private final Target<R> target;
    private final CompareResults<R> compareResults;
    private boolean toRun;
    private Thread[] threadPool;

    private ExecutorManager(Callable<R> callable, Target<R> target, CompareResults<R> compareResults) {
        this.threadPool = new Thread[Runtime.getRuntime().availableProcessors()];
        this.result = new Result<>();
        this.callable = callable;
        this.target = target;
        this.compareResults = compareResults;
        this.toRun = true;
    }

    public synchronized static ExecutorManager getManager(Callable callable, Target target, CompareResults compareResults) {
        if (manager == null) {
            return new ExecutorManager(callable, target, compareResults);
        }
        return manager;
    }


    public void startComputation() {
        for (int i = 0; i < this.threadPool.length; i++) {
            this.threadPool[i] = assignThread("Thread " + i);
            this.threadPool[i].start();
        }
        while (this.toRun) {
            checkThreads();
            R result = this.result.getResult();
            if (result == null) {
                System.out.println("no result");
                continue;
            }
            System.out.println(result + " -> " + this.result.getSize());
            if (this.compareResults.compare(this.target.getTarget(), result) <= 0) {
                this.toRun = false;
            }
        }
    }

    private SingleExecution<R> assignThread(String name) {
        return new SingleExecution<>(this.callable, this.result, name);
    }

    private void checkThreads() {
        for (int i = 0; i < this.threadPool.length; i++) {
            if (this.threadPool[i].getState() == Thread.State.TERMINATED) {
                this.threadPool[i] = assignThread("Thread" + i);

                this.threadPool[i].start();
            }
        }
    }

    public synchronized void killManager(){
        manager = null;
    }

}
