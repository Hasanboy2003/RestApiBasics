package com.epam.esm.entity;

import java.util.UUID;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Tag Entity
 */

public class Tag extends BaseEntity{

    public Tag(UUID id, String name) {
        super(id, name);
    }


    public Tag() {
    }

}
