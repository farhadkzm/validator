package com.github.ozvalidator.inline;


import com.github.ozvalidator.context.ValidationContext;

import java.util.Arrays;
import java.util.function.BiConsumer;

public final class BasicFieldValidationContext<T> implements FieldValidationContext<T> {
    private final T value;
    private final ValidationContext vc;

    private BasicFieldValidationContext(T value, ValidationContext vc) {
        this.value = value;
        this.vc = vc;
    }

    public static <T> BasicFieldValidationContext<T> field(T field, String fieldName, ValidationContext vc) {
        return new BasicFieldValidationContext<>(field, vc.withField(fieldName));
    }

    public static <T> BasicFieldValidationContext<T> field(T field, ValidationContext vc) {
        return new BasicFieldValidationContext<>(field, vc);
    }

    @Override
    @SafeVarargs
    public final void validate(BiConsumer<T, ValidationContext>... valueMatchers) {
        Arrays.asList(valueMatchers).forEach(matcher -> matcher.accept(value, vc));
    }

}
