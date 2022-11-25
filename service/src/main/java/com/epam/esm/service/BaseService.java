package com.epam.esm.service;

import com.epam.esm.DTO.response.ApiResponse;

import java.util.UUID;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Base Service
 */

public interface BaseService <DT>{

    ApiResponse create(DT dto);

    ApiResponse get();

    ApiResponse getById(UUID id);

    ApiResponse delete(UUID id);

}
