package org.slf4j.impl;

import org.slf4j.Logger;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;

import com.bugfender.sdk.Bugfender;

/**
 * A wrapper over Bugfender in conformity with the {@link Logger} interface.
 */
public final class BugfenderLoggerAdapter extends MarkerIgnoringBase {

	private static final long serialVersionUID = -2064252262110589508L;

    BugfenderLoggerAdapter(String name) {
        this.name = name;
    }

    public boolean isTraceEnabled() {
        return true;
    }

    public void trace(String msg) {
        logd(msg, null);
    }

    public void trace(String format, Object arg) {
        FormattingTuple ft = MessageFormatter.format(format, arg);
        logd(ft.getMessage(), ft.getThrowable());
    }

    public void trace(String format, Object arg1, Object arg2) {
        FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
        logd(ft.getMessage(), ft.getThrowable());
    }

    public void trace(String format, Object... argArray) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
        logd(ft.getMessage(), ft.getThrowable());
    }

    public void trace(String msg, Throwable t) {
        logd(msg, t);
    }

    public boolean isDebugEnabled() {
        return true;
    }

    public void debug(String msg) {
        logd(msg, null);
    }

    public void debug(String format, Object arg) {
        FormattingTuple ft = MessageFormatter.format(format, arg);
        logd(ft.getMessage(), ft.getThrowable());
    }

    public void debug(String format, Object arg1, Object arg2) {
        FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
        logd(ft.getMessage(), ft.getThrowable());
    }

    public void debug(String format, Object... argArray) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
        logd(ft.getMessage(), ft.getThrowable());
    }

    public void debug(String msg, Throwable t) {
        logd(msg, t);
    }

    public boolean isInfoEnabled() {
        return true;
    }

    public void info(String msg) {
        logd(msg, null);
    }

    public void info(String format, Object arg) {
        FormattingTuple ft = MessageFormatter.format(format, arg);
        logd(ft.getMessage(), ft.getThrowable());
    }

    public void info(String format, Object arg1, Object arg2) {
        FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
        logd(ft.getMessage(), ft.getThrowable());
    }

    public void info(String format, Object... argArray) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
        logd(ft.getMessage(), ft.getThrowable());
    }

    public void info(String msg, Throwable t) {
        logd(msg, t);
    }

    public boolean isWarnEnabled() {
        return true;
    }

    public void warn(String msg) {
        logw(msg, null);
    }

    public void warn(String format, Object arg) {
        FormattingTuple ft = MessageFormatter.format(format, arg);
        logw(ft.getMessage(), ft.getThrowable());
    }
    public void warn(String format, Object arg1, Object arg2) {
        FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
        logw(ft.getMessage(), ft.getThrowable());
    }

    public void warn(String format, Object... argArray) {
    	FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
        logw(ft.getMessage(), ft.getThrowable());
    }

    public void warn(String msg, Throwable t) {
        logw(msg, t);
    }

    public boolean isErrorEnabled() {
        return true;
    }

    public void error(String msg) {
        loge(msg, null);
    }

    public void error(String format, Object arg) {
        FormattingTuple ft = MessageFormatter.format(format, arg);
        loge(ft.getMessage(), ft.getThrowable());
    }

    public void error(String format, Object arg1, Object arg2) {
        FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
        loge(ft.getMessage(), ft.getThrowable());
    }

    public void error(String format, Object... arguments) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
        loge(ft.getMessage(), ft.getThrowable());
    }

    public void error(String msg, Throwable t) {
        loge(msg, t);
    }

    private void logd(String msg, Throwable t) {
        if(t != null) {
            msg += "\n" + t.toString();
        }
        Bugfender.d(this.name, msg);
    }

    private void logw(String msg, Throwable t) {
        if(t != null) {
            msg += "\n" + t.toString();
        }
        Bugfender.w(this.name, msg);
    }

    private void loge(String msg, Throwable t) {
        if(t != null) {
            msg += "\n" + t.toString();
        }
        Bugfender.e(this.name, msg);
    }

}
