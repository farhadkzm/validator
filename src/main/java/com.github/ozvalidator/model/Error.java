package com.github.ozvalidator.model;

import java.util.Objects;

public class Error {

    private String code;
    private String message;
    private String field;

    public Error(String code, String field, String message) {

        this.code = code;
        this.message = message;
        this.field = field;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Error error = (Error) o;
        return Objects.equals(code, error.code) &&
                Objects.equals(message, error.message) &&
                Objects.equals(field, error.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), code, message, field);
    }

    @Override
    public String toString() {
        return "Error{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", field='" + field + '\'' +
                '}';
    }
}
