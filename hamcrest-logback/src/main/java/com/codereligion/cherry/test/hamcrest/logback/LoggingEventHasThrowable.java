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
 * This matcher returns true when: <li> <ul>the given throwable is {@code null} and the logging event does not have an associated throwable</ul> <ul>the given
 * throwable matches in class and message the throwable which is associated with the logging event</ul> </li>
 *
 * @author Sebastian Gr&ouml;bler
 * @since 17.03.2015
 */
public class LoggingEventHasThrowable extends AbstractILoggingEventDescribingMatcher {

    /**
     * Creates a new matcher for {@link ch.qos.logback.classic.spi.ILoggingEvent ILoggingEvents} that only matches when the examined event has a {@link
     * Throwable} associated to it which matches the given one. This matcher is doing the same assertion as {@link
     * LoggingEventHasThrowable#withThrowable(Throwable)}, with the difference that this matcher's output is optimized for usage on single events.
     * <p/>
     * Example usage: {@code assertThat(event, hasThrowable(new NullPointerException("opsi!)));}
     * <p/>
     * Example output: {@code Expected: an ILoggingEvent with a throwable matching: java.lang.NullPointerException{message='opsi!'} but: was
     * ILoggingEvent{level=ERROR, formattedMessage='some Message', loggedBy=SomeLogger, throwable=java.lang.NullPointerException{message='nope!'}}}
     *
     * @param throwable the throwable to match the event's {@code throwableProxy} with
     * @return a new matcher
     */
    public static Matcher<ILoggingEvent> hasThrowable(final Throwable throwable) {
        return new LoggingEventHasThrowable(throwable, false, false);
    }

    /**
     * Creates a new matcher for {@link ch.qos.logback.classic.spi.ILoggingEvent ILoggingEvents} that only matches when the examined event does not have a
     * {@link Throwable} associated to it which matches the given one. This matcher is the negation of {@link LoggingEventHasThrowable#hasThrowable(Throwable)}.
     * It is recommended to use this specific matcher instead of just combining the other matcher with {@link org.hamcrest.CoreMatchers#not(Matcher)} because of
     * the improved error output.
     * <p/>
     * Example usage: {@code assertThat(event, doesNotHaveThrowable(new NullPointerException("ohoh")));}
     * <p/>
     * Example output: {@code Expected: an ILoggingEvent with a throwable not matching: java.lang.NullPointerException{message='ohoh'} but: was
     * ILoggingEvent{level=ERROR, formattedMessage='some message', loggedBy=SomeLogger, throwable=java.lang.NullPointerException{message='ohoh'}}}
     *
     * @param throwable the throwable to match the event's {@code throwableProxy} with
     * @return a new matcher
     */
    public static Matcher<ILoggingEvent> doesNotHaveThrowable(final Throwable throwable) {
        return new LoggingEventHasThrowable(throwable, true, false);
    }

    /**
     * Creates a new matcher for {@link ch.qos.logback.classic.spi.ILoggingEvent ILoggingEvents} that only matches when the examined event has a {@link
     * Throwable} associated to it which matches the given one. This matcher is doing the same assertion as {@link
     * LoggingEventHasThrowable#hasThrowable(Throwable)}, with the difference that this matcher's output is optimized for usage on iterables of events.
     * <p/>
     * Example usage: {@code assertThat(events, hasItem(withThrowable(new NullPointerException("ohoh"))));}
     * <p/>
     * Example output: {@code Expected: an iterable containing an ILoggingEvent with a throwable matching: java.lang.NullPointerException{message='ohoh'} but:
     * iterable contained [ILoggingEvent{level=INFO, formattedMessage='some Message', loggedBy=SomeLogger, throwable=null}]}
     *
     * @param throwable the throwable to match the event's {@code throwableProxy} with
     * @return a new matcher
     */
    public static Matcher<ILoggingEvent> withThrowable(final Throwable throwable) {
        return new LoggingEventHasThrowable(throwable, false, true);
    }

    private final Throwable throwable;

    /**
     * Creates a new instance using the given {@link java.lang.Throwable}.
     *
     * @param throwable      the throwable to match the event's {@code throwableProxy} with
     * @param negated        if the matcher is negated
     * @param usedOnIterable if the matcher is used as part of an iterable matching
     */
    private LoggingEventHasThrowable(@Nullable final Throwable throwable, final boolean negated, final boolean usedOnIterable) {
        super(negated, usedOnIterable);
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
    protected void describeExpectation(final Description description) {
        description.appendText("an ILoggingEvent with a throwable matching: " + toString(throwable));
    }

    @Override
    protected void describeNegatedExpectation(final Description description) {
        description.appendText("an ILoggingEvent with a throwable not matching: " + toString(throwable));
    }

    private String toString(final Throwable throwable) {

        if (throwable == null) {
            return "null";
        }

        final StringBuilder stringBuilder = new StringBuilder().append(throwable.getClass().getName()).append("{");

        if (throwable.getMessage() == null) {
            stringBuilder.append("message=null");
        } else {
            stringBuilder.append("message='").append(throwable.getMessage()).append("'");
        }

        return stringBuilder.append("}").toString();
    }
}
