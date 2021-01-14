package org.kodluyoruz;

public class Food {
    private static Food instance;
    private static Object lock = new Object();

    private Food() {

    }

    public static Food getInstance() {
        synchronized (lock) {
            if (instance == null)
                return new Food();
            return instance;
        }
    }
}
