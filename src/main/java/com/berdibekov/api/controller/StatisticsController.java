package com.berdibekov.api.controller;

import com.berdibekov.domain.dto.Statistics;
import com.berdibekov.repository.CarRepository;
import com.berdibekov.repository.PersonRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StatisticsController {

    private final PersonRepository personRepository;
    private final CarRepository carRepository;
    public StatisticsController(PersonRepository personRepository,
                                CarRepository carRepository) {
        this.personRepository = personRepository;
        this.carRepository = carRepository;
    }

    @ApiOperation(value = "Get statistics.")
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public ResponseEntity<Object> savePerson() {
        Statistics statistics = new Statistics();
        Long personCount = personRepository.count();
        Long carCount = carRepository.count();
        Long uniqueVendorCount = carRepository.countDistinctVendors().orElse(0L);
        statistics.setPersonCount(personCount);
        statistics.setCarCount(carCount);
        statistics.setUniqueVendorCount(uniqueVendorCount);
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }
}
