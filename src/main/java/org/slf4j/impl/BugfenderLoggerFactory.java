package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BugfenderLoggerFactory implements ILoggerFactory {

    // key: name (String), value: a org.slf4j.impl.BugfenderLoggerFactory;
    ConcurrentMap<String, BugfenderLoggerAdapter> loggerMap;

    public BugfenderLoggerFactory() {
        loggerMap = new ConcurrentHashMap<String, BugfenderLoggerAdapter>();
    }

    public Logger getLogger(String name) {
        Logger slf4jLogger = loggerMap.get(name);
        if (slf4jLogger != null)
            return slf4jLogger;
        else {
            BugfenderLoggerAdapter newInstance = new BugfenderLoggerAdapter(name);
            Logger oldInstance = loggerMap.putIfAbsent(name, newInstance);
            return oldInstance == null ? newInstance : oldInstance;
        }
    }
}
