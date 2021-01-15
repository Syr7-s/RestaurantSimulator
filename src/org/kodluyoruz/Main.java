package org.kodluyoruz;

public class Main {

    public static void main(String[] args) {
        // write your code here
        RestaurantAppContext restaurantAppContext = new RestaurantAppContext();
        Food food = restaurantAppContext.food();
        System.out.println("There are "+Restaurant.RESTAURANT_TABLE_COUNT+" tables in the restaurant.\n" +
                "Restaurant customer capacity is "+Restaurant.customerCount+".\n"+Restaurant.RESTAURANT_WAITER_COUNT+
                " waiters and"+ Restaurant.RESTAURANT_COOK_COUNT +"cooks work in the Restaurant.\n" +
                "Food Types: \n" +
                "----------------------------------------------\n"+
                food.getFoods()+"\n" +
                "----------------------------------------------");

        Restaurant restaurant = restaurantAppContext.restaurant(restaurantAppContext.restaurantSimulation());

    }
}
