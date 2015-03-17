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
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * JUnit rule which temporarily adds an in-memory recording appender to the loggers specified by the given {@link
 * com.codereligion.cherry.junit.logback.LogSpec}.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 17.03.2015
 */
public class LogRecorder implements TestRule {

    /**
     * Creates a log recorder rule which records events for all logs specified by the given {@link com.codereligion.cherry.junit.logback.LogSpec}.
     * If the same logger is specified multiple times with a different log level, then the last spec wins.
     *
     * @param logSpecs
     * @return
     */
    public static LogRecorder expectedLogs(final LogSpec... logSpecs) {
        return new LogRecorder(logSpecs);
    }

    private final Set<LogSpec> logSpecs = new HashSet<LogSpec>();
    private final Map<Logger, Level> predivousLogLevels = new HashMap<Logger, Level>();

    @SuppressWarnings("unchecked")
    private final RecordingAppender recordingAppender = new RecordingAppender();

    private LogRecorder(final LogSpec... logSpecs) {
        Collections.addAll(this.logSpecs, logSpecs);
    }

    public Statement apply(final Statement base, final Description description) {
        return statement(base);
    }

    /**
     * @return all recorded events
     */
    public List<ILoggingEvent> events() {
        return recordingAppender.getEvents();
    }

    /**
     * @return the first recorded event
     * @throws LogRecorderException when no event was recorded
     */
    public ILoggingEvent event() {
        if (recordingAppender.getEvents().isEmpty()) {
            throw new LogRecorderException("No event was recorded during the test execution.");
        }

        return recordingAppender.getEvents().get(0);
    }

    private Statement statement(final Statement base) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                before();
                try {
                    base.evaluate();
                } finally {
                    after();
                }
            }
        };
    }

    private void before() throws Throwable {
        for (final LogSpec logSpec : logSpecs) {
            final Logger logger = logSpec.getLogger();
            predivousLogLevels.put(logger, logger.getLevel());
            logger.setLevel(logSpec.getLevel());
            logger.addAppender(recordingAppender);
        }
    }

    private void after() {
        for (final LogSpec logSpec : logSpecs) {
            final Logger logger = logSpec.getLogger();
            logger.setLevel(predivousLogLevels.get(logger));
            logger.detachAppender(recordingAppender);
        }
    }
}