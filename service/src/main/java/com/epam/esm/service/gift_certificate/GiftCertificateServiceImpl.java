package com.epam.esm.service.gift_certificate;

import com.epam.esm.dao.gift_certificate.GiftCertificateDAO;
import com.epam.esm.dao.tag.TagDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.response.ApiResponse;
import com.epam.esm.dto.response.ResponseMessage;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.AlreadyExistsException;
import com.epam.esm.exceptions.NotFoundException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.validators.GiftCertificateValidator;
import com.epam.esm.validators.TagValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final TagValidator tagValidator;
    private final GiftCertificateDAO giftCertificateDAO;
    private final TagDAO tagDAO;
    private final GiftCertificateMapper giftCertificateMapper;

    private static final String NOT_FOUND_MESSAGE = "Gift certificate not found (id=";
    private static final String ALREADY_EXIST_MESSAGE = "Gift certificate already exist (name=";


    public GiftCertificateServiceImpl(GiftCertificateValidator certificateValidator, TagValidator tagValidator, GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO, GiftCertificateMapper giftCertificateMapper) {
        this.certificateValidator = certificateValidator;
        this.tagValidator = tagValidator;
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
        this.giftCertificateMapper = giftCertificateMapper;
    }

    @Transactional
    @Override
    public ApiResponse create(GiftCertificateDTO dto) {
        certificateValidator.validate(dto);

        if (giftCertificateDAO.existByName(dto.getName()))
            throw new AlreadyExistsException(ALREADY_EXIST_MESSAGE + dto.getName() + ")");

        GiftCertificate giftCertificate = giftCertificateMapper.fromDTOToEntity(dto);

        giftCertificate.setId(UUID.randomUUID());
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        giftCertificateDAO.save(giftCertificate);

        if (dto.getTagList() != null && !dto.getTagList().isEmpty()) {
            dto.getTagList().forEach(tagValidator::validate);
            connectGiftCertificateWithTags(giftCertificate.getId(), dto.getTagList());
        }
        return new ApiResponse(ResponseMessage.CREATED.getValue());
    }


    @Transactional
    @Override
    public ApiResponse update(GiftCertificateDTO dto) {

        if (!giftCertificateDAO.existsById(dto.getId()))
            throw new NotFoundException(NOT_FOUND_MESSAGE + dto.getId() + ")");

        if (giftCertificateDAO.existByNameAndIdNotEquals(dto.getId(), dto.getName()))
            throw new AlreadyExistsException(ALREADY_EXIST_MESSAGE + dto.getName() + ")");

        GiftCertificate giftCertificate = giftCertificateDAO.getById(dto.getId());

        updateEntity(dto, giftCertificate);
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        giftCertificateDAO.update(giftCertificate);

        if (dto.getTagList() != null && !dto.getTagList().isEmpty()) {
            dto.getTagList().forEach(tagValidator::validate);
            giftCertificateDAO.deleteConnection(dto.getId());
            connectGiftCertificateWithTags(dto.getId(), dto.getTagList());
        }

        return new ApiResponse(ResponseMessage.UPDATED.getValue());
    }

    @Override
    public ApiResponse delete(UUID id) {
        if (!giftCertificateDAO.existsById(id))
            throw new NotFoundException(NOT_FOUND_MESSAGE + id + ")");

        giftCertificateDAO.deleteConnection(id);
        giftCertificateDAO.deleteById(id);
        return new ApiResponse(ResponseMessage.DELETED.getValue());
    }


    @Override
    public ApiResponse get() {
        List<GiftCertificate> giftCertificateList = giftCertificateDAO.findAll();
        for (GiftCertificate certificate : giftCertificateList) {
            List<Tag> tagList = tagDAO.getByGiftCertificateId(certificate.getId());
            certificate.setTags(tagList);
        }

        List<GiftCertificateDTO> giftCertificateDTOList = giftCertificateList.stream()
                .map(giftCertificateMapper::fromEntityToDTO)
                .collect(Collectors.toList());
        return new ApiResponse(ResponseMessage.READ.getValue(),giftCertificateDTOList);
    }

    @Override
    public ApiResponse getById(UUID id) {
        if (!giftCertificateDAO.existsById(id))
            throw new NotFoundException(NOT_FOUND_MESSAGE + id + ")");

        GiftCertificate giftCertificate = giftCertificateDAO.getById(id);
        List<Tag> tagList = tagDAO.getByGiftCertificateId(giftCertificate.getId());
        giftCertificate.setTags(tagList);
        return new ApiResponse(ResponseMessage.READ.getValue(), giftCertificateMapper.fromEntityToDTO(giftCertificate));
    }

    @Override
    public ApiResponse getFilterResult(String name, String description, String tag, String sortParameters) {
        List<GiftCertificate> giftCertificateList = giftCertificateDAO.searchByFilters(name, description, tag, sortParameters);

        for (GiftCertificate certificate : giftCertificateList) {
            List<Tag> tags = tagDAO.getByGiftCertificateId(certificate.getId());
            certificate.setTags(tags);
        }

        List<GiftCertificateDTO> giftCertificateDTOList = giftCertificateList.stream()
                .map(giftCertificateMapper::fromEntityToDTO)
                .collect(Collectors.toList());

        return new ApiResponse(ResponseMessage.READ.getValue(),giftCertificateDTOList);
    }

    public void connectGiftCertificateWithTags(UUID giftCertificateId, List<TagDTO> tagList) {

        for (TagDTO tagDTO : tagList) {
            Tag tag = tagDAO.getByName(tagDTO.getName());

            if (tag==null) {
                tag = new Tag(UUID.randomUUID(), tagDTO.getName());
                tagDAO.save(tag);
            }

            giftCertificateDAO.connectWithTag(giftCertificateId,tag.getId());
        }
    }


    private void updateEntity(GiftCertificateDTO dto, GiftCertificate certificate) {
        certificate.setName(dto.getName() == null ? certificate.getName() : dto.getName());
        certificate.setDescription(dto.getDescription() == null ? certificate.getDescription() : dto.getDescription());
        certificate.setPrice(dto.getPrice() == null ? certificate.getPrice() : dto.getPrice());
        certificate.setDuration(dto.getDuration() == null ? certificate.getDuration() : dto.getDuration());
    }

}
