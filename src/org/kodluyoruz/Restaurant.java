package org.kodluyoruz;

public class Restaurant {
    static final int RESTAURANT_TABLE_COUNT = 5;
    static final int RESTAURANT_WAITER_COUNT = 3;
    static final int RESTAURANT_COOK_COUNT = 2;

    static int customerCount;

    public Restaurant(int customerCount) {
        Restaurant.customerCount = customerCount;
    }

    static int customerNumber;
    static boolean enterToTheRestaurant (){
        if (customerNumber<RESTAURANT_TABLE_COUNT){
            customerNumber++;
            return true;
        }else
            return false;
    }
    static void customerLeaveRestaurant(){
        customerNumber--;
    }
}
