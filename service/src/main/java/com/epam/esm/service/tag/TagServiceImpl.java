package com.epam.esm.service.tag;

import com.epam.esm.DAO.giftCertificate.GiftCertificateDAO;
import com.epam.esm.DAO.tag.TagDAO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.DTO.response.ApiResponse;
import com.epam.esm.DTO.response.ResponseMessage;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.AlreadyExistsException;
import com.epam.esm.exceptions.NotFoundException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.validators.TagValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Tag Service Implement
 */

@Service
public class TagServiceImpl implements TagService {
    private final TagDAO tagDAO;
    private final GiftCertificateDAO giftCertificateDAO;
    private final TagMapper tagMapper;

    private final TagValidator tagValidator;

    public TagServiceImpl(TagDAO tagDAO, GiftCertificateDAO giftCertificateDAO, TagMapper tagMapper, TagValidator tagValidator) {
        this.tagDAO = tagDAO;
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagMapper = tagMapper;
        this.tagValidator = tagValidator;
    }

    @Override
    public ApiResponse create(TagDTO dto) {
        tagValidator.validate(dto);

        if(tagDAO.existByName(dto.getName()))
            throw new AlreadyExistsException("Tag already exist (name="+dto.getName()+")");

        Tag tag = tagMapper.fromDTOToEntity(dto);
        tag.setId(UUID.randomUUID());
        tagDAO.save(tag);
        return new ApiResponse(ResponseMessage.CREATED.getValue());
    }

    @Override
    public ApiResponse get() {
        List<TagDTO> tagDTOList = tagDAO.findAll().stream().map(tagMapper::fromEntityToDTO).collect(Collectors.toList());
        return new ApiResponse(ResponseMessage.READ.getValue(), tagDTOList);
    }

    @Override
    public ApiResponse getById(UUID id) {
        if (!tagDAO.existsById(id))
            throw new NotFoundException("Requested tag not found (id=" + id + ")");

        TagDTO tagDTO = tagMapper.fromEntityToDTO(tagDAO.getById(id));
        return new ApiResponse(ResponseMessage.READ.getValue(), tagDTO);
    }



    @Override
    public ApiResponse delete(UUID id) {
        if (!tagDAO.existsById(id))
            throw new NotFoundException("Requested tag not found (id=" + id + ")");

        tagDAO.deleteById(id);
        return new ApiResponse(ResponseMessage.DELETED.getValue());
    }
}
