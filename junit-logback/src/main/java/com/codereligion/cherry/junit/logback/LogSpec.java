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
import org.slf4j.LoggerFactory;

/**
 * Holds a {@link ch.qos.logback.classic.Logger} and a {@link ch.qos.logback.classic.Level} which specify the log events
 * which should be recorded.
 */
public class LogSpec {

    private final Level level;
    private final Logger logger;

    public LogSpec(final Class<?> type, final Level level) {
        this(type.getName(), level);
    }

    public LogSpec(final String loggerName, final Level level) {
        this.level = level;
        this.logger = (Logger) LoggerFactory.getLogger(loggerName);
    }

    public Level getLevel() {
        return level;
    }

    public Logger getLogger() {
        return logger;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final LogSpec logSpec = (LogSpec) o;

        if (level != null ? !level.equals(logSpec.level) : logSpec.level != null) return false;
        if (logger != null ? !logger.equals(logSpec.logger) : logSpec.logger != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = level != null ? level.hashCode() : 0;
        result = 31 * result + (logger != null ? logger.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LogInfo{");
        sb.append("level=").append(level);
        sb.append(", logger=").append(logger);
        sb.append('}');
        return sb.toString();
    }
}
