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
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * JUnit rule which records all events emitted by the loggers specified in the given {@link com.codereligion.cherry.junit.logback.LogSpec} at the specified log
 * level.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 17.03.2015
 */
public class LogRecorder implements TestRule {

    /**
     * Creates a log recorder rule which records events for all logs specified by the given {@link com.codereligion.cherry.junit.logback.LogSpec}. If the same
     * logger is specified multiple times with a different log level, then the last spec wins.
     *
     * @param logSpecs the to be expected logs
     * @return a new {@link com.codereligion.cherry.junit.logback.LogRecorder}
     */
    public static LogRecorder expectedLogs(final LogSpec... logSpecs) {
        return new LogRecorder(logSpecs);
    }

    private final Set<LogSpec> logSpecs = Sets.newLinkedHashSet();
    private final Map<Logger, Level> previousLogLevels = Maps.newHashMap();

    @SuppressWarnings("unchecked")
    private final ListAppender<ILoggingEvent> listAppender = new ListAppender<ILoggingEvent>();

    private LogRecorder(final LogSpec... logSpecs) {
        Collections.addAll(this.logSpecs, logSpecs);
        checkArgument(!this.logSpecs.contains(null), "logSpec must not be null.");
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
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

    /**
     * Returns all events recorded during the test execution in the order they were logged.
     *
     * @return all recorded events
     */
    public List<ILoggingEvent> events() {
        return listAppender.getList();
    }

    /**
     * Returns the first event recorded during the test execution, or throws an {@link java.lang.AssertionError}, in case no event was recorded.
     *
     * @return the first recorded event
     * @throws java.lang.AssertionError when no event was recorded
     */
    public ILoggingEvent event() {
        if (listAppender.getList().isEmpty()) {
            throw new AssertionError("No event was recorded during the test execution.");
        }

        return listAppender.getList().get(0);
    }

    private void before() throws Throwable {
        for (final LogSpec logSpec : logSpecs) {
            final Logger logger = logSpec.getLogger();
            previousLogLevels.put(logger, logger.getLevel());
            logger.setLevel(logSpec.getLevel());
            logger.addAppender(listAppender);
        }
        listAppender.start();
    }

    private void after() {
        listAppender.stop();
        for (final LogSpec logSpec : logSpecs) {
            final Logger logger = logSpec.getLogger();
            logger.setLevel(previousLogLevels.get(logger));
            logger.detachAppender(listAppender);
        }
    }
}
