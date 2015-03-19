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

    public static Matcher<ILoggingEvent> hasMessage(final Matcher<String> matcher) {
        return new LoggingEventMessageMatcher(matcher);
    }

    public static Matcher<ILoggingEvent> doesNotHaveMessage(final String message) {
        return not(hasMessage(containsString(message)));
    }

    public static Matcher<ILoggingEvent> hasMessage(final String message, final Object... args) {

        if (args.length == 0) {
            return hasMessage(containsString(message));
        }

        return hasMessage(containsString(String.format(message, args)));
    }

    public static Matcher<Iterable<? super ILoggingEvent>> hasItemWithMessage(final String message, final Object... args) {

        if (args.length == 0) {
            return hasItem(hasMessage(containsString(message)));
        }

        return hasItem(hasMessage(containsString(String.format(message, args))));
    }

    public static Matcher<Iterable<? super ILoggingEvent>> hasItemWithMessage(final Matcher<String> matcher) {
        return hasItem(hasMessage(matcher));
    }

    public static Matcher<Iterable<? super ILoggingEvent>> hasNoItemWithMessage(final String message) {
        return hasItem(not(hasMessage(containsString(message))));
    }

    private Matcher<String> matcher;

    /**
     * Creates a new instance using the given {@link org.hamcrest.Matcher}.
     *
     * @param matcher the matcher to use
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    private LoggingEventMessageMatcher(final Matcher<String> matcher) {
        checkArgument(matcher != null, "matcher must not be null.");
        this.matcher = matcher;
    }

    @Override
    public boolean matchesSafely(final ILoggingEvent event) {
        return matcher.matches(event.getFormattedMessage());
    }

    @Override
    public void describeTo(final Description description) {
        matcher.describeTo(description.appendText("an ILoggingEvent with a message matching: "));
    }

    @Override
    protected void describeMismatchSafely(final ILoggingEvent item, final Description mismatchDescription) {
        mismatchDescription.appendText("an ILoggingEvent with message: " + item.getFormattedMessage());
    }
}
