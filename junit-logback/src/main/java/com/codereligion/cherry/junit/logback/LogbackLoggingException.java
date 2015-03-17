package com.codereligion.cherry.junit.logback;

/**
 * Used when the logback logging test emits an error.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 17.03.2015
 */
public class LogbackLoggingException extends RuntimeException {

    public LogbackLoggingException(final String message) {
        super(message);
    }
}
