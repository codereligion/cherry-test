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
import ch.qos.logback.classic.spi.LoggingEvent;
import com.google.common.collect.Lists;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.junit.Ignore;
import org.junit.Test;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventMessageMatcher.doesNotHaveMessage;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventMessageMatcher.hasMessage;
import static org.junit.Assert.assertThat;

/**
 * TODO
 *
 * @author Sebastian Gr&ouml;bler
 * @since 22.03.2015
 */
@Ignore("only to be executed manually")
public class IntegrationTest {

    @Test
    public void failingHasLevel() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLevel(Level.INFO);

        // TODO
    }

    @Test
    public void failingDoesNotHaveLevel() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLevel(Level.ERROR);

        // TODO
    }

    @Test
    public void failingHasItemWithLevel() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLevel(Level.INFO);

        // TODO
    }

    @Test
    public void failingDoesNotHaveItemWithLevel() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLevel(Level.ERROR);

        // TODO
    }

    @Test
    public void failingHasMessage() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setMessage("foo");

        assertThat(loggingEvent, hasMessage("bar"));
    }

    @Test
    public void failingDoesNotHaveMessage() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setMessage("foo");

        assertThat(loggingEvent, doesNotHaveMessage("foo"));
    }

    @Test
    public void failingHasItemWithMessage() {
        final LoggingEvent first = new LoggingEvent();
        first.setMessage("foo");
        final LoggingEvent second = new LoggingEvent();
        second.setMessage("baz");
        final List<LoggingEvent> actual = Lists.newArrayList(first, second);

        assertThat(actual, CoreMatchers.<LoggingEvent>hasItem(hasMessage("bar")));
    }

    @Test
    public void failingDoesNotHaveItemWithMessage() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLevel(Level.INFO);

        // TODO
//        assertThat(loggingEvent, doesNotHaveMessage("foo"));
    }

    @Test
    public void failingHasThrowable() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLevel(Level.INFO);

        // TODO
    }

    @Test
    public void failingDoesNotHaveThrowable() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLevel(Level.INFO);

        // TODO
    }

    @Test
    public void failingHasItemWithThrowable() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLevel(Level.INFO);

        // TODO
    }

    @Test
    public void failingDoesNotHaveItemThrowable() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLevel(Level.INFO);

        // TODO
    }

    @Test
    public void failingWasLoggedBy() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLevel(Level.INFO);

        // TODO
    }

    @Test
    public void failingWasNotLoggedBy() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLevel(Level.INFO);

        // TODO
    }

    @Test
    public void failingHasItemLoggedBy() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLevel(Level.INFO);

        // TODO
    }

    @Test
    public void failingHasItemNotLoggedBy() {
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setLevel(Level.INFO);

        // TODO
    }
}
