package executorservice;

import java.util.concurrent.Callable;

public class SingleExecution<R> extends Thread{

    private R result;
    private Callable<R> callable;

    public SingleExecution(Callable<R> callable) {
        this.callable = callable;
    }

    @Override
    public void run() {
        try {
            this.result = this.callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public R getResult() {
        return result;
    }
}
