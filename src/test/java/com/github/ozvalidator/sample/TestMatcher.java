package com.github.ozvalidator.sample;

import com.github.ozvalidator.context.ValidationContext;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class TestMatcher extends TypeSafeMatcher<ValidationContext> {
    private String fieldNameToFindInErrors;

    private TestMatcher(String fieldNameToFindInErrors) {
        this.fieldNameToFindInErrors = fieldNameToFindInErrors;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(fieldNameToFindInErrors);
    }

    @Override
    protected boolean matchesSafely(ValidationContext context) {
        return context.hasError() && context.getErrors().stream().anyMatch(error -> error.getField().equals(fieldNameToFindInErrors));
    }

    public static TestMatcher hasErrorFor(String fieldNameToFindInErrors) {
        return new TestMatcher(fieldNameToFindInErrors);
    }
}