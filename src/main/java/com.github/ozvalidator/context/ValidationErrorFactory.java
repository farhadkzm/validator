package com.github.ozvalidator.context;

import com.github.ozvalidator.model.Error;

public interface ValidationErrorFactory {

    /**
     *
     * @param field field name. Usually is a field path. E.x.: address.country
     * @param errorMessage
     * @return
     */
    Error create(String field, String errorMessage);
}
