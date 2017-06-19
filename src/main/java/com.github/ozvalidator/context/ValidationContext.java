package com.github.ozvalidator.context;


import com.github.ozvalidator.MessageParameter;
import com.github.ozvalidator.model.Error;

import java.util.List;

public interface ValidationContext {

    void addError(String messageKey, MessageParameter... arguments);

    ValidationContext withField(String fieldPath);

    ValidationContext withField(String fieldPath, int index);

    List<Error> getErrors();

    boolean hasError();

    <T> T getObjectUnderValidation(Class<T> requiredType);

}
