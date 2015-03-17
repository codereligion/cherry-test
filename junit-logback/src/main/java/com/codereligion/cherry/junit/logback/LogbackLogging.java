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
import ch.qos.logback.core.Appender;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.ArgumentCaptor;
import org.mockito.verification.VerificationMode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class LogbackLogging implements TestRule {

    public static LogbackLogging expectedLogs(final LogInfo... logInfos) {
        return new LogbackLogging(logInfos);
    }

    private final Set<LogInfo> logInfos = new HashSet<LogInfo>();

    @SuppressWarnings("unchecked")
    private final Appender<ILoggingEvent> mockAppender = mock(Appender.class);

    private LogbackLogging(final LogInfo[] logInfos) {
        Collections.addAll(this.logInfos, logInfos);
    }

    public Statement apply(final Statement base, final Description description) {
        return statement(base);
    }

    public List<ILoggingEvent> events(final VerificationMode times) {
        final ArgumentCaptor<ILoggingEvent> errorCaptor = ArgumentCaptor.forClass(ILoggingEvent.class);
        verify(mockAppender, times).doAppend(errorCaptor.capture());
        return errorCaptor.getAllValues();
    }

    public ILoggingEvent event() {
        final ArgumentCaptor<ILoggingEvent> errorCaptor = ArgumentCaptor.forClass(ILoggingEvent.class);
        verify(mockAppender).doAppend(errorCaptor.capture());
        return errorCaptor.getValue();
    }

    public void verifyZeroInteractionsOnAppender() {
        verifyZeroInteractions(mockAppender);
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
        for (final LogInfo logInfo : logInfos) {
            logInfo.getLogger().setLevel(logInfo.getLevel());
            logInfo.getLogger().addAppender(mockAppender);
        }
    }

    private void after() {
        for (final LogInfo logInfo : logInfos) {
            logInfo.getLogger().detachAppender(mockAppender);
        }
    }
}