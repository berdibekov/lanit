package com.berdibekov.domain.dto;

import com.berdibekov.domain.Car;
import com.berdibekov.domain.Person;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PersonWithCars extends PersonDto {
    @NotNull
    @JsonProperty(value = "cars")
    private List<CarDto> carDTOs;

    public PersonWithCars(PersonDto person, Collection<CarDto> cars) {
        super.setId(person.getId());
        super.setName(person.getName());
        super.setBirthDate(person.getBirthDate());
        this.carDTOs = new ArrayList<>(cars);
    }
}
