package com.epam.esm.DTO.response;


/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Response Message
 */

public enum ResponseMessage {
    CREATED("CREATED SUCCESSFULLY"),
    UPDATED("UPDATED SUCCESSFULLY"),
    DELETED("DELETED SUCCESSFULLY"),
    READ("READ SUCCESSFULLY");

    private final String value;

    ResponseMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
