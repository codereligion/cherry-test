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
import org.hamcrest.TypeSafeMatcher;

/**
 * A matcher which expects the {@link ch.qos.logback.classic.spi.ILoggingEvent} to have a specific {@code loggerName}
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
