package org.kodluyoruz.restaurant;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Customer implements Runnable {
    Random random = new Random();

    private Food foodInstance;
    private String customerName;
    private int customerOrderNumber;
    private String food;
    private Semaphore semaphore;

    public Customer(String customerName, int customerOrderNumber, Semaphore semaphore, Food food) {
        this.customerName = customerName;
        this.customerOrderNumber = customerOrderNumber;
        this.semaphore = semaphore;
        this.foodInstance = food;
    }

    public Food getFoodInstance() {
        return foodInstance;
    }

    private void setFoodInstance(Food foodInstance) {
        this.foodInstance = foodInstance;
    }

    public String getCustomerName() {
        return customerName;
    }

    private void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerOrderNumber() {
        return customerOrderNumber;
    }

    private void setCustomerOrderNumber(int customerOrderNumber) {
        this.customerOrderNumber = customerOrderNumber;
    }

    public String getFood() {
        return food;
    }

    private void setFood(String food) {
        this.food = food;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    private void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public String toString() {
        return this.customerName;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println(this.customerName + " is entering to Restaurant.");
            boolean enterRestaurant = false;
            while (!enterRestaurant) {
                enterRestaurant = Restaurant.enterToTheRestaurant.get();//Restaurant.enterToTheRestaurant();
            }
            System.out.println(this + " giving to order.");
            food = foodInstance.getFoods().get(random.nextInt(foodInstance.getFoods().size()));
            Restaurant.customerPlaceOrder(this.customerOrderNumber, food);
            Thread.sleep(2000);
            boolean customerWaiting = true;
            while (customerWaiting) {
                customerWaiting = false;
                Thread.sleep(3000);
                if (Restaurant.checkOrderCompleted.test(customerOrderNumber)) {
                    System.out.println(this.customerName + " received the number " + customerOrderNumber + " " + food + " order the customer wanted on time.");
                    System.out.println(this.customerName + " is eating food.");
                    System.out.println(this.customerName + " is leaving from Restaurant.");
                } else {
                    System.out.println(this.customerName +
                            " could not get the order customer requested.\n" +
                            this.customerName + " is leaving from Restaurant.");
                }

            }
            Restaurant.customerLeaveRestaurant();
        } catch (InterruptedException exception) {
            System.out.println("The Customer is leaving from Restaurant.\nBecause Unexpected a situation occurred.");
        }
        semaphore.release();
    }
}
