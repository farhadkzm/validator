package com.github.ozvalidator.sample;


import com.github.ozvalidator.context.ValidationContext;
import com.github.ozvalidator.Validator;

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public class ProfileAwareAggregatingValidator implements Validator<Address> {

    private final List<Map.Entry<Validator<Address>, Predicate<Profile>>> validators = new LinkedList<>();
    private Set<Profile> profiles;

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
    }

    public void addValidator(Validator<Address> validator, Predicate<Profile> activator) {
        validators.add(new SimpleEntry<>(validator, activator));
    }

    @Override
    public void validate(Address address, ValidationContext context) {
        validators
                .stream()
                .filter(validatorPredicateEntry -> profiles.stream().anyMatch(validatorPredicateEntry.getValue()))
                .map(Map.Entry::getKey)
                .forEach(validator -> validator.validate(address, context));

    }
}
