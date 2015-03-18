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
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LoggingEventMessageMatcherTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void nullMessageCausesIllegalArgumentException() {

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("matcher must not be null.");

        // when
        new LoggingEventMessageMatcher(null);
    }

    @Test
    public void matchesWhenGivenMatcherMatchesEventMessage() {

        // given
        final Matcher<String> matcher = CoreMatchers.containsString("foo");
        final LoggingEventMessageMatcher loggingEventMessageMatcher = new LoggingEventMessageMatcher(matcher);
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setMessage("foo");

        // then
        assertThat(loggingEventMessageMatcher.matches(loggingEvent), is(true));
    }

    @Test
    public void doesNotMatchWhenGivenMatcherDoesNotMatchEventMessage() {

        // given
        final Matcher<String> matcher = CoreMatchers.containsString("foo");
        final LoggingEventMessageMatcher loggingEventMessageMatcher = new LoggingEventMessageMatcher(matcher);
        final LoggingEvent loggingEvent = new LoggingEvent();
        loggingEvent.setMessage("bar");

        // then
        assertThat(loggingEventMessageMatcher.matches(loggingEvent), is(false));

    }

}