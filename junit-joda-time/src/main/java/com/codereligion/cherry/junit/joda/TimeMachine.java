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
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * JUnit rule which allows to manipulate the time returned by various {@link org.joda.time.DateTimeUtils} methods and which will reset the time to the system
 * time after test execution.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 17.03.2015
 */
public class TimeMachine implements TestRule {

    @Override
    public Statement apply(final Statement base, final Description description) {
        return statement(base);
    }

    public void goToAndStayAt(final long millis) {
        DateTimeUtils.setCurrentMillisFixed(millis);
    }

    public void goToAndStayAt(final DateTime dateTime) {

        checkArgument(dateTime != null, "dateTime must not be null.");

        DateTimeUtils.setCurrentMillisFixed(dateTime.getMillis());
    }

    private Statement statement(final Statement base) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } finally {
                    DateTimeUtils.setCurrentMillisSystem();
                }
            }
        };
    }
}

