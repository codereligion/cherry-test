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
     * to the given {@link ch.qos.logback.classic.Level}.
     *
     * @param level the level to match the event's log level against.
     * @return a new matcher
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    public static Matcher<ILoggingEvent> hasLevel(final Level level) {
        return new LoggingEventHasLevel(level, true, false);
    }

    /**
     * TODO document
     *
     * @param level
     * @return
     */
    public static Matcher<ILoggingEvent> doesNotHaveLevel(final Level level) {
        return new LoggingEventHasLevel(level, false, false);
    }

    /**
     * TODO document
     *
     * @param level
     * @return
     */
    public static Matcher<ILoggingEvent> withLevel(final Level level) {
        return new LoggingEventHasLevel(level, true, true);
    }

    private final Level level;

    /**
     * Creates a new instance using the given {@link ch.qos.logback.classic.Level}.
     *
     * @param level the level to match the event's log level against.
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    private LoggingEventHasLevel(final Level level, final boolean shouldMatch, final boolean isIterableMatcher) {
        super(shouldMatch, isIterableMatcher);
        checkArgument(level != null, "level must not be null.");
        this.level = level;
    }

    @Override
    protected boolean internalMatches(final ILoggingEvent event) {
        return event.getLevel().equals(level);
    }

    @Override
    protected void describePositiveMatch(final Description description) {
        description.appendText("an ILoggingEvent with level: " + level);
    }

    @Override
    protected void describeNegativeMatch(final Description description) {
        description.appendText("an ILoggingEvent with level other than: " + level);
    }
}
