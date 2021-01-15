package org.kodluyoruz.simulation;

import org.kodluyoruz.restaurant.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class RestaurantSimulation {

    private Semaphore semaphoreCustomer = new Semaphore(Restaurant.RESTAURANT_TABLE_COUNT, false);
    private Semaphore semaphoreWaiter = new Semaphore(Restaurant.RESTAURANT_WAITER_COUNT, true);
    private Semaphore semaphoreCooker = new Semaphore(Restaurant.RESTAURANT_COOK_COUNT, true);

    private ExecutorService executorServiceCustomer = Executors.newFixedThreadPool(Restaurant.RESTAURANT_TABLE_COUNT);
    private ExecutorService executorServiceWaiter = Executors.newFixedThreadPool(Restaurant.RESTAURANT_WAITER_COUNT);
    private ExecutorService executorServiceCook = Executors.newFixedThreadPool(Restaurant.RESTAURANT_COOK_COUNT);

    private Food food;

    public RestaurantSimulation(Food food) {
        this.food = food;
    }

    public void startSimulation() {
        for (int i = 0; i < Restaurant.CUSTOMER_COUNT; i++) {
            executorServiceCustomer.submit(new Customer("Customer " + (i + 1), i + 1, semaphoreCustomer, food));
        }
        for (int i = 0; i < Restaurant.RESTAURANT_WAITER_COUNT; i++) {
            executorServiceWaiter.submit(new Waiter("Waiter " + (i + 1), semaphoreWaiter));
        }
        for (int i = 0; i < Restaurant.RESTAURANT_COOK_COUNT; i++) {
            executorServiceCook.submit(new Cook("Cook " + (i + 1), semaphoreCooker));
        }
        executorServiceCustomer.shutdown();
        executorServiceWaiter.shutdown();
        executorServiceCook.shutdown();

        try {
            executorServiceCustomer.awaitTermination(1, TimeUnit.DAYS);
            executorServiceWaiter.awaitTermination(1, TimeUnit.DAYS);
            executorServiceCook.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        System.out.println("The Simulation is terminating.");
    }

}
