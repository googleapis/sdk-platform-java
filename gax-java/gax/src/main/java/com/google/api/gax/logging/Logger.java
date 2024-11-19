package com.google.api.gax.logging;

import java.util.function.Supplier;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

public final class Logger {
  private final org.slf4j.Logger log;

  Logger(org.slf4j.Logger log) {
    this.log = log;
  }

  public org.slf4j.Logger logger() {
    return log;
  }

  /**
   * Checks if info is enabled and if so logs the supplied message
   *
   * @param msg - supplier for the log message
   */
  public void info(Supplier<String> msg) {
    if (log.isInfoEnabled()) {
      log.info(msg.get());
    }
  }

  /**
   * Checks if info is enabled and if so logs the supplied message and exception
   *
   * @param msg - supplier for the log message
   * @param throwable - a throwable to log
   */
  public void info(Supplier<String> msg, Throwable throwable) {
    if (log.isInfoEnabled()) {
      log.info(msg.get(), throwable);
    }
  }

  /**
   * Checks if error is enabled and if so logs the supplied message
   *
   * @param msg - supplier for the log message
   */
  public void error(Supplier<String> msg) {
    if (log.isErrorEnabled()) {
      log.error(msg.get());
    }
  }

  /**
   * Checks if error is enabled and if so logs the supplied message and exception
   *
   * @param msg - supplier for the log message
   * @param throwable - a throwable to log
   */
  public void error(Supplier<String> msg, Throwable throwable) {
    if (log.isErrorEnabled()) {
      log.error(msg.get(), throwable);
    }
  }

  /**
   * Checks if debug is enabled and if so logs the supplied message
   *
   * @param msg - supplier for the log message
   */
  public void debug(Supplier<String> msg) {
    if (log.isDebugEnabled()) {
      log.debug(msg.get());
    }
  }

  /**
   * Checks if debug is enabled and if so logs the supplied message and exception
   *
   * @param msg - supplier for the log message
   * @param throwable - a throwable to log
   */
  public void debug(Supplier<String> msg, Throwable throwable) {
    if (log.isDebugEnabled()) {
      log.debug(msg.get(), throwable);
    }
  }

  /**
   * Checks if warn is enabled and if so logs the supplied message
   *
   * @param msg - supplier for the log message
   */
  public void warn(Supplier<String> msg) {
    if (log.isWarnEnabled()) {
      log.warn(msg.get());
    }
  }

  /**
   * Checks if warn is enabled and if so logs the supplied message and exception
   *
   * @param msg - supplier for the log message
   * @param throwable - a throwable to log
   */
  public void warn(Supplier<String> msg, Throwable throwable) {
    if (log.isWarnEnabled()) {
      log.warn(msg.get(), throwable);
    }
  }

  /**
   * Checks if trace is enabled and if so logs the supplied message
   *
   * @param msg - supplier for the log message
   */
  public void trace(Supplier<String> msg) {
    if (log.isTraceEnabled()) {
      log.trace(msg.get());
    }
  }

  /**
   * Checks if trace is enabled and if so logs the supplied message and exception
   *
   * @param msg - supplier for the log message
   * @param throwable - a throwable to log
   */
  public void trace(Supplier<String> msg, Throwable throwable) {
    if (log.isTraceEnabled()) {
      log.trace(msg.get(), throwable);
    }
  }

  /**
   * Determines if the provided log-level is enabled.
   *
   * @param logLevel the SLF4J log level enum
   * @return whether that level is enabled
   */
  public boolean isLoggingLevelEnabled(Level logLevel) {
    switch (logLevel) {
      case TRACE:
        return log.isTraceEnabled();
      case DEBUG:
        return log.isDebugEnabled();
      case INFO:
        return log.isInfoEnabled();
      case WARN:
        return log.isWarnEnabled();
      case ERROR:
        return log.isErrorEnabled();
      default:
        throw new IllegalStateException("Unsupported log level: " + logLevel);
    }
  }

  /**
   * Log a message at the given log level (if it is enabled).
   *
   * @param logLevel the SLF4J log level
   * @param msg supplier for the log message
   */
  public void log(Level logLevel, Supplier<String> msg) {
    switch (logLevel) {
      case TRACE:
        trace(msg);
        break;
      case DEBUG:
        debug(msg);
        break;
      case INFO:
        info(msg);
        break;
      case WARN:
        warn(msg);
        break;
      case ERROR:
        error(msg);
        break;
      default:
        throw new IllegalStateException("Unsupported log level: " + logLevel);
    }
  }

  /**
   * Static factory to get a logger instance for a given class
   *
   * @param clz - class to get the logger for
   * @return a Logger instance
   */
  public static Logger loggerFor(Class<?> clz) {
    return new Logger(LoggerFactory.getLogger(clz));
  }
}
