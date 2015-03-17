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

import org.joda.time.DateTimeUtils;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Test for {@link com.codereligion.cherry.junit.joda.JodaTimeMachine}
 *
 * @author Sebastian Gr&ouml;bler
 * @since 17.03.2015
 */
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
        jodaTimeMachine.apply(mock(Statement.class), Description.EMPTY).evaluate();

        // then
        assertThat(DateTimeUtils.currentTimeMillis(), is(not(1L)));
    }
}