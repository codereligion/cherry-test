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
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggerContextVO;
import ch.qos.logback.classic.spi.ThrowableProxy;
import java.util.Map;
import org.slf4j.Marker;

/**
 * Pojo implementation of {@link ILoggingEvent}.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 30.03.2015
 */
public class LoggingEvent implements ILoggingEvent {


    private String threadName;
    private Level level;
    private String message;
    private String loggerName;
    private IThrowableProxy throwableProxy;

    @Override
    public String getThreadName() {
        return threadName;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Object[] getArgumentArray() {
        return new Object[0];
    }

    @Override
    public String getFormattedMessage() {
        return message;
    }

    @Override
    public String getLoggerName() {
        return loggerName;
    }

    @Override
    public LoggerContextVO getLoggerContextVO() {
        return null;
    }

    @Override
    public IThrowableProxy getThrowableProxy() {
        return throwableProxy;
    }

    @Override
    public StackTraceElement[] getCallerData() {
        return new StackTraceElement[0];
    }

    @Override
    public boolean hasCallerData() {
        return false;
    }

    @Override
    public Marker getMarker() {
        return null;
    }

    @Override
    public Map<String, String> getMDCPropertyMap() {
        return null;
    }

    @Override
    public Map<String, String> getMdc() {
        return null;
    }

    @Override
    public long getTimeStamp() {
        return 0;
    }

    @Override
    public void prepareForDeferredProcessing() {

    }

    public LoggingEvent withThreadName(final String threadName) {
        this.threadName = threadName;
        return this;
    }

    public LoggingEvent withLevel(final Level level) {
        this.level = level;
        return this;
    }

    public LoggingEvent withMessage(final String message) {
        this.message = message;
        return this;
    }

    public LoggingEvent withLoggerName(final String loggerName) {
        this.loggerName = loggerName;
        return this;
    }

    public LoggingEvent withThrowable(final Throwable throwable) {
        this.throwableProxy = new ThrowableProxy(throwable);
        return this;
    }


}
