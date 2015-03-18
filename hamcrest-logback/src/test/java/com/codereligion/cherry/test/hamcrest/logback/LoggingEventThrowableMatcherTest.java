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

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LoggingEventThrowableMatcherTest {

    @Test
    public void matchesWhenBothThrowablesAreNull() {

        // given
        final LoggingEventThrowableMatcher matcher = new LoggingEventThrowableMatcher(null);
        final LoggingEvent loggingEvent = new LoggingEvent();

        // then
        assertThat(matcher.matches(loggingEvent), is(true));
    }

    @Test
    public void doesNotMatchWhenEventThrowableIsNull() {

        // given
        final LoggingEventThrowableMatcher matcher = new LoggingEventThrowableMatcher(new RuntimeException("opsi!"));
        final LoggingEvent loggingEvent = new LoggingEvent();

        // then
        assertThat(matcher.matches(loggingEvent), is(false));
    }

    @Test
    public void doesNotMatchWhenGivenThrowableIsNull() {

        // given
        final LoggingEventThrowableMatcher matcher = new LoggingEventThrowableMatcher(null);
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setThrowableProxy(new ThrowableProxy(new RuntimeException("opsi!")));

        // then
        assertThat(matcher.matches(loggingEvent), is(false));
    }

    @Test
    public void matchesWhenThrowableClassesAreEqual() {

        // given
        final LoggingEventThrowableMatcher matcher = new LoggingEventThrowableMatcher(new RuntimeException());
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setThrowableProxy(new ThrowableProxy(new RuntimeException()));

        // then
        assertThat(matcher.matches(loggingEvent), is(true));
    }

    @Test
    public void doesNotMatchWhenThrowableClassesAreNotEqual() {

        // given
        final LoggingEventThrowableMatcher matcher = new LoggingEventThrowableMatcher(new NullPointerException("opsi!"));
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setThrowableProxy(new ThrowableProxy(new IllegalArgumentException("opsi!")));

        // then
        assertThat(matcher.matches(loggingEvent), is(false));
    }

    @Test
    public void matchesWhenThrowableMessagesAreEqual() {

        // given
        final LoggingEventThrowableMatcher matcher = new LoggingEventThrowableMatcher(new NullPointerException("opsi!"));
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setThrowableProxy(new ThrowableProxy(new NullPointerException("opsi!")));

        // then
        assertThat(matcher.matches(loggingEvent), is(true));
    }

    @Test
    public void matchesWhenThrowableMessagesAreNull() {

        // given
        final LoggingEventThrowableMatcher matcher = new LoggingEventThrowableMatcher(new NullPointerException(null));
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setThrowableProxy(new ThrowableProxy(new NullPointerException(null)));

        // then
        assertThat(matcher.matches(loggingEvent), is(true));
    }

    @Test
    public void doesNotMatchWhenThrowableMessageAreNotEqual() {

        // given
        final LoggingEventThrowableMatcher matcher = new LoggingEventThrowableMatcher(new NullPointerException("opsi!"));
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setThrowableProxy(new ThrowableProxy(new NullPointerException("nope!")));

        // then
        assertThat(matcher.matches(loggingEvent), is(false));
    }


}