package com.epam.esm.validators;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exceptions.NotValidException;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isNumeric;

@Component
public class TagValidator implements BaseValidator<TagDTO> {

    @Override
    public void validate(TagDTO dto) {
        if(dto.getName()==null)
            throw new NotValidException("Tag name must not be null!");

        if(dto.getName().isEmpty())
            throw  new NotValidException("Tag name mus not be empty!");

        if(isNumeric(dto.getName()))
            throw new NotValidException("Tag name must not be digit");
    }
}
