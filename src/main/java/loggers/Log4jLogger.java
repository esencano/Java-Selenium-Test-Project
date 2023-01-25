package loggers;

import io.qameta.allure.Step;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class Log4jLogger {
        private static final Logger logger = LogManager.getLogger(Log4jLogger.class);

        private Log4jLogger() {

        }
        @Step("LOG_INFO")
        public static void info(String msg) {
            logger.info(msg);
        }
        @Step("LOG_DEBUG")
        public static void debug(String msg) {logger.debug(msg);}
        @Step("LOG_TRACE")
        public static void trace(String msg) {logger.trace(msg);}
}
