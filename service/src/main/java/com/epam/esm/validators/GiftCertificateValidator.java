package com.epam.esm.validators;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exceptions.NotValidException;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Gift Certificate Validator
 */

@Component
public class GiftCertificateValidator implements BaseValidator<GiftCertificateDTO>{

    @Override
    public void validate(GiftCertificateDTO dto) {
        if(dto.getName()==null){
            throw new NotValidException("Gift certificate name must not be null!");
        }

        if(dto.getName().isEmpty())
            throw  new NotValidException("Gift certificate name mus not be empty!");

        if(isNumeric(dto.getName()))
            throw new NotValidException("Gift certificate name must not be digit");

        if(dto.getPrice()<=0){
            throw new NotValidException("Gift certificate price must be positive!");
        }

        if(dto.getDuration()<=0){
            throw new NotValidException("Gift certificate duration must be positive!");
        }
    }

}
