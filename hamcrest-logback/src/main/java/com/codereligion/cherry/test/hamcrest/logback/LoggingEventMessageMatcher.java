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
import javax.annotation.Nullable;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import static com.google.common.base.Preconditions.checkArgument;
import static org.hamcrest.CoreMatchers.containsString;

/**
 * A matcher which expects the {@link ch.qos.logback.classic.spi.ILoggingEvent} to have a formatted message which matches the given {@link
 * org.hamcrest.Matcher}.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 17.03.2015
 */
public class LoggingEventMessageMatcher extends TypeSafeMatcher<ILoggingEvent> {

    /**
     * Creates a new matcher for {@link ch.qos.logback.classic.spi.ILoggingEvent ILoggingEvents} that only matches when the examined event has a {@code message}
     * which is matched by the given {@link org.hamcrest.Matcher}.
     *
     * @param matcher the message matcher
     * @return a new matcher
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    public static Matcher<ILoggingEvent> hasMessage(final Matcher<String> matcher) {
        return new LoggingEventMessageMatcher(matcher, true);
    }

    public static Matcher<ILoggingEvent> doesNotHaveMessage(final Matcher<String> matcher) {
        return new LoggingEventMessageMatcher(matcher, false);
    }

    public static Matcher<ILoggingEvent> hasMessage(final String message, final Object... args) {

        if (args.length == 0) {
            return hasMessage(containsString(message));
        }

        return hasMessage(containsString(String.format(message, args)));
    }

    public static Matcher<ILoggingEvent> doesNotHaveMessage(final String message, final Object... args) {

        if (args.length == 0) {
            return doesNotHaveMessage(containsString(message));
        }

        return doesNotHaveMessage(containsString(String.format(message, args)));
    }

    private final Matcher<String> matcher;
    private final boolean shouldMatch;

    /**
     * Creates a new instance using the given {@link org.hamcrest.Matcher}.
     *
     * @param matcher     the matcher to use
     * @param shouldMatch if the matcher is expected to match or not
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    private LoggingEventMessageMatcher(final Matcher<String> matcher, final boolean shouldMatch) {
        checkArgument(matcher != null, "matcher must not be null.");
        this.matcher = matcher;
        this.shouldMatch = shouldMatch;
    }

    @Override
    public boolean matchesSafely(final ILoggingEvent event) {
        return shouldMatch && matcher.matches(event.getFormattedMessage());
    }

    @Override
    public void describeTo(final Description description) {

        if (shouldMatch) {
            matcher.describeTo(description.appendText("an ILoggingEvent with a formattedMessage matching: "));
        } else {
            matcher.describeTo(description.appendText("an ILoggingEvent with a formattedMessage not matching: "));
        }
    }

    @Override
    protected void describeMismatchSafely(final ILoggingEvent item, final Description mismatchDescription) {
        mismatchDescription.appendText("was " + toString(item));
    }

    private String toString(final ILoggingEvent item) {
        return new StringBuilder().append("ILoggingEvent{")
                                  .append("level=")
                                  .append(item.getLevel())
                                  .append(", formattedMessage='")
                                  .append(item.getFormattedMessage())
                                  .append("'")
                                  .append(", loggedBy=")
                                  .append(item.getLoggerName())
                                  .append(", throwable=")
                                  .append(toString(item.getThrowableProxy()))
                                  .append("}")
                                  .toString();
    }

    private String toString(@Nullable final IThrowableProxy throwableProxy) {

        if (throwableProxy == null) {
            return "null";
        }

        return new StringBuilder().append(throwableProxy.getCause()).append("{message='").append(throwableProxy.getMessage()).append("'}").toString();
    }
}
