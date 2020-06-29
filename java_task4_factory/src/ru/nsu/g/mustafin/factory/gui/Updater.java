package ru.nsu.g.mustafin.factory.gui;

import ru.nsu.g.mustafin.factory.WorkerStatus;

public interface Updater {
    void update(ComponentName name, int value);

    void updateWorker(int worker_index, WorkerStatus status);
}
