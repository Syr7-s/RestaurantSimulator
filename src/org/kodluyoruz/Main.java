package org.kodluyoruz;

public class Main {

    public static void main(String[] args) {
        // write your code here
        //The number of customers was determined as 10
        Food food = Food.getInstance();
        int customerCount = 10;
        System.out.println("There are "+Restaurant.RESTAURANT_TABLE_COUNT+" tables in the restaurant.\n" +
                "Restaurant customer capacity is "+Restaurant.customerCount+".\n"+Restaurant.RESTAURANT_WAITER_COUNT+
                " waiters and"+ Restaurant.RESTAURANT_COOK_COUNT +"cooks work in the Restaurant.\n" +
                "Food Types: \n" +
                "----------------------------------------------\n"+
                food.getFoods()+"\n" +
                "----------------------------------------------");
        Restaurant restaurant = new Restaurant(customerCount);

       // restaurant.startSimulation();

    }
}
