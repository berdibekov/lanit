package com.berdibekov.domain;

import lombok.Data;

@Data
public class Model {
    private String vendor;
    private String model;

    public String getValue(){
        return vendor + "-" + model;
    }
}
