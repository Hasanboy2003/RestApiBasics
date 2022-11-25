package com.epam.esm.exceptions;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * NotFound Exception
 *  Already Exists Exception
 */

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
