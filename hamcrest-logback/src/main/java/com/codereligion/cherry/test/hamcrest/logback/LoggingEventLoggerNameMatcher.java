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
import org.hamcrest.Matcher;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * A matcher which expects the {@link ch.qos.logback.classic.spi.ILoggingEvent} to have a specific {@code loggerName}
 *
 * @author Sebastian Gr&ouml;bler
 * @since 17.03.2015
 */
public class LoggingEventLoggerNameMatcher extends DescribingTypeSafeMatcher<ILoggingEvent> {

    public static Matcher<ILoggingEvent> usesLogger(final String loggerName) {
        return new LoggingEventLoggerNameMatcher(loggerName);
    }

    public static Matcher<ILoggingEvent> usesLogger(final Class<?> type) {
        return new LoggingEventLoggerNameMatcher(type.getName());
    }

    private final String loggerName;

    /**
     * Creates a new instance using the given {@code loggerName}.
     *
     * @param loggerName the name of the logger to match the events logger with
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    public LoggingEventLoggerNameMatcher(final String loggerName) {
        checkArgument(loggerName != null, "loggerName must not be null.");
        this.loggerName = loggerName;
    }

    @Override
    public boolean matchesSafely(final ILoggingEvent event) {
        return event.getLoggerName().equals(loggerName);
    }

    @Override
    protected String getMessage(final Object object) {
        return "an ILoggingEvent for logger with name: " + object;
    }

    @Override
    protected Object getActualValue(final ILoggingEvent item) {
        return item.getLoggerName();
    }

    @Override
    protected Object getExpectedValue() {
        return loggerName;
    }
}
