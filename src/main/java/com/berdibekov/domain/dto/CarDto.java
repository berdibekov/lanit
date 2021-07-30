package com.berdibekov.domain.dto;

import com.berdibekov.domain.validation.VendorFormatConstraint;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CarDto {

    @Digits(integer = 20, fraction = 0)
    @JsonProperty(value = "id")
    @NotBlank
    private String id;

    @NotNull
    @VendorFormatConstraint
    @JsonProperty(value = "model")
    private String model;

    @NotNull
    @Min(1)
    @Digits(integer = 10, fraction = 0)
    @JsonProperty(value = "horsePower")
    String horsePower;

    @NotBlank
    @Digits(integer = 20, fraction = 0)
    @JsonProperty(value = "ownerId")
    private String ownerId;
}
