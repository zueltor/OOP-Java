package ru.nsu.g.mustafin.factory;

import ru.nsu.g.mustafin.factory.details.Car;
import ru.nsu.g.mustafin.factory.gui.ComponentName;
import ru.nsu.g.mustafin.factory.gui.Updater;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Shop implements Runnable {
    private Logger log;
    private Dealer first_dealer;
    private ArrayList<Thread> rest_dealers;
    private Storage<Car> carStorage;
    private int sell_time;
    private int cars_sold = 0;
    private Updater view;

    public Shop(Logger log, int num_dealers, int sell_time, Storage<Car> carStorage, Updater view) {
        this.log = log;
        rest_dealers = new ArrayList<>();
        this.sell_time = sell_time;
        this.carStorage = carStorage;
        this.view = view;
        first_dealer = new Dealer(carStorage, 1);
        for (int i = 1; i < num_dealers; i++) {
            rest_dealers.add(new Thread(new Dealer(carStorage, i + 1)));
        }
    }

    @Override
    public void run() {
        for (var dealer : rest_dealers) {
            dealer.start();
        }
        first_dealer.run();
    }

    public void onInterrupt() {
        for (var dealer : rest_dealers) {
            dealer.interrupt();
        }
    }

    public class Dealer implements Runnable {

        private Storage<Car> carStorage;
        private final Object key = new Object();
        private int dealer_number;

        public Dealer(Storage<Car> carStorage, int dealer_number) {
            this.dealer_number = dealer_number;
            this.carStorage = carStorage;
        }

        @Override
        public void run() {
            Car car;
            while (true) {
                try {
                    car = carStorage.get();
                    synchronized (key) {
                        cars_sold++;
                    }
                    log.info(String.format("Dealer %d: %s", dealer_number, car.toString()));
                    view.update(ComponentName.soldCars, cars_sold);

                    synchronized (Shop.this) {
                        Shop.this.notify();
                    }
                    Thread.sleep(sell_time);
                } catch (InterruptedException e) {
                    log.fine(Thread.currentThread().getName() + " interrupted");
                    Shop.this.onInterrupt();
                    return;
                }
            }

        }
    }
}
