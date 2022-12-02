package com.epam.esm.service;

import com.epam.esm.dto.response.ApiResponse;

import java.util.UUID;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Base Service
 */

public interface BaseService <D>{

    ApiResponse create(D dto);

    ApiResponse get();

    ApiResponse getById(UUID id);

    ApiResponse delete(UUID id);

}
