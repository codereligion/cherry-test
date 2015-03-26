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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventHasLevel.doesNotHaveLevel;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventHasLevel.hasLevel;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventHasLevel.withLevel;
import static org.junit.Assert.assertThat;

public class LoggingEventHasLevelTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void hasLevelThrowsIllegalArgumentExceptionOnNullValue() {

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("level must not be null.");

        // when
        hasLevel(null);
    }

    @Test
    public void doesNotHaveLevelThrowsIllegalArgumentExceptionOnNullValue() {

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("level must not be null.");

        // when
        doesNotHaveLevel(null);
    }

    @Test
    public void withLevelThrowsIllegalArgumentExceptionOnNullValue() {

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("level must not be null.");

        // when
        withLevel(null);
    }

    @Test
    public void hasLevelMatchesWhenGivenLevelEqualsEventLevel() {

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withLevel(Level.ERROR);

        // then
        assertThat(loggingEvent, hasLevel(Level.ERROR));
    }

    @Test
    public void hasLevelDoesNotMatchWhenGivenLevelDoesNotEqualEventLevel() {

        // expected
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an ILoggingEvent with level: ERROR\n" +
                                        "     but: was ILoggingEvent{level=INFO, formattedMessage='null', loggedBy=null, throwable=null}");

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withLevel(Level.INFO);

        // then
        assertThat(loggingEvent, hasLevel(Level.ERROR));
    }

    @Test
    public void doesNotHaveLevelDoesNotMatchWhenGivenLevelIsEqualEventLevel() {

        // expected
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an ILoggingEvent with level other than: INFO\n" +
                                        "     but: was ILoggingEvent{level=INFO, formattedMessage='null', loggedBy=null, throwable=null}");

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withLevel(Level.INFO);

        // then
        assertThat(loggingEvent, doesNotHaveLevel(Level.INFO));
    }

    @Test
    public void withLevelDoesNotMatchWhenGivenLevelDoesNotEqualEventLevel() {

        // expected
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an ILoggingEvent with level: INFO\n" +
                                        "     but: ILoggingEvent{level=ERROR, formattedMessage='null', loggedBy=null, throwable=null}");

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withLevel(Level.ERROR);

        // then
        assertThat(loggingEvent, withLevel(Level.INFO));
    }
}