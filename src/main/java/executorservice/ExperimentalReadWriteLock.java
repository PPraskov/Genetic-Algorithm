package executorservice;

public class ExperimentalReadWriteLock {

    private final Object writeLockObject;
    private volatile Boolean reading;
    private volatile Boolean writing;

    public ExperimentalReadWriteLock() {
        this.writeLockObject = new Object();
        this.reading = false;
        this.writing = false;
    }

    public void writeLock() {
        while (reading){

        }
        synchronized (reading) {
            synchronized (writing) {
                writing = true;
            }
        }
    }

    public void writeUnlock() {
        synchronized (writing) {
            writing = false;
        }
        writing.notify();
    }

    public void readLock() {
        while (writing) {
            try {
                writing.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        reading = true;
    }

    public void readUnlock() {
        reading = false;
    }
}
