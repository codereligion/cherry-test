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
import com.google.common.base.Objects;
import org.slf4j.LoggerFactory;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * Holds a {@link ch.qos.logback.classic.Logger} and a {@link ch.qos.logback.classic.Level} which specify the log events which should be recorded.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 17.03.2015
 */
public class LogSpec {

    private final Level level;
    private final Logger logger;

    /**
     * Creates a new instance which will fetch the {@link ch.qos.logback.classic.Logger} specified by the given {@code type} and which will use the given {@code
     * level}.
     *
     * @param type  the type which identifies the logger
     * @param level the level to use
     * @throws java.lang.IllegalArgumentException when any of the given arguments are {@code null}
     */
    public LogSpec(final Class<?> type, final Level level) {

        checkArgument(type != null, "type must not be null.");
        checkArgument(level != null, "level must not be null.");

        this.level = level;
        this.logger = (Logger) LoggerFactory.getLogger(type);
    }

    /**
     * Creates a new instance which will fetch the {@link ch.qos.logback.classic.Logger} specified by the given {@code loggerName} and which will use the given
     * {@code level}.
     *
     * @param loggerName the name which identifies the logger
     * @param level      the level to use
     * @throws java.lang.IllegalArgumentException when any of the given arguments are {@code null}
     */
    public LogSpec(final String loggerName, final Level level) {

        checkArgument(loggerName != null, "loggerName must not be null.");
        checkArgument(level != null, "level must not be null.");

        this.level = level;
        this.logger = (Logger) LoggerFactory.getLogger(loggerName);
    }

    /**
     * The {@link ch.qos.logback.classic.Level} associated witht this spec.
     *
     * @return the level
     */
    public Level getLevel() {
        return level;
    }

    /**
     * The {@link ch.qos.logback.classic.Logger} associated with this spec.
     *
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(level, logger);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final LogSpec other = (LogSpec) obj;
        return Objects.equal(this.level, other.level) && Objects.equal(this.logger, other.logger);
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
