package org.kodluyoruz;

import java.util.HashMap;
import java.util.LinkedList;

public class Restaurant {
    static final int RESTAURANT_TABLE_COUNT = 5;
    static final int RESTAURANT_WAITER_COUNT = 3;
    static final int RESTAURANT_COOK_COUNT = 2;

    static LinkedList<Integer> orderList = new LinkedList<>();
    private static HashMap<Integer, String> orderPlaced = new HashMap<>();

    static LinkedList<Integer> waiterOrderList = new LinkedList<>();
    private static HashMap<Integer, String> waiterOrderPlaced = new HashMap<>();
    private static HashMap<Waiter, String> waiterOrderClaim = new HashMap<>();
    static HashMap<Waiter, Integer> waiterOrderNumClaim = new HashMap<>();


    static int customerCount;

    public Restaurant(int customerCount) {
        Restaurant.customerCount = customerCount;
    }

    static int customerNumber;

    static boolean enterToTheRestaurant() {
        if (customerNumber < RESTAURANT_TABLE_COUNT) {
            customerNumber++;
            return true;
        } else
            return false;
    }

    static void customerLeaveRestaurant() {
        customerNumber--;
    }

    static void customerPlaceOrder(int orderNum, String food) {
        System.out.println("Customer order " + orderNum + " and " + food + " to order");
        orderList.add(orderNum);
        orderPlaced.put(orderNum, food);
    }

    static void orderAvailable(Waiter waiter) {
        if (!orderPlaced.isEmpty()) {
            try {
                int orderNum = orderList.pop();

            }catch (RuntimeException exception){

            }
        }
    }

}
