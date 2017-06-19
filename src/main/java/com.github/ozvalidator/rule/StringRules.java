package com.github.ozvalidator.rule;


import com.github.ozvalidator.inline.FieldValidator;
import com.github.ozvalidator.GenericErrorMessageKeys;

import static com.github.ozvalidator.ErrorMessageParameterNames.LENGTH;
import static com.github.ozvalidator.ErrorMessageParameterNames.MAX;
import static com.github.ozvalidator.ErrorMessageParameterNames.MIN;
import static com.github.ozvalidator.MessageParameter.param;
import static com.github.ozvalidator.rule.CommonRules.ignoreNull;

public final class StringRules {

    private StringRules() {
    }

    public static FieldValidator<String> minLength(int minLength) {
        return ignoreNull((fieldValue, vc) -> {
            if (fieldValue.length() < minLength) {
                vc.addError(GenericErrorMessageKeys.INVALID_FIELD_MIN_CHARACTERS, param(MIN, minLength));
            }
        });

    }

    public static FieldValidator<String> maxLength(int maxLength) {

        return ignoreNull((fieldValue, vc) -> {
            if (fieldValue.length() > maxLength) {
                vc.addError(GenericErrorMessageKeys.INVALID_FIELD_MAX_CHARACTERS, param(MAX, maxLength));
            }
        });
    }

    public static FieldValidator<String> range(int minLength, int maxLength) {

        return ignoreNull((fieldValue, vc) -> {
            int length = fieldValue.length();
            if (length > maxLength || length < minLength) {
                vc.addError(GenericErrorMessageKeys.INVALID_FIELD_RANGE_CHARACTERS, param(MIN, minLength), param(MAX, maxLength));
            }
        });
    }


    public static FieldValidator<String> length(int length) {
        return ignoreNull((fieldValue, vc) -> {
            if (fieldValue.length() != length) {
                vc.addError(GenericErrorMessageKeys.INVALID_FIELD_LENGTH, param(LENGTH, length));
            }
        });


    }


}
