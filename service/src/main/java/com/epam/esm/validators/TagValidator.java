package com.epam.esm.validators;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exceptions.NotValidException;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Tag Validator
 */

@Component
public class TagValidator implements BaseValidator<TagDTO>{




    @Override
    public void validate(TagDTO tagDTO) {
        if(tagDTO.getName()==null)
           throw  new NotValidException("Tag name must not be null");

        if(tagDTO.getName().isEmpty())
            throw new NotValidException("Tag name must not be empty");

        if(isNumeric(tagDTO.getName()))
            throw new NotValidException("Tag name must not be digit");
    }

}
