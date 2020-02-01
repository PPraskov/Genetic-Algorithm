package executorservice;

import java.util.ArrayDeque;
import java.util.Queue;

class Result<R> {

    private final Object notEmpty;
    private final Object notFull;
    private volatile Queue<R> resultList;
    private volatile int capacity;

    Result() {
        this.capacity = 10;
        this.resultList = new ArrayDeque<>(10);
        notEmpty = new Object();
        notFull = new Object();
    }

    R getResult() {
        R result = null;
        synchronized (this.notEmpty) {
            if (this.resultList.isEmpty()) {
                try {
                    this.notEmpty.wait(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        result = this.resultList.poll();

        return result;
    }

    void submitResult(R result) {
        synchronized (this.notEmpty){
            boolean added = true;
            while (added) {
                added = !this.resultList.offer(result);
            }
            this.notEmpty.notifyAll();
        }
    }

    int getSize(){
        return this.resultList.size();
    }
}



