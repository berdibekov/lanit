package com.berdibekov.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Statistics {

    @JsonProperty(value = "personCount")
    private Long personCount;

    @JsonProperty(value = "carCount")
    private Long carCount;

    @JsonProperty(value = "uniqueVendorCount")
    private Long uniqueVendorCount;
}
