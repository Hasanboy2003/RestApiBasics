package com.epam.esm.controller.tag;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.DTO.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Tag Controller
 */

@RequestMapping("/api/v1/tag")
public interface TagController {


    /**
     * Create tag
     * @param dto - TagDto
     * @return ApiResponse
     */
    @PostMapping
    ResponseEntity<ApiResponse> create(@RequestBody TagDTO dto);

    /**
     * Get All Taf
     * @return ApiResponse
     */
    @GetMapping
    ResponseEntity<ApiResponse> get();

    /**
     * Get Tag
     * @param id - UUID
     * @return ApiResponse
     */
    @GetMapping("/{id}")
    ResponseEntity<ApiResponse> getById(@PathVariable UUID id);

    /**
     * Delete tag
     * @param id - UUID
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse> delete(@PathVariable UUID id);


}
