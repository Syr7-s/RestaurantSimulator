package org.kodluyoruz;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Customer implements Runnable{
    Random random = new Random();

    private Food foodInstance = Food.getInstance();

    private String customerName;
    private int customerOrderNumber;
    private String food;
    private Semaphore semaphore;

    public Customer(String customerName, int customerOrderNumber, Semaphore semaphore) {
        this.customerName = customerName;
        this.customerOrderNumber = customerOrderNumber;
        this.semaphore = semaphore;
    }

    @Override
    public String toString() {
        return this.customerName;
    }

    @Override
    public void run() {

    }
}
