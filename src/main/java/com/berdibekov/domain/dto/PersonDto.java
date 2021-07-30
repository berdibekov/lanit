package com.berdibekov.domain.dto;

import com.berdibekov.domain.validation.BirthDateConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

@Data
public class PersonDto {

    @NotBlank
    @Digits(integer = 20, fraction = 0)
    @JsonProperty(value = "id")
    private String id;

    @BirthDateConstraint(format = "dd.MM.yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @JsonProperty(value = "birthDate")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private String birthDate;

    @NotBlank
    @JsonProperty(value = "name")
    private String name;
}
