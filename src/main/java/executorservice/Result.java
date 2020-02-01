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
        boolean condition = false;
        synchronized (this.resultList) {
            if (this.resultList.isEmpty()) {
                condition = true;
            }
        }
        if (condition) {
            synchronized (this.notEmpty) {
                try {
                    this.notEmpty.wait(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        synchronized (this.resultList) {
            result = this.resultList.poll();
        }
        synchronized (this.notFull) {
            this.notFull.notify();
        }


        return result;
    }

    void submitResult(R result) {
        boolean condition = false;
        synchronized (this.resultList) {
            if (this.resultList.size() >= this.capacity) {
                condition = true;
            }
        }
        if (condition) {
            synchronized (this.notFull) {
                try {
                    this.notFull.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        boolean added = true;
        synchronized (this.resultList) {
            while (added) {
                added = !this.resultList.offer(result);
            }
        }
        synchronized (this.notEmpty) {
            this.notEmpty.notify();
        }
    }


    int getSize() {
        int result;
        synchronized (this.resultList){
            result = this.resultList.size();
        }
        return result;
    }
}



