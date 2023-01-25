package loggers;

import org.testng.Reporter;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

public class TestNGReportAppender extends AppenderSkeleton {

    @Override
    protected void append(final LoggingEvent event) {
        Reporter.log(eventToString(event));
    }

    private String eventToString(final LoggingEvent event) {
        String message = layout.format(event);
        if(layout.ignoresThrowable()) {
            final String[] throwableStrRep = event.getThrowableStrRep();
            if (throwableStrRep != null) {
                for (final String value : throwableStrRep) {
                    message += String.join(Layout.LINE_SEP, value);
                }
            }
        }
        return message;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return true;
    }
}