/**
 * Copyright 2015 www.codereligion.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codereligion.cherry.test.hamcrest.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * A matcher which expects the {@link ch.qos.logback.classic.spi.ILoggingEvent} to have a specific {@code loggerName}
 *
 * @author Sebastian Gr&ouml;bler
 * @since 17.03.2015
 */
public class LoggingEventLoggedBy extends AbstractILoggingEventDescribingMatcher {

    /**
     * Creates a new matcher for {@link ch.qos.logback.classic.spi.ILoggingEvent ILoggingEvents} that only matches when the examined event has a {@code
     * loggerName} equal to the given one.
     *
     * @param loggerName the name of the loggerName to match the event's logger with
     * @return a new matcher
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    public static Matcher<ILoggingEvent> wasLoggedBy(final String loggerName) {
        return new LoggingEventLoggedBy(loggerName, false, false);
    }

    /**
     * TODO document
     *
     * @param loggerName
     * @return
     */
    public static Matcher<ILoggingEvent> wasNotLoggedBy(final String loggerName) {
        return new LoggingEventLoggedBy(loggerName, true, false);
    }

    /**
     * TODO document
     *
     * @param loggerName
     * @return
     */
    public static Matcher<ILoggingEvent> loggedBy(final String loggerName) {
        return new LoggingEventLoggedBy(loggerName, false, true);
    }

    /**
     * Creates a new matcher for {@link ch.qos.logback.classic.spi.ILoggingEvent ILoggingEvents} that only matches when the examined event has a {@code
     * loggerName} equal to the name of the given class.
     *
     * @param loggerType the {@link java.lang.Class} of which the name will be used to match the event's logger with
     * @return a new matcher
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    public static Matcher<ILoggingEvent> wasLoggedBy(final Class<?> loggerType) {
        return new LoggingEventLoggedBy(loggerType, false, false);
    }

    /**
     * TODO document
     *
     * @param loggerType
     * @return
     */
    public static Matcher<ILoggingEvent> wasNotLoggedBy(final Class<?> loggerType) {
        return new LoggingEventLoggedBy(loggerType, true, false);
    }

    /**
     * TODO document
     *
     * @param loggerType
     * @return
     */
    public static Matcher<ILoggingEvent> loggedBy(final Class<?> loggerType) {
        return new LoggingEventLoggedBy(loggerType, false, true);
    }

    private final String loggerName;

    /**
     * TODO document
     * Creates a new instance using the given {@code loggerName}.
     *
     * @param loggerName the name of the loggerName to match the event's loggerName with
     * @param negated
     * @param usedOnIterable
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    private LoggingEventLoggedBy(final String loggerName, final boolean negated, final boolean usedOnIterable) {
        super(negated, usedOnIterable);
        checkArgument(loggerName != null, "loggerName must not be null.");
        this.loggerName = loggerName;
    }

    /**
     * TODO document
     *
     * Creates a new instance using the name of the given {@code loggerType}.
     *
     * @param loggerType the {@link java.lang.Class} of which the name will be used to match the event's logger with
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    private LoggingEventLoggedBy(final Class<?> loggerType, final boolean netaged, final boolean usedOnIterable) {
        super(netaged, usedOnIterable);
        checkArgument(loggerType != null, "loggerType must not be null.");
        this.loggerName = loggerType.getName();
    }


    @Override
    protected boolean internalMatches(final ILoggingEvent event) {
        return event.getLoggerName().equals(loggerName);
    }

    @Override
    protected void describeExpectation(final Description description) {
        description.appendText("an ILoggingEvent logged by: " + loggerName);
    }

    @Override
    protected void describeNegatedExpectation(final Description description) {
        description.appendText("an ILoggingEvent not logged by: " + loggerName);
    }
}
