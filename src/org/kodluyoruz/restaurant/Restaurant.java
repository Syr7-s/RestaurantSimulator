package org.kodluyoruz.restaurant;

import org.kodluyoruz.simulation.RestaurantSimulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


public class Restaurant {

    public static final int RESTAURANT_TABLE_COUNT = 5;
    public static final int RESTAURANT_WAITER_COUNT = 3;
    public static final int RESTAURANT_COOK_COUNT = 2;
    public static final int CUSTOMER_COUNT = 10;

    static LinkedList<Integer> orderList = new LinkedList<>();
    static HashMap<Cook, String> cookOrderClaim = new HashMap<>();
    static HashMap<Cook, Integer> cookOrderNumClaim = new HashMap<>();
    static List<Integer> cookCompletedOrders = new ArrayList<>();
    static LinkedList<Integer> waiterOrderList = new LinkedList<>();
    static HashMap<Waiter, Integer> waiterOrderNumClaim = new HashMap<>();
    static int customerNumber;

    private static HashMap<Integer, String> orderPlaced = new HashMap<>();
    private static HashMap<Integer, String> waiterOrderPlaced = new HashMap<>();
    private static HashMap<Waiter, String> waiterOrderClaim = new HashMap<>();
    private static List<Integer> waiterCompletedOrders = new ArrayList<>();

    private RestaurantSimulation restaurantSimulation;

    public Restaurant(RestaurantSimulation restaurantSimulation) {
        this.restaurantSimulation = restaurantSimulation;
        restaurantSimulation.startSimulation();
    }

    static Supplier<Boolean> enterToTheRestaurant = () -> {
        if (customerNumber < RESTAURANT_TABLE_COUNT) {
            customerNumber++;
            return true;
        } else
            return false;
    };

    static void customerLeaveRestaurant() {
        customerNumber--;
    }

    static void customerPlaceOrder(int orderNum, String food) {
        System.out.println("Customer order " + orderNum + " and " + food + " to order");
        orderList.add(orderNum);
        orderPlaced.put(orderNum, food);
    }

    static Predicate<Waiter> orderAvailable = (waiter) -> {
        if (!orderPlaced.isEmpty()) {
            try {
                int orderNum = orderList.pop();
                waiterOrderList.add(orderNum);
                waiterOrderPlaced.put(orderNum, orderPlaced.get(orderNum));
                waiterOrderClaim.put(waiter, orderPlaced.get(orderNum));
                waiterOrderNumClaim.put(waiter, orderNum);
                return true;
            } catch (RuntimeException exception) {
                System.out.println("Unexpected a occurred in the Restaurant.");
                return false;
            }
        } else {
            return false;
        }
    };

    static Predicate<Cook> cookOrderAvailable = (cook) -> {
        if (!waiterOrderPlaced.isEmpty()) {
            try {
                int waiterOrderNum = waiterOrderList.pop();
                cookOrderClaim.put(cook, waiterOrderPlaced.remove(waiterOrderNum));
                cookOrderNumClaim.put(cook, waiterOrderNum);
                return true;
            } catch (RuntimeException exception) {
                System.out.println("Unexpected a accured in the Restaurant.");
                return false;
            }
        } else {
            return false;
        }
    };

    static Function<Waiter, Integer> waiterGetOrderNum = (waiter -> waiterOrderNumClaim.remove(waiter));

    static Function<Waiter, String> waiterGetOrder = (waiter -> waiterOrderClaim.remove(waiter));

    static Function<Cook, Integer> cookGetOrderNum = (cook -> cookOrderNumClaim.remove(cook));

    static Function<Cook, String> cookGetOrder = (cook) -> cookOrderClaim.remove(cook);

    static void cookOrderCompleted(Cook cook, int orderNum, String food) {
        cookCompletedOrders.add(orderNum);
        System.out.println(cook.toString() + " completed the " + food + " order number " + orderNum);
    }

    static Predicate<Integer> checkWaiterOrderStatus = (orderNum) -> cookCompletedOrders.contains(orderNum);

    static void waiterOrderCompleted(Waiter waiter, int orderNum) {
        waiterCompletedOrders.add(orderNum);
        System.out.println(waiter.toString() + " received the completed order number " + orderNum + " from the cook.");
    }

    static Predicate<Integer> checkOrderCompleted = (orderNum) -> waiterCompletedOrders.contains(orderNum);
}
