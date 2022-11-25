package com.epam.esm.service.giftCertificate;

import com.epam.esm.DTO.GiftCertificateDTO;
import com.epam.esm.DTO.response.ApiResponse;
import com.epam.esm.service.BaseService;

import java.util.UUID;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Gift Certificate Service
 */

public interface GiftCertificateService extends BaseService<GiftCertificateDTO> {
    ApiResponse update(GiftCertificateDTO dto);
    ApiResponse getFilterResult(String name, String description, String tag, String sortParameters);

}
