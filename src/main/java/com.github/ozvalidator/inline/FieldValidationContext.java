package com.github.ozvalidator.inline;


import com.github.ozvalidator.context.ValidationContext;

import java.util.function.BiConsumer;

public interface FieldValidationContext<T> {

    void validate(BiConsumer<T, ValidationContext>... valueMatchers);
}
