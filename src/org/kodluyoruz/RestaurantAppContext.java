package org.kodluyoruz;

public class RestaurantAppContext {

    public RestaurantSimulation restaurantSimulation() {
        return new RestaurantSimulation();
    }

    public Restaurant restaurant(RestaurantSimulation restaurantSimulation) {
        return new Restaurant(restaurantSimulation);
    }

}
