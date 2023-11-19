package com.isat46.isaback.dto.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isat46.isaback.model.Company;

public class CompanySearchDto {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("averageRating")
    private double averageRating;
    @JsonProperty("addressId")
    private int addressId;

    public CompanySearchDto(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.description = company.getDescription();
        this.averageRating = company.getAverageRating();
        this.addressId = company.getAddress() != null ? company.getAddress().getId() : 0;
    }
}
