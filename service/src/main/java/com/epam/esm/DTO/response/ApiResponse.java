package com.epam.esm.DTO.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Api Response
 */


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private String message;
    private Object data;

    public ApiResponse() {
    }

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
