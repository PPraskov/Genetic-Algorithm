package geneticalgorithm;

import java.util.concurrent.locks.ReentrantReadWriteLock;

class BestSolutionHolder{

    private Solution bestSolution;
    private final ReentrantReadWriteLock lock;


    BestSolutionHolder(Solution bestSolution) {
        this.bestSolution = bestSolution;
        this.lock = new ReentrantReadWriteLock();

    }

    Solution getBestSolution() {
        try {
            lock.readLock().lock();
            return bestSolution;
        }
        finally {
            lock.readLock().unlock();
        }
    }

    void setBestSolution(Solution bestSolution) {
        try {
            lock.writeLock().lock();
            this.bestSolution = bestSolution;
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    Object getCopy() throws CloneNotSupportedException {
        return this.bestSolution.clone();
    }
}
