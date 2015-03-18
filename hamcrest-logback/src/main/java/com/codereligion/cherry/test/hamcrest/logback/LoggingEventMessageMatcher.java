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
import org.hamcrest.TypeSafeMatcher;
import static com.google.common.base.Preconditions.checkArgument;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.not;

/**
 * A matcher which expects the {@link ch.qos.logback.classic.spi.ILoggingEvent} to have a formatted message which matches the given {@link
 * org.hamcrest.Matcher}.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 17.03.2015
 */
public class LoggingEventMessageMatcher extends TypeSafeMatcher<ILoggingEvent> {

    public static Matcher<ILoggingEvent> containsMessage(final Matcher<String> matcher) {
        return new LoggingEventMessageMatcher(matcher);
    }

    public static Matcher<ILoggingEvent> doesNotContainMessage(final String message) {
        return not(new LoggingEventMessageMatcher(containsString(message)));
    }


    public static Matcher<ILoggingEvent> containsMessage(final String message) {
        return new LoggingEventMessageMatcher(containsString(message));
    }

    public static Matcher<ILoggingEvent> containsMessage(final String message, final Object... args) {
        final String formattedMessage = String.format(message, args);
        return new LoggingEventMessageMatcher(containsString(formattedMessage));
    }


    public static Matcher<Iterable<? super ILoggingEvent>> hasItemWhichContainsMessage(final String message) {
        return hasItem(new LoggingEventMessageMatcher(containsString(message)));
    }

    public static Matcher<Iterable<? super ILoggingEvent>> hasItemWhichContainsMessage(final String message, final Object... args) {
        final String formattedMessage = String.format(message, args);
        return hasItem(new LoggingEventMessageMatcher(containsString(formattedMessage)));
    }

    public static Matcher<Iterable<? super ILoggingEvent>> hasItemWhichContainsMessage(final Matcher<String> matcher) {
        return hasItem(new LoggingEventMessageMatcher(matcher));
    }

    public static Matcher<Iterable<? super ILoggingEvent>> hasNoItemWhichContainsMessage(final String message) {
        return hasItem(not(new LoggingEventMessageMatcher(containsString(message))));
    }

    private Matcher<String> matcher;

    /**
     * Creates a new instance using the given {@link org.hamcrest.Matcher}.
     *
     * @param matcher the matcher to use
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    public LoggingEventMessageMatcher(final Matcher<String> matcher) {
        checkArgument(matcher != null, "matcher must not be null.");
        this.matcher = matcher;
    }

    @Override
    public boolean matchesSafely(final ILoggingEvent event) {
        return matcher.matches(event.getFormattedMessage());
    }

    @Override
    public void describeTo(final Description description) {
        matcher.describeTo(description);
    }
}
