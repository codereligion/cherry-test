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
package com.codereligion.cherry.test.hamcrest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static com.codereligion.cherry.test.hamcrest.StringContains.containsString;
import static org.junit.Assert.assertThat;

/**
 * Tests {@link StringContains}.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 05.04.2015
 */
public class StringContainsTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void containsStringThrowsIllegalArgumentExceptionOnNullString() {

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("substring must not be null");

        // when
        containsString(null);
    }

    @Test
    public void formatsString() {
        assertThat("The bunny jumps over the fence", containsString("The %s jumps over the fence", "bunny"));
    }

    @Test
    public void matchesWhenStringsAreEqual() {
        assertThat("The bunny jumps over the fence", containsString("The bunny jumps over the fence"));
    }

    @Test
    public void doesNotMatchWhenStringDoesNotContainGivenString() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: a string containing \"nope!\"\n" +
                                        "     but: was \"The bunny jumps over the fence\"");

        // when
        assertThat("The bunny jumps over the fence", containsString("nope!"));

    }

    @Test
    public void matchesWhenStringContainsGivenString() {
        assertThat("The bunny jumps over the fence", containsString("jumps over"));
    }

    @Test
    public void matchesWhenStringStartsWithGivenString() {
        assertThat("The bunny jumps over the fence", containsString("The bunny"));
    }

    @Test
    public void matchesWhenStringEndsWithGivenString() {
        assertThat("The bunny jumps over the fence", containsString("over the fence"));
    }

    @Test
    public void matchesCaseSensitive() {

        // expect
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Expected: a string containing \"THE bunny jumps over the fence\"\n" +
                                        "     but: was \"The bunny jumps over the fence\"");

        // when
        assertThat("The bunny jumps over the fence", containsString("THE bunny jumps over the fence"));
    }

}