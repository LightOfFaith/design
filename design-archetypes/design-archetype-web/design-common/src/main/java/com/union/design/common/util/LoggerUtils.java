package com.union.design.common.util;

import org.slf4j.Logger;

/**
 * @date 2021/5/16
 */
public class LoggerUtils {

    public static final String ERROR = "ERROR";

    public static final String WARN = "WARN";

    public static final String INFO = "INFO";

    public static final String DEBUG = "DEBUG";

    public static final String TRACE = "TRACE";

    /**
     * Print if log debug level is enabled.
     *
     * @param logger logger
     * @param msg      log message
     * @param args   arguments
     */
    public static void printIfDebugEnabled(Logger logger, String msg, Object... args) {
        if (logger.isDebugEnabled()) {
            logger.debug(msg, args);
        }
    }

    /**
     * Print if log info level is enabled.
     *
     * @param logger logger
     * @param msg      log message
     * @param args   arguments
     */
    public static void printIfInfoEnabled(Logger logger, String msg, Object... args) {
        if (logger.isInfoEnabled()) {
            logger.info(msg, args);
        }
    }

    /**
     * Print if log trace level is enabled.
     *
     * @param logger logger
     * @param msg      log message
     * @param args   arguments
     */
    public static void printIfTraceEnabled(Logger logger, String msg, Object... args) {
        if (logger.isTraceEnabled()) {
            logger.trace(msg, args);
        }
    }

    /**
     * Print if log warn level is enabled.
     *
     * @param logger logger
     * @param msg      log message
     * @param args   arguments
     */
    public static void printIfWarnEnabled(Logger logger, String msg, Object... args) {
        if (logger.isWarnEnabled()) {
            logger.warn(msg, args);
        }
    }

    /**
     * Print if log error level is enabled.
     *
     * @param logger logger
     * @param msg      log message
     * @param args   arguments
     */
    public static void printIfErrorEnabled(Logger logger, String msg, Object... args) {
        if (logger.isErrorEnabled()) {
            logger.error(msg, args);
        }
    }

}
