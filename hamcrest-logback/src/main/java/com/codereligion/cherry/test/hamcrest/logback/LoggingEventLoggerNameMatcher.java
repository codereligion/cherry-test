package com.codereligion.cherry.test.hamcrest.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * A matcher which expects the {@link ch.qos.logback.classic.spi.ILoggingEvent} to have a specific {@code loggerName}
 *
 * @author Sebastian Gr&ouml;bler
 * @since 17.03.2015
 */
public class LoggingEventLoggerNameMatcher extends TypeSafeMatcher<ILoggingEvent> {

    private final String loggerName;

    public LoggingEventLoggerNameMatcher(final String loggerName) {
        this.loggerName = loggerName;
    }

    @Override
    public boolean matchesSafely(final ILoggingEvent event) {
        return event.getLoggerName().equals(loggerName);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("a LoggingEvent for logger with name: '" + loggerName + "'");
    }
}
