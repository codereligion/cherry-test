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
import ch.qos.logback.classic.spi.IThrowableProxy;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * A matcher which expects the {@link ch.qos.logback.classic.spi.ILoggingEvent} to contain the specified {@link Throwable}.
 */
public class LoggingEventThrowableMatcher extends TypeSafeMatcher<ILoggingEvent> {

    private Throwable throwable;

    public LoggingEventThrowableMatcher(final Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public boolean matchesSafely(final ILoggingEvent event) {

        final IThrowableProxy throwableProxy = event.getThrowableProxy();
        if (throwableProxy == null) {
            return false;
        }

        final boolean classesMatch = throwableProxy.getClassName().equals(throwable.getClass().getName());
        final boolean messagesMatch = messageMatch(throwableProxy.getMessage(), throwable.getMessage());
        return classesMatch && messagesMatch;
    }

    private boolean messageMatch(final String first, final String second) {
        return first == second || (first != null && first.equals(second));
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("a LoggingEvent containing '" + throwable + "'");
    }
}
