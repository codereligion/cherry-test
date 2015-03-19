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
import org.hamcrest.Matcher;
import static com.google.common.base.Preconditions.checkArgument;
import static org.hamcrest.CoreMatchers.everyItem;

/**
 * A matcher which expects the {@link ch.qos.logback.classic.spi.ILoggingEvent} to be of the specified {@code level}.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 17.03.2015
 */
public class LoggingEventLevelMatcher extends DescribingTypeSafeMatcher<ILoggingEvent> {

    /**
     * Creates a new matcher for {@link ch.qos.logback.classic.spi.ILoggingEvent ILoggingEvents} that only matches when the examined event has a log level equal
     * to the given {@link ch.qos.logback.classic.Level}.
     *
     * @param level the level to match the event's log level against.
     * @return a new matcher
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    public static Matcher<ILoggingEvent> hasLevel(final Level level) {
        return new LoggingEventLevelMatcher(level);
    }

    /**
     * Creates a new matcher for {@link Iterable Iterables} that only matches when a single pass over the examined {@link Iterable} yields items that are all
     * matched by the {@link LoggingEventLevelMatcher} using the given {@link ch.qos.logback.classic.Level}.
     *
     * @param level the level to match the iterable items against
     * @return a new matcher
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    public static Matcher<Iterable<ILoggingEvent>> everyItemHasLevel(final Level level) {
        return everyItem(hasLevel(level));
    }

    private final Level level;

    /**
     * Creates a new instance using the given {@link ch.qos.logback.classic.Level}.
     *
     * @param level the level to match the event's log level against.
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    private LoggingEventLevelMatcher(final Level level) {
        checkArgument(level != null, "level must not be null.");
        this.level = level;
    }

    @Override
    public boolean matchesSafely(final ILoggingEvent event) {
        return event.getLevel().equals(level);
    }

    @Override
    protected String getMessage(final Object object) {
        return "an ILoggingEvent with level: " + object;
    }

    @Override
    protected Object getActualValue(final ILoggingEvent item) {
        return item.getLevel();
    }

    @Override
    protected Object getExpectedValue() {
        return level;
    }
}
