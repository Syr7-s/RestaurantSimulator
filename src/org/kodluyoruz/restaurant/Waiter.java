package org.kodluyoruz.restaurant;

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

    public String getWaiterName() {
        return waiterName;
    }

    private void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
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
        return this.waiterName;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println(this.waiterName + " waiter will take the order.");
            for (int i = 0; i <= Restaurant.orderList.size(); i++) {
                if (Restaurant.orderAvailable.test(this)) {
                    orderNum = Restaurant.waiterGetOrderNum.apply(this);
                    food = Restaurant.waiterGetOrder.apply(this);
                    System.out.println("Waiter named " + this + " received the order of " + food + " number " + orderNum + ".");
                } else {
                    System.out.println("Order is not for now.");
                }

                Thread.sleep(2000);
                boolean waiterWaiting = true;
                while (waiterWaiting) {
                    waiterWaiting = false;
                    Thread.sleep(300);
                    if (Restaurant.checkWaiterOrderStatus.test(orderNum)) {
                        Restaurant.waiterOrderCompleted(this, orderNum);
                    } else {
                        System.out.println("Waiter named " + this + " did not receive the order of " + food + " number " + orderNum + " from Cook");
                    }
                    System.out.println("Waiter named " + this + " job is over.");
                }
            }
        } catch (InterruptedException exception) {
            System.out.println("Waiter did not receive the order from Cook");

        }
        semaphore.release();


    }
}
