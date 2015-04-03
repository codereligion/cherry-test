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
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * A matcher which expects at least one item of an iterable of {@link ch.qos.logback.classic.spi.ILoggingEvent ILoggingEvents} to match the given {@link
 * org.hamcrest.Matcher}.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 23.03.2015
 */
public class LoggingEventIterableHasItem extends TypeSafeDiagnosingMatcher<Iterable<ILoggingEvent>> {

    /**
     * Creates a new matcher for iterables of {@link ch.qos.logback.classic.spi.ILoggingEvent ILoggingEvents} that only matches when at least one event matches
     * the given {@link org.hamcrest.Matcher}. It is recommended to use this specific matcher instead of just combining the other matcher with {@link
     * org.hamcrest.CoreMatchers#hasItem(Object)} because of the improved error output and generics handling.
     * <p/>
     * Example usage: {@code assertThat(event, hasItem(withLevel(Level.ERROR)));}
     * <p/>
     * Example output: {@code Expected: an iterable containing an ILoggingEvent with level: ERROR but: was [ILoggingEvent{level=INFO, formattedMessage='some
     * Message', loggedBy=SomeLogger, throwable=null}]}
     *
     * @param itemMatcher the logging event {@link Matcher} to check the items with
     * @return a new matcher
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    public static Matcher<Iterable<ILoggingEvent>> hasItem(final Matcher<ILoggingEvent> itemMatcher) {
        return new LoggingEventIterableHasItem(itemMatcher, false);
    }

    /**
     * Creates a new matcher for iterables of {@link ch.qos.logback.classic.spi.ILoggingEvent ILoggingEvents} that only matches when at no event matches the
     * given {@link org.hamcrest.Matcher}. This matcher is the negation of {@link LoggingEventIterableHasItem#hasItem(Matcher)}. It is recommended to use this
     * specific matcher instead of just combining the other matcher with {@link org.hamcrest.CoreMatchers#not(Matcher)} because of the improved error output.
     * <p/>
     * Example usage: {@code assertThat(event, hasNoItem(withLevel(Level.ERROR)));}
     * <p/>
     * Example output: {@code Expected: an iterable not containing an ILoggingEvent with level: ERROR but: was [ILoggingEvent{level=ERROR,
     * formattedMessage='some Message', loggedBy=SomeLogger, throwable=null}]}
     *
     * @param itemMatcher the logging event {@link Matcher} to check the items with
     * @return a new matcher
     * @throws java.lang.IllegalArgumentException when the given parameter is {@code null}
     */
    public static Matcher<Iterable<ILoggingEvent>> hasNoItem(final Matcher<ILoggingEvent> itemMatcher) {
        return new LoggingEventIterableHasItem(itemMatcher, true);
    }

    private final Matcher<ILoggingEvent> elementMatcher;
    private final boolean negated;

    private LoggingEventIterableHasItem(final Matcher<ILoggingEvent> elementMatcher, final boolean negated) {
        this.elementMatcher = elementMatcher;
        this.negated = negated;
    }

    @Override
    protected boolean matchesSafely(final Iterable<ILoggingEvent> collection, final Description mismatchDescription) {

        mismatchDescription.appendText("iterable contained ");

        if (negated) {
            return negativeMatches(collection, mismatchDescription);
        } else {
            return positiveMatches(collection, mismatchDescription);
        }
    }

    private boolean positiveMatches(final Iterable<ILoggingEvent> collection, final Description mismatchDescription) {

        mismatchDescription.appendText("[");
        boolean isPastFirst = false;
        for (final Object item : collection) {
            if (elementMatcher.matches(item)) {
                return true;
            }
            if (isPastFirst) {
                mismatchDescription.appendText(", ");
            }
            elementMatcher.describeMismatch(item, mismatchDescription);
            isPastFirst = true;
        }

        mismatchDescription.appendText("]");
        return false;
    }

    private boolean negativeMatches(final Iterable<ILoggingEvent> collection, final Description mismatchDescription) {
        for (final Object item : collection) {
            if (elementMatcher.matches(item)) {
                elementMatcher.describeMismatch(item, mismatchDescription);
                return false;
            }
        }
        return true;
    }

    @Override
    public void describeTo(final Description description) {
        if (negated) {
            description.appendText("an iterable not containing ");
        } else {
            description.appendText("an iterable containing ");
        }
        description.appendDescriptionOf(elementMatcher);
    }
}
