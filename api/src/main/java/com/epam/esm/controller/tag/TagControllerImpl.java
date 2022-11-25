package com.epam.esm.controller.tag;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.service.tag.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Tag Controller Implement
 */

@RestController
public class TagControllerImpl implements TagController {

    private final TagService tagService;

    public TagControllerImpl(TagService tagService) {
        this.tagService = tagService;
    }


    @Override
    public ResponseEntity<?> create(TagDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.create(dto));
    }

    @Override
    public ResponseEntity<?> get() {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.get());
    }

    @Override
    public ResponseEntity<?> getById(UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.getById(id));
    }

    @Override
    public ResponseEntity<?> delete(UUID id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tagService.delete(id));
    }
}
