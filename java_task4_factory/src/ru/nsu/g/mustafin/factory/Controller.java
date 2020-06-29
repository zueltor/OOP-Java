package ru.nsu.g.mustafin.factory;

import ru.nsu.g.mustafin.factory.details.Accessory;
import ru.nsu.g.mustafin.factory.details.Body;
import ru.nsu.g.mustafin.factory.details.Car;
import ru.nsu.g.mustafin.factory.details.Motor;
import ru.nsu.g.mustafin.factory.gui.Updater;
import ru.nsu.g.mustafin.threadpool.Task;
import ru.nsu.g.mustafin.threadpool.ThreadPool;

import java.util.logging.Logger;

public class Controller implements Runnable {

    private final Logger log;
    private Storage<Accessory> accessoryStorage;
    private Storage<Body> bodyStorage;
    private Storage<Motor> motorStorage;
    private final Storage<Car> carStorage;
    private ThreadPool workers;
    private final Shop dealers;
    private int build_time;
    private Updater view;

    public Controller(Logger log, int num_threads, Storage<Accessory> accessoryStorage, Storage<Body> bodyStorage, Storage<Motor> motorStorage, Storage<Car> carStorage, Shop dealers, int build_time, Updater view) {
        this.log = log;
        workers = new ThreadPool(log, num_threads, view);
        this.view = view;
        this.build_time = build_time;
        this.dealers = dealers;
        this.accessoryStorage = accessoryStorage;
        this.bodyStorage = bodyStorage;
        this.motorStorage = motorStorage;
        this.carStorage = carStorage;
    }

    @Override
    public void run() {
        workers.run();

        Task buildcar = (worker_index) -> {
            view.updateWorker(worker_index, WorkerStatus.details);
            Accessory accessory = accessoryStorage.get();
            Body body = bodyStorage.get();
            Motor motor = motorStorage.get();

            view.updateWorker(worker_index, WorkerStatus.storage);
            carStorage.put(new Car(accessory, body, motor));
            view.updateWorker(worker_index, WorkerStatus.working);
            Thread.sleep(build_time);
        };

        while (!Thread.currentThread().isInterrupted()) {
            synchronized (dealers) {
                int n;
                while ((n = carStorage.getCapacity() - carStorage.size() - workers.getTasksNumber()) <= 0) {
                    try {
                        dealers.wait();
                    } catch (InterruptedException e) {
                        log.fine(Thread.currentThread().getName() + " interrupted");
                        workers.close();
                        return;
                    }
                }

                for (int i = 0; i < n; i++) {
                    workers.execute(buildcar);
                }
            }
        }
        workers.close();
    }
}
