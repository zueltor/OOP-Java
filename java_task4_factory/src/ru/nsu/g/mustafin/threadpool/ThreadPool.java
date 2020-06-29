package ru.nsu.g.mustafin.threadpool;

import ru.nsu.g.mustafin.factory.WorkerStatus;
import ru.nsu.g.mustafin.factory.gui.Updater;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class ThreadPool implements Runnable {

    private Logger log;
    private final LinkedList<Task> tasks;
    private List<Thread> threads;
    private Updater view;

    public ThreadPool(Logger log, int num_threads, Updater view) {
        this.log = log;
        this.tasks = new LinkedList<>();
        this.view = view;
        threads = new ArrayList<>(num_threads);
        for (int i = 0; i < num_threads; i++) {
            threads.add(new Thread(new Worker(i)));
        }
    }

    class Worker implements Runnable {
        private int worker_index;

        public Worker(int i) {
            worker_index = i;
        }

        @Override
        public void run() {
            Task task;
            while (true) {
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        try {
                            view.updateWorker(worker_index, WorkerStatus.nojob);
                            tasks.wait();
                        } catch (InterruptedException e) {
                            log.fine(Thread.currentThread().getName() + " interrupted");
                            return;
                        }
                    }
                    task = tasks.pollFirst();
                }
                view.updateWorker(worker_index, WorkerStatus.working);
                try {
                    task.perform(worker_index);
                } catch (InterruptedException e) {
                    log.fine(Thread.currentThread().getName() + " interrupted");
                    return;
                }
            }
        }
    }

    public int getTasksNumber() {
        synchronized (tasks) {
            return tasks.size();
        }
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

    public void execute(Task task) {
        synchronized (tasks) {
            tasks.add(task);
            tasks.notify();
        }

    }
}
