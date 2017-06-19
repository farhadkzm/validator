package com.github.ozvalidator.rule;


import com.github.ozvalidator.inline.FieldValidator;
import com.github.ozvalidator.GenericErrorMessageKeys;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.github.ozvalidator.rule.CommonRules.ignoreNull;

public final class AddressRules {
    private static final List<String> AUSTRALIAN_STATES = Arrays.asList("VIC|NT|WA|ACT|QLD|TAS|SA|NSW".split("\\|"));
    private static final Pattern AUSTRALIA_POSTCODE_PATTERN = Pattern.compile("\\d{4}");
    private static final Pattern INTERNATIONAL_POSTCODE_PATTERN = Pattern.compile("^[\\w|\\-| ]{2,10}$");

    private AddressRules() {
    }

    public static FieldValidator<String> australianStates() {
        return ignoreNull((fieldValue, vc) -> {

            boolean valid = AUSTRALIAN_STATES.contains(fieldValue);
            if (!valid) {
                vc.addError(GenericErrorMessageKeys.INVALID_ADDRESS_STATE_FORMAT);
            }
        });
    }

    public static FieldValidator<String> australianPostcode() {
        return ignoreNull((fieldValue, vc) -> {
            if (!(StringUtils.isNotBlank(fieldValue) && AUSTRALIA_POSTCODE_PATTERN.matcher(fieldValue).matches())) {
                vc.addError(GenericErrorMessageKeys.INVALID_AUSTRALIAN_POSTCODE_FORMAT);
            }
        });
    }

    public static FieldValidator<String> internationalPostcode() {
        return ignoreNull((fieldValue, vc) -> {
            if (!(StringUtils.isNotBlank(fieldValue) && INTERNATIONAL_POSTCODE_PATTERN.matcher(fieldValue).matches())) {
                vc.addError(GenericErrorMessageKeys.INVALID_INTERNATIONAL_POSTCODE_FORMAT);
            }
        });
    }


}
