package ru.nsu.g.mustafin.factory;

import ru.nsu.g.mustafin.factory.details.Accessory;
import ru.nsu.g.mustafin.factory.details.Body;
import ru.nsu.g.mustafin.factory.details.Car;
import ru.nsu.g.mustafin.factory.details.Motor;
import ru.nsu.g.mustafin.factory.gui.ComponentName;
import ru.nsu.g.mustafin.factory.gui.Names;
import ru.nsu.g.mustafin.factory.gui.Updater;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Factory implements Runnable {
    private int[] values;
    private ArrayList<Thread> threads;
    private Updater view;
    private Logger log;
    private boolean do_logging;

    public Factory(int[] values, boolean do_logging, Updater view) {
        this.values = values;
        this.view = view;
        this.do_logging = do_logging;
        FactoryLogger.setLogging(do_logging);
        log = FactoryLogger.getLogger();

        threads = new ArrayList<>();
        Storage<Body> bodyStorage = new Storage<>(log, values[Names.bodyStorageSize.ordinal()], ComponentName.body, view);
        Storage<Accessory> accessoryStorage = new Storage<>(log, values[Names.accessoryStorageSize.ordinal()], ComponentName.accessory, view);
        Storage<Motor> motorStorage = new Storage<>(log, values[Names.motorStorageSize.ordinal()], ComponentName.motor, view);
        Storage<Car> carStorage = new Storage<>(log, values[Names.carStorageSize.ordinal()], ComponentName.car, view);
        Shop shop = new Shop(log, values[Names.dealers.ordinal()], values[Names.dealerTime.ordinal()], carStorage, view);
        Controller controller = new Controller(log, values[Names.workers.ordinal()], accessoryStorage, bodyStorage, motorStorage, carStorage, shop, values[Names.workerTime.ordinal()], view);

        Thread t1 = new Thread(new DetailSupplier<>(log, bodyStorage, Body::new, values[Names.supplyBodyTime.ordinal()]));
        Thread t2 = new Thread(new DetailSupplier<>(log, accessoryStorage, Accessory::new, values[Names.supplyAccessoryTime.ordinal()]));
        Thread t3 = new Thread(new DetailSupplier<>(log, motorStorage, Motor::new, values[Names.supplyEngineTime.ordinal()]));
        Thread t4 = new Thread(controller);
        Thread t5 = new Thread(shop);
        threads.add(t1);
        threads.add(t2);
        threads.add(t3);
        threads.add(t4);
        threads.add(t5);
    }


    @Override
    public void run() {
        for (var thread : threads) {
            thread.start();
        }
    }

    public void close() {
        for (var thread : threads) {
            thread.interrupt();
        }
    }
}
