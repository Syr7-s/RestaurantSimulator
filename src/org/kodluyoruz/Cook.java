package org.kodluyoruz;

import java.util.concurrent.Semaphore;

public class Cook implements Runnable{
    private String cookName;
    private int orderNum;
    private Semaphore semaphore;
    private String food;

    public Cook(String cookName, Semaphore semaphore) {
        this.cookName = cookName;
        this.semaphore = semaphore;
    }

    @Override
    public String toString() {
        return this.cookName;
    }

    @Override
    public void run() {

    }
}
