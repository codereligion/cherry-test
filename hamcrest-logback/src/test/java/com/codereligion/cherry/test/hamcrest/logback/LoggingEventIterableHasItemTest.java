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
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventHasLevel.withLevel;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventIterableHasItem.hasItem;
import static com.codereligion.cherry.test.hamcrest.logback.LoggingEventIterableHasItem.hasNoItem;
import static org.junit.Assert.assertThat;

/**
 * TODO document
 */
public class LoggingEventIterableHasItemTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void hasItemMatchesWhenAtLeastOneItemMatches() {

        // given
        final LoggingEvent first = new LoggingEvent().withLevel(Level.INFO);
        final LoggingEvent second = new LoggingEvent().withLevel(Level.ERROR);
        final List<ILoggingEvent> events = Lists.<ILoggingEvent>newArrayList(first, second);

        // then
        assertThat(events, hasItem(withLevel(Level.ERROR)));
    }

    @Test
    public void hasItemDoesNotMatchWhenThereIsNoItemMatching() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an iterable containing an ILoggingEvent with level: ERROR\n" +
                                        "     but: iterable contained [ILoggingEvent{level=INFO, formattedMessage='null', loggedBy=null, throwable=null}, ILoggingEvent{level=WARN, formattedMessage='null', loggedBy=null, throwable=null}]");

        // given
        final LoggingEvent first = new LoggingEvent().withLevel(Level.INFO);
        final LoggingEvent second = new LoggingEvent().withLevel(Level.WARN);
        final List<ILoggingEvent> events = Lists.<ILoggingEvent>newArrayList(first, second);

        // then
        assertThat(events, hasItem(withLevel(Level.ERROR)));
    }

    @Test
    public void hasNoItemMatchesWhenThereIsNoItemMatching() {

        // given
        final LoggingEvent first = new LoggingEvent().withLevel(Level.INFO);
        final LoggingEvent second = new LoggingEvent().withLevel(Level.WARN);
        final List<ILoggingEvent> events = Lists.<ILoggingEvent>newArrayList(first, second);

        // then
        assertThat(events, hasNoItem(withLevel(Level.ERROR)));
    }

    @Test
    public void hasNotItemDoesNotMatchWhenThereIsAtLeastOneItemMatching() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: an iterable not containing an ILoggingEvent with level: ERROR\n" +
                                        "     but: iterable contained ILoggingEvent{level=ERROR, formattedMessage='null', loggedBy=null, throwable=null}");

        // given
        final LoggingEvent first = new LoggingEvent().withLevel(Level.INFO);
        final LoggingEvent second = new LoggingEvent().withLevel(Level.ERROR);
        final List<ILoggingEvent> events = Lists.<ILoggingEvent>newArrayList(first, second);

        // then
        assertThat(events, hasNoItem(withLevel(Level.ERROR)));
    }

}