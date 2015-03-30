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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventLoggedBy.loggedBy;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventLoggedBy.wasLoggedBy;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventLoggedBy.wasNotLoggedBy;
import static org.junit.Assert.assertThat;

/**
 * Tests {@link LoggingEventLoggedBy}.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 30.03.2015
 */
public class LoggingEventLoggedByTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void wasLoggedByThrowsIllegalArgumentExceptionOnNullString() {

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("loggerName must not be null.");

        // given
        final String loggerName = null;

        // when
        wasLoggedBy(loggerName);
    }

    @Test
    public void wasNotLoggedByThrowsIllegalArgumentExceptionOnNullString() {

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("loggerName must not be null.");

        // given
        final String loggerName = null;

        // when
        wasNotLoggedBy(loggerName);
    }

    @Test
    public void loggedByThrowsIllegalArgumentExceptionOnNullString() {

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("loggerName must not be null.");

        // given
        final String loggerName = null;

        // when
        loggedBy(loggerName);
    }

    @Test
    public void wasLoggedByThrowsIllegalArgumentExceptionOnNullClass() {

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("loggerType must not be null.");

        // given
        final Class<?> logger = null;

        // when
        wasLoggedBy(logger);
    }

    @Test
    public void wasNotLoggedByThrowsIllegalArgumentExceptionOnNullClass() {

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("loggerType must not be null.");

        // given
        final Class<?> logger = null;

        // when
        wasNotLoggedBy(logger);
    }

    @Test
    public void loggedByThrowsIllegalArgumentExceptionOnNullClass() {

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("loggerType must not be null.");

        // given
        final Class<?> logger = null;

        // when
        loggedBy(logger);
    }

    @Test
    public void wasLoggedByMatchesWhenGivenLoggerNameEqualsEventLoggerName() {

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withLoggerName("foo");

        // then
        assertThat(loggingEvent, wasLoggedBy("foo"));
    }

    @Test
    public void wasLoggedByDoesNotMatchWhenGivenLoggerNameDoesNotEqualEventLoggerName() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an ILoggingEvent logged by: foo\n" +
                                        "     but: was ILoggingEvent{level=null, formattedMessage='null', loggedBy=bar, throwable=null}");

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withLoggerName("bar");

        // then
        assertThat(loggingEvent, wasLoggedBy("foo"));
    }

    @Test
    public void wasNotLoggedByMatchesWhenGivenLoggerNameDoesNotEqualEventLoggerName() {

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withLoggerName("foo");

        // then
        assertThat(loggingEvent, wasNotLoggedBy("bar"));
    }

    @Test
    public void wasNotLoggedByDoesNotMatchWhenGivenLoggerNameEqualsEventLoggerName() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an ILoggingEvent not logged by: bar\n" +
                                        "     but: was ILoggingEvent{level=null, formattedMessage='null', loggedBy=bar, throwable=null}");

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withLoggerName("bar");

        // then
        assertThat(loggingEvent, wasNotLoggedBy("bar"));
    }

    @Test
    public void loggedByMatchesWhenGivenLoggerNameEqualsEventLoggerName() {

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withLoggerName("foo");

        // then
        assertThat(loggingEvent, loggedBy("foo"));
    }

    @Test
    public void loggedByDoesNotMatchWhenGivenLoggerNameDoesNotEqualEventLoggerName() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an ILoggingEvent logged by: foo\n" +
                                        "     but: ILoggingEvent{level=null, formattedMessage='null', loggedBy=bar, throwable=null}");

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withLoggerName("bar");

        // then
        assertThat(loggingEvent, loggedBy("foo"));
    }

    @Test
    public void wasLoggedByMatchesWhenGivenLoggerEqualsEventLoggerName() {

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withLoggerName(this.getClass().getName());

        // then
        assertThat(loggingEvent, wasLoggedBy(this.getClass()));
    }

    @Test
    public void wasLoggedByDoesNotMatchWhenGivenLoggerDoesNotEqualEventLoggerName() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an ILoggingEvent logged by: java.lang.String\n" +
                                        "     but: was ILoggingEvent{level=null, formattedMessage='null', loggedBy=java.lang.Integer, throwable=null}");

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withLoggerName(Integer.class.getName());

        // then
        assertThat(loggingEvent, wasLoggedBy(String.class));
    }

    @Test
    public void wasNotLoggedByMatchesWhenGivenLoggerDoesNotEqualEventLoggerName() {

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withLoggerName(this.getClass().getName());

        // then
        assertThat(loggingEvent, wasNotLoggedBy(String.class));
    }

    @Test
    public void wasNotLoggedByDoesNotMatchWhenGivenLoggerEqualsEventLoggerName() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an ILoggingEvent not logged by: java.lang.Integer\n" +
                                        "     but: was ILoggingEvent{level=null, formattedMessage='null', loggedBy=java.lang.Integer, throwable=null}");

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withLoggerName(Integer.class.getName());

        // then
        assertThat(loggingEvent, wasNotLoggedBy(Integer.class));
    }

    @Test
    public void loggedByMatchesWhenGivenLoggerEqualsEventLoggerName() {

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withLoggerName(this.getClass().getName());

        // then
        assertThat(loggingEvent, loggedBy(this.getClass()));
    }

    @Test
    public void loggedByDoesNotMatchWhenGivenLoggerDoesNotEqualEventLoggerName() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an ILoggingEvent logged by: java.lang.String\n" +
                                        "     but: ILoggingEvent{level=null, formattedMessage='null', loggedBy=java.lang.Integer, throwable=null}");

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withLoggerName(Integer.class.getName());

        // then
        assertThat(loggingEvent, loggedBy(String.class));
    }
}