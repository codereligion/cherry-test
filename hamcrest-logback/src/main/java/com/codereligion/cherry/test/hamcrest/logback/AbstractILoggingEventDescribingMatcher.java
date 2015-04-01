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
import org.hamcrest.TypeSafeMatcher;

/**
 * Abstract {@link TypeSafeMatcher} which ensures better descriptions of expectations for regular matchers, negatable matchers and matchers used in conjunction
 * with iterable matchers.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 18.03.2015
 */
public abstract class AbstractILoggingEventDescribingMatcher extends TypeSafeMatcher<ILoggingEvent> {

    private final boolean isNegated;
    private final boolean isIterableMatcher;

    /**
     * Creates a new instance using the given parameters to define the behaviour of the description generation.
     *
     * @param isNegated if the matcher is negated
     * @param isIterableMatcher if the matcher is used in conjunction with an iterable matcher
     */
    public AbstractILoggingEventDescribingMatcher(final boolean isNegated, final boolean isIterableMatcher) {
        this.isNegated = isNegated;
        this.isIterableMatcher = isIterableMatcher;
    }

    @Override
    public boolean matchesSafely(final ILoggingEvent event) {
        return isNegated != internalMatches(event);
    }

    @Override
    public void describeTo(final Description description) {
        if (isNegated) {
            describeNegatedExpectation(description);
        } else {
            describeExpectation(description);
        }
    }

    /**
     * This method must return true when the given {@code event} matches. Proper negation in cases of a negation is handled by the caller of this method.
     *
     * @param event the {@link ILoggingEvent} to be checked
     * @return {@code true} if the event matches
     */
    protected abstract boolean internalMatches(ILoggingEvent event);

    /**
     * Describes the expected data.
     *
     * @param description the {@link Description} to amend the text
     */
    protected abstract void describeExpectation(Description description);

    /**
     * Describes the expected data when the matcher has been negated.
     *
     * @param description the {@link Description} to amend the text
     */
    protected abstract void describeNegatedExpectation(Description description);

    @Override
    protected void describeMismatchSafely(final ILoggingEvent item, final Description mismatchDescription) {
        if (!isIterableMatcher) {
            mismatchDescription.appendText("was ");
        }
        mismatchDescription.appendText(toString(item));
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

        final StringBuilder stringBuilder = new StringBuilder().append(throwableProxy.getClassName()).append("{");

        if (throwableProxy.getMessage() == null) {
            stringBuilder.append("message=null");
        } else {
            stringBuilder.append("message='").append(throwableProxy.getMessage()).append("'");
        }

        return stringBuilder.append("}").toString();
    }
}
