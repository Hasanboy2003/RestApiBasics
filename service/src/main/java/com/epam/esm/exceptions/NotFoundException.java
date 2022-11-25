package com.epam.esm.exceptions;


/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * NotFound Exception
 */

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }

}
