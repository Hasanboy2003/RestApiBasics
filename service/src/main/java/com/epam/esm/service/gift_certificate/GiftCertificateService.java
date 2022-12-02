package com.epam.esm.service.gift_certificate;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.response.ApiResponse;
import com.epam.esm.service.BaseService;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Gift Certificate Service
 */

public interface GiftCertificateService extends BaseService<GiftCertificateDTO> {
    ApiResponse update(GiftCertificateDTO dto);
    ApiResponse getFilterResult(String name, String description, String tag, String sortParameters);

}
