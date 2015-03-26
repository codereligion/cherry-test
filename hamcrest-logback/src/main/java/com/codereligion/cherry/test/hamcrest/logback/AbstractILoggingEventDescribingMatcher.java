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
 * Abstract {@link TypeSafeMatcher} which unifies the expected and actual message by using the same message type but different values to reflect the respective
 * states.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 18.03.2015
 */
public abstract class AbstractILoggingEventDescribingMatcher extends TypeSafeMatcher<ILoggingEvent> {

    private final boolean shouldMatch;
    private final boolean isIterableMatcher;

    protected AbstractILoggingEventDescribingMatcher(final boolean shouldMatch, final boolean isIterableMatcher) {
        this.shouldMatch = shouldMatch;
        this.isIterableMatcher = isIterableMatcher;
    }

    @Override
    public boolean matchesSafely(final ILoggingEvent event) {
        return shouldMatch == internalMatches(event);
    }

    @Override
    public void describeTo(final Description description) {
        if (shouldMatch) {
            describePositiveMatch(description);
        } else {
            describeNegativeMatch(description);
        }
    }

    protected abstract boolean internalMatches(ILoggingEvent event);

    protected abstract void describePositiveMatch(Description description);

    protected abstract void describeNegativeMatch(Description description);


    @Override
    protected void describeMismatchSafely(final ILoggingEvent item, final Description mismatchDescription) {
        if (isIterableMatcher) {
            mismatchDescription.appendText(toString(item));
        } else {
            mismatchDescription.appendText("was " + toString(item));
        }
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

        return new StringBuilder().append(throwableProxy.getClassName()).append("{message='").append(throwableProxy.getMessage()).append("'}").toString();
    }
}
