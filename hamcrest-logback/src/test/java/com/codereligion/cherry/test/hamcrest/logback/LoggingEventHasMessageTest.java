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

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventHasMessage.doesNotHaveMessage;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventHasMessage.hasMessage;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventHasMessage.withMessage;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * Tests {@link LoggingEventHasMessage}.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 30.03.2015
 */
public class LoggingEventHasMessageTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void hasMessageThrowsIllegalArgumentExceptionOnNullValue() {

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("matcher must not be null.");

        // given
        final Matcher<String> message = null;

        // when
        hasMessage(message);
    }

    @Test
    public void doesNotHaveMessageThrowsIllegalArgumentExceptionOnNullValue() {

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("matcher must not be null.");

        // given
        final Matcher<String> message = null;

        // when
        doesNotHaveMessage(message);
    }

    @Test
    public void withMessageThrowsIllegalArgumentExceptionOnNullValue() {

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("matcher must not be null.");

        // given
        final Matcher<String> message = null;

        // when
        withMessage(message);
    }

    @Test
    public void hasMessageMatchesWhenGivenMatcherMatchesEventMessage() {

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withMessage("foo");

        // then
        assertThat(loggingEvent, hasMessage(containsString("foo")));
    }

    @Test
    public void hasMessageDoesNotMatchWhenGivenMatcherDoesNotMatchEventMessage() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an ILoggingEvent with a formattedMessage matching: a string containing \"foo\"\n" +
                                        "     but: was ILoggingEvent{level=null, formattedMessage='bar', loggedBy=null, throwable=null}");

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withMessage("bar");

        // then
        assertThat(loggingEvent, hasMessage(containsString("foo")));
    }

    @Test
    public void doesNotHaveMessageMatchesWhenGivenMatcherDoesNotMatchEventMessage() {

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withMessage("foo");

        // then
        assertThat(loggingEvent, doesNotHaveMessage(containsString("bar")));
    }

    @Test
    public void doesNotHaveMessageDoesNotMatchWhenGivenMatcherMatchesEventMessage() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an ILoggingEvent with a formattedMessage not matching: a string containing \"bar\"\n" +
                                        "     but: was ILoggingEvent{level=null, formattedMessage='bar', loggedBy=null, throwable=null}");

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withMessage("bar");

        // then
        assertThat(loggingEvent, doesNotHaveMessage(containsString("bar")));
    }
}
