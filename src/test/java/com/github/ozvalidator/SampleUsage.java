package com.github.ozvalidator;

import com.github.ozvalidator.context.ValidationContext;
import com.github.ozvalidator.context.ValidationContextImpl;
import com.github.ozvalidator.model.Error;
import com.github.ozvalidator.sample.Address;
import com.github.ozvalidator.sample.Profile;
import com.github.ozvalidator.sample.ProfileAwareAggregatingValidator;
import com.github.ozvalidator.sample.validators.DomesticAddressValidator;
import com.github.ozvalidator.sample.validators.InternationalAddressValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThat;

public class SampleUsage {


    private ProfileAwareAggregatingValidator validator;
    private Map<String, String> errorMessages;
    private ValidationContextImpl validationContext;
    private Address address;

    @Before
    public void setUp() throws Exception {

        validator = new ProfileAwareAggregatingValidator();
        validator.addValidator(new DomesticAddressValidator(), profile -> profile == Profile.DOMESTIC);
        validator.addValidator(new InternationalAddressValidator(), profile -> profile == Profile.INTERNATIONAL);

        errorMessages = new HashMap<>();
        errorMessages.put("invalid.field.missing", "You have to provide the field {field_name}");
        address = new Address();
        validationContext = new ValidationContextImpl(address,
                this::getMessage,
                this::validationErrorFactory);
    }

    @Test
    public void profileAShouldEnableMandatoryAustralianStateAndPostcodeWithLengthRangeOnName() {

        validator.setProfiles(Collections.singleton(Profile.DOMESTIC));
        validator.validate(address, validationContext);
        assertThat(validationContext,hasErrorFor("state"));
    }

    private String getMessage(String code) {
        return errorMessages.get(code);
    }

    private Error validationErrorFactory(String field, String message) {
        return new com.github.ozvalidator.model.Error("INVALID_VALUE", field, message);
    }

}
