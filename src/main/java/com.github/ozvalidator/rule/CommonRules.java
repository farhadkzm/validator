package com.github.ozvalidator.rule;


import com.github.ozvalidator.inline.FieldValidator;
import com.github.ozvalidator.GenericErrorMessageKeys;
import com.github.ozvalidator.context.ValidationContext;
import org.apache.commons.collections4.CollectionUtils;

import java.util.function.BiConsumer;
import java.util.regex.Pattern;

import static com.github.ozvalidator.ErrorMessageParameterNames.VALUE;
import static com.github.ozvalidator.MessageParameter.param;

public final class CommonRules {


    public static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    private CommonRules() {
    }

    /**
     * Calls the given matcher only when the object to be checked is not null, otherwise doesn't call the mather.
     */
    static <U> FieldValidator<U> ignoreNull(BiConsumer<U, ValidationContext> matcher) {
        return (valueToBeChecked, validationContext) -> {
            if (valueToBeChecked != null) {
                matcher.accept(valueToBeChecked, validationContext);
            }
        };
    }

    public static FieldValidator<String> email() {
        return ignoreNull((fieldValue, vc) -> {
            if (!EMAIL_PATTERN.matcher(fieldValue).matches()) {
                vc.addError(GenericErrorMessageKeys.INVALID_CONTACT_EMAIL);
            }

        });
    }

    /**
     * Checks presence of the object with following rules:
     * <ul>
     * <li>String: should not be blank
     * <li> Collection: should not be empty
     * <li>array of primitives or objects: should not be empty
     * </ul>
     * Also if the given object should not be null otherwise a missing error is added to the context.
     */
    public static <U> FieldValidator<U> mandatory() {
        return (valueToBeChecked, validationContext) -> {
            if (valueToBeChecked == null || isEmptyIterable(valueToBeChecked) || isBlankString(valueToBeChecked)) {
                validationContext.addError(GenericErrorMessageKeys.INVALID_FIELD_MISSING);
            }
        };
    }

    private static boolean isBlankString(Object obj) {
        return obj instanceof CharSequence && obj.toString().trim().isEmpty();

    }

    private static boolean isEmptyIterable(Object obj) {
        try {
            return CollectionUtils.sizeIsEmpty(obj);
        } catch (IllegalArgumentException ignored) { //in case object is not iterable(i.e. it's not of type array, map, etc.)
            return false;
        }

    }

    public static <U> FieldValidator<U> constant(U constant) {
        return ignoreNull((fieldValue, vc) -> {
            if (!constant.equals(fieldValue)) {
                vc.addError(GenericErrorMessageKeys.INVALID_FIELD_SINGLE_VALUE, param(VALUE, constant));
            }
        });
    }
}
