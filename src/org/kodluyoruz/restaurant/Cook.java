package org.kodluyoruz.restaurant;

import java.util.concurrent.Semaphore;

public class Cook implements Runnable {
    private String cookName;
    private int orderNum;
    private Semaphore semaphore;
    private String food;

    public Cook(String cookName, Semaphore semaphore) {
        this.cookName = cookName;
        this.semaphore = semaphore;
    }

    public String getCookName() {
        return cookName;
    }

    private void setCookName(String cookName) {
        this.cookName = cookName;
    }

    public int getOrderNum() {
        return orderNum;
    }

    private void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    private void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public String getFood() {
        return food;
    }

    private void setFood(String food) {
        this.food = food;
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
            boolean cookWaiting = true;
            while (cookWaiting) {
                for (int i = 0; i <= Restaurant.waiterOrderList.size(); i++) {
                    cookWaiting = false;
                    if (Restaurant.cookOrderAvailable(this)) {
                        if (!Restaurant.cookOrderNumClaim.isEmpty()) {
                            orderNum = Restaurant.cookGetOrderNum(this);
                            food = Restaurant.cookGetOrder(this);
                            System.out.println("Cook named " + this.cookName + " received the order of " + food + " number " + orderNum);
                            Thread.sleep(1000);
                            Restaurant.cookOrderCompleted(this, orderNum, food);
                        } else {
                            System.out.println("Order is not for now.");
                            //return;
                        }

                    } else {
                        System.out.println("Cook named " + this.cookName + " is not available.");
                        return;
                    }
                    System.out.println("Cook named " + this + " job is over.");
                }
            }
        } catch (InterruptedException exception) {
            System.out.println("Cook did not prepare the order.");
        }
        semaphore.release();
    }
}
