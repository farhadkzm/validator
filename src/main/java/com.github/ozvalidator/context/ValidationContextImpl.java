package com.github.ozvalidator.context;


import com.github.ozvalidator.ErrorMessageParameterNames;
import com.github.ozvalidator.MessageParameter;
import com.github.ozvalidator.model.Error;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class ValidationContextImpl implements ValidationContext {

    private static final String FIELD_NAME_PLACEHOLDER = String.format("{%s}", ErrorMessageParameterNames.FIELD_NAME);

    private final Object objectUnderValidation;
    private final MessageTranslator messageKeyTranslator;
    private final ValidationErrorFactory validationErrorFactory;
    private final List<Error> errors = new LinkedList<>();

    public ValidationContextImpl(Object objectUnderValidation,
                                 MessageTranslator messageKeyTranslator,
                                 ValidationErrorFactory validationErrorFactory) {
        this.objectUnderValidation = objectUnderValidation;
        this.messageKeyTranslator = messageKeyTranslator;
        this.validationErrorFactory = validationErrorFactory;
    }


    private Error createError(String field, String messageKey, MessageParameter... params) {
        String message = interpolateErrorMessage(messageKey, field, params);
        return createError(field, message);
    }

    private Error createError(String field, String errorMessage) {
        return validationErrorFactory.create(field, errorMessage);
    }


    private String interpolateErrorMessage(String messageKey, String field, MessageParameter... params) {
        String message = messageKeyTranslator.getMessage(messageKey);

        for (MessageParameter param : params) {
            message = StringUtils.replace(message, param.getMessageKey(), param.getValue());
        }
        //field is actually a complete field path,
        // e.g. shipment[2].items[2].description. So we need to extract the last part (in this case description)
        String fieldName = field.substring(field.lastIndexOf(".") + 1);
        return StringUtils.replace(message, FIELD_NAME_PLACEHOLDER, fieldName);
    }

    @Override
    public <T> T getObjectUnderValidation(Class<T> requiredType) {
        return (T) objectUnderValidation;
    }

    @Override
    public void addError(String messageKey, MessageParameter... arguments) {
        throw new IllegalStateException(
                "The field path has not been configured. Use method withField to set a valid field path");
    }

    @Override
    public List<Error> getErrors() {
        return errors;
    }


    @Override
    public boolean hasError() {
        return !errors.isEmpty();
    }


    public ValidationContext withField(String path) {
        return new FieldNameAwareValidationContextImpl(path);
    }

    public ValidationContext withField(String fieldName, int index) {
        return new FieldNameAwareValidationContextImpl(String.format("%s[%s]", fieldName, index));
    }


    private final class FieldNameAwareValidationContextImpl implements ValidationContext {

        private String fieldPath;

        private FieldNameAwareValidationContextImpl(String newfieldPath) {
            this.fieldPath = newfieldPath;
        }


        @Override
        public void addError(String messageKey, MessageParameter... arguments) {
            getErrors().add(createError(fieldPath, messageKey, arguments));
        }


        @Override
        public <T> T getObjectUnderValidation(Class<T> requiredType) {
            return ValidationContextImpl.this.getObjectUnderValidation(requiredType);
        }


        @Override
        public ValidationContext withField(String path) {
            return ValidationContextImpl.this.withField(getCompleteFieldPath(path));
        }

        @Override
        public ValidationContext withField(String path, int index) {
            return ValidationContextImpl.this.withField(getCompleteFieldPath(path), index);
        }

        @Override
        public List<Error> getErrors() {
            return ValidationContextImpl.this.getErrors();
        }

        @Override
        public boolean hasError() {
            return ValidationContextImpl.this.hasError();
        }

        private String getCompleteFieldPath(String field) {
            return StringUtils.join(new String[]{fieldPath, field}, ".");
        }

    }
}
