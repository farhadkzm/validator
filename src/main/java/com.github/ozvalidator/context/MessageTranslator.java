package com.github.ozvalidator.context;


public interface MessageTranslator {

    /**
     *
     * @param code usually represents a key in the properties file containing error messages
     * @return the value of the given key. This might include placeholders like {field_name},{min}, etc.
     */
    String getMessage(String code);
}
