package com.epam.esm.exceptions;

import com.epam.esm.DTO.response.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Global Exception Handler
 */


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFoundException(NotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrorMessage(e.getMessage());
        errorDTO.setErrorCode(ErrorCodeStatus.NOT_FOUND.code);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleAlreadyExistException(AlreadyExistsException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrorMessage(e.getMessage());
        errorDTO.setErrorCode(ErrorCodeStatus.CONFLICT.code);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDTO);
    }

    @ExceptionHandler(NotValidException.class)
    public ResponseEntity<ErrorDTO> handleNotValidException(NotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrorMessage(e.getMessage());
        errorDTO.setErrorCode(ErrorCodeStatus.BAD_REQUEST.code);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }


}
