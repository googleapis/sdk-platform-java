package com.google.api.gax.logging;

import com.google.gson.JsonObject;
import java.util.logging.Level;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

public class LoggingUtils {

  private static final java.util.logging.Logger LOGGER =
      java.util.logging.Logger.getLogger(LoggingUtils.class.getName());

  private LoggingUtils() {}

  public static Logger getLogger(Class<?> clazz) {

    Logger logger;

    if (isLoggingEnabled()) {

      ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
      if (loggerFactory != null && !(loggerFactory instanceof org.slf4j.helpers.NOPLoggerFactory)) {
        // An SLF4j binding is present
        // You can get the logger and use it:
        logger = LoggerFactory.getLogger(clazz);
        logger.debug("SLF4J BINDING FOUND!!!!!");
        // ...
      } else {
        // No SLF4j binding found
        // Implement your fallback logic here
        logger = new JulWrapperLogger(clazz.getName());
        logger.info("No SLF4J providers were found, fall back to JUL.");
      }
    } else {
      //  use SLF4j's NOP logger regardless of bindings
      logger = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
    }
    return logger;
  }

  public static boolean isLoggingEnabled() {
    String enableLogging = System.getenv("GOOGLE_SDK_JAVA_LOGGING");
    LOGGER.info("GOOGLE_SDK_JAVA_LOGGING=" + enableLogging);
    return "true".equalsIgnoreCase(enableLogging);
  }

  public static JsonObject mergeJsonObject(JsonObject jsonObject1, JsonObject jsonObject2) {
    JsonObject mergedObject = jsonObject1.deepCopy();
    jsonObject2.entrySet().forEach(entry -> mergedObject.add(entry.getKey(), entry.getValue()));
    return mergedObject;
  }
  // JulWrapperLogger implementation
  static class JulWrapperLogger implements Logger {

    private final java.util.logging.Logger julLogger;

    public JulWrapperLogger(String name) {
      this.julLogger = java.util.logging.Logger.getLogger(name);
    }

    @Override
    public String getName() {
      return julLogger.getName();
    }

    @Override
    public boolean isTraceEnabled() {
      return julLogger.isLoggable(java.util.logging.Level.FINEST);
    }

    @Override
    public void trace(String msg) {
      julLogger.log(java.util.logging.Level.FINEST, msg);
    }

    @Override
    public void trace(String s, Object o) {}

    @Override
    public void trace(String s, Object o, Object o1) {}

    @Override
    public void trace(String s, Object... objects) {}

    @Override
    public void trace(String s, Throwable throwable) {}

    @Override
    public boolean isTraceEnabled(Marker marker) {
      return false;
    }

    @Override
    public void trace(Marker marker, String s) {}

    @Override
    public void trace(Marker marker, String s, Object o) {}

    @Override
    public void trace(Marker marker, String s, Object o, Object o1) {}

    @Override
    public void trace(Marker marker, String s, Object... objects) {}

    @Override
    public void trace(Marker marker, String s, Throwable throwable) {}

    @Override
    public boolean isDebugEnabled() {
      return julLogger.isLoggable(Level.FINE);
    }

    @Override
    public void debug(String msg) {

      if (isDebugEnabled()) {
        julLogger.log(java.util.logging.Level.FINE, msg);
      }
    }

    @Override
    public void debug(String format, Object arg) {
      if (isDebugEnabled()) {
        FormattingTuple ft = MessageFormatter.format(format, arg);
        julLogger.log(Level.FINE, ft.getMessage());
      }
    }

    @Override
    public void debug(String s, Object o, Object o1) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void debug(String s, Object... objects) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void debug(String s, Throwable throwable) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void debug(Marker marker, String s) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void debug(Marker marker, String s, Object o) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void debug(Marker marker, String s, Object o, Object o1) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void debug(Marker marker, String s, Object... objects) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void debug(Marker marker, String s, Throwable throwable) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public boolean isInfoEnabled() {
      return julLogger.isLoggable(Level.INFO);
    }

    @Override
    public void info(String msg) {
      if (isInfoEnabled()) {
        julLogger.log(java.util.logging.Level.INFO, msg);
      }
    }

    @Override
    public void info(String format, Object arg) {
      if (isInfoEnabled()) {
        FormattingTuple ft = MessageFormatter.format(format, arg);
        julLogger.log(java.util.logging.Level.INFO, ft.getMessage());
      }
    }

    @Override
    public void info(String s, Object o, Object o1) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void info(String s, Object... objects) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void info(String s, Throwable throwable) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
      return true;
    }

    @Override
    public void info(Marker marker, String s) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void info(Marker marker, String s, Object o) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void info(Marker marker, String s, Object o, Object o1) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void info(Marker marker, String s, Object... objects) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void info(Marker marker, String s, Throwable throwable) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public boolean isWarnEnabled() {
      return true;
    }

    @Override
    public void warn(String msg) {
      julLogger.log(Level.WARNING, msg);
    }

    @Override
    public void warn(String s, Object o) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void warn(String s, Object... objects) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void warn(String s, Object o, Object o1) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void warn(String s, Throwable throwable) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
      return false;
    }

    @Override
    public void warn(Marker marker, String s) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void warn(Marker marker, String s, Object o) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void warn(Marker marker, String s, Object o, Object o1) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void warn(Marker marker, String s, Object... objects) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void warn(Marker marker, String s, Throwable throwable) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public boolean isErrorEnabled() {
      return false;
    }

    @Override
    public void error(String s) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void error(String s, Object o) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void error(String s, Object o, Object o1) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void error(String s, Object... objects) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void error(String s, Throwable throwable) {
      throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
      return false;
    }

    @Override
    public void error(Marker marker, String s) {}

    @Override
    public void error(Marker marker, String s, Object o) {}

    @Override
    public void error(Marker marker, String s, Object o, Object o1) {}

    @Override
    public void error(Marker marker, String s, Object... objects) {}

    @Override
    public void error(Marker marker, String s, Throwable throwable) {}
  }
}
