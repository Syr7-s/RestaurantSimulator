package org.kodluyoruz.restaurant;

import java.util.ArrayList;
import java.util.List;

public class Food {
    private static Food instance;
    private static Object lock = new Object();
    private static List<String> foods = new ArrayList<>();

    private Food() {
        getAllFoods();
    }

    public static Food getInstance() {
        synchronized (lock) {
            if (instance == null)
                return new Food();
            return instance;
        }
    }
    public List<String> getFoods(){
        return foods;
    }

    private  void setFoods(List<String> foods) {
        Food.foods = foods;
    }

    private void getAllFoods(){
        foods.add("Soup");
        foods.add("Fish");
        foods.add("Ravioli");
        foods.add("Meat");
        foods.add("Burger");
        foods.add("Cake");
        foods.add("French fries");
        foods.add("Bratwurst");
        foods.add("Lasagna");
        foods.add("Carpaccio");
        foods.add("Brioche");
        foods.add("Ratatouille");
    }
}
