package com.epam.esm.exceptions;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Error Code Status
 */

public enum ErrorCodeStatus {
    NOT_FOUND(40401), CONFLICT(40902), BAD_REQUEST(40003);

    final int code;

    ErrorCodeStatus(int code) {
        this.code=code;
    }

    public int getCode() {
        return code;
    }
}
