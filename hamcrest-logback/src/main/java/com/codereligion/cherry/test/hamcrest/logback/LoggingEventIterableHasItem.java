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
 * TODO document
 *
 * @author Sebastian Gr&ouml;bler
 * @since 23.03.2015
 */
public class LoggingEventIterableHasItem extends TypeSafeDiagnosingMatcher<Iterable<ILoggingEvent>> {

    /**
     * TODO document
     *
     * @param itemMatcher
     * @return
     */
    public static Matcher<Iterable<ILoggingEvent>> hasItem(final Matcher<ILoggingEvent> itemMatcher) {
        return new LoggingEventIterableHasItem(itemMatcher, true);
    }

    /**
     * TODO document
     *
     * @param itemMatcher
     * @return
     */
    public static Matcher<Iterable<ILoggingEvent>> hasNoItem(final Matcher<ILoggingEvent> itemMatcher) {
        return new LoggingEventIterableHasItem(itemMatcher, false);
    }

    private final Matcher<ILoggingEvent> elementMatcher;
    private final boolean shouldMatch;

    private LoggingEventIterableHasItem(final Matcher<ILoggingEvent> elementMatcher, final boolean shouldMatch) {
        this.elementMatcher = elementMatcher;
        this.shouldMatch = shouldMatch;
    }

    @Override
    protected boolean matchesSafely(final Iterable<ILoggingEvent> collection, final Description mismatchDescription) {

        mismatchDescription.appendText("iterable contained ");

        if (shouldMatch) {
            return positiveMatches(collection, mismatchDescription);
        } else {
            return negativeMatches(collection, mismatchDescription);
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
        if (shouldMatch) {
            description.appendText("an iterable containing ");
        } else {
            description.appendText("an iterable not containing ");
        }
        description.appendDescriptionOf(elementMatcher);
    }
}
