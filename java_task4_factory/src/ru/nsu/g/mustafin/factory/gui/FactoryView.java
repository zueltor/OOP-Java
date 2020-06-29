package ru.nsu.g.mustafin.factory.gui;

import ru.nsu.g.mustafin.factory.Factory;
import ru.nsu.g.mustafin.factory.WorkerStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FactoryView extends JFrame implements Updater {
    private int[] values;
    private String[] names;
    private JPanel content;
    private JProgressBar[] progressBars;
    private JLabel[] storages;
    private int[] capacities;
    private JLabel jCarsSold;
    private JLabel[] workerStatus;
    private Factory factory;
    private int n;

    public FactoryView(int[] values, boolean do_logging) {
        super("Factory");

        this.values = values;
        factory = new Factory(values, do_logging, this);
        content = new JPanel();
        jCarsSold = new JLabel("0");
        n = values[Names.workers.ordinal()] + 5;
        workerStatus = new JLabel[n - 5];
        progressBars = new JProgressBar[4];
        capacities = new int[4];
        storages = new JLabel[4];
        content.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        content.setLayout(new GridLayout(n, 1));

        this.initNames();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                factory.close();
                new MenuView();
            }
        });

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.initContent();
        this.add(content);
        this.setMinimumSize(new Dimension(500, 500));

        this.pack();
        this.setVisible(true);
    }

    public void startFactory() {
        factory.run();
    }

    private void initNames() {
        names = new String[n];
        names[0] = "Accessories";
        names[1] = "Bodies";
        names[2] = "Motors";
        names[3] = "Cars";
        names[4] = "Cars sold";
        for (int i = 5; i < n; i++) {
            names[i] = "Worker " + (i - 4);
        }
    }

    private void initContent() {
        JPanel[] panels = new JPanel[n];
        for (int i = 0; i < n; i++) {
            panels[i] = new JPanel(new GridLayout(1, 2));
            content.add(panels[i]);
        }
        for (int i = 0; i < 4; i++) {
            progressBars[i] = new JProgressBar(0, values[i]);
            capacities[i] = values[i];
            storages[i] = new JLabel(String.format("%s (%d/%d)", names[i], 0, capacities[i]));
            panels[i].add(storages[i]);
            panels[i].add(progressBars[i]);
        }
        panels[4].add(new JLabel(names[4]));
        panels[4].add(jCarsSold);
        for (int i = 5; i < n; i++) {
            workerStatus[i - 5] = new JLabel("Waiting");
            panels[i].add(new JLabel(names[i]));
            panels[i].add(workerStatus[i - 5]);
        }
    }


    @Override
    public synchronized void update(ComponentName name, int value) {
        if (name == ComponentName.soldCars) {
            jCarsSold.setText(String.valueOf(value));
        } else {
            storages[name.ordinal()].setText(String.format("%s (%d/%d)", names[name.ordinal()], value, capacities[name.ordinal()]));
            progressBars[name.ordinal()].setValue(value);
        }
    }

    @Override
    public synchronized void updateWorker(int worker_index, WorkerStatus status) {
        String text;
        switch (status) {
            case nojob:
                text = "Waiting for work";
                break;
            case storage:
                text = "Buiding car";
                break;
            case working:
                text = "Working";
                break;
            default:
            case details:
                text = "Waiting for details";
        }
        workerStatus[worker_index].setText(text);
    }
}
