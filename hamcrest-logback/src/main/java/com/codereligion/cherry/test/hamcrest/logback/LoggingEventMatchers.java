package com.codereligion.cherry.test.hamcrest.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.hamcrest.Matcher;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.not;

/**
 * Matchers for working with {@link ch.qos.logback.classic.spi.ILoggingEvent ILoggingEvents}.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 17.03.2015
 */
public final class LoggingEventMatchers {

    private LoggingEventMatchers() {
        // disallow public instantiation
    }

    public static Matcher<Iterable<? super ILoggingEvent>> hasItemWhichContainsThrowable(final Throwable throwable) {
        return hasItem(new LoggingEventThrowableMatcher(throwable));
    }

    public static Matcher<Iterable<? super ILoggingEvent>> hasItemWhichContainsMessage(final String message) {
        return hasItem(new LoggingEventMessageMatcher(containsString(message)));
    }

    public static Matcher<Iterable<? super ILoggingEvent>> hasItemWhichContainsMessage(final String message, final Object... args) {
        final String formattedMessage = String.format(message, args);
        return hasItem(new LoggingEventMessageMatcher(containsString(formattedMessage)));
    }

    public static Matcher<Iterable<? super ILoggingEvent>> hasItemWhichContainsMessage(final Matcher<String> matcher) {
        return hasItem(new LoggingEventMessageMatcher(matcher));
    }

    public static Matcher<Iterable<? super ILoggingEvent>> hasNoItemWhichContainsMessage(final String message) {
        return hasItem(not(new LoggingEventMessageMatcher(containsString(message))));
    }

    public static Matcher<Iterable<ILoggingEvent>> hasOnlyItemsOfLevel(final Level level) {
        return everyItem(new LoggingEventLevelMatcher(level));
    }

    public static Matcher<ILoggingEvent> containsMessage(final String message) {
        return new LoggingEventMessageMatcher(containsString(message));
    }

    public static Matcher<ILoggingEvent> containsMessage(final String message, final Object... args) {
        final String formattedMessage = String.format(message, args);
        return new LoggingEventMessageMatcher(containsString(formattedMessage));
    }

    public static Matcher<ILoggingEvent> usesLogger(final String loggerName) {
        return new LoggingEventLoggerNameMatcher(loggerName);
    }

    public static Matcher<ILoggingEvent> usesLogger(final Class<?> type) {
        return new LoggingEventLoggerNameMatcher(type.getName());
    }

    public static Matcher<ILoggingEvent> hasLevel(final Level level) {
        return new LoggingEventLevelMatcher(level);
    }

    public static Matcher<ILoggingEvent> containsMessage(final Matcher<String> matcher) {
        return new LoggingEventMessageMatcher(matcher);
    }

    public static Matcher<ILoggingEvent> doesNotContainMessage(final String message) {
        return not(new LoggingEventMessageMatcher(containsString(message)));
    }

    public static Matcher<ILoggingEvent> containsThrowable(final Throwable throwable) {
        return new LoggingEventThrowableMatcher(throwable);
    }

    public static Matcher<ILoggingEvent> doesNotContainThrowable(final Throwable throwable) {
        return not(new LoggingEventThrowableMatcher(throwable));
    }
}
