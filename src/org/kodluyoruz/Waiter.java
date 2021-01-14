package org.kodluyoruz;

import java.util.concurrent.Semaphore;

public class Waiter {
    private String waiterName;
    private int orderNum;
    private Semaphore semaphore;

    public Waiter(String waiterName, Semaphore semaphore) {
        this.waiterName = waiterName;
        this.semaphore = semaphore;
    }

    @Override
    public String toString() {
        return this.waiterName;
    }
}
