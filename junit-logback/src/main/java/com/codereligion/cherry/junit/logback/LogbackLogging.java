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

import ch.qos.logback.classic.spi.ILoggingEvent;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
public class LogbackLogging implements TestRule {

    public static LogbackLogging expectedLogs(final LogSpec... logSpecs) {
        return new LogbackLogging(logSpecs);
    }

    private final Set<LogSpec> logSpecs = new HashSet<LogSpec>();

    @SuppressWarnings("unchecked")
    private final RecordingAppender recordingAppender = new RecordingAppender();

    private LogbackLogging(final LogSpec... logSpecs) {
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
     * @throws com.codereligion.cherry.junit.logback.LogbackLoggingException when no event was recorded
     */
    public ILoggingEvent event() {
        if (recordingAppender.getEvents().isEmpty()) {
            throw new LogbackLoggingException("No events were recorded during the test execution.");
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
            logSpec.getLogger().setLevel(logSpec.getLevel());
            logSpec.getLogger().addAppender(recordingAppender);
        }
    }

    private void after() {
        for (final LogSpec logSpec : logSpecs) {
            logSpec.getLogger().detachAppender(recordingAppender);
        }
    }
}