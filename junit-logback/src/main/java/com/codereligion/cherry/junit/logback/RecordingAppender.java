package com.codereligion.cherry.junit.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.LogbackException;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.status.Status;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores all events it receives.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 17.03.2015
 */
class RecordingAppender implements Appender<ILoggingEvent> {

    private final List<ILoggingEvent> events = new ArrayList<ILoggingEvent>();

    @Override
    public void doAppend(final ILoggingEvent event) throws LogbackException {
        events.add(event);
    }

    public List<ILoggingEvent> getEvents() {
        return events;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(final String name) {

    }

    @Override
    public void setContext(final Context context) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void addStatus(final Status status) {

    }

    @Override
    public void addInfo(final String msg) {

    }

    @Override
    public void addInfo(final String msg, final Throwable ex) {

    }

    @Override
    public void addWarn(final String msg) {

    }

    @Override
    public void addWarn(final String msg, final Throwable ex) {

    }

    @Override
    public void addError(final String msg) {

    }

    @Override
    public void addError(final String msg, final Throwable ex) {

    }

    @Override
    public void addFilter(final Filter<ILoggingEvent> newFilter) {

    }

    @Override
    public void clearAllFilters() {

    }

    @Override
    public List<Filter<ILoggingEvent>> getCopyOfAttachedFiltersList() {
        return null;
    }

    @Override
    public FilterReply getFilterChainDecision(final ILoggingEvent event) {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isStarted() {
        return false;
    }
}
