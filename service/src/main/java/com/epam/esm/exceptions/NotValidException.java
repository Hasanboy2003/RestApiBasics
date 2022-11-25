package com.epam.esm.exceptions;


/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Not Valid Exception
 */


public class NotValidException extends RuntimeException {
    public NotValidException(String message) {
        super(message);
    }
}
