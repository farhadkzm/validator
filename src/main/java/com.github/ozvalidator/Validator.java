package com.github.ozvalidator;


import com.github.ozvalidator.context.ValidationContext;

public interface Validator<T> {

    void validate(T object, ValidationContext context);
}
