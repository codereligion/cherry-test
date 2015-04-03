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
 * A matcher which expects the {@link ch.qos.logback.classic.spi.ILoggingEvent} to have a formatted message which matches the given {@link
 * org.hamcrest.Matcher}.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 17.03.2015
 */
public class LoggingEventHasMessage extends AbstractILoggingEventDescribingMatcher {

    /**
     * Creates a new matcher for {@link ch.qos.logback.classic.spi.ILoggingEvent ILoggingEvents} that only matches when the examined event has a {@code
     * formattedMessage} which is matched by the given {@link org.hamcrest.Matcher}. This matcher is doing the same assertion as {@link
     * LoggingEventHasMessage#withMessage(Matcher)}, with the difference that this matcher's output is optimized for usage on single events.
     * <p/>
     * Example usage: {@code assertThat(event, hasMessage(containsString("ohoh")));}
     * <p/>
     * Example output: {@code Expected: an ILoggingEvent with a formattedMessage matching: a string containing "ohoh" but: was ILoggingEvent{level=INFO,
     * formattedMessage='some Message', loggedBy=SomeLogger, throwable=null}}
     *
     * @param matcher the string {@link Matcher} to check the {@code formattedMessage} against
     * @return a new matcher
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    public static Matcher<ILoggingEvent> hasMessage(final Matcher<String> matcher) {
        return new LoggingEventHasMessage(matcher, false, false);
    }

    /**
     * Creates a new matcher for {@link ch.qos.logback.classic.spi.ILoggingEvent ILoggingEvents} that only matches when the examined event does not have a
     * {@code formattedMessage} which is matched by the given {@link org.hamcrest.Matcher}. This matcher is the negation of {@link
     * LoggingEventHasMessage#hasMessage(Matcher)}. It is recommended to use this specific matcher instead of just combining the other matcher with {@link
     * org.hamcrest.CoreMatchers#not(Matcher)} because of the improved error output.
     * <p/>
     * Example usage: {@code assertThat(event, doesNotHaveMessage(containsString("ohoh")));}
     * <p/>
     * Example output: {@code Expected: an ILoggingEvent with a formattedMessage not matching: a string containing "ohoh" but: was ILoggingEvent{level=ERROR,
     * formattedMessage='ohoh', loggedBy=SomeLogger, throwable=null}}
     *
     * @param matcher the string {@link Matcher} to check the {@code formattedMessage} against
     * @return a new matcher
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    public static Matcher<ILoggingEvent> doesNotHaveMessage(final Matcher<String> matcher) {
        return new LoggingEventHasMessage(matcher, true, false);
    }

    /**
     * Creates a new matcher for {@link ch.qos.logback.classic.spi.ILoggingEvent ILoggingEvents} that only matches when the examined event has a {@code
     * formattedMessage} which is matched by the given {@link org.hamcrest.Matcher}. This matcher is doing the same assertion as {@link
     * LoggingEventHasMessage#hasMessage(Matcher)}, with the difference that this matcher's output is optimized for usage on iterables of events.
     * <p/>
     * Example usage: {@code assertThat(events, hasItem(withMessage(containsString("ohoh"))));}
     * <p/>
     * Example output: {@code Expected: an iterable containing an ILoggingEvent with level: ERROR but: iterable contained [ILoggingEvent{level=INFO,
     * formattedMessage='some Message', loggedBy=SomeLogger, throwable=null}]}
     *
     * @param matcher the string {@link Matcher} to check the {@code formattedMessage} against
     * @return a new matcher
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    public static Matcher<ILoggingEvent> withMessage(final Matcher<String> matcher) {
        return new LoggingEventHasMessage(matcher, false, true);
    }

    private final Matcher<String> matcher;

    /**
     * Creates a new instance using the given {@link org.hamcrest.Matcher}.
     *
     * @param matcher        the matcher to use
     * @param negated        if the matcher is negated
     * @param usedOnIterable if the matcher is used as part of an iterable matching
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    private LoggingEventHasMessage(final Matcher<String> matcher, final boolean negated, final boolean usedOnIterable) {
        super(negated, usedOnIterable);
        checkArgument(matcher != null, "matcher must not be null.");
        this.matcher = matcher;
    }

    @Override
    protected boolean internalMatches(final ILoggingEvent event) {
        return matcher.matches(event.getFormattedMessage());
    }

    @Override
    protected void describeExpectation(final Description description) {
        matcher.describeTo(description.appendText("an ILoggingEvent with a formattedMessage matching: "));
    }

    @Override
    protected void describeNegatedExpectation(final Description description) {
        matcher.describeTo(description.appendText("an ILoggingEvent with a formattedMessage not matching: "));
    }
}
