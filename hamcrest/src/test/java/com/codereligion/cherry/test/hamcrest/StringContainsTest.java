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