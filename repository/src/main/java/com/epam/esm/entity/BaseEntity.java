package com.epam.esm.entity;

import java.util.UUID;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Base Entity
 */

public abstract class BaseEntity {
    private UUID id;
    private String name;

    protected BaseEntity(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    protected BaseEntity() {
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
