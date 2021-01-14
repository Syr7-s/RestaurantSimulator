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
        try {
            semaphore.acquire();
            System.out.println(this.cookName + " cook will take the order.");
            boolean cookWaiting =true;
            while (cookWaiting) {
                for (int i = 0; i <= Restaurant.waiterOrderList.size(); i++) {
                    cookWaiting=false;
                    if (Restaurant.cookOrderAvailable(this)) {
                        if (!Restaurant.cookOrderNumClaim.isEmpty()) {
                            orderNum = Restaurant.cookGetOrderNum(this);
                            food = Restaurant.cookGetOrder(this);
                            System.out.println("Cook named "+this.cookName +"received the order of "+food+" number "+ orderNum);
                            Thread.sleep(1000);
                            Restaurant.cookOrderCompleted(this, orderNum, food);
                        } else {
                            System.out.println("Order is not for now.");
                            //return;
                        }

                    } else {
                        System.out.println("Cook named "+this.cookName+" is not available.");
                        return;
                    }
                    System.out.println("Cook named "+this+" job is over.");
                }
            }
        } catch (InterruptedException exception) {
            System.out.println("Cook did not prepare the order.");
        }
        semaphore.release();
    }
}
