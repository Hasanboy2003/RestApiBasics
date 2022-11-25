package com.epam.esm.validators;

import com.epam.esm.DTO.GiftCertificateDTO;
import com.epam.esm.exceptions.NotValidException;
import org.springframework.stereotype.Component;

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

        if(dto.getPrice()<=0){
            throw new NotValidException("Gift certificate price must be positive!");
        }

        if(dto.getDuration()<=0){
            throw new NotValidException("Gift certificate duration must be positive!");
        }
    }

}
