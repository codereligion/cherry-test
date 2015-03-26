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
     * Creates a new matcher for {@link ch.qos.logback.classic.spi.ILoggingEvent ILoggingEvents} that only matches when the examined event has a {@code message}
     * which is matched by the given {@link org.hamcrest.Matcher}.
     *
     * @param matcher the message matcher
     * @return a new matcher
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    public static Matcher<ILoggingEvent> hasMessage(final Matcher<String> matcher) {
        return new LoggingEventHasMessage(matcher, true, false);
    }

    /**
     * TODO document
     *
     * @param matcher
     * @return
     */
    public static Matcher<ILoggingEvent> withMessage(final Matcher<String> matcher) {
        return new LoggingEventHasMessage(matcher, true, true);
    }

    /**
     * TODO document
     *
     * @param matcher
     * @return
     */
    public static Matcher<ILoggingEvent> doesNotHaveMessage(final Matcher<String> matcher) {
        return new LoggingEventHasMessage(matcher, false, false);
    }

    private final Matcher<String> matcher;

    /**
     * Creates a new instance using the given {@link org.hamcrest.Matcher}.
     *
     * @param matcher     the matcher to use
     * @param shouldMatch if the matcher is expected to match or not
     * @param isIterableMatcher if the matcher is used as part of an iterable matching
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    private LoggingEventHasMessage(final Matcher<String> matcher, final boolean shouldMatch, final boolean isIterableMatcher) {
        super(shouldMatch, isIterableMatcher);
        checkArgument(matcher != null, "matcher must not be null.");
        this.matcher = matcher;
    }

    @Override
    protected boolean internalMatches(final ILoggingEvent event) {
        return matcher.matches(event.getFormattedMessage());
    }

    @Override
    protected void describePositiveMatch(final Description description) {
        matcher.describeTo(description.appendText("an ILoggingEvent with a formattedMessage matching: "));
    }

    @Override
    protected void describeNegativeMatch(final Description description) {
        matcher.describeTo(description.appendText("an ILoggingEvent with a formattedMessage not matching: "));
    }
}
