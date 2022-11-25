package com.epam.esm.mapper;

import com.epam.esm.entity.Tag;
import com.epam.esm.DTO.TagDTO;
import org.springframework.stereotype.Component;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Tag Mapper
 */

@Component
public class TagMapper implements BaseMapper<Tag, TagDTO>{
    @Override
    public Tag fromDTOToEntity(TagDTO tagDTO) {
        Tag tag = new Tag();

        tag.setId(tagDTO.getId());
        tag.setName(tagDTO.getName());

        return tag;
    }

    @Override
    public TagDTO fromEntityToDTO(Tag tag) {
        TagDTO tagDTO = new TagDTO();

        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());

        return tagDTO;
    }
}
