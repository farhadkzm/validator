package com.github.ozvalidator.sample.validators;

import com.github.ozvalidator.Validator;
import com.github.ozvalidator.context.ValidationContext;
import com.github.ozvalidator.sample.Address;

import static com.github.ozvalidator.inline.BasicFieldValidationContext.field;
import static com.github.ozvalidator.rule.AddressRules.australianPostcode;
import static com.github.ozvalidator.rule.AddressRules.australianStates;
import static com.github.ozvalidator.rule.CommonRules.mandatory;
import static com.github.ozvalidator.rule.StringRules.range;

public class DomesticAddressValidator implements Validator<Address> {
    @Override
    public void validate(Address address, ValidationContext context) {
        //Assuming all domestic addresses should have state, postcode
        field(address.getPostcode(), "postcode", context).validate(mandatory(), australianPostcode());
        field(address.getState(), "state", context).validate(mandatory(), australianStates());

        //name is required and should be within specific length range
        field(address.getName(), "name", context).validate(mandatory(), range(5, 100));

    }
}
