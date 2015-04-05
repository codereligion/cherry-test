package com.codereligion.cherry.test.hamcrest;

import org.hamcrest.Matcher;
import org.hamcrest.core.SubstringMatcher;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * A matcher which expects a string to contain a given string. This matcher basically does the same as {@link org.hamcrest.core.StringContains}, with the
 * difference that it allows varargs which will be used to format the given string before the actual matching happens.
 *
 * @author Sebastian Gr&ouml;bler
 * @since 05.04.2015
 */
public class StringContains extends SubstringMatcher {

    /**
     * Creates a matcher that matches if the examined {@link String} contains the specified {@link String} anywhere. Optional arguments can be provided which
     * will be used to format the given {@code substring}.
     * <p/>
     * Example usage: {@code assertThat("myStringOfNote", containsString("ring"))}
     *
     * @param substring the substring that the returned matcher will expect to find within any examined string
     * @param args
     * @return
     */
    public static Matcher<String> containsString(final String substring, final Object... args) {
        checkArgument(substring != null, "substring must not be null.");
        return new StringContains(String.format(substring, args));
    }

    private StringContains(final String substring) {
        super(substring);
    }

    @Override
    protected boolean evalSubstringOf(String s) {
        return s.indexOf(substring) >= 0;
    }

    @Override
    protected String relationship() {
        return "containing";
    }
}
