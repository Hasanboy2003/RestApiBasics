package com.epam.esm.validators;

import com.epam.esm.DAO.tag.TagDAO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.exceptions.AlreadyExistsException;
import com.epam.esm.exceptions.NotValidException;
import org.springframework.stereotype.Component;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Tag Validator
 */

@Component
public class TagValidator implements BaseValidator<TagDTO>{

    private final TagDAO tagDAO;

    public TagValidator(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public void validate(TagDTO tagDTO) {
        if(tagDTO.getName()==null)
           throw  new NotValidException("Name must not be null");
        if(tagDAO.existByName(tagDTO.getName()))
            throw new AlreadyExistsException("Name already exists");
    }

}
