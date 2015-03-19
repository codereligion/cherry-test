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
import ch.qos.logback.classic.spi.LoggingEvent;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventLoggerNameMatcher.wasLoggedBy;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LoggingEventLoggerNameMatcherTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void nullLoggerNameCausesIllegalArgumentException() {

        // given
        final String loggerName = null;

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("loggerName must not be null.");

        // when
        wasLoggedBy(loggerName);
    }

    @Test
    public void nullLoggerTypeCausesIllegalArgumentException() {

        // given
        final Class<?> loggerType = null;

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("loggerType must not be null.");

        // when
        wasLoggedBy(loggerType);
    }

    @Test
    public void matchesWhenGivenLoggerNameEqualsEventLoggerName() {

        // given
        final Matcher<ILoggingEvent> matcher = wasLoggedBy("foo");
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLoggerName("foo");

        // then
        assertThat(matcher.matches(loggingEvent), is(true));
    }

    @Test
    public void doesNotMatchWhenGivenLoggerNameDoesNotEqualEventLoggerName() {

        // given
        final Matcher<ILoggingEvent> matcher = wasLoggedBy("foo");
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLoggerName("bar");

        // then
        assertThat(matcher.matches(loggingEvent), is(false));
    }

    @Test
    public void describesError() {

        // given
        final StringDescription matchDescription = new StringDescription();
        final StringDescription missMatchDescription = new StringDescription();
        final Matcher<ILoggingEvent> matcher = wasLoggedBy("foo");
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLoggerName("bar");
        matcher.matches(loggingEvent);

        // when
        matcher.describeTo(matchDescription);
        matcher.describeMismatch(loggingEvent, missMatchDescription);

        // then
        assertThat(matchDescription.toString(), is("an ILoggingEvent for logger with name: foo"));
        assertThat(missMatchDescription.toString(), is("an ILoggingEvent for logger with name: bar"));
    }
}