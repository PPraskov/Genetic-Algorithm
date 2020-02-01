package executorservice;


public class CustomUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
    }
}
