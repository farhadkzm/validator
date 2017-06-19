package com.github.ozvalidator.inline;


import com.github.ozvalidator.context.ValidationContext;

import java.util.function.BiConsumer;

/**
 * A marker interface to be used as a return type of validating functions.
 */
public interface FieldValidator<T> extends BiConsumer<T, ValidationContext> {
}
