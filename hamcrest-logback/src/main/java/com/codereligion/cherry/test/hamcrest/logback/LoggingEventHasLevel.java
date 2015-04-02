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

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * A matcher which expects the {@link ch.qos.logback.classic.spi.ILoggingEvent} to be of the specified {@code level}.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 17.03.2015
 */
public class LoggingEventHasLevel extends AbstractILoggingEventDescribingMatcher {

    /**
     * Creates a new matcher for {@link ch.qos.logback.classic.spi.ILoggingEvent ILoggingEvents} that only matches when the examined event has a log level equal
     * to the given {@link ch.qos.logback.classic.Level}. This matcher is doing the same assertion as {@link LoggingEventHasLevel#withLevel(Level)} (Level)},
     * with the difference that this matcher's output is optimized for usage on single events.
     * <p/>
     * Example usage: {@code assertThat(event, hasLevel(Level.ERROR));}
     * <p/>
     * Example output: {@code Expected: an ILoggingEvent with level: ERROR but: was ILoggingEvent{level=INFO, formattedMessage='some Message',
     * loggedBy='aLogger', throwable=null}}
     *
     * @param level the level to match the event's log level against
     * @return a new matcher
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    public static Matcher<ILoggingEvent> hasLevel(final Level level) {
        return new LoggingEventHasLevel(level, false, false);
    }

    /**
     * Creates a new matcher for {@link ch.qos.logback.classic.spi.ILoggingEvent ILoggingEvents} that only matches when the examined event does not have a log
     * level equal to the given {@link ch.qos.logback.classic.Level}. This matcher is the negation of {@link LoggingEventHasLevel#hasLevel(Level)}
     * (Level)}. It is recommended to use this specific matcher instead of just combining the other matcher with {@link org.hamcrest.CoreMatchers#not(Matcher)}
     * because of the improved error output of this matcher.
     * <p/>
     * Example usage: {@code assertThat(event, doesNotHaveLevel(Level.ERROR));}
     * <p/>
     * Example output: {@code Expected: an ILoggingEvent with level other than: ERROR but: was ILoggingEvent{level=ERROR, formattedMessage='some Message',
     * loggedBy='aLogger', throwable=null}}
     *
     * @param level the level to match the event's log level against
     * @return a new matcher
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    public static Matcher<ILoggingEvent> doesNotHaveLevel(final Level level) {
        return new LoggingEventHasLevel(level, true, false);
    }

    /**
     * Creates a new matcher for {@link ch.qos.logback.classic.spi.ILoggingEvent ILoggingEvents} that only matches when the examined event has a log level equal
     * to the given {@link ch.qos.logback.classic.Level}. This matcher is doing the same assertion as {@link LoggingEventHasLevel#hasLevel(Level)}, with the
     * difference that this matcher's output is optimized for usage on iterables of events.
     * <p/>
     * Example usage: {@code assertThat(events, hasItem(withLevel(Level.ERROR)));}
     * <p/>
     * Example output: {@code Expected: an iterable containing an ILoggingEvent with level: ERROR but: iterable contained ILoggingEvent{level=INFO,
     * formattedMessage='some Message', loggedBy='aLogger', throwable=null}}
     *
     * @param level the level to match the event's log level against
     * @return a new matcher
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    public static Matcher<ILoggingEvent> withLevel(final Level level) {
        return new LoggingEventHasLevel(level, false, true);
    }

    private final Level level;

    /**
     * Creates a new instance using the given {@link ch.qos.logback.classic.Level} for matching and the values supplied for {@code negated} and {@code
     * usedOnIterable} to configure the behaviour of the description generation.
     *
     * @param level          the level to match the event's log level against
     * @param negated        if the matcher is negated
     * @param usedOnIterable if the matcher is used in conjunction with an iterable matcher
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    private LoggingEventHasLevel(final Level level, final boolean negated, final boolean usedOnIterable) {
        super(negated, usedOnIterable);
        checkArgument(level != null, "level must not be null.");
        this.level = level;
    }

    @Override
    protected boolean internalMatches(final ILoggingEvent event) {
        return event.getLevel().equals(level);
    }

    @Override
    protected void describeExpectation(final Description description) {
        description.appendText("an ILoggingEvent with level: " + level);
    }

    @Override
    protected void describeNegatedExpectation(final Description description) {
        description.appendText("an ILoggingEvent with level other than: " + level);
    }
}
