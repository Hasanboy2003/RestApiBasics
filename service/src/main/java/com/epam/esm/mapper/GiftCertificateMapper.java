package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Gift Certificate Mapper
 */

@Component
public class GiftCertificateMapper implements BaseMapper<GiftCertificate, GiftCertificateDTO>{

    private final TagMapper tagMapper;

    public GiftCertificateMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    public GiftCertificate fromDTOToEntity(GiftCertificateDTO dto) {
        GiftCertificate giftCertificate = new GiftCertificate();

        giftCertificate.setId(dto.getId());
        giftCertificate.setName(dto.getName());
        giftCertificate.setDescription(dto.getDescription());
        giftCertificate.setPrice(dto.getPrice());
        giftCertificate.setDuration(dto.getDuration());

        return giftCertificate;
    }

    @Override
    public GiftCertificateDTO fromEntityToDTO(GiftCertificate entity) {
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();

        giftCertificateDTO.setId(entity.getId());
        giftCertificateDTO.setName(entity.getName());
        giftCertificateDTO.setDescription(entity.getDescription());
        giftCertificateDTO.setPrice(entity.getPrice());
        giftCertificateDTO.setDuration(entity.getDuration());
        giftCertificateDTO.setCreateDate(entity.getCreateDate());
        giftCertificateDTO.setLastUpdateDate(entity.getLastUpdateDate());
        giftCertificateDTO.setTagList(entity.getTags().stream().map(tagMapper::fromEntityToDTO).collect(Collectors.toList()));
        return giftCertificateDTO;
    }

}
