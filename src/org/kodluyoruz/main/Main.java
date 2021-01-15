package org.kodluyoruz.main;

import org.kodluyoruz.context.RestaurantAppContext;
import org.kodluyoruz.restaurant.Food;
import org.kodluyoruz.restaurant.Restaurant;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        // write your code here
        RestaurantAppContext restaurantAppContext = new RestaurantAppContext();
        Food food = restaurantAppContext.food();
        System.out.println("There are " + Restaurant.RESTAURANT_TABLE_COUNT + " tables in the restaurant.\n" +
                "Restaurant customer capacity is " + Restaurant.CUSTOMER_COUNT + ".\n" + Restaurant.RESTAURANT_WAITER_COUNT +
                " waiters and " + Restaurant.RESTAURANT_COOK_COUNT + " cooks work in the Restaurant.\n" +
                "Food Types: \n" +
                "----------------------------------------------");
        Arrays.stream(food.getFoods().toArray()).forEach(System.out::println);
        System.out.println("----------------------------------------------");

        Restaurant restaurant = restaurantAppContext.restaurant(restaurantAppContext.restaurantSimulation(food));

    }
}
