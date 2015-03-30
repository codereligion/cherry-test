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
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventHasThrowable.doesNotHaveThrowable;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventHasThrowable.hasThrowable;
import static org.junit.Assert.assertThat;

/**
 * TODO write test for the other two factory methods
 * Tests {@link LoggingEventHasThrowable}.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 30.03.2015
 */
public class LoggingEventHasThrowableTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void hasThrowableMatchesWhenBothThrowablesAreNull() {

        // given
        final LoggingEvent loggingEvent = new LoggingEvent();

        // then
        assertThat(loggingEvent, hasThrowable(null));
    }

    @Test
    public void hasThrowableDoesNotMatchWhenEventThrowableIsNull() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an ILoggingEvent with a throwable matching: java.lang.RuntimeException{message='opsi!'}\n" +
                                        "     but: was ILoggingEvent{level=null, formattedMessage='null', loggedBy=null, throwable=null}");

        // given
        final LoggingEvent loggingEvent = new LoggingEvent();

        // then
        assertThat(loggingEvent, hasThrowable(new RuntimeException("opsi!")));
    }

    @Test
    public void hasThrowableDoesNotMatchWhenGivenThrowableIsNull() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an ILoggingEvent with a throwable matching: null\n" +
                                        "     but: was ILoggingEvent{level=null, formattedMessage='null', loggedBy=null, throwable=java.lang.RuntimeException{message='opsi!'}}");

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withThrowable(new RuntimeException("opsi!"));

        // then
        assertThat(loggingEvent, hasThrowable(null));
    }

    @Test
    public void hasThrowableMatchesWhenThrowableClassesAreEqual() {

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withThrowable(new RuntimeException());

        // then
        assertThat(loggingEvent, hasThrowable(new RuntimeException()));
    }

    @Test
    public void hasThrowableDoesNotMatchWhenThrowableClassesAreNotEqual() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an ILoggingEvent with a throwable matching: java.lang.NullPointerException{message='opsi!'}\n" +
                                        "     but: was ILoggingEvent{level=null, formattedMessage='null', loggedBy=null, throwable=java.lang.IllegalArgumentException{message='opsi!'}}");

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withThrowable(new IllegalArgumentException("opsi!"));

        // then
        assertThat(loggingEvent, hasThrowable(new NullPointerException("opsi!")));
    }

    @Test
    public void hasThrowableMatchesWhenThrowableMessagesAreEqual() {

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withThrowable(new NullPointerException("opsi!"));

        // then
        assertThat(loggingEvent, hasThrowable(new NullPointerException("opsi!")));
    }

    @Test
    public void hasThrowableMatchesWhenThrowableMessagesAreNull() {

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withThrowable(new NullPointerException(null));

        // then
        assertThat(loggingEvent, hasThrowable(new NullPointerException(null)));
    }

    @Test
    public void hasThrowableDoesNotMatchWhenThrowableMessageAreNotEqual() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an ILoggingEvent with a throwable matching: java.lang.NullPointerException{message='opsi!'}\n" +
                                        "     but: was ILoggingEvent{level=null, formattedMessage='null', loggedBy=null, throwable=java.lang.NullPointerException{message='nope!'}}");

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withThrowable(new NullPointerException("nope!"));

        // then
        assertThat(loggingEvent, hasThrowable(new NullPointerException("opsi!")));
    }

    @Test
    public void doesNotHaveThrowableWithDoesNotMatchWhenBothThrowablesAreNull() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an ILoggingEvent with a throwable not matching: null\n" +
                                        "     but: was ILoggingEvent{level=null, formattedMessage='null', loggedBy=null, throwable=null}");

        // given
        final LoggingEvent loggingEvent = new LoggingEvent();

        // then
        assertThat(loggingEvent, doesNotHaveThrowable(null));
    }

    @Test
    public void doesNotHaveThrowableMatchesEventThrowableIsNull() {

        // given
        final LoggingEvent loggingEvent = new LoggingEvent();

        // then
        assertThat(loggingEvent, doesNotHaveThrowable(new RuntimeException("opsi!")));
    }

    @Test
    public void doesNotHaveThrowableMatchesWhenGivenThrowableIsNull() {

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withThrowable(new RuntimeException("opsi!"));

        // then
        assertThat(loggingEvent, doesNotHaveThrowable(null));
    }

    @Test
    public void doesNotHaveThrowableDoesNotMatchWhenThrowableClassesAreEqual() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an ILoggingEvent with a throwable not matching: java.lang.RuntimeException{message=null}\n" +
                                        "     but: was ILoggingEvent{level=null, formattedMessage='null', loggedBy=null, throwable=java.lang.RuntimeException{message=null}}");

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withThrowable(new RuntimeException());

        // then
        assertThat(loggingEvent, doesNotHaveThrowable(new RuntimeException()));
    }

    @Test
    public void doesNotHaveThrowableMatchesWhenThrowableClassesAreNotEqual() {

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withThrowable(new IllegalArgumentException("opsi!"));

        // then
        assertThat(loggingEvent, doesNotHaveThrowable(new NullPointerException("opsi!")));
    }

    @Test
    public void doesNotHaveThrowableDoesNotMatchWhenThrowableMessagesAreEqual() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an ILoggingEvent with a throwable not matching: java.lang.NullPointerException{message='opsi!'}\n" +
                                        "     but: was ILoggingEvent{level=null, formattedMessage='null', loggedBy=null, throwable=java.lang.NullPointerException{message='opsi!'}}");

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withThrowable(new NullPointerException("opsi!"));

        // then
        assertThat(loggingEvent, doesNotHaveThrowable(new NullPointerException("opsi!")));
    }

    @Test
    public void doesNotHaveThrowableDoesNotMatchWhenThrowableMessagesAreNull() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an ILoggingEvent with a throwable not matching: java.lang.NullPointerException{message=null}\n" +
                                        "     but: was ILoggingEvent{level=null, formattedMessage='null', loggedBy=null, throwable=java.lang.NullPointerException{message=null}}");

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withThrowable(new NullPointerException(null));

        // then
        assertThat(loggingEvent, doesNotHaveThrowable(new NullPointerException(null)));
    }

    @Test
    public void doesNotHaveThrowableMatchesWhenThrowableMessageAreNotEqual() {

        // given
        final LoggingEvent loggingEvent = new LoggingEvent().withThrowable(new NullPointerException("nope!"));

        // then
        assertThat(loggingEvent, doesNotHaveThrowable(new NullPointerException("opsi!")));
    }
}