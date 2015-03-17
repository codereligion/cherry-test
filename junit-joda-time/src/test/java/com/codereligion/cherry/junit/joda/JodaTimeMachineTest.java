package com.codereligion.cherry.junit.joda;

import org.joda.time.DateTimeUtils;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JodaTimeMachineTest {

    @Test
    public void setsTimeToDateTimeUtils() {

        // given
        final JodaTimeMachine jodaTimeMachine = new JodaTimeMachine();

        // when
        jodaTimeMachine.goToAndStayAt(1);

        // then
        assertThat(DateTimeUtils.currentTimeMillis(), is(1L));
    }

    @Test
    public void resetsTimeAfterExecution() throws Throwable {

        // given
        final JodaTimeMachine jodaTimeMachine = new JodaTimeMachine();
        jodaTimeMachine.goToAndStayAt(1);

        // when
        jodaTimeMachine.apply(emptyStatement(), Description.EMPTY).evaluate();

        // then
        assertThat(DateTimeUtils.currentTimeMillis(), is(not(1L)));
    }

    private Statement emptyStatement() {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {

            }
        };
    }

}