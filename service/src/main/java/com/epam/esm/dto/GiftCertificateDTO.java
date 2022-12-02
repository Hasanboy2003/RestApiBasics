package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.time.LocalDateTime;
import java.util.UUID;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftCertificateDTO {

    private UUID id;
    private String name;

    private String description;
    private Double price;
    private Integer duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private List<TagDTO> tagList;

    public GiftCertificateDTO() {
    }


    public GiftCertificateDTO(UUID id, String name, String description, Double price, Integer duration, LocalDateTime createDate, LocalDateTime lastUpdateDate, List<TagDTO> tagList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tagList = tagList;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }


    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<TagDTO> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagDTO> tagList) {
        this.tagList = tagList;
    }

}
