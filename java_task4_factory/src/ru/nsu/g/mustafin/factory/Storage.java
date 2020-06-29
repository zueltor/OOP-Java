package ru.nsu.g.mustafin.factory;

import ru.nsu.g.mustafin.factory.gui.ComponentName;
import ru.nsu.g.mustafin.factory.gui.Updater;

import java.util.LinkedList;
import java.util.logging.Logger;

class Storage<T> {
    private final ComponentName name;
    private LinkedList<T> storage = new LinkedList<>();
    private int capacity;
    private Updater view;
    private Logger log;

    public int getCapacity() {
        return capacity;
    }

    public Storage(Logger log, int capacity, ComponentName name, Updater view) {
        this.log = log;
        this.capacity = capacity;
        this.name = name;
        this.view = view;
    }

    public synchronized int size() {
        return storage.size();
    }

    public synchronized void put(T o) throws InterruptedException {
        while (storage.size() == capacity) {
            log.fine("Storage " + name.toString() + " is full");
            this.wait();
        }
        storage.add(o);
        view.update(name, storage.size());
        if (storage.size() == 1) {
            this.notify();
        }
    }

    public synchronized T get() throws InterruptedException {
        while (storage.isEmpty()) {
            this.wait();
        }
        T detail = storage.poll();
        view.update(name, storage.size());

        if (storage.size() == capacity - 1) {
            notify();
        }
        return detail;
    }
}
