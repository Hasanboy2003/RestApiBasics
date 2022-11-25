package com.epam.esm.controller.giftCertificate;

import com.epam.esm.DTO.GiftCertificateDTO;
import com.epam.esm.DTO.response.ApiResponse;
import com.epam.esm.service.giftCertificate.GiftCertificateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Gift certificate controller implement
 */

@RestController
public class GiftCertificateControllerImpl implements GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    public GiftCertificateControllerImpl(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @Override
    public ResponseEntity<ApiResponse> create(GiftCertificateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(giftCertificateService.create(dto));
    }

    @Override
    public ResponseEntity<ApiResponse> update(GiftCertificateDTO dto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(giftCertificateService.update(dto));
    }

    @Override
    public ResponseEntity<ApiResponse> delete(UUID id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(giftCertificateService.delete(id));
    }

    @Override
    public ResponseEntity<ApiResponse> get() {
        return ResponseEntity.status(HttpStatus.OK).body(giftCertificateService.get());
    }

    @Override
    public ResponseEntity<ApiResponse> getById(UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(giftCertificateService.getById(id));
    }

    @Override
    public ResponseEntity<ApiResponse> certificateFilter(String name, String description, String tag, String sortParameters) {
        return ResponseEntity.status(HttpStatus.OK).body(giftCertificateService.getFilterResult(name,description,tag,sortParameters));
    }
}
