package org.kodluyoruz.context;

import org.kodluyoruz.restaurant.Food;
import org.kodluyoruz.restaurant.Restaurant;
import org.kodluyoruz.simulation.RestaurantSimulation;

public class RestaurantAppContext {

    public RestaurantSimulation restaurantSimulation() {
        return new RestaurantSimulation();
    }

    public Restaurant restaurant(RestaurantSimulation restaurantSimulation) {
        return new Restaurant(restaurantSimulation);
    }

    public Food food(){
        return Food.getInstance();
    }
}
