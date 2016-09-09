package org.slf4j.impl;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.EventConstants;
import org.slf4j.event.LoggingEvent;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

/**
 * A wrapper over Bugfender in conformity with the {@link Logger} interface.
 */
public final class BugfenderLoggerAdapter extends MarkerIgnoringBase implements LocationAwareLogger {

	private static final long serialVersionUID = -2064252262110589508L;
	
	transient final java.util.logging.Logger logger;

    BugfenderLoggerAdapter(java.util.logging.Logger logger) {
        this.logger = logger;
        this.name = logger.getName();
    }

    public boolean isTraceEnabled() {
        return true;
    }

    public void trace(String msg) {
        log(SELF, Level.FINEST, msg, null);
    }

    public void trace(String format, Object arg) {
        FormattingTuple ft = MessageFormatter.format(format, arg);
        log(SELF, Level.FINEST, ft.getMessage(), ft.getThrowable());
    }

    public void trace(String format, Object arg1, Object arg2) {
        FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
        log(SELF, Level.FINEST, ft.getMessage(), ft.getThrowable());
    }

    public void trace(String format, Object... argArray) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
        log(SELF, Level.FINEST, ft.getMessage(), ft.getThrowable());
    }

    public void trace(String msg, Throwable t) {
        log(SELF, Level.FINEST, msg, t);
    }

    public boolean isDebugEnabled() {
        return true;
    }

    public void debug(String msg) {
        log(SELF, Level.FINE, msg, null);
    }

    public void debug(String format, Object arg) {
        FormattingTuple ft = MessageFormatter.format(format, arg);
        log(SELF, Level.FINE, ft.getMessage(), ft.getThrowable());
    }

    public void debug(String format, Object arg1, Object arg2) {
        FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
        log(SELF, Level.FINE, ft.getMessage(), ft.getThrowable());
    }

    public void debug(String format, Object... argArray) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
        log(SELF, Level.FINE, ft.getMessage(), ft.getThrowable());
    }

    public void debug(String msg, Throwable t) {
        log(SELF, Level.FINE, msg, t);
    }

    public boolean isInfoEnabled() {
        return true;
    }

    public void info(String msg) {
        log(SELF, Level.INFO, msg, null);
    }

    public void info(String format, Object arg) {
        FormattingTuple ft = MessageFormatter.format(format, arg);
        log(SELF, Level.INFO, ft.getMessage(), ft.getThrowable());
    }

    public void info(String format, Object arg1, Object arg2) {
        FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
        log(SELF, Level.INFO, ft.getMessage(), ft.getThrowable());
    }

    public void info(String format, Object... argArray) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
        log(SELF, Level.INFO, ft.getMessage(), ft.getThrowable());
    }

    public void info(String msg, Throwable t) {
        log(SELF, Level.INFO, msg, t);
    }

    public boolean isWarnEnabled() {
        return true;
    }

    public void warn(String msg) {
        log(SELF, Level.WARNING, msg, null);
    }

    public void warn(String format, Object arg) {
        FormattingTuple ft = MessageFormatter.format(format, arg);
        log(SELF, Level.WARNING, ft.getMessage(), ft.getThrowable());
    }
    public void warn(String format, Object arg1, Object arg2) {
        FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
        log(SELF, Level.WARNING, ft.getMessage(), ft.getThrowable());
    }

    public void warn(String format, Object... argArray) {
    	FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
        log(SELF, Level.WARNING, ft.getMessage(), ft.getThrowable());
    }

    public void warn(String msg, Throwable t) {
        log(SELF, Level.WARNING, msg, t);
    }

    public boolean isErrorEnabled() {
        return true;
    }

    public void error(String msg) {
        log(SELF, Level.SEVERE, msg, null);
    }

    public void error(String format, Object arg) {
        FormattingTuple ft = MessageFormatter.format(format, arg);
        log(SELF, Level.SEVERE, ft.getMessage(), ft.getThrowable());
    }

    public void error(String format, Object arg1, Object arg2) {
        FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
        log(SELF, Level.SEVERE, ft.getMessage(), ft.getThrowable());
    }

    public void error(String format, Object... arguments) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            log(SELF, Level.SEVERE, ft.getMessage(), ft.getThrowable());
    }

    public void error(String msg, Throwable t) {
            log(SELF, Level.SEVERE, msg, t);
    }

    private void log(String callerFQCN, Level level, String msg, Throwable t) {
        // millis and thread are filled by the constructor
        LogRecord record = new LogRecord(level, msg);
        record.setLoggerName(getName());
        record.setThrown(t);
        // Note: parameters in record are not set because SLF4J only
        // supports a single formatting style
        fillCallerData(callerFQCN, record);
        logger.log(record);
    }

    static String SELF = BugfenderLoggerAdapter.class.getName();
    static String SUPER = MarkerIgnoringBase.class.getName();

    /**
     * Fill in caller data if possible.
     * 
     * @param record
     *          The record to update
     */
    final private void fillCallerData(String callerFQCN, LogRecord record) {
        StackTraceElement[] steArray = new Throwable().getStackTrace();

        int selfIndex = -1;
        for (int i = 0; i < steArray.length; i++) {
            final String className = steArray[i].getClassName();
            if (className.equals(callerFQCN) || className.equals(SUPER)) {
                selfIndex = i;
                break;
            }
        }

        int found = -1;
        for (int i = selfIndex + 1; i < steArray.length; i++) {
            final String className = steArray[i].getClassName();
            if (!(className.equals(callerFQCN) || className.equals(SUPER))) {
                found = i;
                break;
            }
        }

        if (found != -1) {
            StackTraceElement ste = steArray[found];
            // setting the class name has the side effect of setting
            // the needToInferCaller variable to false.
            record.setSourceClassName(ste.getClassName());
            record.setSourceMethodName(ste.getMethodName());
        }
    }

    public void log(Marker marker, String callerFQCN, int level, String message, Object[] argArray, Throwable t) {
        Level julLevel = slf4jLevelIntToJULLevel(level);
        // the logger.isLoggable check avoids the unconditional
        // construction of location data for disabled log
        // statements. As of 2008-07-31, callers of this method
        // do not perform this check. See also
        // http://jira.qos.ch/browse/SLF4J-81
        if (logger.isLoggable(julLevel)) {
            log(callerFQCN, julLevel, message, t);
        }
    }

    private Level slf4jLevelIntToJULLevel(int slf4jLevelInt) {
        Level julLevel;
        switch (slf4jLevelInt) {
        case LocationAwareLogger.TRACE_INT:
            julLevel = Level.FINEST;
            break;
        case LocationAwareLogger.DEBUG_INT:
            julLevel = Level.FINE;
            break;
        case LocationAwareLogger.INFO_INT:
            julLevel = Level.INFO;
            break;
        case LocationAwareLogger.WARN_INT:
            julLevel = Level.WARNING;
            break;
        case LocationAwareLogger.ERROR_INT:
            julLevel = Level.SEVERE;
            break;
        default:
            throw new IllegalStateException("Level number " + slf4jLevelInt + " is not recognized.");
        }
        return julLevel;
    }

    /**
     * @since 1.7.15
     */
    public void log(LoggingEvent event) {
        Level julLevel = slf4jLevelIntToJULLevel(event.getLevel().toInt());
        if (logger.isLoggable(julLevel)) {
            LogRecord record = eventToRecord(event, julLevel);
            logger.log(record);
        }
    }

    private LogRecord eventToRecord(LoggingEvent event, Level julLevel) {
        String format = event.getMessage();
        Object[] arguments = event.getArgumentArray();
        FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
        if (ft.getThrowable() != null && event.getThrowable() != null) {
            throw new IllegalArgumentException("both last element in argument array and last argument are of type Throwable");
        }

        Throwable t = event.getThrowable();
        if (ft.getThrowable() != null) {
            t = ft.getThrowable();
            throw new IllegalStateException("fix above code");
        }

        LogRecord record = new LogRecord(julLevel, ft.getMessage());
        record.setLoggerName(event.getLoggerName());
        record.setMillis(event.getTimeStamp());
        record.setSourceClassName(EventConstants.NA_SUBST);
        record.setSourceMethodName(EventConstants.NA_SUBST);

        record.setThrown(t);
        return record;
    }
}
