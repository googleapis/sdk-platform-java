package com.google.api.gax.logging;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.event.Level;

public class LoggingUtils {

  public static Logger getLogger(Class<?> clazz) {
    return LoggerFactory.getLogger(clazz);
  }

  public static boolean isLoggingEnabled() {
    String enableLogging = System.getenv("GOOGLE_SDK_JAVA_LOGGING");
    return "true".equalsIgnoreCase(enableLogging);
  }

  public static void log(Logger logger, Level level, String msg, Map<String, String> contextMap) {

    if (LoggingUtils.isLoggingEnabled()) {
      MDC.setContextMap(contextMap);
      switch (level) {
        case TRACE:
          logger.trace(msg);
          break;
        case DEBUG:
          logger.debug(msg);
          break;
        case INFO:
          logger.info(msg);
          break;
        case WARN:
          logger.warn(msg);
          break;
        case ERROR:
          logger.error(msg);
          break;
        default:
          logger.info(msg);
          // Default to INFO level if level is invalid
          break;
      }
      MDC.clear();
    }
  }
}
