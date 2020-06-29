package ru.nsu.g.mustafin.factory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class FactoryLogger {
    private static final Logger log;

    static {
        log = Logger.getLogger(FactoryLogger.class.getName());
        Formatter formatter = new Formatter() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

            @Override
            public String format(LogRecord record) {
                return String.format("[%s] %s\n", dateFormat.format(new Date(record.getMillis())), this.formatMessage(record));
            }

        };
        log.setUseParentHandlers(false);
        try {
            FileHandler handler = new FileHandler("factoryLog.txt");

            handler.setFormatter(formatter);
            log.addHandler(handler);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO);
            log.addHandler(consoleHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setLogging(boolean set) {
        if (set) {
            log.setLevel(Level.INFO);
        } else {
            log.setLevel(Level.OFF);
        }
    }

    public static Logger getLogger() {
        return log;
    }


}
