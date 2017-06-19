package com.github.ozvalidator;


import java.util.Objects;

public final class MessageParameter {
    private final String name;
    private final String value;

    private MessageParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static MessageParameter param(String name, Object value) {
        return new MessageParameter(name, value.toString());
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getMessageKey() {
        return String.format("{%s}", name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageParameter that = (MessageParameter) o;
        return Objects.equals(name, that.name)
                && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
