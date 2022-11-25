package com.epam.esm.service.giftCertificate;

import com.epam.esm.DAO.giftCertificate.GiftCertificateDAO;
import com.epam.esm.DAO.tag.TagDAO;
import com.epam.esm.DTO.GiftCertificateDTO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.DTO.response.ApiResponse;
import com.epam.esm.DTO.response.ResponseMessage;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.AlreadyExistsException;
import com.epam.esm.exceptions.NotFoundException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.validators.GiftCertificateValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Gift Certificate Service Implement
 */

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateValidator certificateValidator;
    private final GiftCertificateDAO giftCertificateDAO;
    private final TagDAO tagDAO;
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagMapper tagMapper;

    public GiftCertificateServiceImpl(GiftCertificateValidator certificateValidator, GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO, GiftCertificateMapper giftCertificateMapper, TagMapper tagMapper) {
        this.certificateValidator = certificateValidator;
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
        this.giftCertificateMapper = giftCertificateMapper;
        this.tagMapper = tagMapper;
    }


    @Override
    public ApiResponse create(GiftCertificateDTO dto) {
        certificateValidator.validate(dto);
        if (giftCertificateDAO.existByName(dto.getName()))
            throw new AlreadyExistsException("Gift certificate already exist (name+" + dto.getName() + ")");

        GiftCertificate giftCertificate = giftCertificateMapper.fromDTOToEntity(dto);

        giftCertificate.setId(UUID.randomUUID());
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        giftCertificateDAO.save(giftCertificate);

        if (dto.getTagList() != null && !dto.getTagList().isEmpty()) {
            connectGiftCertificateWithTags(dto.getId(), dto.getTagList());
        }
        return new ApiResponse(ResponseMessage.CREATED.getValue());
    }


    @Override
    public ApiResponse update(GiftCertificateDTO dto) {

        if (!giftCertificateDAO.existsById(dto.getId()))
            throw new NotFoundException("Gift certificate not found (id=" + dto.getId()+")");

        if (giftCertificateDAO.existByNameAndIdNotEquals(dto.getId(),dto.getName()))
            throw new AlreadyExistsException("Gift certificate already exist (name=" + dto.getName() + ")");

        GiftCertificate giftCertificate = giftCertificateDAO.getById(dto.getId());

        updateEntity(dto, giftCertificate);
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        giftCertificateDAO.update(giftCertificate);

        if (dto.getTagList() != null) {
            giftCertificateDAO.deleteConnection(dto.getId());
            connectGiftCertificateWithTags(dto.getId(), dto.getTagList());
        }

        return new ApiResponse(ResponseMessage.UPDATED.getValue());
    }

    @Override
    public ApiResponse delete(UUID id) {
        if (!giftCertificateDAO.existsById(id))
            throw new NotFoundException("Gift certificate not found (id=" + id+")");

        giftCertificateDAO.deleteById(id);
        return new ApiResponse(ResponseMessage.DELETED.getValue());
    }


    @Override
    public ApiResponse get() {
        List<GiftCertificateDTO> giftCertificateDTOList = giftCertificateDAO.findAll()
                .stream()
                .map(giftCertificateMapper::fromEntityToDTO)
                .collect(Collectors.toList());

        return new ApiResponse(ResponseMessage.READ.getValue(),addTagDtoList(giftCertificateDTOList));
    }

    @Override
    public ApiResponse getById(UUID id) {
        if (!giftCertificateDAO.existsById(id))
            throw new NotFoundException("Gift certificate not found (id=" + id+")");
        return new ApiResponse(ResponseMessage.READ.getValue(),giftCertificateMapper.fromEntityToDTO(giftCertificateDAO.getById(id)));
    }

    @Override
    public ApiResponse getFilterResult(String name, String description, String tag, String sortParameters) {
        List<GiftCertificateDTO> giftCertificateDTOList = giftCertificateDAO.searchByFilters(name, description, tag, sortParameters)
                .stream()
                .map(giftCertificateMapper::fromEntityToDTO)
                .collect(Collectors.toList());

        return new ApiResponse(ResponseMessage.READ.getValue(),addTagDtoList(giftCertificateDTOList));
    }

    public void connectGiftCertificateWithTags(UUID giftCertificateId, List<TagDTO> tagList) {

        for (TagDTO tagDTO : tagList) {
            UUID tagId = tagDTO.getId();

            if (!tagDAO.existByName(tagDTO.getName())) {

                Tag tag = new Tag(UUID.randomUUID(), tagDTO.getName());
                tagDAO.save(tag);

                tagId = tag.getId();
            }

            giftCertificateDAO.connectWithTag(giftCertificateId, tagId);
        }
    }

    private List<GiftCertificateDTO> addTagDtoList(List<GiftCertificateDTO> giftCertificateDTOList) {
        for (GiftCertificateDTO dto : giftCertificateDTOList) {
            List<TagDTO> tagDTOList = tagDAO.getByGiftCertificateId(dto.getId()).stream().map(tagMapper::fromEntityToDTO).collect(Collectors.toList());
            dto.setTagList(tagDTOList);
        }
        return giftCertificateDTOList;
    }

    private void updateEntity(GiftCertificateDTO dto,GiftCertificate certificate) {
        certificate.setName(dto.getName()==null? certificate.getName() : dto.getName());
        certificate.setDescription(dto.getDescription()==null? certificate.getDescription() : dto.getDescription());
        certificate.setPrice(dto.getPrice()==null? certificate.getPrice() : dto.getPrice());
        certificate.setDuration(dto.getDuration()==null?certificate.getDuration():dto.getDuration());
    }

}
