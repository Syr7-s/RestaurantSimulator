package org.kodluyoruz;

import java.util.concurrent.Semaphore;

public class Waiter implements Runnable {
    private String waiterName;
    private int orderNum;
    private Semaphore semaphore;
    private String food;
    public Waiter(String waiterName, Semaphore semaphore) {
        this.waiterName = waiterName;
        this.semaphore = semaphore;
    }

    @Override
    public String toString() {
        return this.waiterName;
    }

    @Override
    public void run() {



    }
}
