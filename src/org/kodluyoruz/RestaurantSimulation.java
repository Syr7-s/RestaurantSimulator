package org.kodluyoruz;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class RestaurantSimulation {
    private Semaphore semaphoreCustomer = new Semaphore(Restaurant.RESTAURANT_TABLE_COUNT, false);
    private Semaphore semaphoreWaiter = new Semaphore(Restaurant.RESTAURANT_WAITER_COUNT, true);
    private Semaphore semaphoreCooker = new Semaphore(Restaurant.RESTAURANT_COOK_COUNT, true);

    private static final ExecutorService executorServiceCustomer = Executors.newFixedThreadPool(Restaurant.RESTAURANT_TABLE_COUNT);
    private static final ExecutorService executorServiceWaiter = Executors.newFixedThreadPool(Restaurant.RESTAURANT_WAITER_COUNT);
    private static final ExecutorService executorServiceCook = Executors.newFixedThreadPool(Restaurant.RESTAURANT_COOK_COUNT);


}
