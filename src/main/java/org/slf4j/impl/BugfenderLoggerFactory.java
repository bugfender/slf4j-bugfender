package org.slf4j.impl;

import org.slf4j.Logger;
import org.slf4j.ILoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BugfenderLoggerFactory implements ILoggerFactory {

    // key: name (String), value: a BugfenderLoggerFactory;
    ConcurrentMap<String, Logger> loggerMap;

    public BugfenderLoggerFactory() {
        loggerMap = new ConcurrentHashMap<String, Logger>();
        // ensure jul initialization. see SLF4J-359 
        // note that call to java.util.logging.LogManager.getLogManager() fails on the Google App Engine platform. See SLF4J-363
        java.util.logging.Logger.getLogger("");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.slf4j.ILoggerFactory#getLogger(java.lang.String)
     */
    public Logger getLogger(String name) {
        // the root logger is called "" in JUL
        if (name.equalsIgnoreCase(Logger.ROOT_LOGGER_NAME)) {
            name = "";
        }

        Logger slf4jLogger = loggerMap.get(name);
        if (slf4jLogger != null)
            return slf4jLogger;
        else {
            java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger(name);
            Logger newInstance = new BugfenderLoggerAdapter(julLogger);
            Logger oldInstance = loggerMap.putIfAbsent(name, newInstance);
            return oldInstance == null ? newInstance : oldInstance;
        }
    }
}
