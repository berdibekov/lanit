package com.berdibekov.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Person {
    @Id
    @NotNull
    private Long id;

    @Column
    @NotNull(message = "${NotNull.Person.name}")
    private String name;

    @Column
    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Past
    private Date birthDate;
}
