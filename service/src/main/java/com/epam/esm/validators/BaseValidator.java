package com.epam.esm.validators;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Base Validator
 */

public interface BaseValidator<T> {

    void validate(T t);

}
