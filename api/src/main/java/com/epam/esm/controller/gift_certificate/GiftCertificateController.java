package com.epam.esm.controller.gift_certificate;


import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Gift certificate controller
 */

@RequestMapping("/api/v1/gift/certificates")
public interface GiftCertificateController {


    /**
     * Create gift certificate
     *
     * @param dto -> Gift Certificate DTO
     * @return ApiResponse
     */
    @PostMapping
    ResponseEntity<ApiResponse> create(@Valid @RequestBody GiftCertificateDTO dto);

    /**
     * Update gift certificate
     *
     * @param dto -> Gift Certificate DTO
     * @return ApiResponse
     */
    @PutMapping
    ResponseEntity<ApiResponse> update(@Valid @RequestBody GiftCertificateDTO dto);

    /**
     * Delete gift certificate
     *
     * @param id - UUID
     * @return ApiResponse
     */

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse> delete(@PathVariable UUID id);

    /**
     * Get all gift certificate
     *
     * @return ApiResponse
     */
    @GetMapping
    ResponseEntity<ApiResponse> get();

    /**
     * Get Gift Certificate
     *
     * @param id - UUID
     * @return ApiResponse
     */
    @GetMapping("/{id}")
    ResponseEntity<ApiResponse> getById(@PathVariable UUID id);

    /**
     * Filter Gift certificate
     *
     * @param name           - name
     * @param description    - description
     * @param tag            - tag
     * @param sortParameters - sortParameters
     * @return ApiResponse
     */
    @GetMapping("/filter")
    ResponseEntity<ApiResponse> certificateFilter(@RequestParam(required = false) String name, @RequestParam(required = false) String description,
                                                  @RequestParam(required = false) String tag, @RequestParam(required = false) String sortParameters);

}
