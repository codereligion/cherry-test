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
import ch.qos.logback.classic.spi.ThrowableProxy;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Test;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventThrowableMatcher.hasThrowable;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LoggingEventThrowableMatcherTest {

    @Test
    public void matchesWhenBothThrowablesAreNull() {

        // given
        final Matcher<ILoggingEvent> matcher = hasThrowable(null);
        final LoggingEvent loggingEvent = new LoggingEvent();

        // then
        assertThat(matcher.matches(loggingEvent), is(true));
    }

    @Test
    public void doesNotMatchWhenEventThrowableIsNull() {

        // given
        final Matcher<ILoggingEvent> matcher = hasThrowable(new RuntimeException("opsi!"));
        final LoggingEvent loggingEvent = new LoggingEvent();

        // then
        assertThat(matcher.matches(loggingEvent), is(false));
    }

    @Test
    public void doesNotMatchWhenGivenThrowableIsNull() {

        // given
        final Matcher<ILoggingEvent> matcher = hasThrowable(null);
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setThrowableProxy(new ThrowableProxy(new RuntimeException("opsi!")));

        // then
        assertThat(matcher.matches(loggingEvent), is(false));
    }

    @Test
    public void matchesWhenThrowableClassesAreEqual() {

        // given
        final Matcher<ILoggingEvent> matcher = hasThrowable(new RuntimeException());
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setThrowableProxy(new ThrowableProxy(new RuntimeException()));

        // then
        assertThat(matcher.matches(loggingEvent), is(true));
    }

    @Test
    public void doesNotMatchWhenThrowableClassesAreNotEqual() {

        // given
        final Matcher<ILoggingEvent> matcher = hasThrowable(new NullPointerException("opsi!"));
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setThrowableProxy(new ThrowableProxy(new IllegalArgumentException("opsi!")));

        // then
        assertThat(matcher.matches(loggingEvent), is(false));
    }

    @Test
    public void matchesWhenThrowableMessagesAreEqual() {

        // given
        final Matcher<ILoggingEvent> matcher = hasThrowable(new NullPointerException("opsi!"));
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setThrowableProxy(new ThrowableProxy(new NullPointerException("opsi!")));

        // then
        assertThat(matcher.matches(loggingEvent), is(true));
    }

    @Test
    public void matchesWhenThrowableMessagesAreNull() {

        // given
        final Matcher<ILoggingEvent> matcher = hasThrowable(new NullPointerException(null));
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setThrowableProxy(new ThrowableProxy(new NullPointerException(null)));

        // then
        assertThat(matcher.matches(loggingEvent), is(true));
    }

    @Test
    public void doesNotMatchWhenThrowableMessageAreNotEqual() {

        // given
        final Matcher<ILoggingEvent> matcher = hasThrowable(new NullPointerException("opsi!"));
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setThrowableProxy(new ThrowableProxy(new NullPointerException("nope!")));

        // then
        assertThat(matcher.matches(loggingEvent), is(false));
    }

    @Test
    public void describesErrorWhenGivenNullExceptions() {

        // given
        final StringDescription matchDescription = new StringDescription();
        final StringDescription missMatchDescription = new StringDescription();
        final Matcher<ILoggingEvent> matcher = hasThrowable(null);
        final LoggingEvent loggingEvent = new LoggingEvent();
        matcher.matches(loggingEvent);

        // when
        matcher.describeTo(matchDescription);
        matcher.describeMismatch(loggingEvent, missMatchDescription);

        // then
        assertThat(matchDescription.toString(), is("an ILoggingEvent with a throwable matching: null"));
        assertThat(missMatchDescription.toString(), is("an ILoggingEvent with a throwable matching: null"));
    }

    @Test
    public void describesErrorWhenGivenExceptions() {

        // given
        final StringDescription matchDescription = new StringDescription();
        final StringDescription missMatchDescription = new StringDescription();
        final Matcher<ILoggingEvent> matcher = hasThrowable(new IllegalArgumentException("foo"));
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setThrowableProxy(new ThrowableProxy(new NullPointerException("bar")));
        matcher.matches(loggingEvent);

        // when
        matcher.describeTo(matchDescription);
        matcher.describeMismatch(loggingEvent, missMatchDescription);

        // then
        assertThat(matchDescription.toString(), is("an ILoggingEvent with a throwable matching: java.lang.IllegalArgumentException[foo]"));
        assertThat(missMatchDescription.toString(), is("an ILoggingEvent with a throwable matching: java.lang.NullPointerException[bar]"));
    }

    @Test
    public void describesErrorWhenGivenExceptionsWithoutMessage () {

        // given
        final StringDescription matchDescription = new StringDescription();
        final StringDescription missMatchDescription = new StringDescription();
        final Matcher<ILoggingEvent> matcher = hasThrowable(new IllegalArgumentException());
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setThrowableProxy(new ThrowableProxy(new NullPointerException()));
        matcher.matches(loggingEvent);

        // when
        matcher.describeTo(matchDescription);
        matcher.describeMismatch(loggingEvent, missMatchDescription);

        // then
        assertThat(matchDescription.toString(), is("an ILoggingEvent with a throwable matching: java.lang.IllegalArgumentException[null]"));
        assertThat(missMatchDescription.toString(), is("an ILoggingEvent with a throwable matching: java.lang.NullPointerException[null]"));
    }
}