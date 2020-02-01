package executorservice;

import java.util.concurrent.Callable;

class SingleExecution<R> extends Thread{

    private Callable<R> callable;
    private Result<R> results;
    SingleExecution(Callable<R> callable,Result<R> results,String name) {
        super(name);
        this.callable = callable;
        this.results = results;
    }

    @Override
    public void run() {
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler());
        try {
            this.results.submitResult(this.callable.call());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
