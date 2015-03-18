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
package com.codereligion.cherry.junit.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class LogRecorderTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void recordsSingleEvent() throws Throwable {

        // given
        final String loggerName = "foo";
        final String message = "ermahgerd";
        final LogRecorder logRecorder = LogRecorder.expectedLogs(new LogSpec(loggerName, Level.ERROR));

        // when
        logRecorder.apply(new Statement() {
            @Override
            public void evaluate() throws Throwable {
                final Logger logger = LoggerFactory.getLogger(loggerName);
                logger.error(message);
            }
        }, Description.EMPTY).evaluate();

        // then
        assertThat(logRecorder.event().getMessage(), is(message));
    }

    @Test
    public void recordsMultipleEvents() throws Throwable {

        // given
        final String loggerName = "foo";
        final String firstMessage = "ermahgerd";
        final String secondMessage = "narv!";
        final LogRecorder logRecorder = LogRecorder.expectedLogs(new LogSpec(loggerName, Level.ERROR));

        // when
        logRecorder.apply(new Statement() {
            @Override
            public void evaluate() throws Throwable {
                final Logger logger = LoggerFactory.getLogger(loggerName);
                logger.error(firstMessage);
                logger.error(secondMessage);
            }
        }, Description.EMPTY).evaluate();

        // then
        final List<ILoggingEvent> events = logRecorder.events();
        assertThat(events.get(0).getMessage(), is(firstMessage));
        assertThat(events.get(1).getMessage(), is(secondMessage));

    }

    @Test
    public void doesNotRecordEventsWhenTestIsFinished() throws Throwable {

        // given
        final String loggerName = "foo";
        final LogRecorder logRecorder = LogRecorder.expectedLogs(new LogSpec(loggerName, Level.ERROR));
        logRecorder.apply(mock(Statement.class), Description.EMPTY).evaluate();

        // when
        final Logger logger = LoggerFactory.getLogger(loggerName);
        logger.error("bar");

        // then
        assertThat(logRecorder.events().isEmpty(), is(true));
    }

    @Test
    public void resetsLoggersLogLevelWhenTestIsFinished() throws Throwable {

        // given
        final String loggerName = "foo";
        final Level level = Level.INFO;
        final ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(loggerName);
        logger.setLevel(level);
        final LogRecorder logRecorder = LogRecorder.expectedLogs(new LogSpec(loggerName, Level.ERROR));

        // when
        logRecorder.apply(mock(Statement.class), Description.EMPTY).evaluate();

        // then
        assertThat(logger.getLevel(), is(level));
    }

    @Test
    public void throwsLogRecorderExceptionWhenNoEventWasRecorder() throws Throwable {

        // given
        final LogRecorder logRecorder = LogRecorder.expectedLogs(new LogSpec("foo", Level.ERROR));
        logRecorder.apply(mock(Statement.class), Description.EMPTY).evaluate();

        // expect
        expectedException.expect(LogRecorderException.class);

        // when
        logRecorder.event();
    }


    @Test
    public void lastSpecWins() throws Throwable {

        // given
        final String loggerName = "foo";
        final String message = "ermahgerd";
        final LogRecorder logRecorder = LogRecorder.expectedLogs(new LogSpec(loggerName, Level.INFO), new LogSpec(loggerName, Level.ERROR));

        // when
        logRecorder.apply(new Statement() {
            @Override
            public void evaluate() throws Throwable {
                final Logger logger = LoggerFactory.getLogger(loggerName);
                logger.info("that should not appear");
                logger.error(message);
            }
        }, Description.EMPTY).evaluate();

        // then
        assertThat(logRecorder.event().getMessage(), is(message));
    }

}