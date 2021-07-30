package com.berdibekov.api.controller;

import com.berdibekov.domain.Car;
import com.berdibekov.domain.Person;
import com.berdibekov.domain.dto.CarDto;
import com.berdibekov.domain.mapper.CarMapper;
import com.berdibekov.exception.ResourceNotFoundException;
import com.berdibekov.exception.ValidationException;
import com.berdibekov.repository.CarRepository;
import com.berdibekov.repository.PersonRepository;
import com.berdibekov.service.PersonCarsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping({"/api"})
public class CarController {

    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final PersonRepository personRepository;
    private final PersonCarsService personCarsService;

    public CarController(CarRepository carRepository,
                         CarMapper carMapper,
                         PersonRepository personRepository,
                         PersonCarsService personCarsService) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
        this.personRepository = personRepository;
        this.personCarsService = personCarsService;
    }

    @ApiOperation(value = "Saves given car")
    @RequestMapping(value = "/car", method = RequestMethod.POST)
    public void saveCar(@RequestBody @Valid CarDto carDto) {
        Car car = carMapper.toEntity(carDto);
        if (carRepository.existsById(car.getId())) {
            throw new ValidationException(getExistsMessage(carDto));
        }
        Person person = personRepository.findById(Long.parseLong(carDto.getOwnerId())).
                orElseThrow(() -> new ResourceNotFoundException(getNotFoundMessage(carDto)));

        car.setOwner(person);
        carRepository.save(car);
        System.out.println();
    }

    private String getExistsMessage(CarDto carDto) {
        return "Car with id : " + carDto.getId() + " already exists.";
    }

    private String getNotFoundMessage(CarDto carDto) {
        return "Owner with id : " + carDto.getOwnerId() + " not found.";
    }
}
