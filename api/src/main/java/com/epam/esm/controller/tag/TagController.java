package com.epam.esm.controller.tag;

import com.epam.esm.DTO.TagDTO;
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
    ResponseEntity<?> create(@RequestBody TagDTO dto);

    /**
     * Get All Taf
     * @return ApiResponse
     */
    @GetMapping
    ResponseEntity<?> get();

    /**
     * Get Tag
     * @param id - UUID
     * @return ApiResponse
     */
    @GetMapping("/{id}")
    ResponseEntity<?> getById(@PathVariable UUID id);

    /**
     * Delete tag
     * @param id - UUID
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable UUID id);


}
