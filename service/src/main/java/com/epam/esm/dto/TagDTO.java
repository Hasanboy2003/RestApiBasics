package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Tag DTO
 */


@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDTO {
    private UUID id;


    private String name;

    public TagDTO() {
    }

    public TagDTO(String name) {
        this.name = name;
    }

    public TagDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
