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
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.not;

/**
 * A matcher which expects the {@link ch.qos.logback.classic.spi.ILoggingEvent} to contain the specified {@link Throwable}.
 * <p/>
 * <p/>
 * This matcher returns true when: <li> <ul>the given throwable is {@code null} and the logging event does not have an associated throwable</ul> <ul>the given
 * throwable matches in class and message the throwable which is associated with the logging event</ul> </li>
 *
 * @author Sebastian Gr&ouml;bler
 * @since 17.03.2015
 */
public class LoggingEventThrowableMatcher extends TypeSafeMatcher<ILoggingEvent> {

    public static Matcher<Iterable<? super ILoggingEvent>> hasItemWhichContainsThrowable(final Throwable throwable) {
        return hasItem(new LoggingEventThrowableMatcher(throwable));
    }

    public static Matcher<ILoggingEvent> containsThrowable(final Throwable throwable) {
        return new LoggingEventThrowableMatcher(throwable);
    }

    public static Matcher<ILoggingEvent> doesNotContainThrowable(final Throwable throwable) {
        return not(new LoggingEventThrowableMatcher(throwable));
    }

    private Throwable throwable;

    /**
     * Creates a new instance using the given {@link java.lang.Throwable}.
     *
     * @param throwable the throwable to match the events {@code throwableProxy} with
     */
    public LoggingEventThrowableMatcher(final Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public boolean matchesSafely(final ILoggingEvent event) {

        final IThrowableProxy throwableProxy = event.getThrowableProxy();
        if (throwableProxy == null || throwable == null) {
            return throwableProxy == throwable;
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
