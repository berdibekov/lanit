package com.berdibekov.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Car {

    @Id
    private Long id;

    @NotNull
    private String model;

    @NotNull
    private String vendor;

    @Column
    Integer horsePower;

    @ManyToOne(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "owner_id")
    @NotNull
    @JsonIdentityReference(alwaysAsId = true)
    private Person owner;
}
