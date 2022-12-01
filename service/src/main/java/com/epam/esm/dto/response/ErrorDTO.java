package com.epam.esm.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Error DTO
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO {
    private String errorMessage;
    private int errorCode;

    public ErrorDTO() {
    }

    public ErrorDTO(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
