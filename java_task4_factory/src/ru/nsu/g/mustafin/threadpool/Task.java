package ru.nsu.g.mustafin.threadpool;

public interface Task {
    void perform(int worker_index) throws InterruptedException;
}
