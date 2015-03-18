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
package com.codereligion.cherry.junit.joda;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests {@link TimeMachine}.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 17.03.2015
 */
public class TimeMachineTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void setsTimeToDateTimeUtilsUsingMillis() {

        // given
        final TimeMachine timeMachine = new TimeMachine();

        // when
        timeMachine.goToAndStayAt(1);

        // then
        assertThat(DateTimeUtils.currentTimeMillis(), is(1L));
    }

    @Test
    public void setsTimeToDateTimeUtilsUsingDateTime() {

        // given
        final TimeMachine timeMachine = new TimeMachine();

        // when
        timeMachine.goToAndStayAt(new DateTime(1));

        // then
        assertThat(DateTimeUtils.currentTimeMillis(), is(1L));
    }

    @Test
    public void usingNullForDateTimeCausesIllegalArgumentException() {

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("dateTime must not be null");

        // when
        new TimeMachine().goToAndStayAt(null);
    }

    @Test
    public void resetsTimeAfterExecution() throws Throwable {

        // given
        final TimeMachine timeMachine = new TimeMachine();
        timeMachine.goToAndStayAt(1);

        // when
        timeMachine.apply(mock(Statement.class), Description.EMPTY).evaluate();

        // then
        assertThat(DateTimeUtils.currentTimeMillis(), is(not(1L)));
    }
}