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
        try {
            semaphore.acquire();
            System.out.println(this.waiterName + " waiter will take the order");
            for (int i = 0; i <= Restaurant.orderList.size(); i++) {
                if (Restaurant.orderAvailable(this)) {
                    orderNum = Restaurant.waiterGetOrderNum(this);
                    food = Restaurant.waiterGetOrder(this);
                    System.out.println("Waiter named "+this +"received the order of "+food+" number "+ orderNum);
                } else {
                    System.out.println("Order is not for now.");
                }

                Thread.sleep(2000);
                boolean waiterWaiting = true;
                while (waiterWaiting) {
                    waiterWaiting = false;
                    Thread.sleep(300);
                    if (Restaurant.checkWaiterOrderStatus(orderNum)) {
                        Restaurant.waiterOrderCompleted(this, orderNum);
                    } else {
                        System.out.println("Waiter named "+this +" did not receive the order of "+food+" number "+ orderNum+" from Cook");
                    }
                    System.out.println("Waiter named "+this+" job is over.");
                }
            }
        } catch (InterruptedException exception) {
            System.out.println("Waiter did not receive the order from Cook");

        }
        semaphore.release();


    }
}
