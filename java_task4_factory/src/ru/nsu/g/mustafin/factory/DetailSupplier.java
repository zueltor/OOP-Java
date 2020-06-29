package ru.nsu.g.mustafin.factory;

import ru.nsu.g.mustafin.factory.details.Detail;

import java.util.function.Supplier;
import java.util.logging.Logger;

public class DetailSupplier<T extends Detail> implements Runnable {
    private Logger log;
    private Storage<T> storage;
    private long supply_time;
    private final Supplier<T> supplier;

    DetailSupplier(Logger log, Storage<T> storage, Supplier<T> supplier, long supply_time) {
        this.log = log;
        this.supplier = supplier;
        this.storage = storage;
        this.supply_time = supply_time;
    }

    public void run() {
        while (true) {
            try {
                var detail = supplier.get();
                storage.put(detail);
                Thread.sleep(supply_time);
            } catch (InterruptedException ex) {
                log.fine(Thread.currentThread().getName() + " interrupted");
                return;
            }
        }
    }
}
