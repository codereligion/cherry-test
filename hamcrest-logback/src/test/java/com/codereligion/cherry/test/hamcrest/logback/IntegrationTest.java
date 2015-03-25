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

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Test;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventHasLevel.doesNotHaveLevel;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventHasLevel.hasLevel;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventHasLevel.withLevel;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventHasMessage.doesNotHaveMessage;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventHasMessage.hasMessage;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventHasMessage.withMessage;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventHasThrowable.doesNotHaveThrowable;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventHasThrowable.hasThrowable;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventHasThrowable.withThrowable;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventIterableHasItem.hasItem;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventIterableHasItem.hasNoItem;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventLoggedBy.loggedBy;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventLoggedBy.wasLoggedBy;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventLoggedBy.wasNotLoggedBy;
import static org.junit.Assert.assertThat;

/**
 * TODO move iterable test to respective test classes, kill the others
 * TODO assertMessages (think if this should be done here or in the individual tests
 *
 * @author Sebastian Gr&ouml;bler
 * @since 22.03.2015
 */
public class IntegrationTest {

    @Test(expected = AssertionError.class)
    public void failingHasLevel() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLevel(Level.INFO);

        assertThat(loggingEvent, hasLevel(Level.ERROR));
    }

    @Test(expected = AssertionError.class)
    public void failingDoesNotHaveLevel() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLevel(Level.ERROR);

        assertThat(loggingEvent, doesNotHaveLevel(Level.ERROR));
    }

    @Test(expected = AssertionError.class)
    public void failingHasItemWithLevel() {
        final LoggingEvent first = new LoggingEvent();
        first.setLevel(Level.INFO);
        final LoggingEvent second = new LoggingEvent();
        second.setLevel(Level.WARN);
        final List<ILoggingEvent> events = Lists.<ILoggingEvent>newArrayList(first, second);

        assertThat(events, hasItem(withLevel(Level.ERROR)));
    }

    @Test
    public void hasItemWithLevel() {
        final LoggingEvent first = new LoggingEvent();
        first.setLevel(Level.INFO);
        final LoggingEvent second = new LoggingEvent();
        second.setLevel(Level.ERROR);
        final List<ILoggingEvent> events = Lists.<ILoggingEvent>newArrayList(first, second);

        assertThat(events, hasItem(withLevel(Level.ERROR)));
    }

    @Test(expected = AssertionError.class)
    public void failingHasNoItemWithLevel() {
        final LoggingEvent first = new LoggingEvent();
        first.setLevel(Level.ERROR);
        final LoggingEvent second = new LoggingEvent();
        second.setLevel(Level.WARN);
        final List<ILoggingEvent> events = Lists.<ILoggingEvent>newArrayList(first, second);

        assertThat(events, hasNoItem(withLevel(Level.ERROR)));
    }

    @Test
    public void hasNoItemWithLevel() {
        final LoggingEvent first = new LoggingEvent();
        first.setLevel(Level.INFO);
        final LoggingEvent second = new LoggingEvent();
        second.setLevel(Level.WARN);
        final List<ILoggingEvent> events = Lists.<ILoggingEvent>newArrayList(first, second);

        assertThat(events, hasNoItem(withLevel(Level.ERROR)));
    }

    @Test(expected = AssertionError.class)
    public void failingHasMessage() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setMessage("foo");

        assertThat(loggingEvent, hasMessage("bar"));
    }

    @Test(expected = AssertionError.class)
    public void failingDoesNotHaveMessage() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setMessage("foo");

        assertThat(loggingEvent, doesNotHaveMessage("foo"));
    }

    @Test(expected = AssertionError.class)
    public void failingHasItemWithMessage() {
        final LoggingEvent first = new LoggingEvent();
        first.setMessage("foo");
        final LoggingEvent second = new LoggingEvent();
        second.setMessage("baz");
        final List<ILoggingEvent> events = Lists.<ILoggingEvent>newArrayList(first, second);

        assertThat(events, hasItem(withMessage("bar")));
    }

    @Test(expected = AssertionError.class)
    public void failingHasNoItemWithMessage() {
        final LoggingEvent first = new LoggingEvent();
        first.setMessage("foo");
        final LoggingEvent second = new LoggingEvent();
        second.setMessage("bar");
        final List<ILoggingEvent> events = Lists.<ILoggingEvent>newArrayList(first, second);

        assertThat(events, hasNoItem(withMessage("bar")));
    }

    @Test(expected = AssertionError.class)
    public void failingHasThrowable() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setThrowableProxy(new ThrowableProxy(new NullPointerException()));

        assertThat(loggingEvent, hasThrowable(new IllegalArgumentException()));
    }

    @Test(expected = AssertionError.class)
    public void failingDoesNotHaveThrowable() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setThrowableProxy(new ThrowableProxy(new IllegalArgumentException()));

        assertThat(loggingEvent, doesNotHaveThrowable(new IllegalArgumentException()));
    }

    @Test(expected = AssertionError.class)
    public void failingHasItemWithThrowable() {
        final LoggingEvent first = new LoggingEvent();
        first.setThrowableProxy(new ThrowableProxy(new IllegalArgumentException()));
        final LoggingEvent second = new LoggingEvent();
        second.setThrowableProxy(new ThrowableProxy(new IllegalStateException()));
        final List<ILoggingEvent> events = Lists.<ILoggingEvent>newArrayList(first, second);

        assertThat(events, hasItem(withThrowable(new NullPointerException())));
    }

    @Test (expected = AssertionError.class)
    public void failingHasNoItemWithThrowable() {
        final LoggingEvent first = new LoggingEvent();
        first.setThrowableProxy(new ThrowableProxy(new IllegalArgumentException()));
        final LoggingEvent second = new LoggingEvent();
        second.setThrowableProxy(new ThrowableProxy(new NullPointerException()));
        final List<ILoggingEvent> events = Lists.<ILoggingEvent>newArrayList(first, second);

        assertThat(events, hasNoItem(withThrowable(new NullPointerException())));
    }

    @Test(expected = AssertionError.class)
    public void failingWasLoggedBy() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLoggerName("foo");

        assertThat(loggingEvent, wasLoggedBy("bar"));
    }

    @Test(expected = AssertionError.class)
    public void failingWasNotLoggedBy() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLoggerName("bar");

        assertThat(loggingEvent, wasNotLoggedBy("bar"));
    }

    @Test(expected = AssertionError.class)
    public void failingHasItemLoggedBy() {
        final LoggingEvent first = new LoggingEvent();
        first.setLoggerName("foo");
        final LoggingEvent second = new LoggingEvent();
        second.setLoggerName("bar");
        final List<ILoggingEvent> events = Lists.<ILoggingEvent>newArrayList(first, second);

        assertThat(events, hasItem(loggedBy("baz")));
    }

    @Test(expected = AssertionError.class)
    public void failingHasItemNotLoggedBy() {
        final LoggingEvent first = new LoggingEvent();
        first.setLoggerName("foo");
        final LoggingEvent second = new LoggingEvent();
        second.setLoggerName("bar");
        final List<ILoggingEvent> events = Lists.<ILoggingEvent>newArrayList(first, second);

        assertThat(events, hasNoItem(loggedBy("bar")));
    }
}
