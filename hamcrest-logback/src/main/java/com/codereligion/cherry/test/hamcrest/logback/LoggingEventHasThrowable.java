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
import com.google.common.base.Objects;
import javax.annotation.Nullable;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

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
public class LoggingEventHasThrowable extends AbstractILoggingEventDescribingMatcher {

    public static Matcher<ILoggingEvent> hasThrowable(final Throwable throwable) {
        return new LoggingEventHasThrowable(throwable, true, false);
    }

    public static Matcher<ILoggingEvent> doesNotHaveThrowable(final Throwable throwable) {
        return new LoggingEventHasThrowable(throwable, false, false);
    }

    public static Matcher<ILoggingEvent> withThrowable(final Throwable throwable) {
        return new LoggingEventHasThrowable(throwable, true, true);
    }

    private final Throwable throwable;

    /**
     * Creates a new instance using the given {@link java.lang.Throwable}.
     *
     * @param throwable the throwable to match the event's {@code throwableProxy} with
     */
    private LoggingEventHasThrowable(@Nullable final Throwable throwable, final boolean shouldMatch, final boolean isIterableMatcher) {
        super(shouldMatch, isIterableMatcher);
        this.throwable = throwable;
    }

    @Override
    protected boolean internalMatches(final ILoggingEvent event) {
        final IThrowableProxy throwableProxy = event.getThrowableProxy();
        if (throwableProxy == null || throwable == null) {
            return throwableProxy == throwable;
        }

        final boolean classesMatch = throwableProxy.getClassName().equals(throwable.getClass().getName());
        final boolean messagesMatch = Objects.equal(throwableProxy.getMessage(), throwable.getMessage());
        return classesMatch && messagesMatch;
    }

    @Override
    protected void describePositiveMatch(final Description description) {
        description.appendText("an ILoggingEvent with a throwable matching: " + toString(throwable));
    }

    @Override
    protected void describeNegativeMatch(final Description description) {
        description.appendText("an ILoggingEvent with a throwable matching: " + toString(throwable));
    }

    private String toString(final Throwable throwable) {
        if (throwable == null) {
            return "null";
        }

        return throwable.getClass().getName() + "[" + throwable.getMessage() + "]";
    }
}